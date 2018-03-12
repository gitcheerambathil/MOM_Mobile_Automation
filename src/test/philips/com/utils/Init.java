package test.philips.com.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeSuite;
public class Init {
	public static Properties config,objectRepository,verifyElements;

	//Initializes configuration property file
	@BeforeSuite
	public void readConfigProperties() throws IOException
	{
		FileInputStream fInputS=new FileInputStream(System.getProperty("user.dir")+"\\configurations\\config.properties");
		config=new Properties();
		config.load(fInputS);
		
		FileInputStream fInputS1=new FileInputStream(System.getProperty("user.dir")+"\\configurations\\objectRepository.properties");
		objectRepository=new Properties();
		objectRepository.load(fInputS1);
		
	}
}