package eu.qcloud.communityline;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.qcloud.Instrument.Instrument;
import eu.qcloud.communitypartner.CommunityPartner;
import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.param.Param;
import eu.qcloud.sampleType.SampleType;
import eu.qcloud.traceColor.TraceColor;

@Entity
@Table(name = "c_l")
public class CommunityLine {

    @Id
    @Column(name = "c_l_i")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "c_l_s")
    @SequenceGenerator(name = "c_l_s", sequenceName = "c_l_s", allocationSize = 1)
    private Long id;

    @Column(name = "a_k", updatable = true, nullable = false, unique = true, columnDefinition = "BINARY(16)")
    @org.hibernate.annotations.Type(type = "org.hibernate.type.UUIDBinaryType")
    private UUID apiKey;

    @Column(name = "n")
    private String name;

    @ManyToOne
    @JoinColumn(name = "c_i")
    private Instrument instrument;

    @ManyToOne
    @JoinColumn(name = "p_i")
    private Param param;

    @ManyToOne
    @JoinColumn(name = "s_t_i")
    private SampleType sampleType;

    @ManyToOne
    @JoinColumn(name = "c_s_i")
    private ContextSource contextSource;

    @Column(name = "v")
    private float value;

    @OneToMany(mappedBy = "communityLine")
    private Set<CommunityLineNode> communityLineNode = new HashSet<CommunityLineNode>();

    @ManyToOne
    @JoinColumn(name = "t_c_i")
    private TraceColor traceColor;

    @ManyToOne
    @JoinColumn(name = "c_p_i")
    private CommunityPartner communityPartner;

    @Column(name = "a")
    private String alias;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public CommunityLine(Long id, UUID apiKey, String name, Instrument instrument, Param param, SampleType sampleType) {
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

    public CommunityLine(Long id, UUID apiKey, String name, Instrument instrument, Param param, SampleType sampleType,
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

    @JsonIgnore
    public Set<CommunityLineNode> getCommunityLineNode() {
        return communityLineNode;
    }

    public void setCommunityLineNode(Set<CommunityLineNode> communityLineNode) {
        this.communityLineNode = communityLineNode;
    }

    public CommunityLine(Long id, UUID apiKey, String name, Instrument instrument, Param param, SampleType sampleType,
            ContextSource contextSource, float value, Set<CommunityLineNode> communityLineNode) {
        this.id = id;
        this.apiKey = apiKey;
        this.name = name;
        this.instrument = instrument;
        this.param = param;
        this.sampleType = sampleType;
        this.contextSource = contextSource;
        this.value = value;
        this.communityLineNode = communityLineNode;
    }

    public TraceColor getTraceColor() {
        return traceColor;
    }

    public void setTraceColor(TraceColor traceColor) {
        this.traceColor = traceColor;
    }

    public CommunityPartner getCommunityPartner() {
        return communityPartner;
    }

    public void setCommunityPartner(CommunityPartner communityPartner) {
        this.communityPartner = communityPartner;
    }

    public CommunityLine(Long id, UUID apiKey, String name, Instrument instrument, Param param, SampleType sampleType,
            ContextSource contextSource, float value, Set<CommunityLineNode> communityLineNode, TraceColor traceColor,
            CommunityPartner communityPartner) {
        this.id = id;
        this.apiKey = apiKey;
        this.name = name;
        this.instrument = instrument;
        this.param = param;
        this.sampleType = sampleType;
        this.contextSource = contextSource;
        this.value = value;
        this.communityLineNode = communityLineNode;
        this.traceColor = traceColor;
        this.communityPartner = communityPartner;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public CommunityLine(Long id, UUID apiKey, String name, Instrument instrument, Param param, SampleType sampleType,
            ContextSource contextSource, float value, Set<CommunityLineNode> communityLineNode, TraceColor traceColor,
            CommunityPartner communityPartner, String alias) {
        this.id = id;
        this.apiKey = apiKey;
        this.name = name;
        this.instrument = instrument;
        this.param = param;
        this.sampleType = sampleType;
        this.contextSource = contextSource;
        this.value = value;
        this.communityLineNode = communityLineNode;
        this.traceColor = traceColor;
        this.communityPartner = communityPartner;
        this.alias = alias;
    }

}