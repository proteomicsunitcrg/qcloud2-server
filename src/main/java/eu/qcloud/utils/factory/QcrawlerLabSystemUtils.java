package eu.qcloud.utils.factory;

import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.QcrawlerLabSystem;

public class QcrawlerLabSystemUtils {
	
	public static QcrawlerLabSystem createQcrawlerLabSystem(LabSystem ls) {
		
		return new QcrawlerLabSystem(ls.getName(), ls.getApiKey(), ls.getMainDataSource().getCv().getSampleTypes());
	}

}
