package eu.qcloud.file;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eu.qcloud.data.Data;
import eu.qcloud.data.DataRepository;
import eu.qcloud.file.FileRepository.OnlyChecksum;
import eu.qcloud.file.FileRepository.OnlySmalls;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.guideset.automatic.AutomaticGuideSetRepository;
import eu.qcloud.intranet.file.IntranetService;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemService;
import eu.qcloud.node.Node;
import eu.qcloud.sampleComposition.SampleComposition;
import eu.qcloud.sampleComposition.SampleCompositionRepository;
import eu.qcloud.sampleComposition.SampleCompositionService;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.sampleType.SampleTypeRepository;
import eu.qcloud.sampleType.SampleTypeService;
import eu.qcloud.security.model.User;
import eu.qcloud.websocket.WebSocketService;

import eu.qcloud.sampleComposition.SampleCompositionRepository.PeptidesFromSample;

/**
 * File service
 *
 * @author dmancera
 *
 */
@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private SampleTypeService sampleTypeService;

    @Autowired
    private LabSystemService labSystemService;

    @Autowired
    private AutomaticGuideSetRepository guideSetRepository;

    @Autowired
    private SampleTypeRepository sampleTypeRepository;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private IntranetService intranetService;

    @Autowired
    private SampleCompositionService sampleCompositionService;

    @Autowired
    private SampleCompositionRepository sampleCompositionRepository;

    @Autowired
    private DataRepository dataRepository;

    private final Log logger = LogFactory.getLog(this.getClass());

    public File addNewFile(File file) {

        GuideSet guideSet = guideSetRepository.findByIsActiveTrue().get(0);

        file.setGuideSet(guideSet);
        return fileRepository.save(file);
    }

    public List<File> getAllFiles() {
        List<File> files = new ArrayList<>();
        fileRepository.findAll().forEach(files::add);
        return files;
    }

    public OnlySmalls getFileById(Long idFile) {
        return fileRepository.findFileById(idFile);
    }

    public File addSpecial(File file, Long labSystemId, Long sampleTypeId) {
        SampleType s = new SampleType();
        s.setId(sampleTypeId);
        LabSystem ls = new LabSystem();
        ls.setId(labSystemId);
        file.setLabSystem(ls);
        ;
        file.setSampleType(s);
        return fileRepository.save(file);
    }

    public File getFileByChecksum(String checksum) {
        return fileRepository.findByChecksum(checksum);

    }

    public List<OnlySmalls> getLastFilesByDataSourceIdAndSampleTypeId(Long dataSourceId, Long sampleTypeId) {
        List<OnlySmalls> initials = new ArrayList<>();
        fileRepository.findTop10ByLabSystemIdAndSampleTypeIdOrderByCreationDateDesc(dataSourceId, sampleTypeId)
                .forEach(initials::add);
        List<OnlySmalls> initialAndFinalDate = new ArrayList<>();
        initialAndFinalDate.add(initials.get(initials.size() - 1));
        initialAndFinalDate.add(initials.get(0));
        return initialAndFinalDate;
    }

    public OnlyChecksum getFileByFilename(String filename) {
        return fileRepository.getFileByFilename(filename);

    }

    public File addFile(String sampleTypeQCCV, File file) {
        // get the sample type
        SampleType t = sampleTypeService.getSampleTypeByQCCV(sampleTypeQCCV);
        file.setSampleType(t);
        return fileRepository.save(file);
    }

    public File addFromWorkflow(File file, String sampleTypeQCCV, UUID labSystemApiKey, User user) {
        // Find if file already exists
        if (getFileByChecksum(file.getChecksum()) != null) {
            logger.error("ERROR: File NOT inserted with duplicated checksum: " + file.getChecksum());
            throw new DataIntegrityViolationException("A file with that checksum already exists!");
        }

        GuideSet guideSet = guideSetRepository.findByIsActiveTrue().get(0);

        file.setGuideSet(guideSet);

        SampleType st = sampleTypeService.getSampleTypeByQCCV(sampleTypeQCCV);

        if (st == null) {
            logger.error("ERROR: File NOT inserted because sample type is not found: " + sampleTypeQCCV);
            throw new DataRetrievalFailureException("Sample type not found.");
        }

        Optional<LabSystem> ls = labSystemService.findSystemByApiKey(labSystemApiKey);

        if (ls.isPresent()) {
            file.setSampleType(st);
            file.setLabSystem(ls.get());
            GuideSet mgs = ls.get().getGuideActiveSetBySampleType(st.getId());
            if (mgs != null) {
                file.setGuideSet(mgs);
            }
            file.setInsertDate(new Date());
            logger.info("File inserted with checksum: " + file.getChecksum() + " for labsystem: "
                    + file.getLabSystem().getName());
            // webSocket.sendUpdateIntranet(file); // perform this in the data insert to
            // avoid errors
        } else {
            throw new DataRetrievalFailureException("Lab system not found.");
        }
        if (!isLastFile(file, st, ls.get())) {
            throw new DataIntegrityViolationException(
                    "Can not insert this file because it is not the last file! " + file.getChecksum());
        }
        fileRepository.save(file);
        webSocketService.sendUpdateForDashboard(file);
        return file;
    }

    private boolean isLastFile(File file, SampleType st, LabSystem labSystem) {
        return fileRepository.countByLabSystemIdAndSampleTypeIdAndCreationDateGreaterThan(labSystem.getId(), st.getId(),
                file.getCreationDate()) == 0;
    }

    public void deleteFile(String checksum) {
        File f = fileRepository.findByChecksum(checksum);
        if (f == null) {
            throw new DataRetrievalFailureException("File not found.");
        }
        fileRepository.delete(f);

    }

    public File getLastFileBySampleTypeQCCVAndLabSystemApikey(String sampleTypeQCCV, UUID labSystemApikey) {
        return fileRepository
                .findTop1BySampleTypeQualityControlControlledVocabularyAndLabSystemApiKeyOrderByCreationDateDesc(
                        sampleTypeQCCV, labSystemApikey);
    }

    public List<SampleType> findSampleTypesByLabSystemApiKey(UUID labSystemApiKey) {
        return fileRepository.findDistinctSampleTypeByLabSystemApiKey(labSystemApiKey);
    }

    public File getFileByChecksumWithUserCheck(String checksum, User userFromSecurityContext) {
        File file = fileRepository.findByChecksum(checksum);
        if (file == null) {
            throw new DataRetrievalFailureException("Not your");
        }
        if (file.getLabSystem().getMainDataSource().getNode().getId() != userFromSecurityContext.getNode().getId()) {
            throw new DataRetrievalFailureException("Not your file");
        } else {
            return file;
        }
    }

    public void checkIfFileExistsByChecksum(String checksum) {
        File file = fileRepository.findByChecksum(checksum);
        if (file == null) {
            throw new DataRetrievalFailureException("File not found");
        }
    }

    public void updateFile(String checksum, File file) {
        File f = fileRepository.findByChecksum(checksum);
        if (f == null) {
            throw new DataRetrievalFailureException("File not found");
        }
        f.setIsValidChecksum(file.getIsValidChecksum());
        fileRepository.save(f);

    }

    public Long getCountDataBetweenDates(UUID labSystemApiKey, String sampleTypeName, Date startDate, Date endDate) {
        SampleType sample = sampleNameToQQCV(sampleTypeName);
        return fileRepository.countByLabSystemApiKeyAndSampleTypeIdAndCreationDateBetween(labSystemApiKey,
                sample.getId(), startDate, endDate);
    }

    private SampleType sampleNameToQQCV(String sampleTypeName) {
        SampleType sample = sampleTypeRepository.findAllByName(sampleTypeName);
        return sample;
    }

    public Page<File> getFilesByNodePaged(Node node, Pageable page, String filename, String labsystemApiKey,
            String sampleTypeQCCV) {
        List<LabSystem> ls = labSystemService.findAllByNode(node.getId());
        if (labsystemApiKey.equals("")) {
            return fileRepository.findByFilenameContainingAndSampleTypeQualityControlControlledVocabularyContainingAndLabSystemInOrderByIdDesc(filename, sampleTypeQCCV, ls, page);
        }
        else {
            UUID caca = UUID.fromString(labsystemApiKey);
            return fileRepository.findByFilenameContainingAndLabSystemApiKeyAndSampleTypeQualityControlControlledVocabularyContainingAndLabSystemInOrderByIdDesc(filename, caca, sampleTypeQCCV, ls, page);
        }
    }

    public boolean getFileStatusByChecksum(String checksum) {
        return intranetService.getFileStatus(checksum);
    }

    public List<Summary> getSummary(String checksum) {
        List<Summary> summaryList = new ArrayList<>();
        File file = fileRepository.findByChecksum(checksum);
        List<PeptidesFromSample> peptides = sampleCompositionRepository.findBySampleType(file.getSampleType());
        for (PeptidesFromSample peptide: peptides) {
            Summary summary = new Summary();
            summary.setSequence(peptide.getPeptide().getSequence());
            List<Data> data = dataRepository.findByFileAndContextSourceId(file, peptide.getPeptide().getId());
            summary.setValues(data);
            summaryList.add(summary);
        }
        return summaryList;
    }

}
