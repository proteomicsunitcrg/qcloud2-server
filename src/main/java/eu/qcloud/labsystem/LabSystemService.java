package eu.qcloud.labsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import eu.qcloud.dataSource.DataSource;
import eu.qcloud.dataSource.DataSourceRepository;
import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.file.FileRepository;
import eu.qcloud.guideset.GuideSet;
import eu.qcloud.guideset.automatic.AutomaticGuideSet;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.sampleType.SampleTypeRepository;
import eu.qcloud.security.model.User;
import eu.qcloud.security.service.UserService;
import eu.qcloud.utils.factory.QcrawlerLabSystemUtils;
import eu.qcloud.websocket.WebSocketService;

/**
 * Service for system
 *
 * @author dmancera
 *
 */
@Service
public class LabSystemService {

	@Autowired
	private LabSystemRepository systemRepository;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private WebSocketService websocketService;

	@Autowired
	private DataSourceRepository dataSourceRepository;

	@Autowired
	private SampleTypeRepository sampleTypeRepository;

	@Autowired
	private UserService userService;

	public LabSystem saveSystem(LabSystem system) {
		system.setActive(true);
		return systemRepository.save(system);
	}

	public List<LabSystem> findAllByNode(Long nodeId) {
		List<LabSystem> labSystems = systemRepository.findAllByNode(nodeId);
		List<LabSystem> labSystemsR = new ArrayList<>();
		for (LabSystem ls : labSystems) {
			// Getting its active guide sets
			List<SampleType> sampleTypes = fileRepository.findDistinctSampleTypeByLabSystemId(ls.getId());
			for (SampleType st : sampleTypes) {
				GuideSet gs = ls.getGuideActiveSetBySampleType(st.getId());
				if (gs == null) {
					// generate a guide set
					AutomaticGuideSet ags = new AutomaticGuideSet();
					ags.setIsActive(true);
					ags.setSampleType(st);
					ls.getGuideSets().add(ags);
					// ags.setTotalFiles(fileRepository.countByLabSystemIdAndSampleTypeIdAndCreationDateBetween(ls.getId(),
					// st.getId(), gs.getStartDate(), gs.getEndDate()));
					ags.setLabSystemTotalFiles(
							fileRepository.countByLabSystemIdAndSampleTypeId(ls.getId(), st.getId()));
				} else {
					// get guideset total files
					gs.setTotalFiles(fileRepository.countByLabSystemIdAndSampleTypeIdAndCreationDateBetween(ls.getId(),
							st.getId(), gs.getStartDate(), gs.getEndDate()));
					gs.setLabSystemTotalFiles(fileRepository.countByLabSystemIdAndSampleTypeId(ls.getId(), st.getId()));
				}
			}
			labSystemsR.add(ls);
		}

		return labSystemsR;
	}

	public Optional<LabSystem> findSystemBySystemId(Long systemId) {
		return systemRepository.findById(systemId);
	}

	public boolean enableDisable(LabSystem ls) {
		ls = systemRepository.findByApiKey(ls.getApiKey()).get();
		ls.setActive(!ls.isActive());
		systemRepository.save(ls);
		websocketService.sendEnableDisableLS(getManagerFromSecurityContext().getNode(), ls);
		return true;
	}

	public Optional<LabSystem> findSystemByApiKey(UUID apikey) {
		return systemRepository.findByApiKey(apikey);
	}

	public List<LabSystem> findLabSystemByDataSourceId(Long dataSourceId) {
		return systemRepository.findAllByDataSourcesId(dataSourceId);
	}

	/**
	 * This method will check if the datasources belongs to the node, and will
	 * return an array with the proper data source entities from the database. If
	 * not it will throw an exception
	 *
	 * @param dataSources
	 */
	private List<DataSource> checkDataSources(User manager, List<DataSource> dataSources) {
		List<DataSource> data = new ArrayList<>();
		for (DataSource ds : dataSources) {
			DataSource d = dataSourceRepository.findByApiKey(ds.getApiKey());
			if (!d.getNode().getApiKey().equals(manager.getNode().getApiKey())) {
				throw new InvalidActionException("You do not own this data sources");
			}
			data.add(d);
		}
		return data;
	}

	public void addDataSourcesToLabSystem(User manager, LabSystem labSystem, List<DataSource> dataSources) {
		// check if data sources belongs to the lab
		dataSources = checkDataSources(manager, dataSources);
		// add the data sources
		labSystem.setDataSources(dataSources);
		// save the system
		websocketService.sendNewLabSystemToNodeUsers(manager.getNode(), saveSystem(labSystem));

	}

	public QcrawlerLabSystemList findAllByNodeForQcrawler(Long nodeId) {
		List<LabSystem> labSystems = systemRepository.findAllByNode(nodeId);
		List<QcrawlerLabSystem> qCrawlerLabSystems = new ArrayList<>();
		setDefaultSampleTypesIfNotDefined(labSystems);
		for (LabSystem ls : labSystems) {
			qCrawlerLabSystems.add(QcrawlerLabSystemUtils.createQcrawlerLabSystem(ls));
		}

		return new QcrawlerLabSystemList(qCrawlerLabSystems);
	}

	private void setDefaultSampleTypesIfNotDefined(List<LabSystem> labSystems) {
		// get the defaults
		List<SampleType> defaultSampleTypes = sampleTypeRepository.findByIsMainSampleTypeTrue();
		for (LabSystem ls : labSystems) {
			if (ls.getMainDataSource().getCv().getSampleTypes().size() == 0) {
				ls.getMainDataSource().getCv().setSampleTypes(defaultSampleTypes);
			} else {
				for (SampleType defaultSampleType : defaultSampleTypes) {
					if (!ls.getMainDataSource().getCv().getSampleTypes().stream().anyMatch(s -> s
							.getSampleTypeCategory().getId() == defaultSampleType.getSampleTypeCategory().getId())) {
						ls.getMainDataSource().getCv().getSampleTypes().add(defaultSampleType);
					}
				}
			}
		}

	}

	/*
	 * Helper classes
	 */
	/**
	 * Get the current user from the security context
	 *
	 * @return the logged user
	 */
	private User getManagerFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User manager = userService.getUserByUsername(authentication.getName());
		return manager;
	}

}
