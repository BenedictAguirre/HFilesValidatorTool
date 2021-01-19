package com.svi.hfilesvalidatortool.csvparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.svi.hfilesvalidatortool.config.Config;
import com.svi.hfilesvalidatortool.excelparser.ExcelReader;
import com.svi.hfilesvalidatortool.objects.DataMap;
import com.svi.hfilesvalidatortool.objects.Record;

public class CsvReader {
	public static final String MASTERLIST_FILE = Config.getProperty("masterlistFile");
	ExcelReader read = new ExcelReader();

	public List<Record> getListOfRecords() {
		List<Record> csvRecords = new ArrayList<>();
		try {

			Path path = Paths.get(MASTERLIST_FILE);
			BufferedReader br = Files.newBufferedReader(path,StandardCharsets.ISO_8859_1);
	            String line = br.readLine();
	            while (line != null) {
	            	if (!line.startsWith("employee_id")) {
	            		String[] records = line.split("\\|");
		                csvRecords.add(new Record(records));
					}
	                line = br.readLine();
	            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return csvRecords;

	}

	public List<Map<String, String>> getRecordsFieldValueMap() {
		List<DataMap> dataMaps = read.getColumnXmlMap();
		List<Map<String, String>> listOfRecords = new ArrayList<>();
		List<Record> csvRecords = getListOfRecords();
		for (Record rec : csvRecords) {
			Map<String, String> fieldValues = new HashMap<>();
			for (DataMap dataMap : dataMaps) {
				for (int i = 0; i < rec.getRecords().length; i++) {
					String record = rec.getRecords()[i];
					if (i == dataMap.getColumnId()) {
						if (record.contains("E+") || record.contains("E-")) {
							String fixedValue = String.format("%.0f", Double.parseDouble(record));
							record = fixedValue;
						}
//						System.out.print("<" + dataMap.getColumnHeader() + ": " + rec.getRecords()[i] + ">");
						fieldValues.put(dataMap.getColumnHeader(), record);
					}
				}
			}
			listOfRecords.add(fieldValues);
		}
		return listOfRecords;
	}
//	public static void main(String[] args) {
//		CsvReader r = new CsvReader();
//		List<Record> lr = r.getListOfRecords();
//		for (Record record : lr) {
//			for (String record2 : record.getRecords()) {
//				System.out.print("<"+record2+">");
//			}
//			System.out.println("");
//		}
//	}
}
