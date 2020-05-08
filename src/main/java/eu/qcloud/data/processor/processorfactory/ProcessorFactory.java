package eu.qcloud.data.processor.processorfactory;

import eu.qcloud.data.ProcessorType;
import eu.qcloud.data.processor.processors.Log2Processor;
import eu.qcloud.data.processor.processors.NoProcessor;
import eu.qcloud.data.processor.processors.Processor;
import eu.qcloud.data.processor.processors.RetentionTimeProcessor;

public class ProcessorFactory {

	public static Processor getProcessor(ProcessorType processorName) {
		switch (processorName) {
			case RETENTION_TIME:
				return new RetentionTimeProcessor();
			case LOG2:
				return new Log2Processor();
			case MEDIANFWHM:
				System.out.println("median");
				break;
			case NO_PROCESSOR:
				return new NoProcessor();
			default:
				System.out.println("errol");
				break;
		}

		return null;
	}

}
