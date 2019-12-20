package eu.qcloud.communityline;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.qcloud.node.Node;

@Entity
@Table(name = "c_l_n")
public class CommunityLineNode {

    private long id;
    private CommunityLine communityLine;
    private Node node;

    // additional
    private boolean active;

    @Id
    @GeneratedValue
    @Column(name = "c_l_n_i")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "c_l_i")
    public CommunityLine getCommunityLine() {
        return communityLine;
    }

    public void setCommunityLine(CommunityLine communityLine) {
        this.communityLine = communityLine;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "i") // TODO CHECK THIS IF NOT WORKS REMOVE referenced and put id in name
    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Column(name = "a")
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
