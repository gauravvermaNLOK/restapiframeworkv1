package liftlab;

import java.io.IOException;

import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


import liftlab.framework.ApiUtil;

//import liftlab.framework.PropertyReader;
//import liftlab.framework.TestCaseReader;
import liftlab.framework.Javautil;
import liftlab.framework.PropertyReader;
import liftlab.framework.TestCaseReader;



public class TestCases{
	
	
	private static Logger logger = LogManager.getLogger(TestCases.class);
	private LinkedHashMap<String, LinkedHashMap<String, String>> mapTestCase = null; //Store test case
	private PropertyReader propertyReader = null;
	private String strBaseURL = null;
	private ApiUtil apiUtil= null;
//	private String strTestCaseFilePath = null;
//	private String strTestCaseSheetName = null;
	private LinkedHashMap<String, String> runtimeOutput = new LinkedHashMap<String, String>(); //To hold run time values
	
	
	@Parameters(value={"TestCaseFilePath", "TestCaseSheetName"})
	@BeforeSuite
	public void init(String strTestCaseFilePath, String strTestCaseSheetName) throws Exception
	{
		String strFunctionName = "init";
		logger.info("Started " + strFunctionName);
		if (System.getProperty("TestCaseFilePath")!= null && System.getProperty("TestCaseFilePath").trim().length()>0)
			strTestCaseFilePath = System.getProperty("TestCaseFilePath");
		if (System.getProperty("TestCaseSheetName")!= null && System.getProperty("TestCaseSheetName").trim().length()>0)
			strTestCaseSheetName = System.getProperty("TestCaseSheetName");
		
		String strTestCaseFileAbsolutePath = System.getProperty("user.dir") + strTestCaseFilePath;
		logger.info("Test case file relative path is " + strTestCaseFilePath + ". Test Case sheet name is " + strTestCaseSheetName + ". Test case file absolute path is " + strTestCaseFileAbsolutePath);
		
		//Read test case from Excel
		TestCaseReader testCaseReader = new TestCaseReader(strTestCaseFileAbsolutePath, strTestCaseSheetName);
		mapTestCase = testCaseReader.getTestData();
		
		String strPropertFilePath = System.getProperty("user.dir")+"/com/liftlab/apiproperties.properties";
		propertyReader = new PropertyReader(strPropertFilePath);
		strBaseURL = propertyReader.getValue("BaseUrl");
		
		apiUtil = new ApiUtil();
	}
	
	
	@Test(priority = 1)
	public void GetPetDetailsByStatus() throws Exception
	{
		String strTestName = "GetPetDetailsByStatus";
		
		try {
			LinkedHashMap<String, String> mapTestParam = getTestParam(strTestName);
			String strPetStatus = mapTestParam.get("status");
			String strPetDeatils = mapTestParam.get("out_PetDetails");
			String strApiPath =  propertyReader.getValue("getPetDeatilsByStatusPath");
			String strApiEndpoint = strBaseURL.concat(strApiPath).replace("${status}", strPetStatus);
			
			LinkedHashMap<ApiUtil.Responses, String> apiResponse = apiUtil.getRequest(strApiEndpoint);
			int intActualResponseCode = Integer.parseInt(apiResponse.get(ApiUtil.Responses.ACTUAL_RESPONSE_CODE));
			String strActualResponseMessage = apiResponse.get(ApiUtil.Responses.ACTUAL_RESPONSE_MESSAGE);
			String strActualResponseBody = apiResponse.get(ApiUtil.Responses.ACTUAL_RESPONSE_BODY);
			String strAdditonalInfo = "Request URL is " + strApiEndpoint + "\n. Response message is " + strActualResponseMessage + ". Actual response is " + strActualResponseBody;
			if (intActualResponseCode == 200)
			{
				apiUtil.pass(strTestName, "Fectched Pet details scuccessfully. \n\n" + strAdditonalInfo);
				//Reporter.log("Fectched Pet details scuccessfully. " + strAdditonalInfo);
				runtimeOutput.put(strPetDeatils, strActualResponseBody);
			}
			else
			{
				//Reporter.log("Error in fectching Pet details." + strAdditonalInfo);
				apiUtil.fail(strTestName, "Error in fectching Pet details. " + strAdditonalInfo);
			}
		} catch (Exception e) {
			apiUtil.fail(strTestName, "Failed in fectching Pet details. \n\n" + Javautil.getCustomStackTrace(e));
			e.printStackTrace();
		}
	}
	
//	@Test(priority = 1)
//	public void GetPetDetailsByStatus() throws Exception
//	{
//		String strTestName = "GetPetDetailsByStatus";
//		LinkedHashMap<String, String> mapTestParam = getTestParam(strTestName);
//		String strPetStatus = mapTestParam.get("status");
//		String strPetDeatils = mapTestParam.get("out_PetDetails");
//		String strApiPath =  propertyReader.getValue("getPetDeatilsByStatusPath");
//		String strApiEndpoint = strBaseURL.concat(strApiPath).replace("${status}", strPetStatus);
//		
//		LinkedHashMap<ApiUtil.Responses, String> apiResponse = apiUtil.getRequest(strApiEndpoint);
//		int intActualResponseCode = Integer.parseInt(apiResponse.get(ApiUtil.Responses.ACTUAL_RESPONSE_CODE));
//		String strActualResponseMessage = apiResponse.get(ApiUtil.Responses.ACTUAL_RESPONSE_MESSAGE);
//		String strActualResponseBody = apiResponse.get(ApiUtil.Responses.ACTUAL_RESPONSE_BODY);
//		
//		//Assert.assertEquals(intActualResponseCode, 200, "GetPetDetailsByStatus: Request URL is " + strApiEndpoint + ". Response message is " + strActualResponseMessage + ". Actual response is " + strActualResponseBody);
//		String strAdditonalInfo = "Request URL is " + strApiEndpoint + "\n. Response message is " + strActualResponseMessage + ". Actual response is " + strActualResponseBody;
//		if (intActualResponseCode == 200)
//		{
//			apiUtil.pass(strTestName, "Fectched Pet details scuccessfully. \n\n" + strAdditonalInfo);
//			//Reporter.log("Fectched Pet details scuccessfully. " + strAdditonalInfo);
//			runtimeOutput.put(strPetDeatils, strActualResponseBody);
//		}
//		else
//		{
//			//Reporter.log("Error in fectching Pet details." + strAdditonalInfo);
//			apiUtil.fail(strTestName, "Error in fectching Pet details. " + strAdditonalInfo);
//		}
//	}
	
	@Test(priority = 2)
	public void UpdatePetDetails() throws Exception
	{
		String strTestName = "UpdatePetDetails";
		try {
			LinkedHashMap<String, String> mapTestParam = getTestParam(strTestName);
			String strPetDetailsKey = mapTestParam.get("PetDetails");
			String strIdKey = mapTestParam.get("out_Id");
			String strPetDeatilsJson = runtimeOutput.get(strPetDetailsKey);
			JsonNode jsonPet  = getPetDetailsOfPetAtGivenIndexFromAllPetDetails(0, strPetDeatilsJson);
			String strIdOfFirstPet = getPetId(jsonPet);
			String strPutBody = createPutBody(jsonPet.toString(), mapTestParam);
//		String strIdOfFirstPet= "";
//		String strFirstPetJson = "";
//		try {
//			ObjectMapper mapper = new ObjectMapper();
//			JsonNode firstPet =  mapper.readTree(strPetDeatilsJson).get(0);
//			strIdOfFirstPet = firstPet.get("id").asText();
//			strFirstPetJson = firstPet.toString();
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
			//if (strPetId == null)
			//{
				
				//strFirstPetJson = mapper.readTree(strPetDeatilsJson).get(0).asText();
			//}
			
			apiUtil.info(strTestName, "Put request body is " + strPutBody);
			mapTestParam.put("RequestBody", strPutBody);
			//String strApiPath =  propertyReader.getValue("getPetDeatilsByStatusPath");
			//String strApiEndpoint = strBaseURL.concat(strApiPath).replace("${status}", strPetStatus);
			//apiUtil.putRequest(mapTestParam);
			LinkedHashMap<ApiUtil.Responses, String> apiResponse = apiUtil.putRequest(strBaseURL, mapTestParam);
			int intActualResponseCode = Integer.parseInt(apiResponse.get(ApiUtil.Responses.ACTUAL_RESPONSE_CODE));
			String strActualResponseMessage = apiResponse.get(ApiUtil.Responses.ACTUAL_RESPONSE_MESSAGE);
			String strActualResponseBody = apiResponse.get(ApiUtil.Responses.ACTUAL_RESPONSE_BODY);
			
			//Assert.assertEquals(intActualResponseCode, 200, "GetPetDetailsByStatus: Request URL is " + strApiEndpoint + ". Response message is " + strActualResponseMessage + ". Actual response is " + strActualResponseBody);
			String strAdditonalInfo = "Request URL is " + strBaseURL + "\n. Response message is " + strActualResponseMessage + ". Actual response is " + strActualResponseBody;
			if (intActualResponseCode == 200)
			{
				//Reporter.log("Fectched Pet details scuccessfully. " + strAdditonalInfo);
				apiUtil.pass(strTestName, "Updated Pet details scuccessfully. \n\n" + strAdditonalInfo);
				runtimeOutput.put(strIdKey, strIdOfFirstPet);
			}
			else
			{
				apiUtil.fail(strTestName, "Failed in upadting Pet details. \n\n" + strAdditonalInfo);
				//Reporter.log("Error in fectching Pet details." + strAdditonalInfo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			apiUtil.fail(strTestName, "Failed in upadting Pet details. \n\n" + Javautil.getCustomStackTrace(e));
			e.printStackTrace();
		} 
	}
	
	
	@Test(priority = 3)
	public void GetPetDetailsById() throws Exception
	{
		String strTestName = "GetPetDetailsById";
		try {
			LinkedHashMap<String, String> mapTestParam = getTestParam(strTestName);
			String strPetId = mapTestParam.get("Id");
			if (strPetId!= null && runtimeOutput.get(strPetId)!= null)
				strPetId = runtimeOutput.get(strPetId);
			String strApiPath =  propertyReader.getValue("getPetDeatilsByIdPath");
			String strApiEndpoint = strBaseURL.concat(strApiPath).replace("${id}", strPetId);
			LinkedHashMap<ApiUtil.Responses, String> apiResponse = apiUtil.getRequest(strApiEndpoint);
			int intActualResponseCode = Integer.parseInt(apiResponse.get(ApiUtil.Responses.ACTUAL_RESPONSE_CODE));
			String strActualResponseMessage = apiResponse.get(ApiUtil.Responses.ACTUAL_RESPONSE_MESSAGE);
			String strActualResponseBody = apiResponse.get(ApiUtil.Responses.ACTUAL_RESPONSE_BODY);
			
			//Assert.assertEquals(intActualResponseCode, 200, "GetPetDetailsByStatus: Request URL is " + strApiEndpoint + ". Response message is " + strActualResponseMessage + ". Actual response is " + strActualResponseBody);
			String strAdditonalInfo = "Request URL is " + strApiEndpoint + "\n. Response message is " + strActualResponseMessage + ". Actual response is " + strActualResponseBody;
			if (intActualResponseCode == 200)
			{
				apiUtil.pass(strTestName, "Fectched Pet details scuccessfully. \n\n" + strAdditonalInfo);
				//Reporter.log("Fectched Pet details scuccessfully. " + strAdditonalInfo);
			}
			else
			{
				apiUtil.fail(strTestName, "Error in fectching Pet details. \n\n" + strAdditonalInfo);
				//Reporter.log("Error in fectching Pet details." + strAdditonalInfo);
			}
		} catch (Exception e) {
			apiUtil.fail(strTestName, "Failed in fectching Pet details. \n\n" + Javautil.getCustomStackTrace(e));
			e.printStackTrace();
		} 
		
	}
	
	private JsonNode getPetDetailsOfPetAtGivenIndexFromAllPetDetails(int intIdIndex, String strPetDeatilsJson)
	{
		//String strPetId = mapTestParam.get("id");
				//String strPetJsonAtGivenIndex = "";
				//String strIdOfFirstPet= "";
				JsonNode jsonPet = null;
				try {
					ObjectMapper mapper = new ObjectMapper();
					jsonPet =  mapper.readTree(strPetDeatilsJson).get(intIdIndex);
					//strIdOfFirstPet = firstPet.get("id").asText();
					//strPetJsonAtGivenIndex = jsonPet.toString();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return jsonPet;
	}
	
	private String getPetId(JsonNode jsonPet)
	{
		String strPetId = "";
		try {
			//ObjectMapper mapper = new ObjectMapper();
			strPetId =  jsonPet.get("id").asText();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strPetId;
		//strIdOfFirstPet = firstPet.get("id").asText();
	}
	
	
	private LinkedHashMap<String, String> getTestParam(String strTestName)
	{
		LinkedHashMap<String, String> mapTestParam = null;
		for (String strKey : mapTestCase.keySet())
		{
			if (strKey!= null && strKey.startsWith(strTestName))
			{
				mapTestParam = mapTestCase.get(strKey);
				mapTestCase.remove(strKey);
				break;
			}
		}
		return mapTestParam;
	}
	
	
	private String createPutBody(String strJson, LinkedHashMap<String, String> mapFieldsToUpdate) throws JsonProcessingException, IOException
	{
		
		//try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(strJson);
			for (String strFieldName : mapFieldsToUpdate.keySet())
			{
				switch (strFieldName)
				{
				case "id":
					((ObjectNode) node).put("id", mapFieldsToUpdate.get(strFieldName));
					break;
				case "name":
					((ObjectNode) node).put("name", mapFieldsToUpdate.get(strFieldName));
					break;
				case "status":
					((ObjectNode) node).put("status", mapFieldsToUpdate.get(strFieldName));
					break;
				case "tag":
					JsonNode tag = node.get("tags").get(0);
					((ObjectNode) tag).put("name", mapFieldsToUpdate.get(strFieldName));
					break;
				case "category":
					JsonNode category = node.get("category");
					((ObjectNode) category).put("name", mapFieldsToUpdate.get(strFieldName));
					break;
				default:
					break;
				}
			}
		//} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		 return node.toString();
		//org.json.JSONObject jsonobject = new JSONObject(strJson);
		
	}
	
	@AfterSuite
	void tearDown()
	{
		apiUtil.generateReport();
	}
	
}
