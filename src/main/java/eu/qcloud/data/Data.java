package eu.qcloud.data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.qcloud.contextSource.ContextSource;
import eu.qcloud.file.File;
import eu.qcloud.param.Param;

@Entity
@Table(name="data")		
public class Data {
	
	@JsonIgnore
	@EmbeddedId
	private DataId dataId;
	
	@ManyToOne
	@JoinColumn(name="paramId",insertable=false, updatable= false)
	private Param param;
		
	@ManyToOne
	@JoinColumn(name="contextSourceId",insertable=false, updatable= false)
	private ContextSource contextSource;
	
	@ManyToOne
	@JoinColumn(name="fileId",insertable=false, updatable= false)
	private File file;
	
	
	private Float value;

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}
	public Data() {}

	public Data(Param param, ContextSource contextSource, File file) {
		this.param = param;
		this.contextSource = contextSource;
		this.file = file;
	}

	public DataId getDataId() {
		return dataId;
	}

	public void setDataId(DataId dataId) {
		this.dataId = dataId;
	}

	public Param getParam() {
		return param;
	}

	public void setParam(Param param) {
		this.param = param;
	}

	public ContextSource getContextSource() {
		return contextSource;
	}

	public void setContextSource(ContextSource contextSource) {
		this.contextSource = contextSource;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	
}
