package com.svi.hfilesvalidatortool.dataprocessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.svi.hfilesvalidatortool.csvparser.CsvReader;
import com.svi.hfilesvalidatortool.csvparser.CsvWriter;
import com.svi.hfilesvalidatortool.objects.ErrorLog;

public class ErrorLogger {
//	public static boolean isAllCaps(String value) {
//		String allCapsRegex = "[A-Z]+";
//		if (value.matches(allCapsRegex)) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	public static boolean isValidDate(String inDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		CsvReader csvreader = new CsvReader();
		FieldValidator validator = new FieldValidator();
		CsvWriter csvWriter = new CsvWriter();
		int recordNo = 0;
		List<ErrorLog> errorLog = new ArrayList<>();
		List<Map<String, String>> mappedField = csvreader.getRecordsFieldValueMap();
		for (Map<String, String> map : mappedField) {
			String employeeId = validator.getEmployeeId(map);
			boolean isMarried = validator.isMarried(map);
			recordNo++;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (!validator.isMandatoryAndNotEmpty(entry)) {
					if (isMarried) {
						errorLog.add(new ErrorLog(recordNo, employeeId, entry.getKey(), entry.getValue(),
								ErrorMessageBuilder.buildEmptyMandatoryFieldErrorMessage(entry)));
					} else {
						if (!entry.getKey().contains("spouse")) {
							errorLog.add(new ErrorLog(recordNo, employeeId, entry.getKey(), entry.getValue(),
									ErrorMessageBuilder.buildEmptyMandatoryFieldErrorMessage(entry)));
						}
					}
				} else {
					if (entry.getValue().endsWith("\s")) {
						errorLog.add(new ErrorLog(recordNo, employeeId, entry.getKey(), entry.getValue(),
								ErrorMessageBuilder.buildEndsWithSpaceErrorMessage(entry)));
					}
					if (entry.getValue().length() != 0 && !validator.areFieldCharactersValid(entry)) {
						if (entry.getKey().contains("date")) {
							if (!isValidDate(entry.getValue())) {
								errorLog.add(new ErrorLog(recordNo, employeeId, entry.getKey(), entry.getValue(),
										ErrorMessageBuilder.buildInvalidDateErrorMessage(entry)));
//								System.out.println(entry.getKey() + " " + entry.getValue()
//										+ ErrorMessageBuilder.buildInvalidDateErrorMessage(entry));
							}
						} else {
							errorLog.add(new ErrorLog(recordNo, employeeId, entry.getKey(), entry.getValue(),
									ErrorMessageBuilder.buildInvalidCharErrorMessage(entry)));
//							System.out.println(entry.getKey() + "= " + entry.getValue()
//									+ ErrorMessageBuilder.buildInvalidCharErrorMessage(entry));
						}

					}
					if (entry.getValue().length() != 0 && !validator.isFieldLengthValid(entry)) {
						errorLog.add(new ErrorLog(recordNo, employeeId, entry.getKey(), entry.getValue(),
								ErrorMessageBuilder.buildFieldLengthErrorMessage(entry)));
//						System.out.println(entry.getKey() + "= " + entry.getValue()
//								+ ErrorMessageBuilder.buildFieldLengthErrorMessage(entry));
					}
					if (entry.getValue().length() != 0 && !validator.isFieldValueValidFromLookUpTable(entry)) {
						errorLog.add(new ErrorLog(recordNo, employeeId, entry.getKey(), entry.getValue(),
								ErrorMessageBuilder.buildInvalidValueFromLookUpTableErrorMessage(entry)));
//						System.out.println(entry.getKey() + "= " + entry.getValue()
//								+ ErrorMessageBuilder.buildInvalidValueFromLookUpTableErrorMessage(entry));
					}
					if (entry.getValue().length() != 0 &&entry.getKey().contains("middle_name")) {
						if (entry.getValue().endsWith(".")) {
							entry.setValue(entry.getValue().substring(0, entry.getValue().length()-1));
						}
						if (entry.getValue().length() <= 1) {
							errorLog.add(new ErrorLog(recordNo, employeeId, entry.getKey(), entry.getValue(),
									ErrorMessageBuilder.buildMiddleNameErrorMessage(entry)));
						}
						
					} 
//						System.out.println(entry.getKey() + "= " + entry.getValue()
//								+ ErrorMessageBuilder.buildInvalidValueFromLookUpTableErrorMessage(entry));
				}

			}
		}
		for (ErrorLog error : errorLog) {
			System.out.println("<" + error.getRecordNo() + "|" + error.getEmployeeId() + "|" + error.getField() + "|"
					+ error.getValue() + "|" + error.getErrorMessage() + ">");
		}
		csvWriter.writeErrorLogs(errorLog);
	}

}
