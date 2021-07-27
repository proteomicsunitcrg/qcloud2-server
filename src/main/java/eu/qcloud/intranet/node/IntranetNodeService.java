package eu.qcloud.intranet.node;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.exceptions.NotFoundException;
import eu.qcloud.file.File;
import eu.qcloud.file.FileRepository;
import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemRepository;
import eu.qcloud.node.Node;
import eu.qcloud.node.NodeRepository;
import eu.qcloud.security.model.User;

@Service
public class IntranetNodeService {

    @Autowired
    NodeRepository nodeRepo;

    @Autowired
    LabSystemRepository lsRepo;

    @Autowired
    FileRepository fileRepo;

    public List<Node> getAll() {
        return nodeRepo.findAll();
    }

    public List<NodeAndStats> getAllStats() {
        List<Node> nodes = nodeRepo.findAll();
        List<NodeAndStats> nodeAndStats = new ArrayList<>();
        for (Node node : nodes) {
            getNodeStatsLastWeek(node);
            nodeAndStats.add(new NodeAndStats(node, getNodeStatsLastWeek(node)));
        }
        return nodeAndStats;
    }

    private Long getNodeStatsLastWeek(Node node) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.WEEK_OF_MONTH, -1);
        Date week1Ago = today.getTime();
        List<LabSystem> labsystems = getLsByNodeApiKey(node.getApiKey());
        Long filesLastWeek = 0l;
        for (LabSystem ls : labsystems) {
            filesLastWeek += fileRepo.countByLabSystemApiKeyAndCreationDateAfter(ls.getApiKey(), week1Ago);
        }
        return filesLastWeek;
    }

    private Long getNodeTotalFiles(Node node) {
        List<LabSystem> labsystems = getLsByNodeApiKey(node.getApiKey());
        Long filesLastWeek = 0l;
        for (LabSystem ls : labsystems) {
            filesLastWeek += fileRepo.countByLabSystemApiKey(ls.getApiKey());
        }
        return filesLastWeek;
    }

    public Node getByApiKey(UUID apiKey) {
        Optional<Node> node = nodeRepo.findByApiKeyOptional(apiKey);
        if (node.isPresent()) {
            return node.get();
        } else {
            throw new NotFoundException("Node not found");
        }
    }

    public List<LabSystem> getLsByNodeApiKey(UUID apiKey) {
        Node node = nodeRepo.findByApiKey(apiKey);
        return lsRepo.findAllByNode(node.getId());
    }

    public LSStats getLSStats(UUID apiKey) {
        Calendar cal = Calendar.getInstance(); // Get current date/month i.e 27 Feb, 2012
        cal.add(Calendar.MONTH, -1); // Go to date, 6 months ago 27 July, 2011
        // cal.set(Calendar.DAY_OF_MONTH, 1); //set date, to make it 1 July, 2011
        Date date = cal.getTime();
        LSStats stats = new LSStats();
        stats.setFilesLastMonths(fileRepo.countByLabSystemApiKeyAndCreationDateAfter(apiKey, date));
        stats.setTotalFiles(fileRepo.countByLabSystemApiKey(apiKey));
        return stats;
    }

    // TODO: Improve the date handling and the Longs to store the results!
    public NodeStats getNodeStats(UUID apiKey) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.MONTH, -1);
        Date month1Ago = today.getTime();
        today = Calendar.getInstance();
        today.add(Calendar.MONTH, -6);
        Date month6Ago = today.getTime();
        today = Calendar.getInstance();
        today.add(Calendar.WEEK_OF_MONTH, -1);
        Date week1Ago = today.getTime();
        List<LabSystem> nodeLabSystems = getLsByNodeApiKey(apiKey);
        NodeStats nodeStats = new NodeStats();
        // I have to this because if i use
        // nodeStats.setTotatFiles(nodeStats.getTotalFiles() += SOMETHING); fails
        Long nodeStatsTotalFiles = 0l;
        Long nodeStatsTotalFiles6M = 0l;
        Long nodeStatsTotalFiles1M = 0l;
        Long nodeStatsTotalFiles1W = 0l;
        for (LabSystem ls : nodeLabSystems) {
            nodeStats.setTotalFiles(nodeStatsTotalFiles += fileRepo.countByLabSystemApiKey(ls.getApiKey()));
            nodeStats.setFiles1Month(nodeStatsTotalFiles1M += fileRepo
                    .countByLabSystemApiKeyAndCreationDateAfter(ls.getApiKey(), month1Ago));
            nodeStats.setFiles6Month(nodeStatsTotalFiles6M += fileRepo
                    .countByLabSystemApiKeyAndCreationDateAfter(ls.getApiKey(), month6Ago));
            nodeStats.setFiles1Week(nodeStatsTotalFiles1W += fileRepo
                    .countByLabSystemApiKeyAndCreationDateAfter(ls.getApiKey(), week1Ago));
        }
        return nodeStats;
    }

    public HomePageStats getHomePageStats() {
        List<Node> nodes = new ArrayList<>();
        List<LabSystem> labsystems = new ArrayList<>();
        Calendar today = Calendar.getInstance();
        today.add(Calendar.MONTH, -6);
        Date month6Ago = today.getTime();
        HomePageStats stats = new HomePageStats();
        Optional <File> lastFile = fileRepo.findTopByOrderByIdDesc();
        if (lastFile.isPresent()) {
            stats.setTotalFiles(fileRepo.findTopByOrderByIdDesc().get().getId());
        }
        List<File> files6MonthsAgo = fileRepo.findByCreationDateAfter(month6Ago);
        for (File file : files6MonthsAgo) {
            if (!nodes.contains(file.getLabSystem().getMainDataSource().getNode())) {
                nodes.add(file.getLabSystem().getMainDataSource().getNode());
            }
            if (!labsystems.contains(file.getLabSystem())) {
                labsystems.add(file.getLabSystem());
            }
        }
        stats.setTotalLs(new Long(labsystems.size()));
        stats.setTotalNodes(new Long(nodes.size()));
        return stats;
    }

    public GeneralStats getGeneralStats() {
        Long labSystemsWithGuidesets = 0l;
        List<Node> nodes = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<String> countries = new ArrayList<>();
        List<LabSystem> labsystems = new ArrayList<>();
        Calendar today = Calendar.getInstance();
        today.add(Calendar.MONTH, -6);
        Date month6Ago = today.getTime();
        GeneralStats stats = new GeneralStats();
        stats.setTotalFiles(fileRepo.count());
        stats.setFilesLast6Months(fileRepo.countByCreationDateAfter(month6Ago));
        List<File> files6MonthsAgo = fileRepo.findByCreationDateAfter(month6Ago);
        for (File file : files6MonthsAgo) {
            if (!nodes.contains(file.getLabSystem().getMainDataSource().getNode())) {
                if (!countries.contains(file.getLabSystem().getMainDataSource().getNode().getCountry())) {
                    countries.add(file.getLabSystem().getMainDataSource().getNode().getCountry());
                }
                if (file.getLabSystem().getGuideSets().size() != 0) {
                    labSystemsWithGuidesets = +Long.valueOf(file.getLabSystem().getGuideSets().size());
                }
                nodes.add(file.getLabSystem().getMainDataSource().getNode());
                users.addAll(file.getLabSystem().getMainDataSource().getNode().getUsers());
            }
            if (!labsystems.contains(file.getLabSystem())) {
                labsystems.add(file.getLabSystem());
            }
            // System.out.println(file.getFilename());
        }
        stats.setNodesWithFiles6Months(new Long(nodes.size()));
        stats.setUsersWithFiles6Months(new Long(users.size()));
        stats.setCountriesWithFilesLast6Months(new Long(countries.size()));
        stats.setNodesWithGuidesets(labSystemsWithGuidesets);
        stats.setLabSystemsWithFiles(new Long(labsystems.size()));
        return stats;
    }

    public List<NodeAndStats> getTop3NodesByFile() {
        List<Node> nodes = nodeRepo.findAll();
        List<NodeAndStats> nodeAndStats = new ArrayList<>();
        for (Node node : nodes) {
            getNodeStatsLastWeek(node);
            NodeAndStats nas = new NodeAndStats();
            nas.setNode(node);
            nas.setTotalFiles(getNodeTotalFiles(node));
            nodeAndStats.add(nas);
        }
        nodeAndStats.sort(Comparator.comparing(NodeAndStats::getTotalFiles).reversed());
        nodeAndStats.subList(3, nodeAndStats.size()).clear();
        return nodeAndStats;

    }

    public LabSystem getSystemByApiKey(UUID apiKey) {
        Optional <LabSystem> ls = lsRepo.findByApiKey(apiKey);
        if (ls.isPresent()) {
            return ls.get();
        } else {
            throw new NotFoundException("Ls not found");
        }

    }

    public Node getNodeByLsApiKey(UUID apiKey) {
        LabSystem ls = getSystemByApiKey(apiKey);
        return ls.getMainDataSource().getNode();
    }
}