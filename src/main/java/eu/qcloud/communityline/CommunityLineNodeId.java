package eu.qcloud.communityline;

import java.io.Serializable;

import javax.persistence.Embeddable;
@Embeddable
public class CommunityLineNodeId implements Serializable {

    public Long communityLine;
    public Long node;

    public Long getCommunityLine() {
        return communityLine;
    }

    public void setCommunityLine(Long communityLine) {
        this.communityLine = communityLine;
    }

    public Long getNode() {
        return node;
    }

    public void setNode(Long node) {
        this.node = node;
    }

    public CommunityLineNodeId(Long communityLine, Long node) {
        this.communityLine = communityLine;
        this.node = node;
    }

    public CommunityLineNodeId() {
    }

}