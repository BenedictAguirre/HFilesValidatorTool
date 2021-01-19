package com.svi.hfilesvalidatortool.dataprocessor;

import java.util.Map;

public class ErrorMessageBuilder {
	public static String buildInvalidCharErrorMessage(Map.Entry<String, String> entry) {
		return "\"At "+entry.getKey()+" the value, "+entry.getValue()+" contains invalid character/s\""; 
	}
	public static String buildFieldLengthErrorMessage(Map.Entry<String, String> entry) {
		return "\"At "+entry.getKey()+", the value, "+entry.getValue()+", has Invalid number of characters\"";
	}
	public static String buildInvalidValueFromLookUpTableErrorMessage(Map.Entry<String, String> entry) {
		return "\"At "+entry.getKey()+", the value, "+entry.getValue()+", doesn't exist from the lookup table\"";
	}
	public static String buildEmptyMandatoryFieldErrorMessage(Map.Entry<String, String> entry) {
		return "\""+entry.getKey()+" must not be null\"";
	}
	public static String buildEndsWithSpaceErrorMessage(Map.Entry<String, String> entry) {
		return "\"At "+entry.getKey()+", the value, "+entry.getValue()+" has space at the end of the text after name\"";
	}
	public static String buildInvalidDateErrorMessage(Map.Entry<String, String> entry) {
		return "\"At "+entry.getKey()+", the value, "+entry.getValue()+" is correct but displayed format is different (should be mm/dd/yyyy)\"";
	}
//	public static String buildAllCapsErrorMessage(Map.Entry<String, String> entry) {
//		return "\"At "+entry.getKey()+", the value, "+entry.getValue()+" should not be all caps\"";
//	}	
	public static String buildMiddleNameErrorMessage(Map.Entry<String, String> entry) {
		return "\"At "+entry.getKey()+", the value, "+entry.getValue()+" should be a name not initials\"";
	}	
	
}
