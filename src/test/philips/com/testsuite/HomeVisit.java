package test.philips.com.testsuite;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import test.philips.com.network.HttpUtils;
import test.philips.com.utils.ReusableMethods;
import test.philips.com.utils.SetupDriver;

public class HomeVisit extends ReusableMethods{
	int numberOfTestScenarios,row;
	String appName,excelTabName;
	String expectedDataLocators[]={"LOGIN_PASSWORD_VALIDATION_MESSAGE","LOGIN_PASSWORD_VALIDATION_TOAST"};
	//String expectedDataLocators[]={};
	String stepNo="1";
	@BeforeTest
	private void beforeTest()throws Exception{
		 appName = "CMAApp";
		 excelTabName = "HomeVisit";
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
					 navigateToCMAApplication();
					 addHomeVisit();
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
	private void addHomeVisit() throws Exception {
		click("PATIENT_LIST");
		click("GUIDED_BUTTON");
		click("HOME_VISIT_BUTTON");
		inputComboboxData("FEVER");
		click("POPUP_SELECT_BUTTON");
		inputRadioButton(TEST_DATA.get("BPV"),"BPV_YES","BPV_NO","BPV_NO_DATA");
		inputRadioButton(TEST_DATA.get("LPV"),"LPV_YES","LPV_NO","LPV_NO_DATA");
		inputRadioButton(TEST_DATA.get("ABDN PAIN"),"ABDNPAIN_YES","ABDNPAIN_NO","ABDNPAIN_NO_DATA");
		inputRadioButton(TEST_DATA.get("HEAD ACHE"),"HEADACHE_YES","HEADACHE_NO","HEADACHE_NO_DATA");
		inputRadioButton(TEST_DATA.get("EDEMA"),"EDEMA_YES","EDEMA_NO","EDEMA_NO_DATA");
		inputComboboxData("VOMITING");
		click("POPUP_SELECT_BUTTON");
		inputRadioButton(TEST_DATA.get("BLUR VISION"),"BLURVISION_YES","BLURVISION_NO","BLURVISION_NO_DATA");
		inputRadioButton(TEST_DATA.get("CONVULSION"),"CONVULSION_YES","CONVULSION_NO","CONVULSION_NO_DATA");
		inputRadioButton(TEST_DATA.get("INSOMNIA"),"INSOMNIA_YES","INSOMNIA_NO","INSOMNIA_NO_DATA");
		inputRadioButton(TEST_DATA.get("CONSTIPATION"),"CONSTIPATION_YES","CONSTIPATION_NO","CONSTIPATION_NO_DATA");
		inputTextData("OTHER_COMPLAINTS");
		swipeRight();
	}
	@AfterTest
	private void endReport ()throws Exception{
		driver.quit();
	}
}

