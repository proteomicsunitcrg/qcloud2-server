package eu.qcloud.intranet.node;

import eu.qcloud.node.Node;

public class NodeAndStats {
    private Node node;
    private Long filesLastWeek;

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


    
    
}