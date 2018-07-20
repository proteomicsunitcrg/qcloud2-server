package eu.qcloud.threshold.sigmathreshold;

import javax.persistence.Entity;

import eu.qcloud.threshold.Direction;
import eu.qcloud.threshold.Threshold;
import eu.qcloud.threshold.ThresholdType;
import eu.qcloud.threshold.constraint.ThresholdConstraint;
import eu.qcloud.threshold.processor.AreaProcessor;

@Entity(name="sigma")
public class SigmaThreshold extends Threshold {
	
	public SigmaThreshold() {
		super();
		this.direction = Direction.UPDOWN;
		this.nonConformityDirection = Direction.DOWN;
		this.processor = new AreaProcessor();
		this.adminThresholdConstraint = new ThresholdConstraint(false,false,false,true,false,false);
		this.managerThresholdConstraint = new ThresholdConstraint(false,false,false,false,false,false);
		this.thresholdType = ThresholdType.SIGMA;
		
	}
}
