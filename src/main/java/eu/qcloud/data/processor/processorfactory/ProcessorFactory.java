package eu.qcloud.data.processor.processorfactory;

import eu.qcloud.data.ProcessorType;
import eu.qcloud.data.processor.processors.Processor;
import eu.qcloud.data.processor.processors.RetentionTime;

public class ProcessorFactory {

	public static Processor getProcessor(ProcessorType processorName) {
		switch (processorName) {
		case RETENTION_TIME:
			return new RetentionTime();
		case LOG2:
			System.out.println("log 2");
			break;
		case MEDIANFWHM:
			System.out.println("median");
			break;
		case NO_PROCESSOR:
			System.out.println("no proc");
			break;
		default:
			System.out.println("errol");
			break;
		}

		return null;
	}

}
