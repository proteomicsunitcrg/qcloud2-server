package eu.qcloud.utils.factory;

import java.util.ArrayList;
import java.util.List;

import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.QcrawlerLabSystem;
import eu.qcloud.labsystem.QcrawlerSampleType;
import eu.qcloud.labsystem.QcrawlerSampleTypeCategory;
import eu.qcloud.sampleType.SampleType;

public class QcrawlerLabSystemUtils {
	
	public static QcrawlerLabSystem createQcrawlerLabSystem(LabSystem ls) {
		
		List<SampleType> sampleTypes = ls.getMainDataSource().getCv().getSampleTypes();
		List<QcrawlerSampleType> sts = new ArrayList<>();
		for(SampleType st : sampleTypes) {
			QcrawlerSampleType qct = new QcrawlerSampleType();
			qct.setName(st.getName());
			qct.setQccv(st.getQualityControlControlledVocabulary());
			qct.setSampleTypeCategory(new QcrawlerSampleTypeCategory(st.getSampleTypeCategory().getName()));
			sts.add(qct);
		}
		
		return new QcrawlerLabSystem(ls.getName(), ls.getApiKey(), sts);
	}

}
