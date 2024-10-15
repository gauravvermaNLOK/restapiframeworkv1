package liftlab.framework;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import io.restassured.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


/**
 * This class has utility functions to execute Get and put request
 * 
 *
 */
public class ApiUtil extends Reporting{

	private static Logger logger = LogManager.getLogger(ApiUtil.class);
	public ApiUtil() throws EncryptedDocumentException, IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * This is enum to store API response
	 */
	public enum Responses{
		ACTUAL_RESPONSE_CODE,
		ACTUAL_RESPONSE_MESSAGE,
		ACTUAL_RESPONSE_BODY
	}
	
	
	
	/**
	 * This function executes get request
	 * @param strGetURL - API End point
	 * @return - LinkedHashMap<Responses, String>: API response( response code, response message, response body
	 * @throws Exception
	 */
	public LinkedHashMap<Responses, String> getRequest(String strGetURL) throws Exception {

		String strFuncionName = "getRequest";
		logger.info("Started " + strFuncionName + "(" + strGetURL + ")");
		LinkedHashMap<Responses, String> output = new LinkedHashMap<Responses,String>();
		
		//Execute Get Request
		RequestSpecification requestSpecification=RestAssured.given();
		requestSpecification.headers("Content-Type", "application/json");
		Response response=requestSpecification.get(strGetURL); //call get request 
		
		//store response 
		int intCode=response.getStatusCode();
		output.put(Responses.ACTUAL_RESPONSE_CODE, Integer.toString(intCode));//added response code 
		String strActualResponseMessage = response.getStatusLine();
		output.put(Responses.ACTUAL_RESPONSE_MESSAGE, strActualResponseMessage);//added response message
		String strActualResponseBody = response.getBody().asString();
		output.put(Responses.ACTUAL_RESPONSE_BODY, strActualResponseBody);//added response body
		logger.info("Response is " + output);
		logger.info("End " + strFuncionName);
		return output;
	}
	
	
	/**
	 * This function executes put request
	 * @param strPutURL - API End point
	 * @param map - <String, String> : Key is RequestBody. Value is request body to pass in Put request
	 * @return - LinkedHashMap<Responses, String>: API response( response code, response message, response body
	 * @throws Exception
	 */
	public LinkedHashMap<Responses, String> putRequest(String strPutURL, LinkedHashMap<String,String> map) throws Exception {
		String strFuncionName = "putRequest";
		logger.info("Started " + strFuncionName + "(" + strPutURL + "," + map + ")");
		LinkedHashMap<Responses, String> output = new LinkedHashMap<Responses,String>();
		RequestSpecification requestSpecification=RestAssured.given();
		requestSpecification.headers("Content-Type", "application/json");

		String strRequestBody = map.get("RequestBody");
		requestSpecification.body(strRequestBody);  //added json request body in request
		Response response=requestSpecification.put(strPutURL); //execute post request 
		int intCode=response.getStatusCode();

		output.put(Responses.ACTUAL_RESPONSE_CODE, Integer.toString(intCode));//added code actual
		String strActualResponseMessage = response.getStatusLine();
		output.put(Responses.ACTUAL_RESPONSE_MESSAGE, strActualResponseMessage);//added response message
		String strActualResponseBody = response.getBody().asString();
		output.put(Responses.ACTUAL_RESPONSE_BODY, strActualResponseBody);//added response body
		logger.info("Response is " + output);
		logger.info("End " + strFuncionName);
		return output;
	}
	
	
}
