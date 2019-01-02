package RoughWork;
/*package test.philips.com.testsuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import test.philips.com.utils.ReusableMethods;
import test.philips.com.utils.SetupDriver;

public class AddHeroTestCases extends SetupDriver{
	String reportOrder="child2";
	int numberOfTestScenarios=0;
	String excelTabName = "AddHero";
	int row;
	String expectedDataKeywords[]={"MODEL_DIALOG_TITLE","MODEL_DIALOG_BODY","GUIDE_FNAME_ERR_MSG","GUIDE_LNAME_ERR_MSG","ADD_HERO_SAVE_BTN","ADD_HERO_BTN"};
	String expectedDataKeywords1[]={"MODEL_DIALOG_TITLE","MODEL_DIALOG_BODY"};
	@BeforeTest
	private void intitalizeReport()throws Exception{
		 child2 =report.startTest("Add Hero Test Cases");
		 numberOfTestScenarios=ReusableMethods.getNumberOfTestScenarios(excelTabName);
	}
	@Test
	private void addHeroTest(){
		for(row=1;row<numberOfTestScenarios;row++){
			 try{
				 ReusableMethods.initiateTest(excelTabName, row); 
				 ReusableMethods.loginToApplication();
				 addHero();		 
				 if(ReusableMethods.TEST_DATA.get("TYPE").equalsIgnoreCase("VerifyPage")){
					 ReusableMethods.clickButton("MODEL_DIALOG_PROCEED_BUTTON");
					 ReusableMethods.verifyData(expectedDataKeywords,reportOrder, excelTabName, row);  
				 }
				 else if(ReusableMethods.TEST_DATA.get("TYPE").equalsIgnoreCase("VerifyMessageBox")){
					 ReusableMethods.verifyData(expectedDataKeywords1,reportOrder, excelTabName, row);
					 ReusableMethods.clickButton("MODEL_DIALOG_PROCEED_BUTTON");
				 }
				 else{
					 ReusableMethods.verifyData(expectedDataKeywords,reportOrder, excelTabName, row);
				 }	
				 The above If block is necessary to identify the type of test and based on that it will verify the data. Some test cases will verify the dialog box and some will verify the error message etc..
				 Sometimes need verification after click of Ok button and some before that.
				 
				 ReusableMethods.logoutOfApplication();
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
	*//**
	 * This function has all the keywords to add hero, this will fetch test data from excel and enter the data in the fields based on the type of field.
	 * @throws Exception
	 *//*
	private void addHero() throws Exception {
		ReusableMethods.clickButton("ADD_HERO_BTN");
  		ReusableMethods.inputTextData("ADD_HERO_FIRST_NAME");
  		ReusableMethods.inputTextData("ADD_HERO_LAST_NAME");
  		ReusableMethods.inputTextData("ADD_HERO_DISPLAY_NAME");
  		ReusableMethods.inputTextData("ADD_HERO_EMAIL");
  		ReusableMethods.inputTextData("ADD_HERO_PHONE");
  		ReusableMethods.inputTextData("ADD_HERO_ADDRESS");
  		ReusableMethods.inputListData("ADD_HERO_COUNTRY","ADD_HERO_COUNTRY_LIST");
  		ReusableMethods.inputListData("ADD_HERO_STATE","ADD_HERO_STATE_LIST");
  		ReusableMethods.inputComboboxData("ADD_HERO_TIMEZONE", "ADD_HERO_TIMEZONE_LIST");
  		ReusableMethods.inputTextData("ADD_HERO_AGE");
  		ReusableMethods.selectGender("ADD_HERO_GENDER_MALE","ADD_HERO_GENDER_FEMALE","ADD_HERO_GENDER_OTHER");
  		ReusableMethods.selectProfileImage("ADD_HERO_IMAGE_ONE", "ADD_HERO_IMAGE_TWO","ADD_HERO_IMAGE_THREE", "ADD_HERO_IMAGE_FOUR", "ADD_HERO_IMAGE_FIVE");
  		ReusableMethods.clickButton("ADD_HERO_SAVE_BTN");
	}

	@AfterTest
	private void endReport ()throws Exception{
		ReusableMethods.appendChild(reportOrder); 
		WD.close();
	}
}


*/