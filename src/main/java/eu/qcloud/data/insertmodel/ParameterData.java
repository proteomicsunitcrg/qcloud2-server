package eu.qcloud.data.insertmodel;

import java.util.List;

import eu.qcloud.param.Param;

public class ParameterData {

	private Param parameter;

	private List<DataValues> values;

	public Param getParameter() {
		return parameter;
	}

	public void setParameter(Param parameter) {
		this.parameter = parameter;
	}

	public List<DataValues> getValues() {
		return values;
	}

	public void setValues(List<DataValues> values) {
		this.values = values;
	}

}
