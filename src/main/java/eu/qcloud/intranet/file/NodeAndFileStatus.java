package eu.qcloud.intranet.file;

import eu.qcloud.node.Node;

public class NodeAndFileStatus {
    private Node node;
    private boolean isDataOk;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public boolean isDataOk() {
        return isDataOk;
    }

    public void setDataOk(boolean isDataOk) {
        this.isDataOk = isDataOk;
    }

    public NodeAndFileStatus() {
    }

    public NodeAndFileStatus(Node node, boolean isDataOk) {
        this.node = node;
        this.isDataOk = isDataOk;
    }

    
    
}