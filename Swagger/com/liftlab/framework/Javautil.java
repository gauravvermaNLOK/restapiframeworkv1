package liftlab.framework;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;

public class Javautil {

	public static String getCustomStackTrace(Exception cause) {
		//Exception rootCause = cause;
		String strExceptionMsg = cause.getMessage() == null ? "" : cause.getMessage();
		//while (rootCause.getCause() != null && rootCause.getCause() != rootCause)
		//	rootCause = rootCause.getCause();
		StringBuilder strMethodsStack = new StringBuilder();
		StackTraceElement[] arrStackTraceElement = cause.getStackTrace();
		StringWriter errors = new StringWriter();
		cause.printStackTrace(new PrintWriter(errors));
		for (StackTraceElement stackTraceElement : arrStackTraceElement) {
			if (!stackTraceElement.isNativeMethod()) {
				strMethodsStack.append(stackTraceElement.getClassName() + ":" + stackTraceElement.getMethodName() + ":"
						+ stackTraceElement.getLineNumber()  + "<-");
						/*+ ":" + rootCause.getMessage() + ":"
						+ rootCause.getLocalizedMessage() + ":" + cause.getCause() + ":" + "<-");*/
			}
		}
		return strExceptionMsg + "\n" + strMethodsStack.toString();
		/*
		if (rootCause.getLocalizedMessage()!= null)
			return strMethodsStack.toString() + "***Exception: " + rootCause.getLocalizedMessage() + "***";
		else
			return strMethodsStack.toString();
			*/
	}
	
	public static int getRandomNum() {
		Random unqueID = new Random();
		int ranNum = unqueID.nextInt(10000);
		return ranNum;

	}
	
}
