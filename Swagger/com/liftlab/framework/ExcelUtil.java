package liftlab.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	
	
	protected Workbook workbook = null;
	protected Sheet sheet = null;
	protected FormulaEvaluator formulaEvaluator;
	protected int intRowCountInSheet = 0; //Gives row count of sheet
	private static Logger logger = LogManager.getLogger(ExcelUtil.class);

	
	public ExcelUtil()
	{
		System.out.println();
	}
	
	/**
	 * Initialize workbook and sheet
	 * @param strFilePath - full qualified path of file
	 * @param strSheetName - Name of sheet
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public ExcelUtil(String strFilePath, String strSheetName) throws EncryptedDocumentException, IOException
	{
		String strFunctionName = "ExcelUtil";
		logger.info(strFunctionName + "(" + strFilePath + "," + strSheetName + ")");
		FileInputStream file = new FileInputStream(new File(strFilePath));  

        // Create a Workbook object from the Excel file  
        workbook = WorkbookFactory.create(file);  
        logger.info(strFunctionName + ": created workbook object");

        sheet = workbook.getSheet(strSheetName);
        logger.info(strFunctionName + ": created sheet object");
        formulaEvaluator =  workbook.getCreationHelper().createFormulaEvaluator();
        getRowCount();
        logger.info("End " + strFunctionName);
	}
	
	/***
	 * Initialize workbook and sheet
	 * @param strFilePath - full qualified path of file
	 * @param intSheetIndex - Index of sheet (or tab) inside Excel. Index is zero based i.e. first sheet index will be zero
	 * @throws EncryptedDocumentException
	 * @throws IOException
	 */
	public ExcelUtil(String strFilePath, int intSheetIndex) throws EncryptedDocumentException, IOException
	{
		String strFunctionName = "TestCaseReader";
		logger.info(strFunctionName + "(" + strFilePath + "," + intSheetIndex + ")"); 
		FileInputStream file = new FileInputStream(new File(strFilePath));  

         // Create a Workbook object from the Excel file  
         workbook = WorkbookFactory.create(file);  
         logger.info(strFunctionName + ": created workbook object");
         // Get the sheet in the workbook  
         sheet = workbook.getSheetAt(intSheetIndex);
         logger.info(strFunctionName + ": created sheet object");
         formulaEvaluator =  workbook.getCreationHelper().createFormulaEvaluator();
         
         getRowCount();
         //logger.info("End " + strFunctionName);
	}
	
	/**
	 * gives a row count in sheet.
	 * To utilize this function, create a object of class and then call this function
	 * @return number of rows in sheet
	 */
	private void getRowCount()
	{
		String strFunctionName = "getRowCount";
		logger.info("Start " + strFunctionName );
		intRowCountInSheet = sheet.getPhysicalNumberOfRows();
		logger.info(strFunctionName + ": Row count of sheet is " + intRowCountInSheet);
		logger.info("End " + strFunctionName);
	}
	
	/**
	 * Get the value of cell
	 * @param intRowNumber
	 * @param intColumnNumber
	 * @return Value of cell in form of String
	 */
	protected String getValueOfGivenCell(int intRowNumber, int intColumnNumber)
	{
		String strFunctionName = "getRowNumberHavingGivenText";
		logger.info(strFunctionName + "(" + intRowNumber + "," + intColumnNumber + ")"); 
		String strCellValue = null;
		Row row = sheet.getRow(intRowNumber);  
        Cell cell = row.getCell(intColumnNumber);
        if (cell != null)
        	strCellValue = getCellValue(cell);
		logger.info("End " +  strFunctionName);
		return strCellValue;
	}
	
	
	
	/**
	 * Returns value of given cell
	 * @param cell
	 * @return value of cell in form of string
	 */
	private String getCellValue(Cell cell)
	{
		String strFunctionName = "getCellValue";
		logger.info("Started " + strFunctionName + "(" + cell + ")");
		//public String get_cell(int intRow,int intColumnn) {
			//logger.info("-----------------Function get_cell -: Start");
			//Cell cell = this.sheet.getRow(intRow).getCell(intColumnn);
		CellType cellType = cell.getCellType();
		String strCellText = null;
		logger.info("Cell type is " + cellType);
			switch(cellType) {
			case STRING:{
				strCellText = cell.getStringCellValue();
				break;
				}
			case NUMERIC:{
				Double doubleValue=cell.getNumericCellValue();
				int intValue=((int)cell.getNumericCellValue());
				if(doubleValue==intValue) {
					strCellText=  Integer.toString(intValue);
				}
				else {
					strCellText =  Double.toString(doubleValue);
				}
				break;
			}
			case BOOLEAN:{
				strCellText = Boolean.toString(cell.getBooleanCellValue());
				break;
			}
			case FORMULA:{
				strCellText =  new DataFormatter().formatCellValue(cell, formulaEvaluator);	
				break;
				//return cellText;
				//return this.sheet.getRow(intRow).getCell(intColumnn).getStringCellValue();
			}
			default:
				break;
			}
			logger.info("Cell text is " + strCellText);
			logger.info("End " + strFunctionName);
			return strCellText;
		}

	
	/**
	 * gives last Cell index of given row. Row index is zero based
	 * @return last Cell index of given row. Cell index is zero based
	 */
	public int getLastCellNumberOfRow(int intRowNumber)
	{
		String strFunctionName = "getLastCellNumberOfRow";
		logger.info("Start " + strFunctionName + "(" + intRowNumber + ")"); 
		int intLastCellIndex = 0;
		Row row = sheet.getRow(intRowNumber);  
		intLastCellIndex = row.getLastCellNum() - 1;  
		logger.info(strFunctionName + ": Last cell index of row " + intRowNumber + " is " + intLastCellIndex);
		logger.info("End " +  strFunctionName);
		return intLastCellIndex;
	}
	

}
