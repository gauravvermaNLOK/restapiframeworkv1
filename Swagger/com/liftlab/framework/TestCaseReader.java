package liftlab.framework;


import java.io.IOException;

import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;



/**
 * 
 * This class provides functions to read the test case and test data from Excel.
 *
 */
public class TestCaseReader extends ExcelUtil{
	
	//Workbook workbook = null;
	//Sheet sheet = null;
	
	//private FormulaEvaluator formulaEvaluator;
	protected static Logger logger = LogManager.getLogger(TestCaseReader.class);

	private int intTestStepStartRow = 1;
	private int intTestStepEndRow = 1;
	//private int intRowCountInSheet = 0; //Gives row count of sheet
	static final int TESTCASENAME_COLUMN_INDEX = 0;
	private static final int EXECUTE_COLUMN_INDEX = 1;
	private static final int TESTDATA_COLUMN_INDEX = 2;
	
	
	/*
	public static void main(String [] args) throws EncryptedDocumentException, IOException
	{
		TestCaseReader testcaseReader = new TestCaseReader("/Users/gverma/Downloads/IT-QA-Automation/Swagger/com/liftlab/testcase/SwaggerTest.xlsx", "Swagger");
		LinkedHashMap<String, LinkedHashMap<String, String>> mapTestCase = testcaseReader.getTestData();
	}*/
	
	public TestCaseReader()
	{
		
	}
	
	//protected Logger logger = LogManager.getLogger(ExcelUtilityV2.class);
	/***
	 * Initialize workbook and sheet
	 * @param strFilePath - full qualified path of file e.g. /Users/gverma/git/IT-QA-Automation/AutomationProject/N_US_USD_CAP_24_Upfront_VPS0111231000026.843.xlsx
	 * @param strSheetName - Name of sheet (or tab) inside Excel
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public TestCaseReader(String strFilePath, String strSheetName) throws EncryptedDocumentException, IOException
	{
		super(strFilePath,strSheetName);
		//String strFunctionName = "ExcelUtilityV2";
		//logger.info(strFunctionName + "(" + strFilePath + "," + strSheetName + ")");
		//FileInputStream file = new FileInputStream(new File(strFilePath));  

        // Create a Workbook object from the Excel file  
        //workbook = WorkbookFactory.create(file);  
        //logger.info(strFunctionName + ": created workbook object");
        // Get the first sheet in the workbook  
        //Iterator<Sheet> itr = workbook.sheetIterator();
       /* while (itr.hasNext())
        {
        	String strSheetName1 = itr.next().getSheetName();
        }*/
        //sheet = workbook.getSheet(strSheetName);
        //formulaEvaluator =  workbook.getCreationHelper().createFormulaEvaluator();
        //logger.info(strFunctionName + ": created sheet object");
        //getRowCount();
        //logger.info("End " + strFunctionName);
	}
	
	/***
	 * Initialize workbook and sheet
	 * @param strFilePath - full qualified path of file e.g. /Users/gverma/git/IT-QA-Automation/AutomationProject/N_US_USD_CAP_24_Upfront_VPS0111231000026.843.xlsx
	 * @param intSheetIndex - Index of sheet (or tab) inside Excel. Index is zero based i.e. first sheet index will be zero
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public TestCaseReader(String strFilePath, int intSheetIndex) throws EncryptedDocumentException, IOException
	{
		super(strFilePath,intSheetIndex);
		String strFunctionName = "TestCaseReader";
		logger.info(strFunctionName + "(" + strFilePath + "," + intSheetIndex + ")"); 
		//FileInputStream file = new FileInputStream(new File(strFilePath));  

         // Create a Workbook object from the Excel file  
        // workbook = WorkbookFactory.create(file);  
         //logger.info(strFunctionName + ": created workbook object");
         // Get the first sheet in the workbook  
         //sheet = workbook.getSheetAt(intSheetIndex);
         //logger.info(strFunctionName + ": created sheet object");
         //getRowCount();
         logger.info("End " + strFunctionName);
	}
	
//	/**
//	 * gives a row count in sheet.
//	 * To utilize this function, create a object of class and then call this function
//	 * @return number of rows in sheet
//	 */
//	private void getRowCount()
//	{
//		String strFunctionName = "getRowCount";
//		//logger.info("Start " + strFunctionName );
//		intRowCountInSheet = sheet.getPhysicalNumberOfRows();
//		//logger.info(strFunctionName + ": Row count of sheet is " + intRowCountInSheet);
//		//logger.info("End " + strFunctionName);
//	}
	
	
//	public String getValueOfGivenCell(int intRowNumber, int intColumnNumber)
//	{
//		String strFunctionName = "getRowNumberHavingGivenText";
//		//logger.info(strFunctionName + "(" + intRowNumber + "," + intColumnNumber + ")"); 
//		String strCellValue = null;
//		Row row = sheet.getRow(intRowNumber);  
//        Cell cell = row.getCell(intColumnNumber);
//        if (cell != null)
//        	strCellValue = getCellValue(cell);
//		//logger.info(strFunctionName + ": Cell value at row " + intRowNumber + " and column " + intColumnNumber + " is " +  strCellValue);
//		//logger.info("End " +  strFunctionName);
//		return strCellValue;
//	}
//	
//	
//	
//	private String getCellValue(Cell cell)
//	{
//		//public String get_cell(int intRow,int intColumnn) {
//			//logger.info("-----------------Function get_cell -: Start");
//			//Cell cell = this.sheet.getRow(intRow).getCell(intColumnn);
//			switch(cell.getCellType()) {
//			case STRING:{
//				return cell.getStringCellValue();
//				}
//			case NUMERIC:{
//				Double doubleValue=cell.getNumericCellValue();
//				int intValue=((int)cell.getNumericCellValue());
//				if(doubleValue==intValue) {
//					return Integer.toString(intValue);
//				}
//				else {
//					return  Double.toString(doubleValue);
//				}
//			}
//			case BOOLEAN:{
//				return Boolean.toString(cell.getBooleanCellValue());
//			}
//			case FORMULA:{
//				String cellText =  new DataFormatter().formatCellValue(cell, formulaEvaluator);	
//				return cellText;
//				//return this.sheet.getRow(intRow).getCell(intColumnn).getStringCellValue();
//			}
//			default:
//				break;
//			}
//			//logger.info("Function get_cell -: End-----------------");
//			return "";
//		}
//
//	
//	/**
//	 * gives last Cell index of given row. Row index is zero based
//	 * @return last Cell index of given row. Cell index is zero based
//	 */
//	public int getLastCellNumberOfRow(int intRowNumber)
//	{
//		String strFunctionName = "getLastCellNumberOfRow";
//		//logger.info("Start " + strFunctionName + "(" + intRowNumber + ")"); 
//		int intLastCellIndex = 0;
//		Row row = sheet.getRow(intRowNumber);  
//		intLastCellIndex = row.getLastCellNum() - 1;  
//		//logger.info(strFunctionName + ": Last cell index of row " + intRowNumber + " is " + intLastCellIndex);
//		//logger.info("End " +  strFunctionName);
//		return intLastCellIndex;
//	}
	
	/**
	 * returns End Row of  Test Step
	 * @param int - intStartRow - End row of  Test Step
	 * @return int - End row of  Test Step
	 */
	private int getTestStepEndRow(int intStartRow)
	{
		String strFunctionName = "getTestStepEndRow";
		logger.info("Started " + strFunctionName + "(" + intStartRow + ")");
		int intEndRow = 0;
		for (int intCurrentRow = intStartRow; intCurrentRow<=intRowCountInSheet; intCurrentRow++)
		{
			logger.info("Current row number is " + intCurrentRow);
			String strCellValue = getValueOfGivenCell(intCurrentRow, 0);
			if (strCellValue!= null && strCellValue.equalsIgnoreCase("EndStep"))
			{
				intEndRow = intCurrentRow ;
				logger.info("End row of step is " + intEndRow);
				break;
			}
		}
		logger.info("End " + strFunctionName);
		return intEndRow;
	}
	
	/**
	 * This function returns start row of next Test step
	 * @param intEndRowOfPreviousStep - End row of previous test step
	 * @return
	 */
	private int getTestStepStartRow(int intEndRowOfPreviousStep)
	{
		String strFunctionName = "getTestStepStartRow";
		logger.info("Started " + strFunctionName + "(" + intEndRowOfPreviousStep + ")");
		int intStartRow = 0;
		for (int intCurrentRow = intEndRowOfPreviousStep; intCurrentRow<=intRowCountInSheet; intCurrentRow++)
		{
			String strCellValue = getValueOfGivenCell(intCurrentRow, 0);
			if (strCellValue!= null && strCellValue.trim().length()>0 && !strCellValue.equalsIgnoreCase("EndStep") )
			{
				intStartRow = intCurrentRow ;
				logger.info("End row of step is " + intStartRow);
				break;
			}
		}
		logger.info("End " + strFunctionName);
		return intStartRow;
	}
	
	
	/**
	 * Reads the test case and test data
	 * @return LinkedHashMap<String, LinkedHashMap<String, String>>
	 * Key is test case name e.g. GetPetDetailsByStatus
	 * Value is LinkedHashMap<String, String> represents the test data e.g. {status=available}
	 *  
	 */
	public LinkedHashMap<String, LinkedHashMap<String, String>> getTestData()
	{
		String strFunctionName = "getTestData";
		logger.info("Started " + strFunctionName);
		LinkedHashMap<String, LinkedHashMap<String, String>> mapTestCase = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		//boolean boolTestStarted = false;
		while (intTestStepEndRow < intRowCountInSheet-1)
		{
			
			//if (!boolTestStarted)
			//{
			intTestStepStartRow = getTestStepStartRow(intTestStepEndRow);
			intTestStepEndRow = getTestStepEndRow(intTestStepStartRow);
			//boolTestStarted = true;
			//}
			String strExecutionFlag = getValueOfGivenCell(intTestStepStartRow, EXECUTE_COLUMN_INDEX);
			if (strExecutionFlag!= null && strExecutionFlag.trim().toLowerCase().equals("true"))
			{
				String strTestCaseName = getValueOfGivenCell(intTestStepStartRow, TESTCASENAME_COLUMN_INDEX);
				LinkedHashMap<String, String> mapTestStepData = getCurrentTestStepData(intTestStepStartRow, intTestStepEndRow);
				//intCurrentRow = intCurrentRow + 1;
				mapTestCase.put(strTestCaseName, mapTestStepData); 
			}
			//intTestStepStartRow = getTestStepStartRow(intTestStepEndRow);
			//intTestStepEndRow = getTestStepEndRow(intTestStepStartRow);

		}

		/*for (int intCurrentRow = 0 ; intCurrentRow < intRowCountInSheet ; )
		{
			intCurrentRow = intCurrentRow + 1;
			int intTestStepEndRow = getTestStepEndRow(intCurrentRow);
			
			String strExecutionFlag = getValueOfGivenCell(intCurrentRow, EXECUTE_COLUMN_INDEX);
			if (strExecutionFlag!= null && strExecutionFlag.trim().toLowerCase().equals("true"))
			{
				String strTestCaseName = getValueOfGivenCell(intCurrentRow, TESTCASENAME_COLUMN_INDEX);
				LinkedHashMap<String, String> mapTestStepData = getCurrentTestStepData(intCurrentRow, intTestStepEndRow);
				//intCurrentRow = intCurrentRow + 1;
				mapTestCase.put(strTestCaseName, mapTestStepData); 
			}
			intCurrentRow = intCurrentRow + intTestStepEndRow-1;
			
                }*/
		logger.info("Test case stored in map. Map values are " + mapTestCase);
			return mapTestCase;
            } 
	
	
	
	/**
	 * This functions returns the data of test steps e.g. {status=available}
	 * @param intStartRowIndex - Start row of Test step
	 * @param intEndRowIndex - End row of Test step
	 * @return LinkedHashMap<String, String>: Key : fieldname like status and value like available
	 */
	private LinkedHashMap<String, String> getCurrentTestStepData(int intStartRowIndex, int intEndRowIndex)
	{
		String strFunctionName = "getCurrentTestStepData";
		logger.info("Started " + strFunctionName + "(" + intStartRowIndex + "," + intEndRowIndex + ")");
		LinkedHashMap<String, String> mapTestStepData = new LinkedHashMap<String, String>();
		int intLastCellIndex = getLastCellNumberOfRow(intStartRowIndex);
		
		for (int intCurrentRowIndex = intStartRowIndex; intCurrentRowIndex<intEndRowIndex; intCurrentRowIndex++)
		{
			for (int intCurrentCellIndex = TESTDATA_COLUMN_INDEX; intCurrentCellIndex<=intLastCellIndex ; intCurrentCellIndex++)
			{
				String strFieldName = getValueOfGivenCell(intStartRowIndex,intCurrentCellIndex);
				String strCellData = getValueOfGivenCell(intStartRowIndex+1,intCurrentCellIndex);
				mapTestStepData.put(strFieldName, strCellData);
				logger.info("Test data added to map. Map values are " + mapTestStepData);
			}
			//intCurrentRowIndex = intCurrentRowIndex + 2;
		}
		logger.info("Test data of test step is " + mapTestStepData);
		return mapTestStepData;
	}
	
	

}
