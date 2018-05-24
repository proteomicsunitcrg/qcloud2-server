package eu.qcloud.threshold;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import eu.qcloud.CV.CV;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.param.Param;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.threshold.constraint.ThresholdConstraint;
import eu.qcloud.threshold.params.ThresholdParamsRepository.paramsNoThreshold;

@NoRepositoryBean
public interface ThresholdRepository<T extends Threshold> extends CrudRepository<T, Long> {

	public Optional<Threshold> findOptionalBySampleTypeIdAndParamIdAndCvIdAndLabSystemId(Long sampleTypeId,Long paramId, Long cvId, Long labSystemId);
	
	public withParamsWithoutThreshold findBySampleTypeIdAndParamIdAndCvIdAndLabSystemId(Long sampleTypeId,Long paramId, Long cvId, Long labSystemId);

	public Optional<Threshold> findOptionalBySampleTypeIdAndParamIdAndCvId(Long sampleTypeId, Long paramId, Long cvId);
	
	public withParamsWithoutThreshold findBySampleTypeIdAndParamIdAndCvId(Long sampleTypeId, Long paramId, Long cvId);
	
	@Query("select t from Threshold t where t.id =?1")
	public withParamsWithoutThreshold findThresholdById(Long thresholdId);
	
	@Query("select t from Threshold t where t.id =?1")
	public ThresholdForPlot getThresholdForPlot(Long thresholdId);
	
	@Query("select t from Threshold t where t.labSystem = null")
	public List<withParamsWithoutThreshold> findMini();
	
	@Query("select t from Threshold t where t.labSystem = null")
	public List<Threshold> findAllDefaultThresholds();
	
	@Query("select t from Threshold t where t.labSystem = null and t.cv.id = ?1")
	public List<Threshold> findAllDefaultThresholdsByThresholdCVId(Long cvId);
	
	@Query("select t from Threshold t where t.labSystem.id = ?1")
	public List<withParamsWithoutThreshold> findLabSystemThresholds(Long labSystemId);
	
	@Query("select t from Threshold t where t.id = ?1")
	public List<withParamsWithoutThreshold> findTresholdById(Long thresholdId);
	
	interface withParamsWithoutThreshold {
		Long getId();
		String getName();
		ThresholdType getThresholdType();
		SampleType getSampleType();
		Param getParam();
		CV getCv();
		int getSteps();
		LabSystem getLabSystem();
		ThresholdConstraint getAdminThresholdConstraint();
		ThresholdConstraint getManagerThresholdConstraint();
		List<paramsNoThreshold> getThresholdParams();
		Direction getNonConformityDirection();
	}
	
	interface onlyConstraints {
		ThresholdConstraint getThresholdConstraint();
	}
	
	interface ThresholdForPlot {
		ThresholdType getThresholdType();
		Direction getDirection();
		Direction getNonConformityDirection();
		int getSteps();
		List<paramsNoThreshold> getThresholdParams();
	}
}
