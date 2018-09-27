package eu.qcloud.data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import eu.qcloud.chart.Chart;
import eu.qcloud.chart.ChartRepository;
import eu.qcloud.chart.chartParams.ChartParams;
import eu.qcloud.chart.chartParams.ChartParamsRepository;
import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.contextSource.ContextSourceRepository;
import eu.qcloud.contextSource.instrumentSample.InstrumentSample;
import eu.qcloud.contextSource.instrumentSample.InstrumentSampleRepository;
import eu.qcloud.contextSource.peptide.Peptide;
import eu.qcloud.contextSource.peptide.PeptideRepository;
import eu.qcloud.data.DataRepository.MiniData;
import eu.qcloud.data.insertmodel.DataFromPipeline;
import eu.qcloud.data.insertmodel.DataValues;
import eu.qcloud.data.insertmodel.ParameterData;
import eu.qcloud.data.processor.processorfactory.ProcessorFactory;
import eu.qcloud.data.processor.processors.Processor;
import eu.qcloud.file.File;
import eu.qcloud.file.FileRepository;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemRepository;
import eu.qcloud.nonconformity.thresholdnonconformity.ThresholdNonConformity;
import eu.qcloud.nonconformity.thresholdnonconformity.ThresholdNonConformityRepository;
import eu.qcloud.param.Param;
import eu.qcloud.param.ParamRepository;
import eu.qcloud.sampleComposition.SampleComposition;
import eu.qcloud.sampleComposition.SampleCompositionRepository;
import eu.qcloud.sampleComposition.SampleCompositionRepository.PeptidesFromSample;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.sampleType.SampleTypeRepository;
import eu.qcloud.threshold.Direction;
import eu.qcloud.threshold.InstrumentStatus;
import eu.qcloud.threshold.Threshold;
import eu.qcloud.threshold.ThresholdRepository;
import eu.qcloud.threshold.params.ThresholdParams;
import eu.qcloud.threshold.params.ThresholdParamsRepository;
import eu.qcloud.utils.ThresholdUtils;

/**
 * Service for the data
 * 
 * @author dmancera
 *
 */
@Service
public class DataService {

	@Autowired
	private DataRepository dataRepository;

	@Autowired
	private ChartParamsRepository chartParamRepository;

	@Autowired
	private LabSystemRepository labSystemRepository;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private ParamRepository paramRepository;

	@Autowired
	private PeptideRepository peptideRepository;

	@Autowired
	private SampleTypeRepository sampleTypeRepository;

	@Autowired
	private SampleCompositionRepository sampleCompositionRepository;

	@Autowired
	private InstrumentSampleRepository instrumentSampleRepository;

	@Autowired
	private ChartRepository chartRepository;

	@Autowired
	private ThresholdRepository<Threshold> thresholdRepository;

	@Autowired
	private ContextSourceRepository contextSourceRepository;

	@Autowired
	private ThresholdUtils thresholdUtils;

	@Autowired
	private ThresholdParamsRepository thresholdParamsRepository;

	@Autowired
	private ThresholdNonConformityRepository thresholdNonConformityRepository;

	@Value("${qcloud.threshold.min-points-auto}")
	private int minPointsAutoThreshold;

	@Value("${qcloud.threshold.files-auto-plot}")
	private int filesForAutoPlot;

	private final Log logger = LogFactory.getLog(this.getClass());

	public List<Data> getAllData() {
		List<Data> data = new ArrayList<>();
		dataRepository.findAll().forEach(data::add);
		return data;
	}

	public Data addData(Data data) {
		return dataRepository.save(data);
	}

	public MiniData getMiniData(Long fileId) {
		return dataRepository.findByDataIdFileId(fileId);
	}

	/**
	 * Recover data from the server by parameters. Note the usage of a class named
	 * DataForPlot and its extensions. If there is a need for further data
	 * processing it will do by reflections. Take a look at the processor package.
	 * 
	 * @author Daniel Mancera <daniel.mancera@crg.eu>
	 * 
	 * @param start
	 * @param end
	 * @param chartId
	 * @param dataSourceId
	 * @param sampleTypeId
	 * @return
	 */
	public List<DataForPlot> getPlotData(Date start, Date end, UUID chartApiKey, UUID labSystemApiKey,
			String sampleTypeQCCV) {
		Optional<Chart> chart = chartRepository.findByApiKey(chartApiKey);
		Optional<LabSystem> labSystem = labSystemRepository.findByApiKey(labSystemApiKey);
		Optional<SampleType> sampleType = sampleTypeRepository.findByQualityControlControlledVocabulary(sampleTypeQCCV);
		if (!chart.isPresent() || !labSystem.isPresent() || !sampleType.isPresent()) {
			throw new DataRetrievalFailureException("Wrong chart, system or sample type");
		}
		// Get the param
		Param param = chartParamRepository.findTopByChartId(chart.get().getId()).getParam();

		List<ChartParams> chartParams = chartParamRepository.findByChartIdAndParamId(chart.get().getId(),
				param.getId());

		List<ContextSource> contextSources = new ArrayList<>();

		chartParams.forEach(cp -> {
			contextSources.add(cp.getContextSource());
		});

		List<DataForPlot> dataForPlot = getDataForPlot(labSystem.get(), param, contextSources, sampleType.get(), start,
				end);

		if (chart.get().isNormalized()) {
			return normalizeData(dataForPlot, labSystem.get(), sampleType.get(), chart.get());
		} else {
			return dataForPlot;
		}
	}

	/**
	 * It needs some supervision
	 * 
	 * @param list
	 * @param labSystem
	 * @param sampleType
	 * @param chart
	 * @return
	 */
	private List<DataForPlot> normalizeData(List<DataForPlot> list, LabSystem labSystem, SampleType sampleType,
			Chart chart) {

		HashMap<String, ArrayList<Float>> guideSetValues = new HashMap<>();

		for (DataForPlot d : list) {
			if (guideSetValues.containsKey(d.getContextSourceName())) {
				if (d.getValue() != 0f && !d.getValue().isNaN()) {
					guideSetValues.get(d.getContextSourceName()).add(d.getValue());
				}
			} else {
				guideSetValues.put(d.getContextSourceName(), new ArrayList<>());
			}
		}

		HashMap<String, Float> means = new HashMap<>();

		for (Map.Entry<String, ArrayList<Float>> entry : guideSetValues.entrySet()) {
			means.put(entry.getKey(), calculateMeanOfArrayList(entry.getValue()));
		}

		for (DataForPlot d : list) {
			Float res = d.getValue() - means.get(d.getContextSourceName());
			d.setValue(res);
		}
		return list;

	}

	private float calculateMeanOfArrayList(ArrayList<Float> values) {
		float sum = 0f;
		for (Float f : values) {
			sum += f;
		}
		return sum / values.size();
	}

	/**
	 * Prepare the output data for the client. It will take into account if the
	 * sample category is HIGHWITHISOTOPOLOGUE for susbsitute the abbreviated name
	 * with the concentration
	 * 
	 * @param dataFromDb
	 * @param sampleType
	 * @param param
	 * @return
	 */
	private List<DataForPlot> prepareDataForPlot(List<Data> dataFromDb, SampleType sampleType, Param param) {

		List<DataForPlot> dataForPlot = new ArrayList<>();
		switch (sampleType.getSampleTypeCategory().getSampleTypeComplexity()) {
		case HIGHWITHISOTOPOLOGUES:
			if (param.getIsFor().equals("Peptide")) {
				for (Data data : dataFromDb) {
					// Instead of getting the full name or the abbreviated one we need to get the
					// concentration
					SampleComposition concentration = sampleCompositionRepository
							.getSampleCompositionBySampleTypeIdAndPeptideId(sampleType.getId(),
									data.getContextSource().getId());

					dataForPlot.add(new DataForPlot(data.getFile().getFilename(), data.getFile().getCreationDate(),
							concentration.getConcentration().toString(), data.getValue(),
							data.getNonConformityStatus()));
					Collections.sort(dataForPlot);
				}
			} else {
				convertDbDataToPlotData(dataFromDb, dataForPlot);
			}
			break;
		default:
			convertDbDataToPlotData(dataFromDb, dataForPlot);
			break;
		}

		return dataForPlot;
	}

	private List<DataForPlot> prepareCalculatedDataForPlot(List<Data> dataFromDb, SampleType sampleType, Param param) {

		List<DataForPlot> dataForPlot = new ArrayList<>();
		switch (sampleType.getSampleTypeCategory().getSampleTypeComplexity()) {
		case HIGHWITHISOTOPOLOGUES:
			if (param.getIsFor().equals("Peptide")) {
				for (Data data : dataFromDb) {
					// Instead of getting the full name or the abbreviated one we need to get the
					// concentration
					SampleComposition concentration = sampleCompositionRepository
							.getSampleCompositionBySampleTypeIdAndPeptideId(sampleType.getId(),
									data.getContextSource().getId());

					dataForPlot.add(new DataForPlot(data.getFile().getFilename(), data.getFile().getCreationDate(),
							concentration.getConcentration().toString(), data.getCalculatedValue(),
							data.getNonConformityStatus()));
					Collections.sort(dataForPlot);
				}
			} else {
				convertDbDataToCalculatedPlotData(dataFromDb, dataForPlot);
			}
			break;
		default:
			convertDbDataToCalculatedPlotData(dataFromDb, dataForPlot);
			break;
		}

		return dataForPlot;
	}

	private void convertDbDataToPlotData(List<Data> dataFromDb, List<DataForPlot> dataForPlot) {
		for (Data data : dataFromDb) {
			dataForPlot.add(new DataForPlot(data.getFile().getFilename(), data.getFile().getCreationDate(),
					data.getContextSource().getAbbreviated(), data.getValue(), data.getNonConformityStatus()));
		}
	}

	private void convertDbDataToCalculatedPlotData(List<Data> dataFromDb, List<DataForPlot> dataForPlot) {
		for (Data data : dataFromDb) {
			dataForPlot.add(new DataForPlot(data.getFile().getFilename(), data.getFile().getCreationDate(),
					data.getContextSource().getAbbreviated(), data.getCalculatedValue(),
					data.getNonConformityStatus()));
		}
	}

	/**
	 * Insert data from the pipeline into the database
	 * 
	 * @param dataFromPipeline
	 */
	public void insertDataFromPipeline(DataFromPipeline dataFromPipeline) {
		// Check if file exists
		File file = fileRepository.findByChecksum(dataFromPipeline.getFile().getChecksum());
		if (file == null) {
			logger.info("insertDataFromPipeline() file not found. " + dataFromPipeline.getFile().getChecksum());
			throw new DataRetrievalFailureException("File not found");
		}
		dataFromPipeline.setFile(file);
		logger.info("Insert data of file:" + file.getFilename() + " - lab system: " + file.getLabSystem().getName());
		// Loop through the parameters
		for (ParameterData parameterData : dataFromPipeline.getData()) {
			logger.info("Insert data of :" + parameterData.getParameter().getqCCV());
			// Loop through the parameters
			Param param = paramRepository.findByQccv(parameterData.getParameter().getqCCV());
			if (param == null) {
				continue;
			}
			parameterData.setParameter(param);
			// loop through values
			for (DataValues dataValue : parameterData.getValues()) {
				ContextSource cs = null;
				switch (param.getIsFor()) {
				case "Peptide":
					cs = peptideRepository.findBySequence(dataValue.getContextSource());
					if (cs == null) {
						continue;
					}
					if (!peptideBelongsToSampleType(file.getSampleType(), dataValue.getContextSource())) {
						continue;
					}
					break;
				case "InstrumentSample":
					cs = instrumentSampleRepository
							.findByQualityControlControlledVocabulary(dataValue.getContextSource());
					if (cs == null) {
						continue;
					}
					break;
				default:
					logger.info("Unknown isFor");
					break;
				}

				// TODO load user guidesets it there is any
				Data d = new Data(param, cs, file);
				d.setValue(dataValue.getValue());
				d.setDataId(new DataId(param.getId(), cs.getId(), file.getId()));
				Float calc = calculateValueFromGuideSet(param, cs, file, d);
				d.setCalculatedValue(calc);
				dataValue.setCalculatedValue(calc);
				dataRepository.save(d);
			}
		}
		// Do threshold checks
		
		if (fileRepository.countByLabSystemIdAndSampleTypeId(file.getLabSystem().getId(),
				file.getSampleType().getId()) > minPointsAutoThreshold) {
			 evaluateDataForNonConformities(file, dataFromPipeline);
			 // Recalculate user thresholds
			 regenerateThresholds(file);
		}
		
	}

	private Float calculateValueFromGuideSet(Param param, ContextSource cs, File file, Data value) {
		Processor processor = ProcessorFactory.getProcessor(param.getProcessor());
		if (processor.isGuideSetRequired()) {
			if (fileRepository.countByLabSystemIdAndSampleTypeId(file.getLabSystem().getId(),
					file.getSampleType().getId()) > minPointsAutoThreshold) {

				GuideSet guideSet = thresholdUtils.generateGuideSetFromWithFile(file, param, cs);

				ArrayList<Data> guideSetData = (ArrayList<Data>) dataRepository.findParamData(cs.getId(), param.getId(),
						guideSet.getStartDate(), guideSet.getEndDate(), file.getLabSystem().getId(),
						file.getSampleType().getId());
				processor.setData(prepareDataForPlot(Arrays.asList(value), file.getSampleType(), param));
				processor.setGuideSet(guideSet);
				processor.setGuideSetData(guideSetData);
				List<DataForPlot> processedValue = processor.processData();

				if (processedValue.get(0).getValue() != null && !processedValue.get(0).getValue().isNaN()) {
					if (param.isZeroNoData() && value.getValue() == 0f) {
						return null;
					}
					return processedValue.get(0).getValue();
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			processor.setData(prepareDataForPlot(Arrays.asList(value), file.getSampleType(), param));
			Float processedValue = processor.processData().get(0).getValue();
			if(processedValue == null) {
				System.out.println("null");
			}
			if (!processedValue.isNaN() && processedValue != null) {
				return processedValue;
			} else {
				return null;
			}
		}
	}
	/*
	private void evaluateDataForParamNonConformities(File file, DataFromPipeline data) {

		for (ParameterData parameterData : data.getData()) {
			// Get the threshold for this parameter, sample type and instrument
			Threshold threshold = thresholdUtils
					.findOrCreateLabSystemThresholdBySampleTypeIdAndParamIdAndCvIdAndLabSystemId(
							file.getSampleType().getId(), parameterData.getParameter().getId(),
							file.getLabSystem().getMainDataSource().getCv().getId(), file.getLabSystem().getId());

			if (threshold == null) {
				continue;
			}

			Float value = 0f;
			for (DataValues dataValue : parameterData.getValues()) {

				ContextSource cs = getContextSourceFromDatabase(dataValue.getContextSource(),
						parameterData.getParameter().getIsFor());

				value = dataValue.getCalculatedValue();
				if (parameterData.getParameter().isZeroNoData()) {
					if (value == null) {
						continue;
					}
				}
				// Checking if the context source is monitored for this threshold
				Optional<ThresholdParams> cc = threshold.getThresholdParams().stream().filter(tp -> {
					return tp.getContextSource().getId() == cs.getId();
				}).findFirst();
				if (!cc.get().getIsEnabled()) {
					continue;
				}

				// Compare here
				InstrumentStatus is = isNonConformity(value,
						getInitialValueFromThresholdParamByContextSource(threshold.getThresholdParams(),
								dataValue.getContextSource(), parameterData.getParameter().getIsFor()),
						getStepValueFromThresholdParamByContextSourceName(threshold.getThresholdParams(),
								dataValue.getContextSource(), parameterData.getParameter().getIsFor()),
						threshold.getSteps(), threshold.getNonConformityDirection());

				if (is != InstrumentStatus.OK && threshold.isMonitored()) {
					logger.info("Storing non conformity");
					ThresholdNonConformity tnc = new ThresholdNonConformity();
					tnc.setStatus(is);
					tnc.setContextSource(cs);
					tnc.setFile(file);
					tnc.setThreshold(threshold);
					thresholdNonConformityRepository.save(tnc);
					Data d = dataRepository.findByFileIdAndParamIdAndContextSourceId(file.getId(),
							threshold.getParam().getId(), cs.getId());
					d.setNonConformityStatus(is);
					dataRepository.save(d);
				}
			}
		}
	}
	*/

	private void evaluateDataForNonConformities(File file, DataFromPipeline data) {

		
		for (ParameterData parameterData : data.getData()) {
			// Get the threshold for this parameter, sample type and instrument
			Threshold threshold = thresholdUtils
					.findOrCreateLabSystemThresholdBySampleTypeIdAndParamIdAndCvIdAndLabSystemId(
							file.getSampleType().getId(), parameterData.getParameter().getId(),
							file.getLabSystem().getMainDataSource().getCv().getId(), file.getLabSystem().getId());

			if (threshold == null) {
				continue;
			}
			// delete previous warnings
			thresholdNonConformityRepository.deletePreviousParamWarnings(file.getLabSystem().getId(),
					file.getSampleType().getId(), InstrumentStatus.WARNING.toString(), threshold.getId());


			Float value = 0f;
			for (DataValues dataValue : parameterData.getValues()) {
				ContextSource cs = getContextSourceFromDatabase(dataValue.getContextSource(),
						parameterData.getParameter().getIsFor());

				value = dataValue.getCalculatedValue();
				if (parameterData.getParameter().isZeroNoData()) {
					if (value == null) {
						continue;
					}
				}
				// Checking if the context source is monitored for this threshold
				Optional<ThresholdParams> cc = threshold.getThresholdParams().stream().filter(tp -> {
					return tp.getContextSource().getId() == cs.getId();
				}).findFirst();
				if (!cc.get().getIsEnabled()) {
					continue;
				}

				// Compare here
				InstrumentStatus is = isNonConformity(value,
						getInitialValueFromThresholdParamByContextSource(threshold.getThresholdParams(),
								dataValue.getContextSource(), parameterData.getParameter().getIsFor()),
						getStepValueFromThresholdParamByContextSourceName(threshold.getThresholdParams(),
								dataValue.getContextSource(), parameterData.getParameter().getIsFor()),
						threshold.getSteps(), threshold.getNonConformityDirection());

				if (is != InstrumentStatus.OK && threshold.isMonitored()) {
					logger.info("NC -> " + file.getFilename());
					ThresholdNonConformity tnc = new ThresholdNonConformity();
					tnc.setStatus(is);
					tnc.setContextSource(cs);
					tnc.setFile(file);
					tnc.setThreshold(threshold);
					thresholdNonConformityRepository.save(tnc);
					Data d = dataRepository.findByFileIdAndParamIdAndContextSourceId(file.getId(),
							threshold.getParam().getId(), cs.getId());
					d.setNonConformityStatus(is);
					dataRepository.save(d);
				}
			}
		}
	}

	private ContextSource getContextSourceFromDatabase(String name, String isFor) {
		switch (isFor) {
		case "Peptide":
			return peptideRepository.findBySequence(name);
		case "InstrumentSample":
			return instrumentSampleRepository.findByQualityControlControlledVocabulary(name);
		}
		return null;
	}

	private InstrumentStatus isNonConformity(Float value, Float initialValue, Float stepValue, int steps,
			Direction direction) {
		Float upperLimit = initialValue + (stepValue * steps);
		Float midLimit = initialValue - (stepValue * (steps - 1));
		Float lowerLimit = initialValue - (stepValue * steps);
		switch (direction) {
		case DOWN:
			// taking care if there is only one step
			if (steps == 1) {
				if (value < lowerLimit) {
					return InstrumentStatus.DANGER;

				}
			} else {
				if (value < lowerLimit) {
					return InstrumentStatus.DANGER;
				} else if (value >= lowerLimit && value < midLimit) {
					return InstrumentStatus.WARNING;
				}
			}
			break;
		case UPDOWN:
			if (value < lowerLimit || value > upperLimit) {
				return InstrumentStatus.DANGER;
			}
			break;
		case UP:
			if (steps == 1) {
				if(value > upperLimit) {
					return InstrumentStatus.DANGER;
				}	
			} else {
				if (value > upperLimit) {
					return InstrumentStatus.DANGER;
				} else if (value <= upperLimit && value > midLimit) {
					return InstrumentStatus.WARNING;
				}
			}
			
			break;
		default:
			System.out.println("Direction not found");
		}
		return InstrumentStatus.OK;
	}

	private Float getInitialValueFromThresholdParamByContextSource(List<ThresholdParams> tp, String contextSourceName,
			String isFor) {
		switch (isFor) {

		case "Peptide":
			return tp.stream().filter(cs -> {
				return cs.getContextSource().getName().equals(contextSourceName);
			}).collect(Collectors.toList()).get(0).getInitialValue();
		case "InstrumentSample":
			return tp.stream().filter(cs -> {
				InstrumentSample is = (InstrumentSample) cs.getContextSource();
				return is.getqCCV().equals(contextSourceName);
			}).collect(Collectors.toList()).get(0).getInitialValue();
		default:
			logger.info("Unknown is for parameter");
			break;
		}

		return null;
	}

	private Float getStepValueFromThresholdParamByContextSourceName(List<ThresholdParams> tp, String contextSourceName,
			String isFor) {
		switch (isFor) {

		case "Peptide":
			return tp.stream().filter(cs -> {
				return cs.getContextSource().getName().equals(contextSourceName);
			}).collect(Collectors.toList()).get(0).getStepValue();
		case "InstrumentSample":
			return tp.stream().filter(cs -> {
				InstrumentSample is = (InstrumentSample) cs.getContextSource();
				return is.getqCCV().equals(contextSourceName);
			}).collect(Collectors.toList()).get(0).getStepValue();
		default:
			logger.info("Unknown is for parameter");
			break;
		}

		return null;

	}

	private void regenerateThresholds(File file) {
		List<Threshold> defaultThresholds = new ArrayList<>();
		List<Threshold> nodeThresholds = new ArrayList<>();
		thresholdRepository
				.findAllDefaultThresholdsByThresholdCVIdAndSampleTypeId(
						file.getLabSystem().getMainDataSource().getCv().getId(), file.getSampleType().getId())
				.forEach(defaultThresholds::add);
		// Get the labsystem thresholds if any
		defaultThresholds.forEach(defaultThreshold -> {
			nodeThresholds
					.add(thresholdUtils.findOrCreateLabSystemThresholdBySampleTypeIdAndParamIdAndCvIdAndLabSystemId(
							file.getSampleType().getId(), defaultThreshold.getParam().getId(),
							defaultThreshold.getCv().getId(), file.getLabSystem().getId()));
		});
		List<ThresholdParams> params = new ArrayList<>();

		nodeThresholds.forEach(nodeThreshold -> {
			nodeThreshold.getThresholdParams().forEach(tp -> {

				GuideSet gs = thresholdUtils.generateGuideSetFromWithFile(file, nodeThreshold.getParam(),
						tp.getContextSource());

				if (gs != null) {
					params.add(thresholdUtils.processThresholdParam(nodeThreshold, gs, tp));
				}
			});

			nodeThreshold.setThresholdParams(params);
			saveThresholdParams(nodeThreshold);
		});

	}

	private void regenerateParamThresholds(File file, Param param) {
		Optional<Threshold> defaultThreshold = thresholdRepository.findDefaultThresholdByThresholdCVIdAndSampleTypeIdAndParamId(
				file.getLabSystem().getMainDataSource().getCv().getId(), file.getSampleType().getId(), param.getId());
		// Get the labsystem thresholds if any
		
		if(defaultThreshold.isPresent()) {
			Threshold nodeThreshold = thresholdUtils
					.findOrCreateLabSystemThresholdBySampleTypeIdAndParamIdAndCvIdAndLabSystemId(
							file.getSampleType().getId(), defaultThreshold.get().getParam().getId(),
							defaultThreshold.get().getCv().getId(), file.getLabSystem().getId());

			List<ThresholdParams> params = new ArrayList<>();

			nodeThreshold.getThresholdParams().forEach(tp -> {
				GuideSet gs = thresholdUtils.generateGuideSetFromWithFile(file, nodeThreshold.getParam(),
						tp.getContextSource());
				if (gs != null) {
					params.add(thresholdUtils.processThresholdParam(nodeThreshold, gs, tp));
				}
			});

			nodeThreshold.setThresholdParams(params);
			saveThresholdParams(nodeThreshold);	
		}
		
	}

	private void saveThresholdParams(Threshold threshold) {
		for (ThresholdParams p : threshold.getThresholdParams()) {
			if (!p.getInitialValue().isNaN() || !p.getStepValue().isNaN()) {
				thresholdParamsRepository.save(p);
			}
		}
	}

	/**
	 * Check if a peptide belongs to a sample type
	 * 
	 * @param sampleType
	 * @param sequence
	 * @return true or false
	 */
	private boolean peptideBelongsToSampleType(SampleType sampleType, String sequence) {
		List<SampleComposition> sampleComposition = sampleCompositionRepository
				.findBySampleTypeQualityControlControlledVocabulary(sampleType.getQualityControlControlledVocabulary());
		for (SampleComposition sc : sampleComposition) {
			if (sc.getPeptide().getSequence().equals(sequence)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * TODO: try to use the generic data getter
	 * 
	 * @param checksum
	 * @param abbreviatedSequence
	 * @return
	 */
	public List<DataForPlot> getIsotopologueData(String checksum, String abbreviatedSequence) {
		List<Data> dataFromDb = new ArrayList<>();

		// Getting file
		File file = fileRepository.findByChecksum(checksum);
		if (file == null) {
			throw new DataRetrievalFailureException("File not found");
		}

		// Getting param
		Param param = paramRepository.findByQccv("QC:1001844");
		if (param == null) {
			throw new DataRetrievalFailureException("Database error, please contact administrator");
		}

		// get peptides by abbreviated sequence
		List<Peptide> peptides = new ArrayList<>();
		List<PeptidesFromSample> pps = sampleCompositionRepository
				.findBySampleTypeQualityControlControlledVocabularyAndPeptideAbbreviated(
						file.getSampleType().getQualityControlControlledVocabulary(), abbreviatedSequence);
		for (int i = 0; i < pps.size(); i++) {
			peptides.add(pps.get(i).getPeptide());
		}
		if (peptides.size() == 0) {
			throw new DataRetrievalFailureException("No isotopologues found.");
		}
		for (Peptide peptide : peptides) {
			Data d = dataRepository.findByFileIdAndParamIdAndContextSourceId(file.getId(), param.getId(),
					peptide.getId());

			/**
			 * This part of the code should be removed when the migration ends.
			 */
			if (d == null) {
				// It is possible that there is no data from the current file.
				List<File> files = fileRepository.findByCreationDateAndLabSystemIdAndSampleTypeId(
						file.getCreationDate(), file.getLabSystem().getId(), file.getSampleType().getId());
				for (File f : files) {
					d = dataRepository.findByFileIdAndParamIdAndContextSourceId(f.getId(), param.getId(),
							peptide.getId());
					if (d != null) {
						continue;
					}
				}
			}
			dataFromDb.add(d);
		}
		List<DataForPlot> dataForPlot = prepareDataForPlot(dataFromDb, file.getSampleType(), param);

		/**
		 * The peptide area of isotopologues will only need the LOG2 processor. So,
		 * there is no need to check if the processor needs a guide set because we
		 * already know what processor will it be.
		 */
		Processor processor = ProcessorFactory.getProcessor(param.getProcessor());
		Collections.reverse(dataForPlot);
		processor.setData(dataForPlot);

		return processor.processData();

	}

	/**
	 * 
	 * @param labSystem
	 * @param param
	 * @param contextSources
	 * @param sampleType
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private List<DataForPlot> getDataForPlot(LabSystem labSystem, Param param, List<ContextSource> contextSources,
			SampleType sampleType, java.util.Date startDate, java.util.Date endDate) {
		List<DataForPlot> dataForPlot = new ArrayList<>();
		contextSources.forEach(cs -> {
			List<Data> data = dataRepository.findParamData(cs.getId(), param.getId(), startDate, endDate,
					labSystem.getId(), sampleType.getId());
			List<DataForPlot> contextSourceDataForPlot = prepareCalculatedDataForPlot(data, sampleType, param);
			dataForPlot.addAll(contextSourceDataForPlot);
		});

		return dataForPlot;
	}

	public List<DataForPlot> getAutoPlotData(UUID labSystemApiKey, String paramQccv, UUID contextSourceApiKey,
			String sampleTypeQccv, UUID thresholdApiKey) {

		Optional<Threshold> threshold = thresholdRepository.findByApiKey(thresholdApiKey);
		Param param = paramRepository.findByQccv(paramQccv);
		Optional<LabSystem> labSystem = labSystemRepository.findByApiKey(labSystemApiKey);
		Optional<ContextSource> contextSource = contextSourceRepository.findByApiKey(contextSourceApiKey);
		Optional<SampleType> sampleType = sampleTypeRepository.findByQualityControlControlledVocabulary(sampleTypeQccv);

		checkAutoPlotParameters(threshold, param, labSystem, contextSource, sampleType);

		List<File> files = getFilesForAutoPlot(labSystem.get(), sampleType.get());

//		return getDataForPlot(labSystem.get(), param, Arrays.asList(contextSource.get()), sampleType.get(),
//				files.get(files.size() - 1).getCreationDate(), files.get(0).getCreationDate());
		return getDataForPlot(labSystem.get(), threshold.get().getParam(), Arrays.asList(contextSource.get()), threshold.get().getSampleType(),
				files.get(files.size() - 1).getCreationDate(), files.get(0).getCreationDate());
	}

	public List<DataForPlot> getNonConformityPlotData(UUID labSystemApiKey, String paramQccv, UUID contextSourceApiKey,
			String sampleTypeQccv, String fileChecksum, UUID guideSetApiKey) {

		Param param = paramRepository.findByQccv(paramQccv);
		Optional<LabSystem> labSystem = labSystemRepository.findByApiKey(labSystemApiKey);
		Optional<ContextSource> contextSource = contextSourceRepository.findByApiKey(contextSourceApiKey);
		Optional<SampleType> sampleType = sampleTypeRepository.findByQualityControlControlledVocabulary(sampleTypeQccv);
		Optional<File> file = fileRepository.findOptionalByChecksum(fileChecksum);
		Optional<GuideSet> guideSet = null;

		checkNonConformityPlotParameters(param, labSystem, contextSource, sampleType, file, guideSet);

		List<File> files = getFilesForNonConformityPlot(labSystem.get(), sampleType.get(), file.get());

		return getDataForPlot(labSystem.get(), param, Arrays.asList(contextSource.get()), sampleType.get(),
				files.get(files.size() - 1).getCreationDate(), files.get(0).getCreationDate());
	}

	/**
	 * Retrieve the files for the auto plot. It will consider if the 0.0 values are
	 * no data or just a 0.0
	 * 
	 * @param threshold
	 * @param param
	 * @param labSystem
	 * @param contextSource
	 * @param sampleType
	 * @return
	 */
	private List<File> getFilesForAutoPlot(LabSystem labSystem, SampleType sampleType) {
		List<File> files;
		Pageable maxFiles = PageRequest.of(0, filesForAutoPlot,
				new Sort(Sort.Direction.DESC, Arrays.asList("creationDate")));
		files = fileRepository.findByLabSystemIdAndSampleTypeId(labSystem.getId(), sampleType.getId(), maxFiles);
		return files;
	}

	private List<File> getFilesForNonConformityPlot(LabSystem labSystem, SampleType sampleType, File file) {
		List<File> files;
		Pageable maxFiles = PageRequest.of(0, filesForAutoPlot,
				new Sort(Sort.Direction.DESC, Arrays.asList("creationDate")));
		files = fileRepository.findByLabSystemIdAndSampleTypeIdAndCreationDateLessThanEqual(labSystem.getId(),
				sampleType.getId(), file.getCreationDate(), maxFiles);
		return files;
	}

	/**
	 * Check if all the required parameters for the function are ok. It will throw
	 * exceptions if it fails.
	 * 
	 * @param threshold
	 * @param param
	 * @param labSystem
	 * @param contextSource
	 * @param sampleType
	 */
	private void checkAutoPlotParameters(Optional<Threshold> threshold, Param param, Optional<LabSystem> labSystem,
			Optional<ContextSource> contextSource, Optional<SampleType> sampleType) {
		if (!threshold.isPresent()) {
			throw new DataRetrievalFailureException("No threshold found.");
		}
		if (param == null) {
			throw new DataRetrievalFailureException("No parameter found.");
		}
		if (!labSystem.isPresent()) {
			throw new DataRetrievalFailureException("No lab system found.");
		}
		if (!contextSource.isPresent()) {
			throw new DataRetrievalFailureException("No context source found.");
		}
		if (!sampleType.isPresent()) {
			throw new DataRetrievalFailureException("No sample type found.");
		}
	}

	/**
	 * Check if the parameters for the function are setted properly, if not it will
	 * throw an exception
	 * 
	 * @param param
	 * @param labSystem
	 * @param contextSource
	 * @param sampleType
	 * @param file
	 * @param guideSet
	 */
	private void checkNonConformityPlotParameters(Param param, Optional<LabSystem> labSystem,
			Optional<ContextSource> contextSource, Optional<SampleType> sampleType, Optional<File> file,
			Optional<GuideSet> guideSet) {

		if (param == null) {
			throw new DataRetrievalFailureException("No parameter found.");
		}
		if (!labSystem.isPresent()) {
			throw new DataRetrievalFailureException("No lab system found.");
		}
		if (!contextSource.isPresent()) {
			throw new DataRetrievalFailureException("No context source found.");
		}
		if (!sampleType.isPresent()) {
			throw new DataRetrievalFailureException("No sample type found.");
		}
		if (!file.isPresent()) {
			throw new DataRetrievalFailureException("No file found.");
		}
		if (guideSet != null) {
			if (!guideSet.isPresent()) {
				throw new DataRetrievalFailureException("No guide set found.");
			}
		}
	}

}
