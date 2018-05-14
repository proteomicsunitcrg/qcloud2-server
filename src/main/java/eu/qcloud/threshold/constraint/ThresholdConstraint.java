package eu.qcloud.threshold.constraint;
/**
 * This class is used to indicate the frontend
 * what fields should be editable.
 * @author dmancera
 *
 */
public class ThresholdConstraint {
	
	private boolean isInitialValueEditable;
	
	private boolean isStepValueEditable;
	
	private boolean isStepsEditable;
	
	public ThresholdConstraint() {}

	public ThresholdConstraint(boolean isInitialValueEditable, boolean isStepValueEditable, boolean isStepsEditable) {
		super();
		this.isInitialValueEditable = isInitialValueEditable;
		this.isStepValueEditable = isStepValueEditable;
		this.isStepsEditable = isStepsEditable;
	}

	public boolean isInitialValueEditable() {
		return isInitialValueEditable;
	}

	public void setInitialValueEditable(boolean isInitialValueEditable) {
		this.isInitialValueEditable = isInitialValueEditable;
	}

	public boolean isStepValueEditable() {
		return isStepValueEditable;
	}

	public void setStepValueEditable(boolean isStepValueEditable) {
		this.isStepValueEditable = isStepValueEditable;
	}

	public boolean isStepsEditable() {
		return isStepsEditable;
	}

	public void setStepsEditable(boolean isStepsEditable) {
		this.isStepsEditable = isStepsEditable;
	}
	
	
}
