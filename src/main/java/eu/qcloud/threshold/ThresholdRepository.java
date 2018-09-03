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
	/**
	 * 
	 * @param sampleTypeQCCV
	 * @param paramQCCV
	 * @param instrumentQCCV
	 * @return
	 */
	public Optional<Threshold> findOptionalBySampleTypeQualityControlControlledVocabularyAndParamQccvAndInstrumentQccvAndLabSystemApiKeyIsNull(String sampleTypeQCCV, String paramQCCV, String instrumentQCCV);
	
	public Optional<Threshold> findOptionalBySampleTypeQualityControlControlledVocabularyAndParamQccvAndInstrumentQccvAndLabSystemApiKeyAndIsEnabledTrue(String sampleTypeQCCV, String paramQCCV, String instrumentQCCV, UUID labSystemApiKey);
	
	public List<Threshold> findBySampleTypeIdAndLabSystemIdAndIsMonitoredTrue(Long sampleTypeId, Long labSystemId);
	
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
	
	@Query("select t from Threshold t where t.apiKey =?1")
	public ThresholdForPlot getThresholdForPlot(UUID thresholdApiKey);
	
	@Query("select t from Threshold t where t.labSystem = null")
	public List<withParamsWithoutThreshold> findMini();
	
	@Query("select t from Threshold t where t.labSystem = null")
	public List<Threshold> findAllDefaultThresholds();
	
	@Query("select t from Threshold t where t.labSystem = null and t.instrument.id = ?1")
	public List<Threshold> findAllDefaultThresholdsByThresholdCVId(Long cvId);
	
	@Query("select t from Threshold t where t.labSystem = null and t.instrument.id = ?1 and t.sampleType.id = ?2")
	public List<Threshold> findAllDefaultThresholdsByThresholdCVIdAndSampleTypeId(Long cvId, Long sampleTypeId);
	
	@Query("select t from Threshold t where t.labSystem.id = ?1 and t.isEnabled= true")
	public List<withParamsWithoutThreshold> findLabSystemThresholds(Long labSystemId);
	
	@Query("select t from Threshold t where t.id = ?1")
	public List<withParamsWithoutThreshold> findTresholdById(Long thresholdId);
	
	public ThresholdForPlot findByParamIdAndSampleTypeIdAndLabSystemId(Long paramId, Long sampleTypeId, Long labSystemId);
	
	@Query("select t from Threshold t where t.param.id = ?1 and t.sampleType.id = ?2 and t.labSystem.id = ?3 and t.isEnabled = 1")
	public Threshold findThresholdByParamIdAndSampleTypeIdAndLabSystemId(Long paramId, Long sampleTypeId, Long labSystemId);
	
	/**
	 * 
	 * @param labSystemApiKey
	 * @param sampleTypeQccv
	 * @return
	 */
	public List<Threshold> findByLabSystemApiKeyAndSampleTypeQualityControlControlledVocabulary(UUID labSystemApiKey, String sampleTypeQccv);
	
	interface withParamsWithoutThreshold {
		UUID getApiKey();
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
	
	interface ThresholdApiAndParam {
		UUID getApiKey();
		Param getParam();
	}
	
	interface ThresholdForPlot {
		ThresholdType getThresholdType();
		Direction getDirection();
		Direction getNonConformityDirection();
		int getSteps();
		List<paramsNoThreshold> getThresholdParams();
		boolean isMonitored();
	}

	public Optional<Threshold> findByApiKey(UUID thresholdApiKey);
}
