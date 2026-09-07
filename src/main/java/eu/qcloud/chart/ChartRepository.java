package eu.qcloud.chart;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.Instrument.Instrument;
import eu.qcloud.param.Param;
import eu.qcloud.sampleType.SampleType;

@Repository
public interface ChartRepository extends CrudRepository<Chart, Long> {

	Optional<Chart> findById(Long chartId);

	List<Chart> findByCvId(Long cvId);

	@Query("select c from Chart c where c.cv.qccv = ?1")
	List<NoView> findByCvIdWithoutView(String cvId);

	List<NoView> findByCvQccvAndSampleTypeQualityControlControlledVocabulary(String instrumentQCCV,
			String sampleTypeQCCV);

	@Query("select c from Chart c")
	List<NoView> findAllCharts();

	List<NoView> findByCvIdAndSampleTypeId(Long cvId, Long sampleTypeId);

	@Query("select distinct c.sampleType from Chart c where c.cv.id = ?1")
	List<SampleType> findChartSampleTypesByCvId(Long cvId);

	// A Chart can exist without ever being placed on a view (see charts #443-445,
	// #446, #440 for bsa_dia/Astral - defined but never added to any layout), so
	// "does a chart exist" isn't the same as "is this actually shown to the user".
	@Query("select distinct c.param.id from Chart c join ViewDisplay vd on vd.chart = c "
			+ "where c.sampleType.id = ?1 and c.cv.id in ?2")
	List<Long> findDisplayedParamIds(Long sampleTypeId, List<Long> cvIds);

	interface ChartDescription {
		Long getId();

		String getName();
	}

	interface NoView {
		Long getId();

		String getName();

		SampleType getSampleType();

		Instrument getCv();

		boolean getIsThresholdEnabled();

		UUID getApiKey();

		Param getParam();
	}

	Optional<Chart> findByApiKey(UUID chartApiKey);

	void deleteByApiKey(UUID chartApiKey);

}
