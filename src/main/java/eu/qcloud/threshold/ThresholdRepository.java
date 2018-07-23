package eu.qcloud.threshold;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import eu.qcloud.Instrument.Instrument;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.param.Param;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.threshold.constraint.ThresholdConstraint;
import eu.qcloud.threshold.params.ThresholdParamsRepository.paramsNoThreshold;

@NoRepositoryBean
public interface ThresholdRepository<T extends Threshold> extends CrudRepository<T, Long> {

	public Optional<Threshold> findOptionalBySampleTypeIdAndParamIdAndInstrumentIdAndLabSystemIdAndIsEnabledTrue(Long sampleTypeId,Long paramId, Long cvId, Long labSystemId);
	
	public withParamsWithoutThreshold findBySampleTypeIdAndParamIdAndInstrumentIdAndLabSystemId(Long sampleTypeId,Long paramId, Long cvId, Long labSystemId);

	public Optional<Threshold> findOptionalBySampleTypeIdAndParamIdAndInstrumentIdAndIsEnabledTrue(Long sampleTypeId, Long paramId, Long cvId);

	public Optional<Threshold> findOptionalBySampleTypeQualityControlControlledVocabularyAndParamQccvAndInstrumentQccv(String sampleTypeQCCV, String paramQCCV, String instrumentQCCV);
	
	public Optional<Threshold> findOptionalBySampleTypeQualityControlControlledVocabularyAndParamQccvAndInstrumentQccvAndLabSystemApiKeyIsNull(String sampleTypeQCCV, String paramQCCV, String instrumentQCCV);
	
	public Optional<Threshold> findOptionalBySampleTypeQualityControlControlledVocabularyAndParamQccvAndInstrumentQccvAndLabSystemApiKeyAndIsEnabledTrue(String sampleTypeQCCV, String paramQCCV, String instrumentQCCV, UUID labSystemApiKey);
	
	
	/**
	 * Find a default threshold
	 * @param sampleTypeId
	 * @param paramId
	 * @param cvId
	 * @return
	 */
	public Optional<Threshold> findOptionalBySampleTypeIdAndParamIdAndInstrumentIdAndIsEnabledTrueAndLabSystemIdIsNull(Long sampleTypeId, Long paramId, Long cvId);
	
	public withParamsWithoutThreshold findBySampleTypeIdAndParamIdAndInstrumentIdAndIsEnabledTrue(Long sampleTypeId, Long paramId, Long cvId);
	
	@Query("select t from Threshold t where t.id =?1")
	public withParamsWithoutThreshold findThresholdById(Long thresholdId);
	
	@Query("select t from Threshold t where t.id =?1")
	public ThresholdForPlot getThresholdForPlot(Long thresholdId);
	
	@Query("select t from Threshold t where t.labSystem = null")
	public List<withParamsWithoutThreshold> findMini();
	
	@Query("select t from Threshold t where t.labSystem = null")
	public List<Threshold> findAllDefaultThresholds();
	
	@Query("select t from Threshold t where t.labSystem = null and t.instrument.id = ?1")
	public List<Threshold> findAllDefaultThresholdsByThresholdCVId(Long cvId);
	
	@Query("select t from Threshold t where t.labSystem.id = ?1 and t.isEnabled= true")
	public List<withParamsWithoutThreshold> findLabSystemThresholds(Long labSystemId);
	
	@Query("select t from Threshold t where t.id = ?1")
	public List<withParamsWithoutThreshold> findTresholdById(Long thresholdId);
	
	interface withParamsWithoutThreshold {
		Long getId();
		String getName();
		ThresholdType getThresholdType();
		SampleType getSampleType();
		Param getParam();
		Instrument getCv();
		int getSteps();
		LabSystem getLabSystem();
		ThresholdConstraint getAdminThresholdConstraint();
		ThresholdConstraint getManagerThresholdConstraint();
		List<paramsNoThreshold> getThresholdParams();
		Direction getNonConformityDirection();
		Boolean getIsMonitored();
		Boolean getIsZeroNoData();
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
		boolean isMonitored();
	}
}
