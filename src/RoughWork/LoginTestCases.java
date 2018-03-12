/*package RoughWork;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import test.philips.com.utils.ReusableMethods;
import test.philips.com.utils.SetupDriver;

public class LoginTestCases extends SetupDriver{
	String reportOrder="child1";
	int numberOfTestScenarios=0;
	String excelTabName = "Login";
	int row;
	String testCaseName = "Default";
	
	@BeforeTest
	void intitalizeReport()throws Exception{
		 SetupDriver.child1 =SetupDriver.report.startTest("Login Test Cases"); //Initialize child report for this test case
		 numberOfTestScenarios=ReusableMethods.getNumberOfTestScenarios(excelTabName); //Get the number of test scenarios from the test data excel file
	}
	@Test
	public void loginTest(){
		for(row=1;row<numberOfTestScenarios;row++){
			 try{
				 ReusableMethods.getTestData(excelTabName,row);
				 testCaseName = ReusableMethods.TEST_DATA.get("ID")+"_"+ ReusableMethods.TEST_DATA.get("STEPNAME");
				 videoReord.startRecording(testCaseName);
				 ReusableMethods.launchApplication();
				 ReusableMethods.loginToApplication("USERNAME", "PASSWORD");
				 verifyLogin();
				 ReusableMethods.stopRecordingAndCaptureScreenshot(testCaseName);
				 logoutIfValidCredentials();
			 }catch(Exception e){
				 e.printStackTrace();	
				 try{
					 ReusableMethods.resultFail(reportOrder,testCaseName, ReusableMethods.TEST_DATA.get("EXPECTED_RESULT"),ReusableMethods.getExceptionString(e));
					 ReusableMethods.writeTestResultsToExcel(excelTabName, row,"Fail",ReusableMethods.getExceptionString(e));
					 ReusableMethods.stopRecordingAndCaptureScreenshot(testCaseName);
					 ReusableMethods.restartDriver();
				 }catch(Exception e2){
					 e2.printStackTrace();	
				 }
			 }	
		}
	 }	
	public void verifyLogin() throws Exception {
			if (ReusableMethods.TEST_DATA.get("TYPE").equalsIgnoreCase("Credentials_Null")){	
				String expected[]={"LOGIN_USERNAME_VALIDATION","LOGIN_PASSWORD_VALIDATION"};
				String actual[]={"USERNAME_ERROR", "PASSWORD_ERROR"};
				ReusableMethods.verifyData(expected,actual,reportOrder,excelTabName,row);
			}
			else if (ReusableMethods.TEST_DATA.get("TYPE").equalsIgnoreCase("Username_Null")) {
				String expected[]={"LOGIN_USERNAME_VALIDATION"};
				String actual[]={"USERNAME_ERROR"};
				ReusableMethods.verifyData(expected,actual,reportOrder,excelTabName,row);
			}
			else if (ReusableMethods.TEST_DATA.get("TYPE").equalsIgnoreCase("Password_Null")) {	
				String expected[]={"LOGIN_PASSWORD_VALIDATION"};
				String actual[]={"PASSWORD_ERROR"};
				ReusableMethods.verifyData(expected,actual,reportOrder,excelTabName,row);
			}
			else if (ReusableMethods.TEST_DATA.get("TYPE").equalsIgnoreCase("Invalid")) {
				String expected[]={"LOGIN_USERNAME_PASSWORD_VALIDATION"};
				String actual[]={"GENERAL_ERROR"};
				ReusableMethods.verifyData(expected,actual,reportOrder,excelTabName,row);
			}
			else if (ReusableMethods.TEST_DATA.get("TYPE").equalsIgnoreCase("Valid_Admin")) {
				String expected[]={"ADD_HERO_BTN"};
				ReusableMethods.verifyPage(expected,reportOrder,excelTabName,row);	
			}
			else if (ReusableMethods.TEST_DATA.get("TYPE").equalsIgnoreCase("Valid_Supervisor")){
				String expected[]={"HERO_LIST_PAGE_TXT"};
				ReusableMethods.verifyPage(expected,reportOrder,excelTabName,row);	
			}
		String expected[]={"LOGIN_USERNAME_VALIDATION","LOGIN_PASSWORD_VALIDATION","LOGIN_USERNAME_PASSWORD_VALIDATION","ADD_HERO_BTN","HERO_LIST_PAGE_TXT"};
		String actual[]={"USERNAME_ERROR", "PASSWORD_ERROR","GENERAL_ERROR","EXPECTED_ADMIN_PAGE_STRING","EXPECTED_SUPERVISOR_PAGE_STRING"};
		ReusableMethods.verifyData(expected,actual,reportOrder,excelTabName,row);
		
	}
	public void logoutIfValidCredentials()throws Exception{
		 if(ReusableMethods.TEST_DATA.get("TYPE").equalsIgnoreCase("Valid_Admin")||ReusableMethods.TEST_DATA.get("TYPE").equalsIgnoreCase("Valid_Supervisor"))
			 ReusableMethods.logoutOfApplication();
	}
	
	@AfterTest
	void endReport ()throws Exception{
		ReusableMethods.appendChild(reportOrder); 
	}
}


*/





















/*//Function to switch to a window
public static void switchWindow() throws Exception{
	boolean switched = false;
    int i=0;
    do{
        try {
    		//for(String str:WD.getWindowHandles())
    			//WD.switchTo().window(str);
    		//Confirm al//div[@class='modal-dialog cc-modal-dialog']//div[@class='modal-content cc-modal-content']//div[@class='modal-header cc-modal-header']//h4[@class='modal-title ng-binding']
    		//System.out.println(WD.findElement(By.xpath("//DIV[@class='modal-dialog cc-modal-dialog']//DIV[@class='modal-content cc-modal-content']//DIV[@class='modal-header cc-modal-header']//H4[@class='modal-title ng-binding']")).getText());
    		System.out.println(WD.findElement(By.xpath("//DIV[@class='modal-header cc-modal-header']//H4[@class='modal-title ng-binding']")).getText());
    		System.out.println(WD.findElement(By.xpath("//DIV[@class='modal-body']//P[@class='ng-binding']")).getText());
    		//System.out.println(WD.findElement(By.xpath("//DIV[@class='modal-header cc-modal-header']//button[@class='close']")).getText());
    		//System.out.println(WD.findElement(By.xpath("//DIV[@class='modal-footer cc-modal-footer']/button[@class='mybutton']")).getText());
    		
    		//WD.findElement(By.xpath("//DIV[@class='modal-header cc-modal-header']//button[@class='close']")).click();
    		
    		WD.findElement(By.xpath("//DIV[@class='modal-footer cc-modal-footer']/button[@class='mybutton']")).click();
        	switched=true;
        	System.out.println("AlertWindow");
        } catch (Exception e) {
        	i++;
        	System.out.println("Exception Count: "+i);
        	if(i<500)
        		continue;
        	else
        		throw e;
        } 
    } while (!switched);

}*/

/*//Function to verify the page focus
public static void verifyPage(String element[],String reportOrder,String excelTabName,int row)throws Exception{
	ArrayList<String> actualresult = new ArrayList<String>();
	boolean status=true;
	boolean elementFound=true;
	String testCaseName= TEST_DATA.get("ID")+": "+ TEST_DATA.get("STEPNAME");
	for(int i=0;i<element.length;i++){	
		int j=0;
		String elementName;
		do{
			WebElement we=getElement(element[i]);
			elementName=element[i];
			if(!we.isDisplayed()){
				elementFound=false;
				Thread.sleep(500);
				j++;
			}
			else{
				elementFound=true;
				break;
			}
		}while(j<40);
		if(elementFound==false){
			status=false;
			actualresult.add(elementName);
		}
		else
			status=true;
		
	}
	if(status==true){
		resultPass(reportOrder,testCaseName,"EXPECTED_RESULT",TEST_DATA.get("ACTUAL_RESULT"));
		writeTestResultsToExcel("Login", row,"Pass",ReusableMethods.TEST_DATA.get("ACTUAL_RESULT"));
	}
	else{
		resultFail(reportOrder,testCaseName,"EXPECTED_RESULT",actualresult.toString()+ "was not displayed");
		writeTestResultsToExcel("Login", row,"Fail",actualresult.toString()+ "was not displayed");
	}
}	*/


/* public static void verifyConfirmationWindowMessage(String element,String child,String message,String reportMessage,String elementMessage)
	{
		try{
			for(String str:SetupDriver.WD.getWindowHandles()){
				SetupDriver.WD.switchTo().window(str);
			}
			Thread.sleep(2000);
			String confMessage = null;
			try{
				confMessage = ReusableMethods.getElement(elementMessage).getText();
			}catch(Exception e)
			{
			}
			ReusableMethods.verify(confMessage, message, reportMessage, "AdminPage", child);
			ReusableMethods.getElement(element).click();
			Thread.sleep(2000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
 
	public static void closeWindow(String element)
	{
		try{
			for(String str:SetupDriver.WD.getWindowHandles()){
				SetupDriver.WD.switchTo().window(str);
			}
			Thread.sleep(2000);
			ReusableMethods.getElement(element).click();
			Thread.sleep(2000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void closeWindowByAddingComment(String element,String commentElement, String comment)
	{
		try{
			for(String str:SetupDriver.WD.getWindowHandles()){
				SetupDriver.WD.switchTo().window(str);
			}
			Thread.sleep(2000);
			ReusableMethods.getElement(commentElement).sendKeys(comment);
			Thread.sleep(2000);
			ReusableMethods.getElement(element).click();
			Thread.sleep(2000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void SwitchTabandClose(String child)
	{
		try{
			//Set <String> windows = SetupDriver.WD.getWindowHandles();
		    String mainwindow = SetupDriver.WD.getWindowHandle();
		  //  String title = null;
		    for (String handle: SetupDriver.WD.getWindowHandles())
		    {
		    	SetupDriver.WD.switchTo().window(handle);
		    	Thread.sleep(2000);
		    	SetupDriver.WD.manage().window().maximize();
		    	Thread.sleep(2000);
		    //	title = SetupDriver.WD.getTitle();
		    	SetupDriver.WD.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "w");
		        Thread.sleep(2000);
		    }
		   // ReusableMethods.verify(title,"Responsive HTML5", ReusableMethods.TEST_DATA.get("STEPNAME"), "Help page",child);
		    Thread.sleep(2000);
		    SetupDriver.WD.switchTo().window(mainwindow);
		}catch(Exception th)
		{
			th.printStackTrace();
		}
	    
	 }*/

	/*//Function to get focus in current page
	public static void getCurrentPage(){
		try{
			Thread.sleep(3000);
			String redirected_url=SetupDriver.WD.getCurrentUrl();
			SetupDriver.WD.get(redirected_url);
			Thread.sleep(3000);
			System.out.println("Redirected URL : "+redirected_url);
		}catch(Throwable th){
			System.out.println(th);
		}
		
	}
	public static String getRandomAlphaNumericString(int length) {
	       final String characters = "abcdefghijklmnopqrstuvwxyz1234567890";
	       StringBuilder result = new StringBuilder();
	       while(length > 0) {
	           Random rand = new Random();
	           result.append(characters.charAt(rand.nextInt(characters.length())));
	           length--;
	       }
	       return result.toString();
	    }
	public static void CloseAlert(String child){
		try{
			String window = SetupDriver.WD.getWindowHandle();
			for(String wndowHandles: SetupDriver.WD.getWindowHandles()){
				SetupDriver.WD.switchTo().window(wndowHandles);
			}
			Thread.sleep(1000);
			SetupDriver.WD.findElement(By.xpath("//*[text()='Yes']")).click();
			Thread.sleep(1000);
			SetupDriver.WD.switchTo().defaultContent();
		}
		 catch(Throwable e)
           {
			 e.printStackTrace();
			 ReusableMethods.resultFail(child,"STEPNAME","Should allow to test", e.getMessage());
           }
	}
	
	public static void waitUntill()
	{
		try{
			WebDriverWait wait = new WebDriverWait(SetupDriver.WD, 15);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Yes']")));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String todaysDate()
	{
		String todaysDate = null;
		try{
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			todaysDate = format.format(date);
		}catch(Throwable th){
			th.printStackTrace();
		}
		return todaysDate;
	}
	
	public static void verifyAlertText(String element,String child)
	{
		try{
			for(String str:SetupDriver.WD.getWindowHandles()){
				SetupDriver.WD.switchTo().window(str);
			}
			Thread.sleep(2000);
			ReusableMethods.verify(SetupDriver.WD.findElement(By.xpath("//*[contains(text(),'Reject Study ')]/../../div[2]/p")).getText(), "Please select reject reason", ReusableMethods.TEST_DATA.get("STEPNAME"), "ConsultantPhysicianPage", child);
			Thread.sleep(2000);
			ReusableMethods.getElement(element).click();
			Thread.sleep(2000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void VerifySearch(String child)
	{  
		boolean Matched_With_DisplayName = false;
	    boolean Matched_With_Mail = false;
	   // boolean Matched_With_Mail_Other_tab = false;
	    
		if(ReusableMethods.TEST_DATA.get("TESTTYPE").equalsIgnoreCase("valid_mail"))
		{
		try {
	    	Thread.sleep(3000);
	    	if(ReusableMethods.TEST_DATA.get("STEPIDENTIFIER").equalsIgnoreCase("AdminLogin_HeroSearch")
	    	    ||ReusableMethods.TEST_DATA.get("STEPIDENTIFIER").equalsIgnoreCase("AdminLogin_UserSearch")){
			
	    		Matched_With_Mail =WD.findElement(By.xpath("//*[contains(text(),'Email Id')]/../../../tbody/tr/td[5][contains(text(),'"+ReusableMethods.TEST_DATA.get("ExpectedSearchKey")+"')]")).isDisplayed();
	    	}
	    	else
	    	{   // Matched_With_Mail =WD.findElement(By.linkText(ReusableMethods.TEST_DATA.get("ExpectedSearchKey"))).isDisplayed();
	    		Matched_With_Mail =WD.findElement(By.xpath("//*[contains(text(),'Email Id')]/../../../tbody/tr/td[4][contains(text(),'"+ReusableMethods.TEST_DATA.get("ExpectedSearchKey")+"')]")).isDisplayed();
	    	}
			ReusableMethods.verify(Matched_With_Mail ,true, ReusableMethods.TEST_DATA.get("STEPNAME"),ReusableMethods.TEST_DATA.get("PAGENAME"), child);
		} catch (Exception e) {
		
			ReusableMethods.resultFail(child,"Verification of email failed", "true", "False");
		}
		}
		
		
		if(ReusableMethods.TEST_DATA.get("TESTTYPE").equalsIgnoreCase("valid_name"))
		{
		try {Thread.sleep(3000);
		if(!ReusableMethods.TEST_DATA.get("STEPIDENTIFIER").equalsIgnoreCase("SupervisorLogin_HeroSearch")){
			Matched_With_DisplayName = WD.findElement(By.xpath("//*[contains(text(),'Display Name')]/../../../tbody/tr/td[3][contains(text(),'"+ReusableMethods.TEST_DATA.get("ExpectedSearchKey")+"')]")).isDisplayed();
		}
		else
		{  
			Matched_With_DisplayName = WD.findElement(By.xpath("//*[contains(text(),'Display Name')]/../../../tbody/tr/td[2][contains(text(),'"+ReusableMethods.TEST_DATA.get("ExpectedSearchKey")+"')]")).isDisplayed();
		}
			ReusableMethods.verify(Matched_With_DisplayName ,true,ReusableMethods.TEST_DATA.get("STEPNAME"),ReusableMethods.TEST_DATA.get("PAGENAME"), child);
		} catch (Exception e) {
			
			ReusableMethods.resultFail(child,"Verification of Display Name failed ", "true", "False");
		}
	   }
		
		
		else if(ReusableMethods.TEST_DATA.get("TESTTYPE").equalsIgnoreCase("invalid"))
			try {
				  
			ReusableMethods.verify(ReusableMethods.getElement("NO_SEARCH_RESULT").getText(), ReusableMethods.TEST_DATA.get("ExpectedResult"),ReusableMethods.TEST_DATA.get("STEPNAME"),"Searched Page",child);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				ReusableMethods.resultFail(child,"Verification of Search failed ",ReusableMethods.TEST_DATA.get("ExpectedResult"), ReusableMethods.getElement("NO_SEARCH_RESULT").getText());
			}
		
		if(ReusableMethods.TEST_DATA.get("TESTTYPE").equalsIgnoreCase("valid"))
		{
		try {
	    	Thread.sleep(3000);
			Matched_With_Mail_Other_tab =WD.findElement(By.xpath("//*[contains(text(),'Email Id')]/../../../tbody/tr/td[4][contains(text(),'"+ReusableMethods.TEST_DATA.get("ExpectedSearchKey")+"')]")).isDisplayed();
			
			ReusableMethods.verify(Matched_With_Mail_Other_tab ,true, ReusableMethods.TEST_DATA.get("STEPNAME"),ReusableMethods.TEST_DATA.get("PAGENAME"), child);
		} catch (Exception e) {
		
			ReusableMethods.resultFail(child,"Verification of email failed", "true", "False");
		}
		}
	
			} 
	}
	*/
	
	
	

	



