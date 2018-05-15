package eu.qcloud.threshold.hardlimitthreshold;

import javax.persistence.Entity;

import eu.qcloud.threshold.Direction;
import eu.qcloud.threshold.Threshold;
import eu.qcloud.threshold.ThresholdType;
import eu.qcloud.threshold.constraint.ThresholdConstraint;
import eu.qcloud.threshold.processor.NoProcessor;
@Entity(name="hard_limit")
public class HardLimitThreshold extends Threshold {
	
	public HardLimitThreshold() {
		super();
		this.direction = Direction.UPDOWN;
		this.nonConformityDirection = Direction.UPDOWN;
		this.processor = new NoProcessor();
		this.adminThresholdConstraint = new ThresholdConstraint(false,true,true,true);
		this.managerThresholdConstraint = new ThresholdConstraint(false,true,false,false);
		this.thresholdType = ThresholdType.HARDLIMIT;
	}

}
