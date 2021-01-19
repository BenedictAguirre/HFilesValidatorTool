package com.svi.hfilesvalidatortool.objects;

public class DataMap {
	private int columnId;
	private String columnHeader;
	private String xmlLabel;

	public DataMap(int columnId, String columnHeader, String xmlLabel) {
		this.columnId = columnId;
		this.columnHeader = columnHeader;
		this.xmlLabel = xmlLabel;
	}
	public int getColumnId() {
		return columnId;
	}
	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}
	public String getColumnHeader() {
		return columnHeader;
	}
	public void setColumnHeader(String columnHeader) {
		this.columnHeader = columnHeader;
	}
	public String getXmlLabel() {
		return xmlLabel;
	}
	public void setXmlLabel(String xmlLabel) {
		this.xmlLabel = xmlLabel;
	}


}
