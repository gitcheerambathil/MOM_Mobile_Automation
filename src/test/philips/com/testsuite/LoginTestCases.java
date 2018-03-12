package test.philips.com.testsuite;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import test.philips.com.network.HttpUtils;
import test.philips.com.utils.ReusableMethods;
import test.philips.com.utils.SetupDriver;

public class LoginTestCases extends SetupDriver{
	String appName = "DoctorApp";
	String reportOrder="child1";
	int numberOfTestScenarios=0;
	String excelTabName = "Login";
	int row;
	String testCaseName = "Default";
	String expectedDataLocators[]={};
	@BeforeTest
	private void intitalizeReport()throws Exception{
		 child1 =report.startTest("Login Test Cases"); //Initialize child report for this test case
		 numberOfTestScenarios=ReusableMethods.getNumberOfTestScenarios(excelTabName); //Get the number of test scenarios from the test data excel file
	}
	@Test
	private void loginTest() throws Exception{
		for(row=1;row<2;row++){
			 try{
				 ReusableMethods.initiateTest(excelTabName, row, appName);
				 ReusableMethods.loginToApplication();
				 ReusableMethods.verifyData(expectedDataLocators,reportOrder,excelTabName,row);				
				 //if(ReusableMethods.TEST_DATA.get("TYPE").equalsIgnoreCase("VerifyPage"))
					// ReusableMethods.logoutOfApplication();

			 }catch(Exception e){
				 e.printStackTrace();	
				 try{
					 ReusableMethods.suspendTest(excelTabName, row, reportOrder, e);
				 }catch(Exception e2){
					 e2.printStackTrace();	
				 }
			 }	
		}
	 }	

	@AfterTest
	private void endReport ()throws Exception{
		ReusableMethods.appendChild(reportOrder); 
	}
}

