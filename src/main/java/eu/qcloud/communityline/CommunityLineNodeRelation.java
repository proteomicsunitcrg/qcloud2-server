package eu.qcloud.communityline;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import eu.qcloud.node.Node;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@IdClass(CommunityLineNodeId.class)
@Table(name = "community_line_node")
public class CommunityLineNodeRelation implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference(value = "communityLine")
    @JoinColumn(name = "community_line_id", referencedColumnName = "id")
    private CommunityLine communityLine;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference(value = "node")
    @JoinColumn(name = "node_id", referencedColumnName = "id")
    private Node node;

    @Column(name = "is_active")
    private boolean isActive;

    public CommunityLine getCommunityLine() {
        return communityLine;
    }

    public void setCommunityLine(CommunityLine communityLine) {
        this.communityLine = communityLine;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public CommunityLineNodeRelation() {
    }

    public CommunityLineNodeRelation(CommunityLine communityLine, Node node, boolean isActive) {
        this.communityLine = communityLine;
        this.node = node;
        this.isActive = isActive;
    }

    // public CommunityLineNodeRelation(CommunityLine communityLine, boolean active)
    // {
    // this.communityLine = communityLine;
    // // this.node = node;
    // this.active = active;
    // }

}
