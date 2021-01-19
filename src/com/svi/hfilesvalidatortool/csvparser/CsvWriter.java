package com.svi.hfilesvalidatortool.csvparser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.svi.hfilesvalidatortool.config.Config;
import com.svi.hfilesvalidatortool.objects.ErrorLog;

public class CsvWriter {
	public static final String MASTERLIST_FILE = Config.getProperty("masterlistFile");
	public static final String MASTERLIST_ERROR_LOG_PATH = Config.getProperty("errorLogFolderPath");
	public String getErrorLogFilePath() {
		String fileName = null;
		File file = new File(MASTERLIST_FILE);
		File dir = new File(MASTERLIST_ERROR_LOG_PATH);
	        // true if the directory was created, false otherwise
	        if (dir.mkdirs()) {
	            System.out.println("Directory is created!");
	        } else {
	            System.out.println("Failed to create directory!");
	        }
	        fileName = MASTERLIST_ERROR_LOG_PATH+"/"+file.getName().replaceFirst("[.][^.]+$", "")+"_Errors.csv";
		return fileName;
		
	}
	
	public File createFile() {
		File myObj = null;
		try {
		      myObj = new File(getErrorLogFilePath());
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		return myObj;
	}
	private void appendHeader(FileWriter masterlist) throws IOException {
		masterlist.append("Record No.|");
		masterlist.append("Employee Id|");
		masterlist.append("Field|");
		masterlist.append("Value|");
		masterlist.append("Error\n");
	}
	private void appendErrorLog(FileWriter masterlist, ErrorLog errorLog) throws IOException {
		masterlist.append(String.valueOf(errorLog.getRecordNo()));
		masterlist.append("|");
		masterlist.append(errorLog.getEmployeeId());
		masterlist.append("|");
		masterlist.append(errorLog.getField());
		masterlist.append("|");
		masterlist.append(errorLog.getValue());
		masterlist.append("|");
		masterlist.append(errorLog.getErrorMessage());
		masterlist.append("\n");
	}
	
	public void writeErrorLogs(List<ErrorLog> errorLogs) {
		File errorLogFile = createFile();
		try {
			FileWriter masterlist = new FileWriter(errorLogFile);
			appendHeader(masterlist);
			for (ErrorLog errorLog : errorLogs) {
				appendErrorLog(masterlist, errorLog);
			}
			
			masterlist.flush();
			masterlist.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
