package liftlab.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/** Reads the value of property form given property file
 *
 */
public class PropertyReader {
	
	private static Logger logger = LogManager.getLogger(PropertyReader.class);
	private Properties prop;
	private String strPropertyFileName="";
	
	/**
	 * This function accepts a path to the properties file and loads the properties file from location
	 * @param path Path to the properties file
	 * @throws IOException
	 */
	public PropertyReader(String strPath) throws IOException {
		String strFunctionName = "PropertyReader";
		logger.info("Start " + strFunctionName + "(" + strPath + ")");
		File file = new File(strPath);
		strPropertyFileName=file.getName();
		FileInputStream fileInput = new FileInputStream(file);
		prop = new Properties();
		prop.load(fileInput);
		logger.info("End " + strFunctionName);
	}

	
	
	/**
	 * This function accepts a property and returns string value for given property
	 * @param strProperty Property name
	 * @return Property value
	 * @throws Exception 
	 */
	public String getValue(String strPropertyName) throws Exception {
		String strFunctionName = "getValue";
		logger.info("Started " + strFunctionName + "(" +  strPropertyName + ")");
		String strValue = this.prop.getProperty(strPropertyName);
		if(this.prop.get(strPropertyName)==null) {
			throw new Exception("getValue: "+strPropertyName+" not found in "+ strPropertyFileName +"properties file");
		}
		logger.info("Value of " + strPropertyName + " is " + strValue);
		logger.info("End " + strFunctionName);
		return strValue;
	}

}
