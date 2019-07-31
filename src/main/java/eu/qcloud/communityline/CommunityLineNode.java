package eu.qcloud.communityline;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import eu.qcloud.node.Node;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "community_line_node")
public class CommunityLineNode {

    private long id;
    private CommunityLine communityLine;
    private Node node;

    // additional
    private boolean active;

    @Id
    @GeneratedValue
    @Column(name = "community_line_node_id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "community_line_id")
    public CommunityLine getCommunityLine() {
        return communityLine;
    }

    public void setCommunityLine(CommunityLine communityLine) {
        this.communityLine = communityLine;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id") // TODO CHECK THIS IF NOT WORKS REMOVE referenced and put id in name
    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CommunityLineNode(CommunityLine communityLine, Node node, boolean active) {
        this.communityLine = communityLine;
        this.node = node;
        this.active = active;
    }

    public CommunityLineNode() {
    }

}
