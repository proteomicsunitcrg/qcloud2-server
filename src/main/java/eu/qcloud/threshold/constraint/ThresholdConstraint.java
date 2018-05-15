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

	private boolean isSingleContextSource;
	
	public ThresholdConstraint() {}
	
	/**
	 * Defines the threshold constraints
	 * @param isSingleContextSource
	 * @param isInitialValueEditable
	 * @param isStepValueEditable
	 * @param isStepsEditable
	 */
	public ThresholdConstraint(boolean isSingleContextSource, boolean isInitialValueEditable, boolean isStepValueEditable, boolean isStepsEditable) {
		super();
		this.isInitialValueEditable = isInitialValueEditable;
		this.isStepValueEditable = isStepValueEditable;
		this.isStepsEditable = isStepsEditable;
		this.isSingleContextSource = isSingleContextSource;
	}	
	
	public boolean isSingleContextSource() {
		return isSingleContextSource;
	}

	public void setSingleContextSource(boolean isSingleContextSource) {
		this.isSingleContextSource = isSingleContextSource;
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
