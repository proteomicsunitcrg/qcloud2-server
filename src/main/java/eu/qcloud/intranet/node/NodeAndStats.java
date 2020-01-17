package eu.qcloud.intranet.node;

import eu.qcloud.node.Node;

public class NodeAndStats {
    private Node node;
    private Long filesLastWeek;
    private Long totalFiles; 

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Long getFilesLastWeek() {
        return filesLastWeek;
    }

    public void setFilesLastWeek(Long filesLastWeek) {
        this.filesLastWeek = filesLastWeek;
    }

    public NodeAndStats() {
    }

    public NodeAndStats(Node node, Long filesLastWeek) {
        this.node = node;
        this.filesLastWeek = filesLastWeek;
    }

    public Long getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(Long totalFiles) {
        this.totalFiles = totalFiles;
    }

    // @Override
    // public int compareTo(NodeAndStats u) {
    //   if (getTotalFiles() == null || u.getTotalFiles() == null) {
    //     return 0;
    //   }
    //   return getTotalFiles().compareTo(u.getTotalFiles());
    // }
}