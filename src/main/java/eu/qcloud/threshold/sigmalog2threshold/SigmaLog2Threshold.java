package eu.qcloud.threshold.sigmalog2threshold;

import javax.persistence.Entity;

import eu.qcloud.threshold.Direction;
import eu.qcloud.threshold.Threshold;
import eu.qcloud.threshold.ThresholdType;
import eu.qcloud.threshold.constraint.ThresholdConstraint;
import eu.qcloud.threshold.processor.SigmaLog2Processor;

@Entity(name="sigmalog2")
public class SigmaLog2Threshold extends Threshold {
	
	public SigmaLog2Threshold() {
		super();
		this.direction = Direction.DOWN;
		// this.nonConformityDirection = Direction.DOWN;
		this.processor = new SigmaLog2Processor();
		this.adminThresholdConstraint = new ThresholdConstraint(false,false,false,true,false,false);
		this.managerThresholdConstraint = new ThresholdConstraint(false,false,false,false,false,false);
		this.thresholdType = ThresholdType.SIGMALOG2;
		
	}
}
