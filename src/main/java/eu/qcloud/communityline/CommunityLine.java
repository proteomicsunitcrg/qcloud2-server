package eu.qcloud.communityline;

import javax.persistence.Id;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.qcloud.Instrument.Instrument;
import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.node.Node;
import eu.qcloud.param.Param;
import eu.qcloud.sampleType.SampleType;

@Entity
@Table(name = "community_line")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class CommunityLine {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "community_line_seq")
    @SequenceGenerator(name = "community_line_seq", sequenceName = "community_line_seq", allocationSize = 1)
    private long id;

    @Column(name = "apiKey", updatable = true, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    @org.hibernate.annotations.Type(type = "org.hibernate.type.UUIDBinaryType")
    private UUID apiKey;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "cv_id")
    private Instrument instrument;

    @ManyToOne
    @JoinColumn(name = "param_id")
    private Param param;

    @ManyToOne
    @JoinColumn(name = "sample_type_id")
    private SampleType sampleType;

    @ManyToOne
    @JoinColumn(name = "context_source_id")
    private ContextSource contextSource;

    @Column(name = "value")
    private float value;

    @ManyToMany
    private List<Node> nodes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getApiKey() {
        return apiKey;
    }

    public void setApiKey(UUID apiKey) {
        this.apiKey = apiKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }

    public SampleType getSampleType() {
        return sampleType;
    }

    public void setSampleType(SampleType sampleType) {
        this.sampleType = sampleType;
    }

    public CommunityLine(long id, UUID apiKey, String name, Instrument instrument, Param param, SampleType sampleType) {
        this.id = id;
        this.apiKey = apiKey;
        this.name = name;
        this.instrument = instrument;
        this.param = param;
        this.sampleType = sampleType;
    }

    public CommunityLine() {
    }

    public ContextSource getContextSource() {
        return contextSource;
    }

    public void setContextSource(ContextSource contextSource) {
        this.contextSource = contextSource;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public CommunityLine(long id, UUID apiKey, String name, Instrument instrument, Param param, SampleType sampleType,
            ContextSource contextSource, float value) {
        this.id = id;
        this.apiKey = apiKey;
        this.name = name;
        this.instrument = instrument;
        this.param = param;
        this.sampleType = sampleType;
        this.contextSource = contextSource;
        this.value = value;
    }

    @Override
    public String toString() {
        return "CommunityLine [apiKey=" + apiKey + ", contextSource=" + contextSource + ", id=" + id + ", instrument="
                + instrument + ", name=" + name + ", param=" + param + ", sampleType=" + sampleType + ", value=" + value
                + "]";
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public CommunityLine(long id, UUID apiKey, String name, Instrument instrument, Param param, SampleType sampleType,
            ContextSource contextSource, float value, List<Node> nodes) {
        this.id = id;
        this.apiKey = apiKey;
        this.name = name;
        this.instrument = instrument;
        this.param = param;
        this.sampleType = sampleType;
        this.contextSource = contextSource;
        this.value = value;
        this.nodes = nodes;
    }

}