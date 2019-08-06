package eu.qcloud.generalannotation;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return generalAnnotationRepository.findByDateBetween(dateStart, dateEnd);
	}
}