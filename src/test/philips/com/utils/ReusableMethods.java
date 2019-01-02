package test.philips.com.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;

import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import jxl.Sheet;
import jxl.Workbook;
import test.philips.com.network.HttpUtils;

/**
 * This class contains all the methods that can be reused in all the test case classes. 
 * @author Maneesh Cheerambathil
 *
 */
public class ReusableMethods extends SetupDriver {
	
	public static Map<String,String> TEST_DATA=new HashMap<String,String>();
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
    public static String videoFileName,videoFilePath,screenshotFilePath,screenshotFileName,logFilePath;
		
  	/**
  	 * This function will find the number of test scenarios from the test data excel file. Based on the excel file sheet/tab name passed as parameter, it will open the file and find number of test scearios in that.
  	 * @param excelSheetTabName  - Sheet/Tab name inside the test data excel file where the corresponding test case is written.
  	 * @return
  	 * @throws Exception
  	 */
  	public static int getNumberOfTestScenarios(String excelSheetTabName) throws Exception 
  	{
  			int rowCount=0;
  			File src=new File(SetupDriver.class.getClassLoader().getResource(config.getProperty("testDataPath")).getPath());
  		    Workbook workbook=Workbook.getWorkbook(src);
  		    Sheet worksheet=workbook.getSheet(excelSheetTabName);
  		    rowCount=worksheet.getRows();
  		    return rowCount;
  	}
  	
  	/**
  	 * This function will fetch all the test data in test scenario under the a particular row of excel tab name and this will write the data into a hash map. 
  	   Data will be written in hash map under the title of  column headers in the excel. For  eg: If we call Test_data.get("ID"), it will fin the id under title ID.
  	 * @param excelSheetTabName -  Excel sheet/ tab name under which the test scenario resides. 
  	 * @param excelSheetRowId - Row number of particular test scenario in the excel
  	 * @throws Exception
  	 */
  	public static void getTestData(String excelSheetTabName,int excelSheetRowId) throws Exception{
  			Map<String,String> dataSource=new HashMap<String,String>();
  		    String col,row = null;
  		    File src=new File(SetupDriver.class.getClassLoader().getResource(config.getProperty("testDataPath")).getPath());
  		    Workbook workbook=Workbook.getWorkbook(src);
  		    Sheet worksheet=workbook.getSheet(excelSheetTabName);
  				  for (int j=0;j<worksheet.getColumns();j++){
  						jxl.Cell cell1=worksheet.getCell(j,0);
  						jxl.Cell cell2=worksheet.getCell(j,excelSheetRowId);	        
  				        row= cell1.getContents();
  				        col= cell2.getContents();
  				        dataSource.put(row, col);
  				        //System.out.println(TEST_DATA);
  				        TEST_DATA=dataSource; 
  				  }        
  	  	}

  	/**
  	 * This function will write the test results to excel report. It will clear the actual results and test data fields and write the actual results based on the test.
  	 * This function will also color the actual result and status fields as Red/Green based on the status.
  	 * @param excelSheetTabName -  Excel sheet/ tab name under which the test scenario resides. 
  	 * @param excelSheetRowId - Row number of particular test scenario in the excel
  	 * @param tesStatus - Test results whether Pass or Fail
  	 * @param actualResult - Actual result description of the test if pass or if failed then log files
  	 * @throws Exception
  	 */
  	public static void writeTestResultsToExcel(String excelSheetTabName,int excelSheetRowId,String tesStatus,String actualResult) throws Exception{
  			HSSFSheet sheet=wb.getSheet(excelSheetTabName);
  			Row r= sheet.getRow(excelSheetRowId);
  			int lastColumnNumber= sheet.getRow(0).getLastCellNum();
  			Cell c=r.createCell(lastColumnNumber-1);
  			c.setCellValue(tesStatus); 
  			CellStyle s = c.getSheet().getWorkbook().createCellStyle();	
  			Cell c1=r.createCell(lastColumnNumber-2);
  			c1.setCellValue(actualResult); 
  			CellStyle s1 = c1.getSheet().getWorkbook().createCellStyle();
  			
  			s.setBorderBottom((short) 1);
  			s.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
  			s.setBorderTop((short) 1);
  			s.setBorderRight((short) 1);
  			s.setBorderLeft((short) 1);
  			s.setVerticalAlignment((short) 0);
  			s.setWrapText(true);
  			
  			s1.setBorderBottom((short) 1);
  			s1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
  			s1.setBorderTop((short) 1);
  			s1.setBorderRight((short) 1);
  			s1.setBorderLeft((short) 1);
  			s1.setVerticalAlignment((short) 0);
  			s1.setWrapText(true);
  			
  			if(tesStatus.equalsIgnoreCase("Pass")){
  	  			s.setFillForegroundColor(HSSFColor.GREEN.index);
  	  			s1.setFillForegroundColor(HSSFColor.GREEN.index);
  			}
  			else{
  	  			s.setFillForegroundColor(HSSFColor.RED.index);
  	  			s1.setFillForegroundColor(HSSFColor.RED.index);
  			}
  			
  			r.getCell(lastColumnNumber-1).setCellStyle(s);
  			r.getCell(lastColumnNumber-2).setCellStyle(s1);
  	  	}
  	
  	/**
  	 * This function will login to the application with the credentials matching the keyword given from excel.
  	 * @param usernameTestDataField - Keyword of the username test data in excel and repository
  	 * @param passwordTestDataField - Keyword of the password test data in excel and repository
  	 * @throws Exception
  	 */
  	public static void loginToCMAApplication() throws Exception {
  		inputTextData("LOGINPAGE_INPUT_USERNAME");
		inputTextData("LOGINPAGE_INPUT_PASSWORD");
		getElement("LOGINPAGE_BTN_LOGIN").click();		
	} 	
  	
  	public static void navigateToCMAApplication() throws Exception {
		 click("LAUNCHER_CMA_APP");
		 click("LAUNCH_CMA_BUTTON");
	} 	
  	
    /**
     * This function will launch the application based on the URL given in the config properties file. This will also maximize the window.
     * @throws Exception
     */
	public static void launchApplication(String appName) throws Exception {
		if(appName.equalsIgnoreCase("CMAApp")){
			deviceName = Init.config.getProperty("deviceName1");
			String testCaseName = (TEST_DATA.get("TEST_CASE_ID")+"_"+ TEST_DATA.get("STEPNAME")).replaceAll("\\s+","");
			driver=new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),phone1DC);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			if(config.getProperty("videoCapture").equalsIgnoreCase("true")) {
				process = (Process) startScreenRecord(testCaseName);
			}
			if(config.getProperty("logCapture").equalsIgnoreCase("true")) {
				process1= (Process) startLogRecord(testCaseName);
			}
		}
		else if(appName.equalsIgnoreCase("PatientApp")) {
		    deviceName = Init.config.getProperty("deviceName2");
			String testCaseName = (TEST_DATA.get("TEST_CASE_ID")+"_"+ TEST_DATA.get("STEPNAME")).replaceAll("\\s+","");
			driver=new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),phone2DC);	
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			if(config.getProperty("videoCapture").equalsIgnoreCase("true")) {
				process = (Process) startScreenRecord(testCaseName);
			}
			if(config.getProperty("logCapture").equalsIgnoreCase("true")) {
				process1= (Process) startLogRecord(testCaseName);
			}
		}
	}
	/**
	 * This function will logout of the application.
	 * @throws Exception
	 */
	public static void logoutOfApplication() throws Exception{
		click("LOGOUT_ARROW");
		click("LOGOUT_BUTTON");
	}
	
	/**
	 * This function will fetch the locator path given in the object repository and it will fetch the element based on whether it is a xpath, id ,name etc...
	   User has to just pass the keyword and this function will return the element. Also it wait explicitly for 30 seconds to find the element.
	 * @param elementORKey - Keyword to fetch the locator from object repository
	 * @return - Actual element found based on the keyword.
	 * @throws Exception
	 */
	public static WebElement getElement(String elementORKey)throws Exception{
		String locatorData,locateBy,locator;
		WebElement element = null;
		locatorData=Init.objectRepository.getProperty(elementORKey);
		String temp[]=locatorData.split("~");
		locateBy=temp[0];
		locator=temp[1];
		//WebDriverWait wait = new WebDriverWait(driver, 30);			
		if(locateBy.equalsIgnoreCase("XPATH")){
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
			for(int i=1;i<=10;i++) {
				try {
					element= SetupDriver.driver.findElement(By.xpath(locator));	
					break;
				} catch (Exception e) {
					scrollDown();
					if(i==10)
						throw e;
				}
			}
		
		}
		else if(locateBy.equalsIgnoreCase("ID")){
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
			element=driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\""+locator+"\").instance(0))"));
		}
		return element;
	}
	/**
	 * This function will fetch the locator path given in the object repository and it will find the elements as list based on whether it is a xpath, id ,name etc...
	   User has to just pass the keyword and this function will return the element. Also it wait explicitly for 30 seconds to find the element.
	   This function is required if there are multiple line of fields under a locator - For example Listbox in web ui.
	 * @param elementORKey - Keyword to fetch the locator from object repository
	 * @return - Actual element found based on the keyword.
	 * @throws Exception
	 */
	public static List<MobileElement> getElements(String elementORKey)throws Exception{
		String locatorData,locateBy,locator;
		List<MobileElement> element = null;
		locatorData=Init.objectRepository.getProperty(elementORKey);
		String temp[]=locatorData.split("~");
		locateBy=temp[0];
		locator=temp[1];
		//WebDriverWait wait = new WebDriverWait(driver, 30);		
		if(locateBy.equalsIgnoreCase("XPATH")){
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
			for(int i=1;i<=10;i++) {
				try {
					element= SetupDriver.driver.findElements(By.xpath(locator));	
					break;
				} catch (Exception e) {
					scrollDown();
					if(i==10)
						throw e;
				}
			}
		}
		else if(locateBy.equalsIgnoreCase("ID")){
			//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
			element=driver.findElements(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().resourceId(\""+locator+"\").instance(0))"));
		}
		return element;
	}
	/**
	 * This function will fetch the data from testdata excel file,get the element from web UI matching the elements keyword given and compare the data. 
	   If data in excel and web UI is not matching it will fail the test, else pass the test. This function will also write the test result in Excel and html format. 
	 * @param expectedDataArray - List of expected keywords of locators with that code can fetch matching data from Excel and Object Repository. User can pass all the data to be verified
	   at one shot and this function will verify the data one by one.
	 * @param reportOrder - The Order in which the test report will display in html. If child1, then that will be the first in report.
	 * @param excelTabName - The sheet name of test data excel to fetch the data from each row of that sheet.
	 * @param row - The row number of the test case in excel sheet tab.
	 * @throws Exception
	 */
	public static void verifyData(String expectedDataArray[],String excelTabName,int row, String stepNo) throws Exception{
		ArrayList<String> actualresultFailures = new ArrayList<String>();
		ArrayList<String> actualresultSuccess = new ArrayList<String>();
		boolean status=true;
		boolean elementDataMatched=true;
		
		String testCaseName= TEST_DATA.get("TEST_CASE_ID")+"_"+ TEST_DATA.get("STEPNAME");
		for(int i=0;i<expectedDataArray.length;i++){
			//int j=0;
			if(!TEST_DATA.get(expectedDataArray[i]).isEmpty()&&!TEST_DATA.get(expectedDataArray[i]).equals(null)&&!TEST_DATA.get(expectedDataArray[i]).equals("")
					&&!TEST_DATA.get(expectedDataArray[i]).equalsIgnoreCase("X")){
				//do{
					String actualText=null;
					WebElement we=getElement(expectedDataArray[i]);
					actualText=we.getText();
					
					if(!actualText.equals(TEST_DATA.get(expectedDataArray[i]))){
						elementDataMatched=false;
						actualresultFailures.add(expectedDataArray[i]+":"+actualText);
					}
					else{
						elementDataMatched=true;
						actualresultSuccess.add(expectedDataArray[i]+":"+actualText);
						//break;
					}
					//j++;
					//Thread.sleep(500);
				//}while(j<10);
			}
			else{
				String locatorData=Init.objectRepository.getProperty(expectedDataArray[i]);
				String temp[]=locatorData.split("~");
				String locator=temp[1];		
				try{
					if(!TEST_DATA.get(expectedDataArray[i]).trim().equalsIgnoreCase("X")){
						if(driver.findElement(By.xpath(locator)).isDisplayed()){
							if(!driver.findElement(By.xpath(locator)).getText().isEmpty()) {
								elementDataMatched=false;
								actualresultFailures.add(expectedDataArray[i]+":"+driver.findElement(By.xpath(locator)).getText());
							}
						}
					}
				}catch(Exception e){
					continue;
				}
			}
			if(elementDataMatched==false){
				status=false;
			}

		}

		if(config.getProperty("screenshotCapture").equalsIgnoreCase("true")) {
			 takeScreenshot(testCaseName);
		}
		if(status==true){
			if(config.getProperty("htmlReportFlag").equalsIgnoreCase("true"))
				resultPass(TEST_DATA.get("EXPECTED_RESULT"),"Page displayed with the data as expected as in the Test data : Following assertions are passed: "+actualresultSuccess.toString(),stepNo);
			if(config.getProperty("excelReportFlag").equalsIgnoreCase("true"))
				writeTestResultsToExcel(excelTabName, row,"Pass",TEST_DATA.get("ACTUAL_RESULT"));
			if(config.getProperty("tfsResultsUploadFlag").equalsIgnoreCase("true"))
				uploadResultsToTfs(TEST_DATA.get("ACTUAL_RESULT"), "Passed");
			if(config.getProperty("tfsImageUploadFlag").equalsIgnoreCase("true")&&config.getProperty("tfsResultsUploadFlag").equalsIgnoreCase("true"))
				uploadAttachmentToTfs("image");
/*			if(config.getProperty("tfsVideoUploadFlag").equalsIgnoreCase("true")&& config.getProperty("tfsResultsUploadFlag").equalsIgnoreCase("true"))
				uploadAttachmentToTfs("video");*/
		}
		else{
			if(config.getProperty("htmlReportFlag").equalsIgnoreCase("true"))
				resultFail(TEST_DATA.get("EXPECTED_RESULT"),"The following assertions are failed: "+actualresultFailures.toString(),stepNo);
			if(config.getProperty("excelReportFlag").equalsIgnoreCase("true"))
				writeTestResultsToExcel(excelTabName, row,"Fail",actualresultFailures.toString()+ "was displayed");
			if(config.getProperty("tfsResultsUploadFlag").equalsIgnoreCase("true"))
				uploadResultsToTfs(actualresultFailures.toString()+ "was displayed", "Failed");
			if(config.getProperty("tfsImageUploadFlag").equalsIgnoreCase("true")&&config.getProperty("tfsResultsUploadFlag").equalsIgnoreCase("true"))
				uploadAttachmentToTfs("image");
/*			if(config.getProperty("tfsVideoUploadFlag").equalsIgnoreCase("true")&& config.getProperty("tfsResultsUploadFlag").equalsIgnoreCase("true"))
				uploadAttachmentToTfs("video");*/
		}

	}
		
	/**
	 * This function will fetch the data from hash map file based on the keyword passed and it will click on the text field based on the keyword given, clear the field and enter the data given.
	   This function will also check whether the data is empty or not, if empty then it does nothing and pass to next field.
	 * @param elementKeyWord - The keyword given in the Object repository matching the locator of web UI
	 * @param testDataKeyWord - The keyword given in the test data excel column as title
	 * @throws Exception
	 */
	public static void inputTextData(String elementKeyWord) throws Exception{
		if(!TEST_DATA.get(elementKeyWord).isEmpty()){
			WebElement textField=getElement(elementKeyWord);
			click(textField);
			textField.clear();
			textField.sendKeys(TEST_DATA.get(elementKeyWord));
		}
	}
	

	/**
	 * This function will fetch the data from hash map file based on the keyword passed and it will click on the list field based on the keyword given.
	   Then this will find the element in the list based on the second keyword passed in the function and click on the list
	 * @param textFiledElementKeyWord - The keyword of text field (where list will display) given in the Object repository matching the locator of web UI
	 * @param listElementKeyWord - The keyword of list field given in the Object repository matching the locator of web UI
	 * @param testDataField - The keyword given in the test data excel column as title
	 * @throws Exception
	 */
	public static void inputListData(String textFiledElementKeyWord,String listElementKeyWord) throws Exception{
		if(!TEST_DATA.get(textFiledElementKeyWord).isEmpty()){
			inputTextData(textFiledElementKeyWord);
			List<MobileElement> list = getElements(listElementKeyWord);
			for (WebElement listField : list) {
				String listValue = listField.getText();
				if (listValue.equalsIgnoreCase(TEST_DATA.get(textFiledElementKeyWord))){
					click(listField);		
				}
			}
		}
	}

	/**
	 * This function will fetch the data from hash map file based on the keyword passed and it will click on the combo box field based on the keyword given.
	   Then this will find the element in the options based on the second keyword passed in the function and click on the list
	 * @param comboFiledElementKeyWord - The keyword of combo field (where combo box will display) given in the Object repository matching the locator of web UI
	 * @param optionsElementKeyWord - The keyword of options field given in the Object repository matching the locator of web UI
	 * @param testDataField - The keyword given in the test data excel column as title
	 * @throws Exception
	 */
/*	public static void inputComboboxData(String comboFiledElementKeyWord,String optionsElementKeyWord) throws Exception{
		if(!TEST_DATA.get(comboFiledElementKeyWord).isEmpty()){
			click(comboFiledElementKeyWord);
			List<WebElement> list = getElements(optionsElementKeyWord);
			for (WebElement comboField : list) {
				String comboValue = comboField.getText();
				if (comboValue.equalsIgnoreCase(TEST_DATA.get(comboFiledElementKeyWord))){
					click(comboField);
				}
			}
		}
	}*/
	public static void inputComboboxData(String comboFiledElementKeyWord) throws Exception{
		if(!TEST_DATA.get(comboFiledElementKeyWord).isEmpty()){
			click(comboFiledElementKeyWord);
			List<MobileElement> list = getElements("CHECK_BOX_SELECTION");
			System.out.println(list);
			for (WebElement comboField : list) {
				String comboValue = comboField.getText();
				if (comboValue.equalsIgnoreCase(TEST_DATA.get(comboFiledElementKeyWord))){
					click(comboField);
				}
			}
		}
	}
/*	public static void inputPopUpData(String comboFiledElementKeyWord) throws Exception{
		if(!TEST_DATA.get(comboFiledElementKeyWord).isEmpty()){
			click("CHECK_BOX_SELECTION"+"'No')]");
		}
		click("CHECK_BOX_SELECTION"+"'No')]");
		String s=objectRepository.getProperty("CHECK_BOX_SELECTION");
		System.out.println(s+"'"+TEST_DATA.get("HOME_VISIT_FEVER_TYPE")+"')]");
		WebElement we =getElementConstructed(s+"'"+TEST_DATA.get("HOME_VISIT_FEVER_TYPE")+"')]");		
		we.click();
	}*/
	/**
	 * This function will click on a button based on the keywords passed.It will find the button locator from the object repository.It is possible to use çlick'funnction directly, This function is added to keep the uniformity in calling methods.
	 * @param elementKeyWord - The keyword given in the Object repository matching the locator of web UI
	 * @throws Exception
	 */
	public static void clickButton(String elementKeyWord) throws Exception{
		click(elementKeyWord);
	}
	
	/**
	 * This function will convert the exception caught to a string and return the exception as string.
	 * @param exception - Actual exception caught from the main class
	 * @return - It will return the exception as string value
	 * @throws Exception
	 */
	public static String getExceptionString(Exception exception) throws Exception{
		StringWriter errors = new StringWriter();
		exception.printStackTrace(new PrintWriter(errors));
		return(errors.toString());
	}
	
	/**
	 * This function will take the screenshot of the UI and stop the recording of video after the test.
	 * @param testCaseName - Name of the test case 
	 * @throws Exception
	 */
	public static void stopVideoAndLogRecording() throws Exception{
		String testCaseName = TEST_DATA.get("TEST_CASE_ID")+"_"+ TEST_DATA.get("STEPNAME");	
		if(config.getProperty("videoCapture").equalsIgnoreCase("true")) {
			 stopScreenRecord(process,testCaseName);
		}
		if(config.getProperty("logCapture").equalsIgnoreCase("true")) {
			 stopLogRecord(process1,testCaseName);
		 }
		 
	}
	
	public static void resultPass(String expectedResultField, String actualResult, String stepNo)throws Exception{
		parent=report.startTest("TC ID : "+TEST_DATA.get("TEST_CASE_ID")+"_"+stepNo+" : "+TEST_DATA.get("STEPNAME"));
		String reportMessage="<u><b>Expected Result</b></u>"+"<br />"+expectedResultField+"<br />"+"Test Data,"+"<br />"+TEST_DATA+"<br />"+"<u><b>Actual Result</b></u>"+"<br />"+actualResult+"<br />";
		parent.log(LogStatus.PASS,reportMessage);
		
	}
	public static void resultFail(String expectedResultField, String actualResult, String stepNo)throws Exception{
		parent=report.startTest("TC ID : "+TEST_DATA.get("TEST_CASE_ID")+"_"+stepNo+" : "+TEST_DATA.get("STEPNAME"));
		String reportMessage="<u><b>Expected Result</b></u>"+"<br />"+expectedResultField+"<br />"+"Test Data,"+"<br />"+TEST_DATA+"<br />"+"<u><b>Actual Result</b></u>"+"<br />"+actualResult+"<br />";
		parent.log(LogStatus.FAIL,reportMessage);
	}
	
	/**
	 * This function will take the screenshot of UI and name it with the test case name
	 * @param testCaseName - Name of the test case.
	 * @throws Exception
	 */
	public static void takeScreenshot(String testCaseName) throws Exception{
			File file = new File(SetupDriver.screenshotPath); 
	        if (!file.exists())
	        	file.mkdirs();
	        screenshotFilePath = file+"\\"+testCaseName+"-"+dateFormat.format(new Date())+".png";
			screenshotFileName = testCaseName+"-"+dateFormat.format(new Date())+".png";
		    File srcFile=driver.getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(srcFile,new File(screenshotFilePath));
	}
	
	/**
	 * This function will click on any element passed based on the key word. This will take care of the selenium exceptions occurring based on the delays, unknown errors etc.
	 * There is a while loop given just to avoid such delay exceptions. It is necessary to avoid such erros.
	 * @param elementKeyWord - Keyword of the element given in object repo and excel.
	 * @throws Exception
	 */
	public static void click(String elementKeyWord) throws Exception{
		WebElement we =getElement(elementKeyWord);		
		we.click();
       /* boolean clicked = false;
        int i=0;
        do{
            try {
            	we.click();
            	i++;
            	clicked=true;
            	System.out.println("Normal :Count"+i);
            } catch (Exception e) {
            	i++;
                Thread.sleep(500);
                if(i>40)
                	throw e;
                continue;     
            }         	        
        } while (clicked ==false && i<40);*/
	}
	
	/**
	 * This function will generate random key value based on the length and format given and return the data
	 * @param length - length of string to be generated.
	 * @return
	 */
	public static String generateRandomKey(int length){
		String alphabet = new String("0123456789abcdefghijklmnopqrstuvwxyz");
		int n = alphabet.length();

		String result = new String(); 
		Random r = new Random();

		for (int i=0; i<length; i++)
		    result = result + alphabet.charAt(r.nextInt(n));
		return result;
	}
	
	/**
	 * This function will generate name based on the length and format given and return the data
	 * @param length - length of string to be generated.
	 * @return
	 */
	public static String generateRandomName(int length){
		String alphabet = new String("abcdefghijklmnopqrstuvwxyz");
		int n = alphabet.length();
		String result = new String(); 
		Random r = new Random();

		for (int i=0; i<length; i++)
		    result = result + alphabet.charAt(r.nextInt(n));
			String randomName = result.substring(0, 1).toUpperCase() + result.substring(1);
			return randomName;
	}
	
	/**
	 * This function will generate number based on the length and format given and return the data
	 * @param length - length of number to be generated.
	 * @return
	 */
	public static String generateRandomNumber(int length){
		String alphabet = new String("123456789");
		int n = alphabet.length();
		String result = new String(); 
		Random r = new Random();

		for (int i=0; i<length; i++)
		    result = result + alphabet.charAt(r.nextInt(n));
			return result;
	}
	
	/**
	 * Overridden function - This function will click on any element passed based on the actual element passed. This will take care of the selenium exceptions occurring based on the delays, unknown errors etc.
	 * There is a while loop given just to avoid such delay exceptions. It is necessary to avoid such errors. This is required in some cases where we have to fetch.
	 * @param elementKeyWord - Keyword of the element given in object repo and excel.
	 * @throws Exception
	 */
	public static void click(WebElement element) throws Exception{	
		element.click();
/*        boolean clicked = false;
        int i=0;
        do{
            try {
            	element.click();
            	clicked=true;
            } catch (Exception e) {
            	i++;
            	if(i<500)
            		continue;
            	else
            		throw e;
            } 
        } while (!clicked);*/
	}
	
	/**
	 * This function will click on the gender button based on the gender keyword in excel sheet.
	 * @param radioButtonKeyword1 - Gender Male radio button keyword
	 * @param radioButtonKeyword2 - Gender female radio button keyword
	 * @param radioButtonKeyword3 - Gender Other radio button keyword
	 * @throws Exception
	 */
	public static void inputRadioButton(String Value,String radioButtonKeyword1,String radioButtonKeyword2,String radioButtonKeyword3)throws Exception{
  		if(Value.equalsIgnoreCase("Yes"))
  			ReusableMethods.clickButton(radioButtonKeyword1);
  		else if(Value.equalsIgnoreCase("No"))
  			ReusableMethods.clickButton(radioButtonKeyword2);
  		else if(Value.equalsIgnoreCase("No Data"))
  			ReusableMethods.clickButton(radioButtonKeyword3);
	}
	
	/**
	 * This function will click on the image button based on the gimage key number in excel sheet.
	 * @param image1Keyword - Image 1 keyword
	 * @param image2Keyword - Image 2 keyword
	 * @param image3Keyword - Image 3 keyword
	 * @param image4Keyword - Image 4 keyword
	 * @param image5Keyword - Image 5 keyword
	 * @throws Exception
	 */
	public static void selectProfileImage(String image1Keyword,String image2Keyword,String image3Keyword,String image4Keyword,String image5Keyword)throws Exception{
  		if(ReusableMethods.TEST_DATA.get("IMAGE").equalsIgnoreCase("1"))
  			ReusableMethods.clickButton(image1Keyword);
  		else if(ReusableMethods.TEST_DATA.get("IMAGE").equalsIgnoreCase("2"))
  			ReusableMethods.clickButton(image2Keyword);
  		else if(ReusableMethods.TEST_DATA.get("IMAGE").equalsIgnoreCase("3"))
  			ReusableMethods.clickButton(image3Keyword);
  		else if(ReusableMethods.TEST_DATA.get("IMAGE").equalsIgnoreCase("4"))
  			ReusableMethods.clickButton(image4Keyword);
  		else if(ReusableMethods.TEST_DATA.get("IMAGE").equalsIgnoreCase("5"))
  			ReusableMethods.clickButton(image5Keyword);
	}
	
	/**
	 * This function will get the test data from excel, launch the application and start video recording. This should be the first function to be called in all the test cases.
	 * @param excelTabName - The sheet name of test data excel to fetch the data from each row of that sheet.
	 * @param row - The row number in the excel where test scenario exists
	 * @throws Exception
	 */
	public static Boolean initiateTest(String excelTabName,int row) throws Exception{
		 getTestData(excelTabName,row);
		 if(scope.equalsIgnoreCase("Smoke")){
				if(TEST_DATA.get("SCOPE").equalsIgnoreCase("Smoke")) {
					 return true;
				}
				else
					return false;
		 }
		 else {
			 return true;
		 }
	}
	
	/**
	 * This function will fail the test case , write results to excel, html and then it will stop the video recording and also capture the screenshot.
	   This function should be called in the main class exception block and it will restart the driver.
	 * @param excelTabName - The sheet name of test data excel to fetch the data from each row of that sheet.
	 * @param row - The row number in the excel where test scenario exists
	 * @param reportOrder - The order in which the test result should be added to html report
	 * @param exception - Exception caught from the main class.
	 * @throws Exception
	 */
	public static void suspendTest(String excelTabName, int row,Exception exception,String stepNo) throws Exception{
		String testCaseName = TEST_DATA.get("TEST_CASE_ID")+"_"+ TEST_DATA.get("STEPNAME");	
		if(config.getProperty("screenshotCapture").equalsIgnoreCase("true")) {
			 takeScreenshot(testCaseName);
		}
		if(config.getProperty("htmlReportFlag").equalsIgnoreCase("true"))
			resultFail(TEST_DATA.get("EXPECTED_RESULT"),getException(exception),stepNo);
		if(config.getProperty("excelReportFlag").equalsIgnoreCase("true"))
			writeTestResultsToExcel(excelTabName, row,"Fail",getExceptionString(exception));
		if(config.getProperty("tfsResultsUploadFlag").equalsIgnoreCase("true"))
			 uploadResultsToTfs(getExceptionString(exception),"Failed");
		if(config.getProperty("tfsImageUploadFlag").equalsIgnoreCase("true")&&config.getProperty("tfsResultsUploadFlag").equalsIgnoreCase("true"))
			uploadAttachmentToTfs("image");
/*		if(config.getProperty("tfsVideoUploadFlag").equalsIgnoreCase("true")&& config.getProperty("tfsResultsUploadFlag").equalsIgnoreCase("true"))
			uploadAttachmentToTfs("video");*/
	}
	
	/**
	 * This function will internally create the json structure for uploading the results into TFS and upload the actual and status.
	   This function will create a http connection and and call the Rest API of Tfs to update the result.
	 * @param uploadType - Mention whether is an update or a new test
	 * @param runId - Test Run Id Generated in TFS
	 * @param testId - Test Case Id Generated in TFS
	 * @param actualResult - Actual result of the test
	 * @param status - Status of the test (Use enums "Passed, Failed, Blocked" only
	 * @throws Exception
	 */
	public static void uploadResultsToTfs(String actualResult, String status){
		Object jsonBody1 =null;	
		Object jsonBody2 =null;	
		try{
			URL url = new URL(HttpUtils.getAbsoluteUrl("getTestPointUrl"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));	
			JSONObject response1Json=new JSONObject(br.readLine());
			testPointId=response1Json.getJSONArray("value").getJSONObject(0).get("id");
			conn.disconnect();
			
			URL url1 = new URL(HttpUtils.getAbsoluteUrl("startTestRun"));
			HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
			conn1.setDoOutput(true);
			conn1.setRequestMethod("POST");
			conn1.setRequestProperty("Content-Type", "application/json");
			jsonBody1 = HttpUtils.createTestRunJson(testPointId);
			
			DataOutputStream wr = new DataOutputStream(conn1.getOutputStream());
			wr.write(jsonBody1.toString().getBytes("UTF-8"));
			BufferedReader br1 = new BufferedReader(new InputStreamReader((conn1.getInputStream())));
			JSONObject responseJson=new JSONObject(br1.readLine());
			testRunId=responseJson.get("id");
			conn1.disconnect();
			
			URL url2 = new URL(HttpUtils.getAbsoluteUrl("updateTestStepResult"));
			HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
			conn2.setDoOutput(true);
			conn2.setRequestMethod("POST");
			conn2.setRequestProperty("Content-Type", "text/plain");
			jsonBody2 = HttpUtils.createStepUpdateJson(testPointId,testRunId,actualResult,status);
			
			DataOutputStream wr1 = new DataOutputStream(conn2.getOutputStream());
			wr1.write(jsonBody2.toString().getBytes("UTF-8"));
			System.out.println(url2);
			System.out.println("Final : "+conn2.getResponseCode()+conn2.getResponseMessage());
			conn2.disconnect();
			
			}catch(Exception e){
				try {
					String message = "TFS Upload Failed for "+ TEST_DATA.get("TEST_CASE_ID")+"_"+testRunId+". Exception : " + getExceptionString(e);
					logger.info(message);  
					e.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	}
/**
 * This function will upload the screenshot or video taken for this test case to Tfs based on the type of file
 * @param type : Type of file whether it is image or video. For screenshot use  keyword "image" and for video use keyword "video"
 * @throws Exception
 */
	public static void uploadAttachmentToTfs(String type) throws Exception{	
		try{
			Thread.sleep(1000);
			String charset = "UTF-8";
			File file=null;
			URL url = null;
			if(type.equalsIgnoreCase("image")){
				file = new File(screenshotFilePath);
				url = new URL(HttpUtils.getAbsoluteUrl("uploadFileUrl")+screenshotFileName);
			}
			else if(type.equalsIgnoreCase("video")){
				file = new File(videoFilePath);
				url = new URL(HttpUtils.getAbsoluteUrl("uploadFileUrl")+videoFileName);
			}			
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			url = uri.toURL();
			
			System.out.println(url);
			
			URLConnection connection = new URL(url.toString()).openConnection();
			connection.setDoOutput(true);
		    
			OutputStream output = connection.getOutputStream();
		    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
		    writer.append("Content-Transfer-Encoding: binary");
		    
		    InputStream is = new FileInputStream(file);
		    IOUtils.copy(is, output);
		    
		    output.flush(); 
		    writer.close();

			int responseCode = ((HttpURLConnection) connection).getResponseCode();
			System.out.println(responseCode); 

		}catch(Exception e){
			try {
				String message = "TFS Image/video Upload Failed for "+ TEST_DATA.get("TEST_CASE_ID")+"_"+testRunId+". Exception : " + getExceptionString(e);
				logger.info(message);  
				e.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
	}
	
/**
 * 
 * @param testCaseName
 * @return
 * @throws Exception
 */
	public static Object startScreenRecord(String testCaseName) throws Exception {
		Process p;
		p = Runtime.getRuntime().exec("adb -s "+deviceName+" shell screenrecord /sdcard/tempFile/"+testCaseName+".mp4");
		System.out.println("adb -s "+deviceName+" shell screenrecord /sdcard/tempFile/"+testCaseName+".mp4");
		return p;	

	}
	public static Object startLogRecord(String testCaseName) throws Exception {
		Process p;
		p = Runtime.getRuntime().exec("adb shell logcat -v time -f /sdcard/tempFile/"+testCaseName+".txt&");
		System.out.println("adb shell logcat -v time -f /sdcard/tempFile/"+testCaseName+".txt&");
		Thread.sleep(1000);
		return p;	

	}
/**
 * 
 * @param processRecordingVideo
 * @param testCaseName
 * @throws Exception
 */
	public static void stopScreenRecord(Process processRecordingVideo,String testCaseName) throws Exception {   
		processRecordingVideo.destroyForcibly();
		Process process;
		String testCaseTempName = testCaseName.replaceAll("\\s+","");
		Thread.sleep(1000);
		process = Runtime.getRuntime().exec("adb pull /sdcard/tempFile/"+testCaseTempName+".mp4"+" "+tempPath);
		process.waitFor(30, TimeUnit.SECONDS);
		process.destroy();
		System.out.println("adb pull /sdcard/tempFile/"+testCaseTempName+".mp4"+" "+tempPath);	
		File file = new File(SetupDriver.videoPath); 
        if (!file.exists())
        	file.mkdirs();
        videoFilePath = file+"\\"+testCaseName+"-"+dateFormat.format(new Date())+".mp4";
	    File srcFile= new File(tempPath+"\\"+testCaseTempName+".mp4");
	    Thread.sleep(1000);
	    FileUtils.copyFile(srcFile,new File(videoFilePath)); 
	}
	
	public static void stopLogRecord(Process processLog,String testCaseName) throws Exception {   
		processLog.destroyForcibly();
		Process process;
		String testCaseTempName = testCaseName.replaceAll("\\s+","");
		Thread.sleep(1000);
		process = Runtime.getRuntime().exec("adb pull /sdcard/tempFile/"+testCaseTempName+".txt"+" "+tempPath);
		process.waitFor(30, TimeUnit.SECONDS);
		process.destroy();
		System.out.println("adb pull /sdcard/tempFile/"+testCaseTempName+".txt"+" "+tempPath);
		File file = new File(SetupDriver.logPath); 
        if (!file.exists())
        	file.mkdirs();
        logFilePath = file+"\\"+testCaseName+"-"+dateFormat.format(new Date())+".txt";
	    File srcFile= new File(tempPath+"\\"+testCaseTempName+".txt");
	    Thread.sleep(1000);
	    FileUtils.copyFile(srcFile,new File(logFilePath));  	    
	   
	}
/**
 * 
 * @param file
 * @throws Exception
 */
	public static void deleteTempFiles(File file) throws Exception {
	    if (file.isDirectory()){
	        for (File f : file.listFiles())
	        	deleteTempFiles(f);
	    file.delete();
	    }
	    else
	        file.delete();
	}
	
	public static String getException(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
	
	public static void scrollDown()
  	{
	    Dimension size = driver.manage().window().getSize();
	    //Starting y location set to 80% of the height (near bottom)
	    int starty = (int) (size.height * 0.80);
	    //Ending y location set to 20% of the height (near top)
	    int endy = (int) (size.height * 0.20);
	    //x position set to mid-screen horizontally
	    int startx = size.width / 2;
	    
	    TouchAction action = new TouchAction(driver);
	    action.press(PointOption.point(startx, starty))
        .moveTo(PointOption.point(startx, endy)).release().perform();
  	}
	public static void scrollUp()
  	{
	    Dimension size = driver.manage().window().getSize();
	    //Starting y location set to 80% of the height (near bottom)
	    int starty = (int) (size.height * 0.20);
	    //Ending y location set to 20% of the height (near top)
	    int endy = (int) (size.height * 0.80);
	    //x position set to mid-screen horizontally
	    int startx = size.width / 2;
	    
	    TouchAction action = new TouchAction(driver);
	    action.press(PointOption.point(startx, starty))
        .moveTo(PointOption.point(startx, endy)).release().perform();
  	}
	public static void swipeRight()
  	{
		Dimension size = driver.manage().window().getSize();
        int  startY = (int) (size.height / 2);
        int startX = (int) (size.width * 0.90);
        int endX = (int) (size.width * 0.05);
        
	    TouchAction action = new TouchAction(driver);
	    action.press(PointOption.point(startY, startX))
        .moveTo(PointOption.point(endX, startY)).release().perform();
  	}
	public static void swipeLeft()
  	{
		Dimension size = driver.manage().window().getSize();
        int startY = (int) (size.height / 2);
        int startX = (int) (size.width * 0.05);
        int endX = (int) (size.width * 0.90);
        
	    TouchAction action = new TouchAction(driver);
	    action.press(PointOption.point(startX, startY))
        .moveTo(PointOption.point(endX, startY)).release().perform();
  	}
  
}