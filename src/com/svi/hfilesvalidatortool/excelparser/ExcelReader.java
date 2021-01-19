package com.svi.hfilesvalidatortool.excelparser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import com.svi.hfilesvalidatortool.objects.ParameterValues;
import com.svi.hfilesvalidatortool.config.Config;
import com.svi.hfilesvalidatortool.objects.DataMap;
import com.svi.hfilesvalidatortool.objects.FieldConditions;

public class ExcelReader {
	public static final String FIELD_CONFIG_FILE = Config.getProperty("fieldConfigFile");
	public static final String LOOKUP_TABLE = Config.getProperty("lookupTableFile");
	public static final String ALPHABETIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMERIC = "0123456789";
	public static final String COMMA = ",";
	public static final String DATE = "0123456789/-";
	

	public ParameterValues getColumn(String excelFilePath, int colIndex) {
		List<String> values = new ArrayList<>();
		String parameter = null;
//		String excelFilePath = "resources/Master List CDCI 02162020.xlsx";
		HSSFWorkbook wBook;
		try {
			wBook = new HSSFWorkbook(new FileInputStream(excelFilePath));
			HSSFSheet sheet = wBook.getSheetAt(0);
			Row header = sheet.getRow(0);
			if (header != null) {
				Cell cell = header.getCell(colIndex);
				if (cell != null) {
					parameter = (String) cell.getStringCellValue();
				}
			}
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (row != null) {
					Cell cell = row.getCell(colIndex);
					if (cell != null && cell.getCellTypeEnum() != CellType.BLANK) {

						if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
							String value = Boolean.toString(cell.getBooleanCellValue());
							values.add(value);
						} else {
							String value = cell.getStringCellValue();
							values.add(value);

						}
					}

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ParameterValues(parameter, values);
	}

	public List<ParameterValues> getParameterValues() {
		HSSFWorkbook wBook;
		List<ParameterValues> paramValues = new ArrayList<>();
		try {
			wBook = new HSSFWorkbook(new FileInputStream(LOOKUP_TABLE));
			HSSFSheet sheet = wBook.getSheetAt(0);
			Row row = sheet.getRow(0);
//			int totalColumns = row.getLastCellNum();
			for (int i = 0; i < row.getLastCellNum(); i++) {
				paramValues.add(getColumn(LOOKUP_TABLE, i));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paramValues;

	}


	public int getColumnIndex(String index) {
		int result = 0;
		for (int i = 0; i < index.length(); i++) {
			result *= 26;
			result += index.charAt(i) - 'A' + 1;
		}
		return result -= 1;
	}

	public List<DataMap> getColumnXmlMap() {
		List<DataMap> dataMaps = new ArrayList<>();
		HSSFWorkbook wBook;
		try {
			wBook = new HSSFWorkbook(new FileInputStream(FIELD_CONFIG_FILE));
			HSSFSheet sheet = wBook.getSheetAt(0);
			for (Row row : sheet) {
				int columnId = 0;
				String columnHeader = null;
				String xmlLabel = null;
				for (Cell cell : row) {
					if (cell.getColumnIndex() == 0) {
						columnId = getColumnIndex(cell.getStringCellValue());

					}
					if (cell.getColumnIndex() == 1) {
						columnHeader = cell.getStringCellValue();
					}
					if (cell.getColumnIndex() == 2) {
						xmlLabel = cell.getStringCellValue();
					}
				}
				dataMaps.add(new DataMap(columnId, columnHeader, xmlLabel));
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataMaps;
	}

	public List<FieldConditions> getFieldConditions() {
		List<FieldConditions> conditions = new ArrayList<>();
		HSSFWorkbook wBook;
		try {

			wBook = new HSSFWorkbook(new FileInputStream(FIELD_CONFIG_FILE));
			HSSFSheet sheet = wBook.getSheetAt(1);
			for (int i = 1; i < sheet.getLastRowNum()+1; i++) {
				Row row = sheet.getRow(i);
				String field = null;
				List<String> acceptedCharacters = new ArrayList<>();
				String acceptedCharactersRegex = "";
				int fieldLength = 0;
				String lookupTable = null;
				boolean isMandatory = false;
				for (Cell cell : row) {
					
					if (cell.getColumnIndex() == 0) {
						field = cell.getStringCellValue();

					}
					if (cell.getColumnIndex() == 1) {
						String[] accepted = cell.getStringCellValue().split(COMMA);
						for (String characters : accepted) {
							acceptedCharacters.add(characters);
						}
					}
					if (cell.getColumnIndex() == 2) {
						fieldLength = (int) cell.getNumericCellValue();
					}
					if (cell.getColumnIndex() == 3) {
						if (cell != null && cell.getCellTypeEnum() != CellType.BLANK) {
							lookupTable = cell.getStringCellValue();
						}
					}
					if (cell.getColumnIndex() == 4) {
						if (cell != null && cell.getCellTypeEnum() != CellType.BLANK) {
							String yn = cell.getStringCellValue();
							if (yn.length()!=0) {
								if (yn.toUpperCase().contains("Y")) {
									isMandatory = true;
								}
								if (yn.toUpperCase().contains("N")) {
									isMandatory = false;
								}
							}else {
								isMandatory = false;
							}
							
						}
					}
					
					for (int i1 = 0; i1 < acceptedCharacters.size(); i1++) {
						if (acceptedCharacters.get(i1).trim().equals("ALPHABETIC")) {
							acceptedCharacters.set(i1, ALPHABETIC);
						}
						if (acceptedCharacters.get(i1).trim().equals("NUMERIC")) {
							acceptedCharacters.set(i1, NUMERIC);
						}
						if (acceptedCharacters.get(i1).trim().equals("COMMA")) {
							acceptedCharacters.set(i1, COMMA);
						}
						if (acceptedCharacters.get(i1).trim().equals("DATE")){
							acceptedCharacters.set(i1, DATE);
						}
					}

				}
				for (String string : acceptedCharacters) {
					acceptedCharactersRegex += string;
				}
				conditions.add(new FieldConditions(field, acceptedCharactersRegex, fieldLength, lookupTable, isMandatory));
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conditions;
	}
}
