package test.philips.com.testsuite;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import test.philips.com.network.HttpUtils;
import test.philips.com.utils.ReusableMethods;
import test.philips.com.utils.SetupDriver;

public class Login extends ReusableMethods{
	int numberOfTestScenarios,row;
	String testCaseName,appName,excelTabName;
	String expectedDataLocators[]={"LOGIN_PASSWORD_VALIDATION_MESSAGE","LOGIN_PASSWORD_VALIDATION_TOAST"};
	//String expectedDataLocators[]={};
	String stepNo="1";
	@BeforeTest
	private void beforeTest()throws Exception{
		 appName = "CMAApp";
		 excelTabName = "Login";
		 numberOfTestScenarios=getNumberOfTestScenarios(excelTabName);
	}
	@Test
	private void test() throws Exception{
		for(row=1;row<numberOfTestScenarios;row++){
			 try{
				 Boolean flag=initiateTest(excelTabName, row);
				 if(flag==true){
					 launchApplication(appName);
					 loginToCMAApplication();
					 verifyData(expectedDataLocators,excelTabName,row,stepNo);
				 }
			 }catch(Exception e){
				 e.printStackTrace();	
				 try{
					 suspendTest(excelTabName, row, e,stepNo);
				 }catch(Exception e2){
					 e2.printStackTrace();	
				 } 
			 }	
			stopVideoAndLogRecording();
		}
	 }	
	@AfterTest
	private void endReport ()throws Exception{
		driver.quit();
	}
}

