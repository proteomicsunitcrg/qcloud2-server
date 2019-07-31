package eu.qcloud.communityline;

import java.util.List;
import java.util.UUID;

public class Holder {
private List<Long> nodeKeyString;
private CommunityLine lineKeyString;

    public Holder() {
    }

    public Holder(List<Long> nodeKeyString, CommunityLine lineKeyString) {
        this.nodeKeyString = nodeKeyString;
        this.lineKeyString = lineKeyString;
    }

    public List<Long> getNodeKeyString() {
        return nodeKeyString;
    }

    public void setNodeKeyString(List<Long> nodeKeyString) {
        this.nodeKeyString = nodeKeyString;
    }

    public CommunityLine getLineKeyString() {
        return lineKeyString;
    }

    public void setLineKeyString(CommunityLine lineKeyString) {
        this.lineKeyString = lineKeyString;
    }



}
