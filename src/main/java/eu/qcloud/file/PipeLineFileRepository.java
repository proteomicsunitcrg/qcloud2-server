package eu.qcloud.file;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.labsystem.LabSystem;

@Repository
public interface PipeLineFileRepository extends JpaRepository<PipeLineFile, Long> {

	public Optional<PipeLineFile> findByChecksum(String checksum);

	public Page<PipeLineFile> findByLabSystemInOrderByIdDesc(List<LabSystem> ls, Pageable page);

	public Page<PipeLineFile> findByFilenameContainingAndLabSystemInOrderByIdDesc(String filename, List<LabSystem> ls,
			Pageable page);

	public Page<PipeLineFile> findByLabSystemApiKeyAndLabSystemInOrderByIdDesc(UUID labSystemApiKey,
			List<LabSystem> ls, Pageable page);

	public Page<PipeLineFile> findByFilenameContainingAndLabSystemApiKeyAndLabSystemInOrderByIdDesc(String filename,
			UUID labSystemApiKey, List<LabSystem> ls, Pageable page);

	// "First10" variant for PipeLineFileService#hasError: bounds the query at
	// the DB level (LIMIT 10) so the banner reflects only recent activity,
	// mirroring what the first page of the Pipeline Status dashboard shows.
	// Without the cap, an old ERROR row would keep the banner alive forever,
	// even though the offending file was reprocessed long ago.
	public List<PipeLineFile> findFirst10ByLabSystemOrderByIdDesc(LabSystem labSystem);

}
