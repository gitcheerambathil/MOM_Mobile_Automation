
package test.philips.com.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class SetupDriver extends Init {
	public static  RemoteWebDriver driver=null;
	public static DesiredCapabilities phone1DC=new DesiredCapabilities();
	public static DesiredCapabilities phone2DC=new DesiredCapabilities();
	
    public static TouchActions phone1TouchAction;
    public static TouchActions phone2TouchAction;
	
	public static ExtentReports report;
    public static ExtentTest parent;
    public static ExtentTest child1,child2,child3,child4,child5,child6,child7,child8,child9,child10,child11,child12,child13,child14,child15,child16,child17,child18;
    public static String videoPath=null;
    public static String screenshotPath=null;
    public static String reportPath=null;
    public static String logPath=null;
    public static String reportExcelPath=null;
	public static FileInputStream src;
	public static HSSFWorkbook wb;
	public static Logger logger;
	public static Object testRunId = null;
	public static Object testPointId = null;
	public static String deviceName = null;
    public static Process process =null;
    public static String tempPath=null;
    public static File tempFile;

    
	
	@BeforeSuite
	public  void setUp() throws Exception
	{
		try{
  	      	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
  	      	videoPath = config.getProperty("videoPath")+ dateFormat.format(new Date());
  	      	screenshotPath = config.getProperty("screenshotPath")+ dateFormat.format(new Date());
  	      	reportPath = config.getProperty("reportPath")+dateFormat.format(new Date())+".html";
  	      	reportExcelPath=config.getProperty("reportPath")+dateFormat.format(new Date())+".xls";
  	      	logPath = config.getProperty("logPath")+dateFormat.format(new Date())+".log";

  	      	
  	      	File fh = new File(logPath);
  		    logger = Logger.getLogger("MyLog");  
  		    FileHandler fh1;  
	        fh1 = new FileHandler(logPath);  
	        logger.addHandler(fh1);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh1.setFormatter(formatter);  
	    	
  	      	
  	      	//Start of html report
  	        reportPath = config.getProperty("reportPath")+dateFormat.format(new Date())+".html";
  	      	report=new ExtentReports(reportPath); 
			parent=report.startTest("MOM Mobile Automation Test Suite");			
			//End
	        
		    tempPath = "c:\\tempFolder";
			tempFile = new File(tempPath);  
	        if (!tempFile.exists())
	        	tempFile.mkdirs();
	        
			phone1DC.setCapability("deviceName", Init.config.getProperty("deviceName1"));
			phone1DC.setCapability("platformName", Init.config.getProperty("platformName"));
			phone1DC.setCapability("appActivity", Init.config.getProperty("appActivityNameDoctor"));
			phone1DC.setCapability("appPackage", Init.config.getProperty("appPackageNameDoctor"));
			phone1DC.setCapability("automationName",Init.config.getProperty("automationName"));
			phone1DC.setCapability("platformVersion","6.0.1");
			phone1DC.setCapability("noReset",true);
			phone1DC.setCapability("fullReset",false); 
			phone1DC.setCapability("newCommandTimeout",0);    
			phone1DC.setCapability("appWaitDuration", "600000");
			phone1DC.setCapability("unicodeKeyboard", true);
			
			phone2DC.setCapability("deviceName", Init.config.getProperty("deviceName2"));
			phone2DC.setCapability("platformName", Init.config.getProperty("platformName"));
			phone2DC.setCapability("appActivity", Init.config.getProperty("appActivityNameMidwife"));
			phone2DC.setCapability("appPackage", Init.config.getProperty("appPackageNameMidwife"));
			phone2DC.setCapability("automationName",Init.config.getProperty("automationName"));
			phone2DC.setCapability("platformVersion","6.0.1");
			phone2DC.setCapability("noReset",true);
			phone2DC.setCapability("fullReset",false); 
			phone2DC.setCapability("newCommandTimeout",0);    
			phone2DC.setCapability("appWaitDuration", "600000");
			phone2DC.setCapability("unicodeKeyboard", true); 
			
			//Start of Excel report
  			src=new FileInputStream(config.getProperty("testCasePath"));
  			wb = new HSSFWorkbook(src);
  			//End
		}catch(Exception e){			
			e.printStackTrace();
        }
	}
	
	@AfterSuite
	public void tearDown(){
		try{
			Thread.sleep(5000);
			ReusableMethods.deleteTempFiles(tempFile);
			driver.quit();	
			report.endTest(parent);
			report.flush();
  			FileOutputStream src1=new FileOutputStream(reportExcelPath);
  			wb.write(src1);
  			src1.close();
		} catch(Exception e)
          {
			System.out.println("Setup exception");
			e.printStackTrace();
			report.endTest(parent);
			report.flush();
          }
	}

}

