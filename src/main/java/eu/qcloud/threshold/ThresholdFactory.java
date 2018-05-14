package eu.qcloud.threshold;

import eu.qcloud.threshold.constraint.ThresholdConstraint;
import eu.qcloud.threshold.hardlimitthreshold.HardLimitThreshold;
import eu.qcloud.threshold.sigmathreshold.SigmaThreshold;

public class ThresholdFactory {
	public static ThresholdConstraint getAdminConstraints(ThresholdType thresholdType) {
		switch (thresholdType) {
		case SIGMA:
			return (new SigmaThreshold().getAdminThresholdConstraint());
		case HARDLIMIT:
			return (new HardLimitThreshold().getAdminThresholdConstraint());
		default:
			System.out.println("error");
		}
		return null;
	}
	public static ThresholdConstraint getManagerConstraints(ThresholdType thresholdType) {
		switch (thresholdType) {
		case SIGMA:
			return (new SigmaThreshold().getManagerThresholdConstraint());
		default:
			System.out.println("error");
		}
		return null;

	}
}
