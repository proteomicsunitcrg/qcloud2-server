package eu.qcloud.userDefaultView;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.view.View;

@Entity
public class UserDefaultView {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "user_default_view_seq")
	@SequenceGenerator(name = "user_default_view_seq", sequenceName = "user_default_view_seq", allocationSize = 1)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ViewType viewType;

	@ManyToOne
	@JoinColumn(name = "view_id", insertable = true, updatable = true, nullable = true)
	private View view;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "lab_system_id", insertable = true, updatable = true, nullable = true)
	private LabSystem labSystem;

	public ViewType getViewType() {
		return viewType;
	}

	public void setViewType(ViewType viewType) {
		this.viewType = viewType;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LabSystem getLabSystem() {
		return labSystem;
	}

	public void setLabSystem(LabSystem labSystem) {
		this.labSystem = labSystem;
	}

}
