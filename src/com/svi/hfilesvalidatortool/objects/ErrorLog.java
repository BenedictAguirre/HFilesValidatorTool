package com.svi.hfilesvalidatortool.objects;

public class ErrorLog {
	private int recordNo;
	private String employeeId;
	private String field;
	private String value;
	private String errorMessage;
	public ErrorLog(int recordNo, String employeeId, String field, String value,String errorMessage) {
		this.recordNo = recordNo;
		this.employeeId = employeeId;
		this.field = field;
		this.value = value;
		this.errorMessage =errorMessage;
	}
	public int getRecordNo() {
		return recordNo;
	}
	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
