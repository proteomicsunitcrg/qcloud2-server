package eu.qcloud.node;

public class NodeWithTotalFiles {

    private Node node;
    private Long totalFiles;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Long getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(Long totalFiles) {
        this.totalFiles = totalFiles;
    }

    public NodeWithTotalFiles(Node node, Long totalFiles) {
        this.node = node;
        this.totalFiles = totalFiles;
    }

}