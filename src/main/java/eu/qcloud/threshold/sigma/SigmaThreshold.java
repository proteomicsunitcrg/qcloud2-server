package eu.qcloud.threshold.sigma;

import javax.persistence.Entity;

import eu.qcloud.threshold.Direction;
import eu.qcloud.threshold.Threshold;
import eu.qcloud.threshold.ThresholdType;
import eu.qcloud.threshold.constraint.ThresholdConstraint;
import eu.qcloud.threshold.processor.NoProcessor;

@Entity(name="sigma")
public class SigmaThreshold extends Threshold {

	public SigmaThreshold() {
		super();
		this.direction = Direction.DOWN;
		this.nonConformityDirection = Direction.DOWN;
		this.processor = new NoProcessor();
		this.adminThresholdConstraint = new ThresholdConstraint(false,false,false,true,false,false);
		this.managerThresholdConstraint = new ThresholdConstraint(false,false,false,false,false,false);
		this.thresholdType = ThresholdType.SIGMA;
	}
}
