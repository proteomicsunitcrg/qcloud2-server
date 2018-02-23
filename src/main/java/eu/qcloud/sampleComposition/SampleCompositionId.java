package eu.qcloud.sampleComposition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SampleCompositionId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1554686L;

	@Column(nullable = false, updatable = false)
	private Long sampleTypeId;
	@Column(nullable = false, updatable = false)
	private Long peptideId;
	
	public SampleCompositionId() {}

	public Long getSampleTypeId() {
		return sampleTypeId;
	}

	public void setSampleTypeId(Long sampleTypeId) {
		this.sampleTypeId = sampleTypeId;
	}

	public Long getPeptideId() {
		return peptideId;
	}

	public void setPeptideId(Long peptideId) {
		this.peptideId = peptideId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((peptideId == null) ? 0 : peptideId.hashCode());
		result = prime * result + ((sampleTypeId == null) ? 0 : sampleTypeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SampleCompositionId other = (SampleCompositionId) obj;
		if (peptideId == null) {
			if (other.peptideId != null)
				return false;
		} else if (!peptideId.equals(other.peptideId))
			return false;
		if (sampleTypeId == null) {
			if (other.sampleTypeId != null)
				return false;
		} else if (!sampleTypeId.equals(other.sampleTypeId))
			return false;
		return true;
	}
	
	
	
	
}
