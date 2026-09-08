package eu.qcloud.file;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemService;
import eu.qcloud.node.Node;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.sampleType.SampleTypeService;

/**
 * Tracks every raw file the pipeline sees through its received -> processing
 * -> processed/error lifecycle. Complements {@link FileService}, which only
 * ever sees files that made it all the way to a successful QC-metric insert.
 */
@Service
public class PipeLineFileService {

	@Autowired
	private PipeLineFileRepository pipeLineFileRepository;

	@Autowired
	private SampleTypeService sampleTypeService;

	@Autowired
	private LabSystemService labSystemService;

	/**
	 * Called by trigger.sh as soon as it moves a matched raw file into its run
	 * folder and launches the pipeline. Idempotent: if a row for this checksum
	 * already exists (e.g. a retried call), it is returned unchanged rather than
	 * duplicated or overwritten.
	 */
	public PipeLineFile markReceived(PipeLineFile incoming, String sampleTypeQCCV, UUID labSystemApiKey) {
		Optional<PipeLineFile> existing = pipeLineFileRepository.findByChecksum(incoming.getChecksum());
		if (existing.isPresent()) {
			return existing.get();
		}

		SampleType st = sampleTypeQCCV == null ? null : sampleTypeService.getSampleTypeByQCCV(sampleTypeQCCV);

		Optional<LabSystem> ls = labSystemApiKey == null ? Optional.empty()
				: labSystemService.findSystemByApiKey(labSystemApiKey);

		if (st != null) {
			incoming.setSampleType(st);
		}
		if (ls.isPresent()) {
			incoming.setLabSystem(ls.get());
		}

		Date now = new Date();
		incoming.setStatus(PipelineFileStatus.PROCESSING);
		incoming.setReceivedDate(now);
		incoming.setUpdatedDate(now);

		return pipeLineFileRepository.save(incoming);
	}

	/**
	 * Called by the pipeline itself right as it starts actually running this
	 * file (not by trigger.sh, which only marks it received). Idempotent -
	 * only ever sets processingStartedDate once, so a retried/duplicate call
	 * never resets it.
	 */
	public PipeLineFile markProcessingStarted(String checksum) {
		PipeLineFile pf = pipeLineFileRepository.findByChecksum(checksum).orElseGet(() -> {
			PipeLineFile fresh = new PipeLineFile();
			fresh.setChecksum(checksum);
			fresh.setStatus(PipelineFileStatus.PROCESSING);
			fresh.setReceivedDate(new Date());
			return fresh;
		});
		if (pf.getProcessingStartedDate() == null) {
			pf.setProcessingStartedDate(new Date());
		}
		return pipeLineFileRepository.save(pf);
	}

	/**
	 * Called at the same point report_qcloud.nf inserts the successful, final
	 * {@link File} row. Creates the tracking row if one is missing (e.g. the
	 * "received" call never happened for some reason) so PROCESSED files are
	 * never silently absent from the dashboard.
	 */
	public PipeLineFile markProcessed(String checksum, String filename) {
		PipeLineFile pf = pipeLineFileRepository.findByChecksum(checksum).orElseGet(() -> {
			PipeLineFile fresh = new PipeLineFile();
			fresh.setChecksum(checksum);
			fresh.setFilename(filename);
			fresh.setReceivedDate(new Date());
			return fresh;
		});
		pf.setStatus(PipelineFileStatus.PROCESSED);
		pf.setUpdatedDate(new Date());
		return pipeLineFileRepository.save(pf);
	}

	/**
	 * Called by atlas_checker.sh once it has classified a pipeline failure,
	 * reusing the diagnostic fields it already parses for its Slack alert.
	 * Creates the tracking row if the "received" call is missing (e.g. the
	 * failure happened before that hook ran).
	 */
	public PipeLineFile markError(PipeLineFile errorInfo) {
		PipeLineFile pf = pipeLineFileRepository.findByChecksum(errorInfo.getChecksum()).orElseGet(() -> {
			PipeLineFile fresh = new PipeLineFile();
			fresh.setChecksum(errorInfo.getChecksum());
			fresh.setFilename(errorInfo.getFilename());
			fresh.setReceivedDate(new Date());
			return fresh;
		});
		// updatedDate must only be stamped on the FIRST transition into ERROR:
		// the pipeline's own onError hook calls this immediately (accurate
		// "Time to process"), and atlas_checker.sh's cron calls it again
		// later with a richer, log-classified reason - that second call must
		// enrich the diagnostic fields without re-stamping the timestamp,
		// or every re-classification would silently inflate the duration
		// shown on the dashboard.
		boolean firstTimeError = pf.getStatus() != PipelineFileStatus.ERROR;
		pf.setStatus(PipelineFileStatus.ERROR);
		pf.setSample(errorInfo.getSample());
		pf.setQcCode(errorInfo.getQcCode());
		pf.setAcquisitionDate(errorInfo.getAcquisitionDate());
		pf.setInstrumentUuid(errorInfo.getInstrumentUuid());
		pf.setDatabaseName(errorInfo.getDatabaseName());
		pf.setSizeMb(errorInfo.getSizeMb());
		pf.setErrorReason(errorInfo.getErrorReason());
		if (firstTimeError) {
			pf.setUpdatedDate(new Date());
		}
		return pipeLineFileRepository.save(pf);
	}

	public Page<PipeLineFile> getDashboard(Node node, Pageable page, String filename) {
		List<LabSystem> ls = labSystemService.findAllByNode(node.getId());
		if (filename == null || filename.isEmpty()) {
			return pipeLineFileRepository.findByLabSystemInOrderByIdDesc(ls, page);
		}
		return pipeLineFileRepository.findByFilenameContainingAndLabSystemInOrderByIdDesc(filename, ls, page);
	}

	public PipeLineFile getByChecksum(String checksum) {
		return pipeLineFileRepository.findByChecksum(checksum)
				.orElseThrow(() -> new DataRetrievalFailureException("Pipeline file not found: " + checksum));
	}

}
