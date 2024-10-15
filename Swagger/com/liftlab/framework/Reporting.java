package liftlab.framework;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;



/**
 * 
 * This has functions to log the result on HTML Test Execution report
 *
 */
@SuppressWarnings("deprecation")
public class Reporting extends TestCaseReader {
	
	private static Logger logger = LogManager.getLogger(Reporting.class);

	String strPreviousTestCaseName = "";
	String strCurrentTestCaseName = "";
	private String strReportPath = System.getProperty("user.dir") + "/Report/Report_"+new SimpleDateFormat("ddMMyyHHmmsss.SSS").format(new Date())+"/ExtentReport.html";
	protected static ExtentReports extentReports = new ExtentReports();
	private ExtentTest testCase;
	//private ExtentTest testStep;
	
	
	
	
	public Reporting() throws EncryptedDocumentException, IOException {
		
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(strReportPath);
		extentReports.attachReporter(htmlReporter);
	}
	
	/**
	 * This function must be used after logging all results on HTML report. 
	 * Without calling this function HTML report would not be generated.
	 */
	public void generateReport()
	{
		String strFunctionName = "createTestCase";
		logger.info("Started "+ strFunctionName );
		extentReports.flush();
		logger.info("HTML extent report generated.");
		logger.info("End "+ strFunctionName);
	}
	
	/**
	 * This function creates a test case on HTML Test execution report
	 * @param strTestCaseName
	 */
	private void createTestCase(String strTestCaseName) {
		String strFunctionName = "createTestCase";
		logger.info("Started "+ strFunctionName + "(" + strTestCaseName + ")");
		strCurrentTestCaseName = strTestCaseName;
		logger.info("Previous Test case name was " + strPreviousTestCaseName + ". Current Test Case Name is " + strCurrentTestCaseName);
		if(!strPreviousTestCaseName.equals(strCurrentTestCaseName))
		{
			testCase = extentReports.createTest(strTestCaseName);
			logger.info("Created Test with name " + strTestCaseName);
			strPreviousTestCaseName = strCurrentTestCaseName;
			logger.info("Previous Test case name was " + strPreviousTestCaseName + ". Current Test Case Name is " + strCurrentTestCaseName);
		}
		logger.info("End "+ strFunctionName);
	}

//	private void logPass(String strResult) {
//		String strFunctionName = "logPass";
//		logger.info("Started "+ strFunctionName + "(" + strResult + ")"); 
//		testCase.pass(strResult);
//		logger.info("End "+ strFunctionName);
//	}
//	
//	protected void logFail(String strResult) {
//		testCase.fail(strResult);
//	}
//	
//	protected void logInfo(String strInfo) {
//		testCase.fail(strInfo);
//	}
	
	/**
	 * Logs the test execution result on HTML report, on TestNg Emailable report and in Debug logs
	 * and mark the test cases as Pass
	 * @param strTestCaseName
	 * @param strResult
	 */
	public void pass(String strTestCaseName, String strResult)
	{
		String strFunctionName = "pass";
		logger.info("Started " + strFunctionName + "(" + strResult);
		createTestCase(strTestCaseName);
		testCase.pass(strResult);
		Reporter.log(strResult);
		logger.info("Logged result on TestNg emailable report.");
		System.out.println();
		logger.info("End " + strFunctionName);
	}
	
	/**
	 * Logs the test execution result on HTML report, on TestNg Emailable report and in Debug logs
	 * and mark the test cases as fail
	 * @param strTestCaseName
	 * @param strResult
	 */
	public void fail(String strTestCaseName, String strResult)
	{
		String strFunctionName = "fail";
		logger.info("Started " + strFunctionName + "(" + strResult);
		createTestCase(strTestCaseName);
		testCase.fail(strResult);
		Reporter.log(strResult);
		Assert.fail(strResult);
		logger.info("Logged result on TestNg emailable report.");
		System.out.println();
		logger.info("End " + strFunctionName);
	}
	
	/**
	 * Logs the test execution result on HTML report, on TestNg Emailable report and in Debug logs
	 * This function should be used to put additional info
	 * @param strTestCaseName
	 * @param strResult
	 */
	public void info(String strTestCaseName, String strResult)
	{
		String strFunctionName = "info";
		logger.info("Started " + strFunctionName + "(" + strResult);
		createTestCase(strTestCaseName);
		testCase.info(strResult);
		Reporter.log(strResult);
		logger.info("Logged result on TestNg emailable report.");
		System.out.println();
		logger.info("End " + strFunctionName);
	}
	

}
