package eu.qcloud.intranet.file;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import eu.qcloud.data.Data;
import eu.qcloud.data.DataRepository;
import eu.qcloud.dataSource.DataSource;
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
    public Long countAllFiles() {
        return fileRepo.count();
    }

	public Page<File> getAllFiles(String name, String checksum, String labsystemName, String sampleTypeId, Pageable page, String nodeToFind, String email, boolean exact) {
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
            if (user!=null) {
                nodeToFind = user.getNode().getName();
            }
        }
        System.out.println(email);
        if (exact) {
            return fileRepo.findByFilenameContainingAndChecksumContainingAndLabSystemNameAndSampleTypeQualityControlControlledVocabularyContainingOrderByIdDesc(name, checksum, labsystemName, sampleTypeId, page);
        } else {
            return fileRepo.findByFilenameContainingAndChecksumContainingAndLabSystemNameContainingAndSampleTypeQualityControlControlledVocabularyContainingAndLabSystemDataSourcesNodeNameContainingAndLabSystemDataSourcesCvCategoryIdOrderByIdDesc(name, checksum, labsystemName, sampleTypeId, nodeToFind, Long.valueOf(1), page);
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
            if (nonConf.size()!= 0) {
                nonConformityRepository.deleteAll(nonConf);
            }
            fileRepo.delete(file);
            return true;
        } catch (Exception e) {
            return false;
        }
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
                    if (user!=null) {
                        node = user.getNode().getName();
                    }
                }
                System.out.println(email);
                if (exact) {
                    return fileRepo.findByFilenameContainingAndChecksumContainingAndLabSystemNameAndSampleTypeQualityControlControlledVocabularyContainingOrderByIdDesc(name, checksum, labsystemName, sampleTypeId);
                } else {
                    return fileRepo.findByFilenameContainingAndChecksumContainingAndLabSystemNameContainingAndSampleTypeQualityControlControlledVocabularyContainingAndLabSystemDataSourcesNodeNameContainingAndLabSystemDataSourcesCvCategoryIdOrderByIdDesc(name, checksum, labsystemName, sampleTypeId, node, Long.valueOf(1));
                }
	}

}
