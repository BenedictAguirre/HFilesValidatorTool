
I.Tool Name:HFilesValidatorTool
II. Tool Description: 
	This program validates the records from the Masterlist CSV File.
    	It returns an Error Log CSV File containing the record number, employee id, field and value that has an error and the error message.
	A field is be valid if its value:
	1. Doesn't contain invalid characters 
		a. every field have corresponding valid characters located at the fieldconfig.xls, formatcondition sheet.
		b. ALPHABETIC = all alphabetical characters, NUMERIC = all numerical characters, COMMA = ",", ALL CHARS = all characters are valid, DATE = NUMERICAL + "/"&"-"
	2. Doesn't exceed Field Lenght
		a. every field have corresponding Field lengths located at the fieldconfig.xls, formatcondition sheet.
	3. Is not empty when Mandatory
		a. every field have corresponding is mandatory indicator located at the fieldconfig.xls, formatcondition sheet.
		b. Y/y = is mandatory, null/N/n = not mandatory
	4. Is found from the lookup table 
		a. some fields has corresponding lookup tables located at the fieldconfig.xls, formatcondition sheet.
	5. Doesn't end with space
	6. Is a Date and is in correct date format
		a.the correct date format should be (MM/dd/yyyy)
III. How To Use
	1.Preparation:
		a. to redirect the path of the masterlist csv file and the desired destination of the error log csv file, open config/config.priperties and change the value of masterlistFile and/or errorLogFolderPath
		b. to check/add/change values from the lookup tables, open resources/lookup table.xls, you can change or add values of the lookup table 
		c. to check/add/change field conditions(field length, valid characters, lookup table name, is mandatoryindicator), open open resources/fieldconfig.xls, formatcondition sheet and adjust values
			>to add all alphabetical characters, add ALPHABETIC to the valid characters column
			>to add all numerical characters, add NUMERIC to the valid characters column
			>to add comma(,), add COMMA to the valid characters column
			>to add all characters, add ALLCHARS to the valid characters column
			>to add date characters(NUMERICAL+"/"+"-"), add NUMERIC to the valid characters column
			>to add extra special characters, add Doublequote(")(all the special characters you want. space = \s)"Doublequote(") (e.g. "\s-/."
			>valid characters column is comma delimited (e.g. ALPHABETICAL,NUMERICAL,COMMA,"\s-/.")
			>to make a field mandatory, add Y or y to the is mandatory column
			>to make a field notmandatory, add N or n to the is mandatory column or just leave it empty.
			>to add a lookup table to a field, add a lookup table name to the Lookup table column. make sure that the added lookup table name is present in lookup table.xls
	2. Running: 
		a. Once all the preparations are set, double click the HFilesValidator.jar. 
		b. Go the the path declared in config/config.properties errorLogFolderPath. look for the masterlist file name declared in masterlistFile that now have "_Error" with its name.