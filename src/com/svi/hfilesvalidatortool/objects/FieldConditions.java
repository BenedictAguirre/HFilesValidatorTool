package com.svi.hfilesvalidatortool.objects;


public class FieldConditions {
	private String field;
	private String acceptedCharactersRegex;
	private int fieldLength;
	private String lookupTable;
	private boolean isMandatory;
	public FieldConditions(String field,String acceptedCharactersRegex,int fieldLength,String lookupTable, boolean isMandatory) {
		this.field = field;
		this.acceptedCharactersRegex = acceptedCharactersRegex;
		this.fieldLength = fieldLength;
		this.lookupTable = lookupTable;
		this.isMandatory = isMandatory;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getAcceptedCharactersRegex() {
		return acceptedCharactersRegex;
	}
	public void setAcceptedCharactersRegex(String acceptedCharacters) {
		this.acceptedCharactersRegex = acceptedCharacters;
	}
	public int getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(int fieldLength) {
		this.fieldLength = fieldLength;
	}
	public String getLookupTable() {
		return lookupTable;
	}
	public void setLookupTable(String lookupTable) {
		this.lookupTable = lookupTable;
	}
	public boolean isMandatory() {
		return isMandatory;
	}
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
}
