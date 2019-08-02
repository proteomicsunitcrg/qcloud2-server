package eu.qcloud.view;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import eu.qcloud.chart.ChartRepository.NoView;

public interface ViewDisplayRepository extends CrudRepository<ViewDisplay, Long> {

	// List<DefaultView> findByViewId(Long viewId);

	List<WithOutViewDisplay> findByViewId(Long viewId);

	void deleteByViewId(Long viewId);

	void deleteByViewApiKey(UUID viewApiKey);

	int countByViewId(Long viewId);

	int countByViewApiKey(UUID apiKey);

	interface WithOutViewDisplay {
		Long getId();

		NoView getChart();

		// ViewWithoutDisplay getView();
		int getCol();

		int getRow();
	}

	List<WithOutViewDisplay> findByViewApiKey(UUID viewApiKey);

}
