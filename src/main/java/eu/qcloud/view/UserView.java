package eu.qcloud.view;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import eu.qcloud.labsystem.LabSystem;

@Entity(name = "user_view")
public class UserView extends ViewDisplay {

	@ManyToOne
	@JoinColumn(name = "labSystemId", insertable = true, updatable = false)
	private LabSystem labSystem;

	public LabSystem getLabSystem() {
		return labSystem;
	}

	public void setLabSystem(LabSystem labSystem) {
		this.labSystem = labSystem;
	}

}
