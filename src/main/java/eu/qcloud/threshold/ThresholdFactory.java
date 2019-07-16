package eu.qcloud.threshold;

import eu.qcloud.threshold.constraint.ThresholdConstraint;
import eu.qcloud.threshold.hardlimitthreshold.HardLimitThreshold;
import eu.qcloud.threshold.sigma.SigmaThreshold;
import eu.qcloud.threshold.sigmalog2threshold.SigmaLog2Threshold;

public class ThresholdFactory {
	public static ThresholdConstraint getAdminConstraints(ThresholdType thresholdType) {
		switch (thresholdType) {
		case SIGMALOG2:
			return (new SigmaLog2Threshold().getAdminThresholdConstraint());
		case HARDLIMIT:
			return (new HardLimitThreshold().getAdminThresholdConstraint());
		case SIGMA:
			return (new SigmaThreshold().getAdminThresholdConstraint());
		default:
			System.out.println("error");
		}
		return null;
	}

	public static ThresholdConstraint getManagerConstraints(ThresholdType thresholdType) {
		switch (thresholdType) {
		case SIGMA:
			return (new SigmaLog2Threshold().getManagerThresholdConstraint());
		default:
			System.out.println("error");
		}
		return null;

	}
}
