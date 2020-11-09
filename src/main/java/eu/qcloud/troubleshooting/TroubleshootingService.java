package eu.qcloud.troubleshooting;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import eu.qcloud.troubleshooting.annotation.Annotation;
import eu.qcloud.troubleshooting.annotation.AnnotationRepository;

@Service
public class TroubleshootingService {

    @Autowired
    private TroubleshootingRepo troubleshootingRepository;


    @Autowired
    private AnnotationRepository annoRepo;

    public Troubleshooting addTroubleshootingItem(Troubleshooting troubleshooting) {
        Optional<Troubleshooting> ts = troubleshootingRepository.findByQccv(troubleshooting.getQccv());
        troubleshooting.setApiKey(UUID.randomUUID());
        if (ts.isPresent()) {
            throw new DataIntegrityViolationException("An item with that QCCV already exists");
        }
        return troubleshootingRepository.save(troubleshooting);
    }

    public List<Troubleshooting> getAllTopParents() {
        return troubleshootingRepository.findAllByParentIsNull();
    }

    public Troubleshooting getByApiKey(UUID apiKey) {
        Optional<Troubleshooting> tr = troubleshootingRepository.findByApiKey(apiKey);
        if (!tr.isPresent()) {
            throw new DataRetrievalFailureException("Chart not found");
        } else {
            return tr.get();
        }
    }

    public Troubleshooting unLink(UUID apiKey) {
        Troubleshooting tr = getByApiKey(apiKey);
        tr.setParent(null);
        return troubleshootingRepository.save(tr);
    }

    public List<Troubleshooting> getByParentNullChildsNullAndType(TroubleshootingType type) {
        List<Troubleshooting> trs = troubleshootingRepository.findAllByParentIsNullAndChildsIsNullAndType(type);
        return trs;
    }

    public Troubleshooting linkChild(UUID parentApiKey, Troubleshooting child) {
        Troubleshooting parent = getByApiKey(parentApiKey);
        child.setParent(parent);
        return troubleshootingRepository.save(child);
    }

    public List<Troubleshooting> getTroubleshootingParentByType(TroubleshootingType type) {
        return troubleshootingRepository.findAllByParentIsNullAndType(type);
    }

    public Troubleshooting updateTroubleshooting(Troubleshooting troubleshooting) {
        return troubleshootingRepository.save(troubleshooting);
    }

    public List<DataForParetto> getForParetto(UUID lsApiKey, TroubleshootingType type) {
        List<DataForParetto> dataForParetto = new ArrayList<>();
        List <String> allTroublesName = new ArrayList<>();
        List <Annotation> allAnos = annoRepo.findByLabSystemApiKey(lsApiKey);
        for (Annotation ano : allAnos) {
            for (Troubleshooting tr : ano.getTroubleshootings())  {
                if (tr.getType().equals(type)) {
                    getSecondLevelTroubleshooting(tr);
                    allTroublesName.add(getSecondLevelTroubleshooting(tr).getName());
                }
            }
        }
        int total = allTroublesName.size();
        Float acumulat = 0f;
        Map<String, Long> counter = allTroublesName.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        Map<String, Long> sortedMap =
        counter.entrySet().stream()
        .sorted(Entry.<String, Long>comparingByValue().reversed())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue,(e1, e2) -> e1, LinkedHashMap::new));
        for (Map.Entry<String, Long> entry : sortedMap.entrySet()) {
            if (entry.getValue() * 100/total + acumulat > 80) {
                return dataForParetto;
            }
            acumulat += (entry.getValue() * 100)/total;
            dataForParetto.add(new DataForParetto(entry.getKey(), acumulat, entry.getValue()));
        }

        return dataForParetto;
    }


    // TODO improve this...is a workaround and only works with 3 levels
    private Troubleshooting getSecondLevelTroubleshooting(Troubleshooting tr) {
        if (tr.getParent() != null) {  // if his parent is not null means that has parent and isnt the first level
            if (tr.getParent().getParent() != null) {   // and if his parent has parent means that he is grandchild...
                return tr.getParent();
            } else {
                return tr;
            }
        }
        return tr;
    }

}
