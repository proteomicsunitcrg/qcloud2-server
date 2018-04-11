package eu.qcloud.dataSource;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="guide_set")
public class GuideSet {
	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "guide_set_seq")
    @SequenceGenerator(name = "guide_set_seq", sequenceName = "guide_set_seq", allocationSize = 1)
	private Long id;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name="start_date", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name="end_date", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	@JsonIgnore
	@OneToOne(mappedBy="guideSet")	
	private DataSource dataSource;
	
	public GuideSet() {}

	public GuideSet(Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	

}
