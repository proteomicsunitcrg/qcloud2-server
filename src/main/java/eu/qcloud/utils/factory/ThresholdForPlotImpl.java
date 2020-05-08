package eu.qcloud.utils.factory;

import java.util.List;

import eu.qcloud.threshold.Direction;
import eu.qcloud.threshold.ThresholdType;

/**
 * This class is like the projection in the threshold repository. It is used
 * because there are some situations when you can not use the repository to get
 * this class and return to the client. It is a trick, and not really an
 * implementation of the interface (projection) at the repository.
 *
 * @author dmancera
 *
 */
public class ThresholdForPlotImpl {

	private ThresholdType thresholdType;

	private Direction direction;

	private Direction nonConformityDirection;

	private int steps;

	private boolean isMonitored;

	private List<ParamsNoThresholdImpl> thresholdParams;

	public ThresholdType getThresholdType() {
		return thresholdType;
	}

	public void setThresholdType(ThresholdType thresholdType) {
		this.thresholdType = thresholdType;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Direction getNonConformityDirection() {
		return nonConformityDirection;
	}

	public void setNonConformityDirection(Direction nonConformityDirection) {
		this.nonConformityDirection = nonConformityDirection;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public boolean getMonitored() {
		return isMonitored;
	}

	public void setIsMonitored(boolean isMonitored) {
		this.isMonitored = isMonitored;
	}

	public List<ParamsNoThresholdImpl> getThresholdParams() {
		return thresholdParams;
	}

	public void setThresholdParams(List<ParamsNoThresholdImpl> thresholdParams) {
		this.thresholdParams = thresholdParams;
	}

	public ThresholdForPlotImpl(ThresholdType thresholdType, Direction direction, Direction nonConformityDirection,
			int steps, boolean isMonitored, List<ParamsNoThresholdImpl> thresholdParams) {
		this.thresholdType = thresholdType;
		this.direction = direction;
		this.nonConformityDirection = nonConformityDirection;
		this.steps = steps;
		this.isMonitored = isMonitored;
		this.thresholdParams = thresholdParams;
	}

}
