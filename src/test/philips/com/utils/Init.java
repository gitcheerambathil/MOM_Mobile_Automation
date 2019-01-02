package test.philips.com.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
public class Init {
	public static Properties config,objectRepository,verifyElements;
	//Initializes configuration property file
	@BeforeSuite
	public void readConfigProperties() throws IOException
	{
		String configpath = getClass().getClassLoader().getResource("config.properties").getPath();
		FileInputStream fInputS=new FileInputStream(configpath);
		config=new Properties();
		config.load(fInputS);
		
		
		String objectRepoPath = getClass().getClassLoader().getResource("objectRepository.properties").getPath();
		FileInputStream fInputS1=new FileInputStream(objectRepoPath);
		objectRepository=new Properties();
		objectRepository.load(fInputS1);

	}
}