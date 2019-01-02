
package test.philips.com.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
//import io.appium.java_client.service.local.AppiumDriverLocalService;

public class SetupDriver extends Init {
	public static  AppiumDriver<MobileElement> driver;
	public static DesiredCapabilities phone1DC=new DesiredCapabilities();
	public static DesiredCapabilities phone2DC=new DesiredCapabilities();
	
    public static TouchActions phone1TouchAction;
    public static TouchActions phone2TouchAction;
	
	public static ExtentReports report;
    public static ExtentTest parent;

   // public static String videoPath,screenshotPath,reportPath,logPath,reportExcelPath,deviceName,tempPath,scope;
    public static String videoPath=null;
    public static String screenshotPath=null;
    public static String reportPath=null;
    public static String reportPathSmoke=null;
    public static String logPath=null;
    public static String reportExcelPath=null;
    public static String tempPath=null;
    public static String scope = null;
    public static String deviceName = null;
	public static FileInputStream src;
	public static HSSFWorkbook wb;
	public static Logger logger;
	public static Object testRunId,testPointId;
    public static Process process,process1;
    public static File tempFile;
   // AppiumDriverLocalService service;

	@BeforeSuite
	public  void setUp() throws Exception
	{
		try{
			//service = AppiumDriverLocalService.buildDefaultService();
			//service.start();
				
  	      	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
  	      	videoPath = config.getProperty("videoPath")+ dateFormat.format(new Date());
  	      	screenshotPath = config.getProperty("screenshotPath")+ dateFormat.format(new Date());
  	      	//reportPath = config.getProperty("reportPath")+dateFormat.format(new Date())+".html";
  	      	reportExcelPath=config.getProperty("reportPath")+dateFormat.format(new Date())+".xls";
  	      	logPath = config.getProperty("logPath")+dateFormat.format(new Date());
  	        scope = config.getProperty("scope");
  	      	
/*  	      	File fh = new File(logPath);
  		    logger = Logger.getLogger("MyLog");  
  		    FileHandler fh1;  
	        fh1 = new FileHandler(logPath);  
	        logger.addHandler(fh1);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh1.setFormatter(formatter);  
	    	*/
  	      	
  	      	//Start of html report
  	        reportPath = config.getProperty("reportPath")+dateFormat.format(new Date())+".html";
  	      	report=new ExtentReports(reportPath); 
			//parent=report.startTest("MOM Mobile Automation Test Suite");			
			//End
	        
		    tempPath = "c:\\tempFolder";
			tempFile = new File(tempPath);  
		    ReusableMethods.deleteTempFiles(tempFile);
	        if (!tempFile.exists())
	        	tempFile.mkdirs();
	        
			Process p;
			p = Runtime.getRuntime().exec("adb shell rm -r /sdcard/tempFile");
			p.waitFor();
	        p=Runtime.getRuntime().exec("adb shell mkdir /sdcard/tempFile");
			p.waitFor();

	        
			phone1DC.setCapability("deviceName", Init.config.getProperty("deviceName1"));
			phone1DC.setCapability("platformName", Init.config.getProperty("platformName"));
			phone1DC.setCapability("appActivity", Init.config.getProperty("appActivityNameCMA"));
			phone1DC.setCapability("appPackage", Init.config.getProperty("appPackageNameCMA"));
			phone1DC.setCapability("automationName",Init.config.getProperty("automationName"));
			phone1DC.setCapability("platformVersion","6.0.1");
			phone1DC.setCapability("noReset",true);
			phone1DC.setCapability("fullReset",false); 
			phone1DC.setCapability("newCommandTimeout",0);    
			phone1DC.setCapability("appWaitDuration", "600000");
			phone1DC.setCapability("unicodeKeyboard", true);
			
			phone2DC.setCapability("deviceName", Init.config.getProperty("deviceName2"));
			phone2DC.setCapability("platformName", Init.config.getProperty("platformName"));
			phone2DC.setCapability("appActivity", Init.config.getProperty("appActivityNamePatient"));
			phone2DC.setCapability("appPackage", Init.config.getProperty("appPackageNamePatient"));
			phone2DC.setCapability("automationName",Init.config.getProperty("automationName"));
			phone2DC.setCapability("platformVersion","6.0.1");
			phone2DC.setCapability("noReset",true);
			phone2DC.setCapability("fullReset",false); 
			phone2DC.setCapability("newCommandTimeout",0);    
			phone2DC.setCapability("appWaitDuration", "600000");
			phone2DC.setCapability("unicodeKeyboard", true); 
			
			//Start of Excel report
  			src=new FileInputStream(SetupDriver.class.getClassLoader().getResource(config.getProperty("testDataPath")).getPath());
  			wb = new HSSFWorkbook(src);
  			//End
  			//driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}catch(Exception e){			
			e.printStackTrace();
        }
	}
	
	@AfterSuite
	public void tearDown(){
		try{
			Thread.sleep(5000);
			ReusableMethods.deleteTempFiles(tempFile);
			Process p;
			p = Runtime.getRuntime().exec("adb shell rm -r /sdcard/tempFile");
			p.waitFor();
			driver.quit();	
			report.endTest(parent);
			report.flush();
  			FileOutputStream src1=new FileOutputStream(reportExcelPath);
  			wb.write(src1);
  			src1.close();
  			Path sourceDirectory1 = Paths.get(reportPath);
		    Path targetDirectory1 = Paths.get(".//test-report//TestReport.html");
  			Path sourceDirectory2 = Paths.get(reportExcelPath);
		    Path targetDirectory2 = Paths.get(".//test-report//TestReport.xlsx");
			try {
				Files.copy(sourceDirectory1, targetDirectory1,StandardCopyOption.REPLACE_EXISTING);
				Files.copy(sourceDirectory2, targetDirectory2,StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch(Exception e)
          {
			System.out.println("Setup exception");
			e.printStackTrace();
			report.endTest(parent);
			report.flush();
          }
	}

}

