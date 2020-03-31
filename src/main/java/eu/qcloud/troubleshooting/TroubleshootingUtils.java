package eu.qcloud.troubleshooting;

import java.util.UUID;

public class TroubleshootingUtils {

	public static Troubleshooting createTroubleshootingByType(Troubleshooting item, TroubleshootingType type) {
		switch (type) {
		// case PROBLEM:
		// 	return new Problem(item.getName(), item.getDescription(), item.getQccv(), UUID.randomUUID());
		// case ACTION:
		// 	return new Action(item.getName(), item.getDescription(), item.getQccv(), UUID.randomUUID());
		// case CAUSE:
		// 	return new Cause(item.getName(), item.getDescription(), item.getQccv(), UUID.randomUUID());
		// case TEST:
		// 	return new Test(item.getName(), item.getDescription(), item.getQccv(), UUID.randomUUID());
		default:
			return null;
		}

	}
}
