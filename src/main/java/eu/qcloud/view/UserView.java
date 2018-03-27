package eu.qcloud.view;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import eu.qcloud.dataSource.DataSource;
@Entity(name="user_view")
public class UserView extends ViewDisplay {
	
	@ManyToOne
	@JoinColumn(name="dataSourceId",insertable=false, updatable= false)
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
