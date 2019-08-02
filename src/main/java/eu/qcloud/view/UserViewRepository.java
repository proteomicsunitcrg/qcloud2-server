package eu.qcloud.view;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import eu.qcloud.chart.ChartRepository.NoView;
import eu.qcloud.labsystem.LabSystem;

public interface UserViewRepository extends CrudRepository<UserView, Long> {

	interface UserDisplayWithOutViewDisplay {
		NoView getChart();

		int getCol();

		int getRow();

		LabSystem getLabSystem();

	}

	List<UserDisplayWithOutViewDisplay> findByViewApiKey(UUID viewApiKey);
}
