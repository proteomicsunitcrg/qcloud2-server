package eu.qcloud.threshold.communitythresholds;

import javax.persistence.Entity;

import eu.qcloud.threshold.Direction;
import eu.qcloud.threshold.Threshold;
import eu.qcloud.threshold.ThresholdType;
import eu.qcloud.threshold.constraint.ThresholdConstraint;
import eu.qcloud.threshold.processor.NoProcessor;

@Entity(name="CommunityThreshold")
public class CommunityThreshold extends Threshold {
	
	public CommunityThreshold() {
		super();
		this.direction = Direction.UPDOWN;
		// this.nonConformityDirection = Direction.UPDOWN;
		this.processor = new NoProcessor();
		this.adminThresholdConstraint = new ThresholdConstraint(false,false,false,true,true,true);
		this.managerThresholdConstraint = new ThresholdConstraint(false,false,false,false,true,true);
        this.thresholdType = ThresholdType.COMM1;
        this.setCommFeat(true);
	}

}