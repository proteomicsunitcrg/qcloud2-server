package eu.qcloud.intranet.file;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import eu.qcloud.data.Data;
import eu.qcloud.data.DataRepository;
import eu.qcloud.dataSource.DataSourceRepository;
import eu.qcloud.file.File;
import eu.qcloud.file.FileRepository;
import eu.qcloud.node.Node;
import eu.qcloud.security.model.User;
import eu.qcloud.security.repository.UserRepository;
import eu.qcloud.thresholdnonconformity.ThresholdNonConformity;
import eu.qcloud.thresholdnonconformity.ThresholdNonConformityRepository;

@Service
public class IntranetService {

    @Autowired
    FileRepository fileRepo;

    @Autowired
    DataRepository dataRepo;

    @Autowired
    DataSourceRepository dataSourceRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    ThresholdNonConformityRepository nonConformityRepository;

    @Value("${qcloud.intranet.pipeline-valid-hours}")
    private int pipelineHours;

    @Value("${qcloud.intranet.api-url}")
    private String APIUrl;

    private final RestTemplate restTemplate;

    public IntranetService(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Long countAllFiles() {
        return fileRepo.count();
    }

    public Page<File> getAllFiles(String name, String checksum, String labsystemName, String sampleTypeId,
            Pageable page, String nodeToFind, String email, boolean exact) {
        if (name.equals("null")) {
            name = "";
        }
        if (checksum.equals("null")) {
            checksum = "";
        }
        if (labsystemName.equals("null")) {
            labsystemName = "";
        }
        if (sampleTypeId.equals("null")) {
            sampleTypeId = "";
        }
        if (nodeToFind.equals("null")) {
            nodeToFind = "";
        }
        if (!email.equals("null")) {
            User user = userRepo.findByUsername(email);
            if (user != null) {
                nodeToFind = user.getNode().getName();
            }
        }
        if (exact) {
            return fileRepo
                    .findByFilenameContainingAndChecksumContainingAndLabSystemNameAndSampleTypeQualityControlControlledVocabularyContainingOrderByIdDesc(
                            name, checksum, labsystemName, sampleTypeId, page);
        } else {
            return fileRepo
                    .findByFilenameContainingAndChecksumContainingAndLabSystemNameContainingAndSampleTypeQualityControlControlledVocabularyContainingAndLabSystemDataSourcesNodeNameContainingAndLabSystemDataSourcesCvCategoryIdOrderByIdDesc(
                            name, checksum, labsystemName, sampleTypeId, nodeToFind, Long.valueOf(1), page);
        }
    }

    public Page<File> caca(Pageable page) {
        return fileRepo.findAll(page);
    }

    public boolean deleteFile(String checksum) {
        try {
            File file = fileRepo.findByChecksum(checksum);
            List<Data> data = dataRepo.findByFileId(file.getId());
            if (data.size() != 0) {
                dataRepo.deleteAll(data);
            }
            List<ThresholdNonConformity> nonConf = nonConformityRepository.findByFileId(file.getId());
            if (nonConf.size() != 0) {
                nonConformityRepository.deleteAll(nonConf);
            }
            fileRepo.delete(file);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public NodeAndFileStatus getNodeAndFileStatus(UUID dataSourceApiKey, String fileChecksum) {
        int nanCounter = 0;
        NodeAndFileStatus response = new NodeAndFileStatus();
        response.setNode(getNodeByDataSourceApiKey(dataSourceApiKey));
        List<Data> data = dataRepo.findByFileChecksumAndParamId(fileChecksum, 1l);
        for (Data d : data) {
            if (Float.isNaN(d.getCalculatedValue())) {
                nanCounter++;
            }
        }
        try {
            if ((nanCounter * 100) / data.size() < 60) {
                response.setDataOk(true);
            } else {
                response.setDataOk(false);
            }
        } catch (ArithmeticException e) {
            response.setDataOk(false);
        }
        return response;
    }

    public Node getNodeByDataSourceApiKey(UUID apiKey) {
        return dataSourceRepo.findByApiKey(apiKey).getNode();
    }

    public List<Data> getDataBychecksum(String checksum) {
        return dataRepo.findByFileChecksumOrderByParamIdAsc(checksum);
    }

    public List<User> getUsers(String email) {
        return userRepo.findByUsernameContaining(email);
    }

    public List<File> getJSON(String name, String checksum, String labsystemName, String sampleTypeId, String node,
            String email, boolean exact) {
        if (name.equals("null")) {
            name = "";
        }
        if (checksum.equals("null")) {
            checksum = "";
        }
        if (labsystemName.equals("null")) {
            labsystemName = "";
        }
        if (sampleTypeId.equals("null")) {
            sampleTypeId = "";
        }
        if (node.equals("null")) {
            node = "";
        }
        if (!email.equals("null")) {
            User user = userRepo.findByUsername(email);
            if (user != null) {
                node = user.getNode().getName();
            }
        }
        if (exact) {
            return fileRepo
                    .findByFilenameContainingAndChecksumContainingAndLabSystemNameAndSampleTypeQualityControlControlledVocabularyContainingOrderByIdDesc(
                            name, checksum, labsystemName, sampleTypeId);
        } else {
            return fileRepo
                    .findByFilenameContainingAndChecksumContainingAndLabSystemNameContainingAndSampleTypeQualityControlControlledVocabularyContainingAndLabSystemDataSourcesNodeNameContainingAndLabSystemDataSourcesCvCategoryIdOrderByIdDesc(
                            name, checksum, labsystemName, sampleTypeId, node, Long.valueOf(1));
        }
    }

    public boolean getPipelineStatus() {
        try {
            File file = fileRepo.findFirstByOrderByIdDesc();
            if (TimeUnit.MILLISECONDS.toHours(new Date().getTime() - file.getInsertDate().getTime()) >= pipelineHours) {
                return false;
            } else {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean testAPI() {
        final HttpHeaders headers = new HttpHeaders();
        final HttpEntity entity = new HttpEntity(headers);
        try {
            final ResponseEntity<String> response = restTemplate.exchange(APIUrl + "/isup", HttpMethod.GET,
                    entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) { // Somethign fails (404, 500, etc)
            return false;
        }
    }

}
