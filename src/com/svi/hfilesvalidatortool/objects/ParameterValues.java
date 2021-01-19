package com.svi.hfilesvalidatortool.objects;

import java.util.List;

public class ParameterValues {
	private String parameter;
	private List<String> values;
	public ParameterValues(String parameter, List<String> values) {
		this.parameter = parameter;
		this.values = values;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	public boolean valuesEmpty(){
		boolean isEmpty = true;
		if(!this.values.isEmpty()) {
			isEmpty = false;
		}
		return isEmpty;
		
	}
	
}
