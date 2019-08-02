package eu.qcloud.troubleshooting;

import java.util.UUID;

import eu.qcloud.troubleshooting.action.Action;
import eu.qcloud.troubleshooting.cause.Cause;
import eu.qcloud.troubleshooting.problem.Problem;
import eu.qcloud.troubleshooting.test.Test;

public class TroubleshootingUtils {

	public static Troubleshooting createTroubleshootingByType(Troubleshooting item, TroubleshootingType type) {
		switch (type) {
		case PROBLEM:
			return new Problem(item.getName(), item.getDescription(), item.getQccv(), UUID.randomUUID());
		case ACTION:
			return new Action(item.getName(), item.getDescription(), item.getQccv(), UUID.randomUUID());
		case CAUSE:
			return new Cause(item.getName(), item.getDescription(), item.getQccv(), UUID.randomUUID());
		case TEST:
			return new Test(item.getName(), item.getDescription(), item.getQccv(), UUID.randomUUID());
		default:
			return null;
		}

	}
}
