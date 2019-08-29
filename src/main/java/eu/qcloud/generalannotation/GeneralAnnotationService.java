package eu.qcloud.generalannotation;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.exceptions.NotFoundException;

@Service
public class GeneralAnnotationService {

    @Autowired
    GeneralAnnotationRepository generalAnnotationRepository;

    public List<GeneralAnnotation> getAll() {
        return generalAnnotationRepository.findAll();
    }

    public GeneralAnnotation save(GeneralAnnotation annotation) {
        annotation.setApiKey(UUID.randomUUID());
        return generalAnnotationRepository.save(annotation);
    }

    public GeneralAnnotation toggleActive(UUID apiKey) {
        GeneralAnnotation generalAnnotation = generalAnnotationRepository.findByApiKey(apiKey);
        generalAnnotation.setActive(!generalAnnotation.isActive());
        return generalAnnotationRepository.save(generalAnnotation);
    }

    public List<GeneralAnnotation> getBetweenDates(Date dateStart, Date dateEnd) {
        List <GeneralAnnotation> caca = generalAnnotationRepository.findByActiveTrueAndDateBetween(dateStart, dateEnd);
        for (GeneralAnnotation caco: caca) {
            System.out.println(caco.getDate());
        }
        return generalAnnotationRepository.findByActiveTrueAndDateBetween(dateStart, dateEnd);
    }

    public boolean delete(Long id) {
        Optional <GeneralAnnotation> generalAnnotation = generalAnnotationRepository.findById(id);
        if (generalAnnotation.isPresent()) {
            generalAnnotationRepository.delete(generalAnnotation.get());
            return true;
        } else {
            return false;
        }
    }

	public GeneralAnnotation getById(Long id) {
        Optional <GeneralAnnotation> geOptional = generalAnnotationRepository.findById(id);
        if (geOptional.isPresent()) {
            return geOptional.get();
        } else {
            throw new NotFoundException("Annotation not found");
        }
	}
}