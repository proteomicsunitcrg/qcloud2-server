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
	
	private boolean isGlobalStepValue;
	
	private boolean isGlobalInitialValue;
	
	public ThresholdConstraint() {}
	
	/**
	 * Defines the threshold constraints
	 * @param isSingleContextSource
	 * @param isInitialValueEditable
	 * @param isStepValueEditable
	 * @param isStepsEditable
	 * @param isGlobalStepValue
	 * @param isGlobalInitialValue
	 */
	public ThresholdConstraint(boolean isSingleContextSource, 
			boolean isInitialValueEditable, 
			boolean isStepValueEditable, 
			boolean isStepsEditable,
			boolean isGlobalStepValue,
			boolean isGlobalInitialValue) {
		this.isInitialValueEditable = isInitialValueEditable;
		this.isStepValueEditable = isStepValueEditable;
		this.isStepsEditable = isStepsEditable;
		this.isSingleContextSource = isSingleContextSource;
		this.isGlobalStepValue = isGlobalStepValue;
		this.isGlobalInitialValue = isGlobalInitialValue;
	}	
	
	public boolean isGlobalInitialValue() {
		return isGlobalInitialValue;
	}

	public void setGlobalInitialValue(boolean isGlobalInitialValue) {
		this.isGlobalInitialValue = isGlobalInitialValue;
	}

	public boolean isGlobalStepValue() {
		return isGlobalStepValue;
	}

	public void setGlobalStepValue(boolean isGlobalStepValue) {
		this.isGlobalStepValue = isGlobalStepValue;
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
