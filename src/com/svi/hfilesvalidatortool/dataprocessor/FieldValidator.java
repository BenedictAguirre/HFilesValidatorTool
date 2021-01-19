package com.svi.hfilesvalidatortool.dataprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.svi.hfilesvalidatortool.excelparser.ExcelReader;
import com.svi.hfilesvalidatortool.objects.FieldConditions;
import com.svi.hfilesvalidatortool.objects.ParameterValues;

public class FieldValidator {
	ExcelReader reader = new ExcelReader();
	List<FieldConditions> fieldConditions = reader.getFieldConditions();
	List<ParameterValues> lookupTable = reader.getParameterValues();

	public boolean areFieldCharactersValid(Map.Entry<String, String> entry) {
		boolean isValid = false;
		for (FieldConditions condition : fieldConditions) {
			if (entry.getKey().equals(condition.getField())) {
				if (condition.getAcceptedCharactersRegex().contains("ALL CHARS")
						|| entry.getValue().matches("[" + condition.getAcceptedCharactersRegex() + "]+")) {
					isValid = true;
//					System.out.print(":"+entry.getValue().matches("[" + condition.getAcceptedCharactersRegex()+ "]+"));
				}
			}
		}

		return isValid;
	}

	public boolean isFieldLengthValid(Map.Entry<String, String> entry) {
		boolean isValid = false;
		for (FieldConditions condition : fieldConditions) {
			if (entry.getKey().equals(condition.getField())) {
				if (entry.getKey().contains("tin") || entry.getKey().contains("ss_no")
						|| entry.getKey().contains("philhealth_id_no") || entry.getKey().contains("pagibig_mid_no")) {
					if (entry.getValue().length() == condition.getFieldLength()) {
						isValid = true;
					}
				} else {
					if (entry.getValue() != null && !entry.getValue().equals("")
							&& entry.getValue().length() <= condition.getFieldLength()) {
						isValid = true;
//					System.out.print(":"+entry.getValue().matches("[" + condition.getAcceptedCharactersRegex()+ "]+"));
					}
				}
			}
		}

		return isValid;
	}

	public boolean isFieldValueValidFromLookUpTable(Map.Entry<String, String> entry) {
		boolean isValid = false;
		if (!hasLookupTable(entry)) {
			isValid = true;
		} else {
			for (FieldConditions condition : fieldConditions) {
				if (entry.getKey().equals(condition.getField())) {
					for (ParameterValues parameterValues : lookupTable) {
						if (condition.getLookupTable() != null && condition.getLookupTable().length() != 0) {
							if (parameterValues.getValues().contains(entry.getValue())
									|| parameterValues.getValues().isEmpty()) {
								isValid = true;
							}
						}
					}
				}
			}
		}
		return isValid;
	}

	public boolean isMandatoryAndNotEmpty(Map.Entry<String, String> entry) {
		boolean isValid = false;
		for (FieldConditions condition : fieldConditions) {
			if (entry.getKey().equals(condition.getField())) {
				if (condition.isMandatory() == true) {
					if (entry.getValue().length() != 0) {
						isValid = true;
					} else {
						isValid = false;
					}
				} else {
					isValid = true;
				}

			}
		}
		return isValid;
	}

	public boolean hasLookupTable(Map.Entry<String, String> entry) {
		boolean hasLookup = false;
		List<String> lookups = new ArrayList<String>();
		for (ParameterValues parameterValues : lookupTable) {
			lookups.add(parameterValues.getParameter());
		}
		for (FieldConditions condition : fieldConditions) {
			if (entry.getKey().equals(condition.getField())) {
				if (condition.getLookupTable() != null && condition.getLookupTable().length() != 0) {
					if (lookups.contains(condition.getLookupTable())) {
						hasLookup = true;
//							System.out.println(entry.getKey()+":"+entry.getValue()+" has lookup");
					}
				}
			}
		}
		return hasLookup;
	}

	public String getEmployeeId(Map<String, String> map) {
		String id = null;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getKey().equals("employee_id") && entry.getKey().length() != 0) {
				id = entry.getValue();
			}
		}
		return id;
	}

	public boolean isMarried(Map<String, String> map) {
		boolean isMarried = false;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getKey().equals("civil_status") && entry.getKey().length() != 0) {
				if (entry.getValue().toUpperCase().equals("MARRIED")) {
					isMarried = true;
				}
			}
		}
		return isMarried;
	}
}
