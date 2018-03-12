package test.philips.com.network;

import org.json.JSONArray;
import org.json.JSONObject;
import test.philips.com.utils.Init;
import test.philips.com.utils.ReusableMethods;
import test.philips.com.utils.SetupDriver;


public class HttpUtils extends SetupDriver {


	public static Object createStepUpdateJson(Object testPointId,Object testRunId,Object actualResult,Object status)throws Exception{
		System.out.println();
		String stepNumber = ReusableMethods.TEST_DATA.get("STEP_NO");
		String testCaseId = ReusableMethods.TEST_DATA.get("TestCase_ID");
		JSONObject object = new JSONObject();
		JSONObject object1 = new JSONObject();
		object1.put("actualResponse", actualResult);
		object1.put("stepNumber", stepNumber);
		object1.put("StepStatus", status);
		JSONArray array = new JSONArray();
		array.put(object1);
		object.put("testSteps", array);
		object.put("testRunId", testRunId);
		object.put("testCaseId", testCaseId);
		object.put("testPointId", testPointId);
		object.put("testCaseStatus", status);
		object.put("comments", actualResult + "\r\n" + "Test Data" + "\r\n" + ReusableMethods.TEST_DATA);
		
		System.out.println("Json New"+object);
		return object;
	}
	/**
	 * This function will create the Json structure for updating the test results in test case level of TFS.
	 * @param testCaseId : Test case id for which the test case has to be updated.
	 * @param actualResult : Actual result of the test case
	 * @param status : Status of test case whether it is pass/fail
	 * @return : It will return the Json array to the HttpURLConnection
	 * @throws Exception
	 */
	public static Object createTestRunJson(Object testPointId)throws Exception{
		String testPlanId = config.getProperty("testPlanId");
		String testCase = ReusableMethods.TEST_DATA.get("TestCase_ID")+"_"+ReusableMethods.TEST_DATA.get("STEPNAME")+"_New Run";
		
		JSONObject object = new JSONObject();
		JSONObject object1 = new JSONObject();
		object1.put("id", testPlanId);
		JSONArray array = new JSONArray();
		array.put(testPointId);
		object.put("name", testCase);
		object.put("pointIds", array);
		object.put("plan", object1);
		
		System.out.println("Json"+object);
		return object;
	}
	/**
	 * This function will create the api URL to be called by adding the test run id.
	 * @param runId : Test Run id of TFS
	 * @return : It will return the final URL
	 * @throws Exception
	 */
	public static String getAbsoluteUrl(String urlType) throws Exception{
		String testCaseId = ReusableMethods.TEST_DATA.get("TestCase_ID");
		String testStepId = ReusableMethods.TEST_DATA.get("STEP_NO");
		String tfsLink = config.getProperty("tfsUrl");
		String apiStructure = config.getProperty("apiStructure");
		String testPlanId = config.getProperty("testPlanId");
		String testSuiteId = config.getProperty("testSuiteId");
		String updateStepUrl = config.getProperty("updateStepUrl");
		String uploadFileUrl = config.getProperty("uploadFileUrl");
		
		if(urlType.equalsIgnoreCase("getTestPointUrl"))
			return tfsLink+apiStructure+"/plans/"+testPlanId+"/suites/"+testSuiteId+"/points?testcaseid="+testCaseId+"&includePointDetails=true";
		else if(urlType.equalsIgnoreCase("startTestRun"))
			return tfsLink+apiStructure+"/runs?api-version=3.0-preview";
		else if(urlType.equalsIgnoreCase("updateTestStepResult"))
			return updateStepUrl;
		else if(urlType.equalsIgnoreCase("uploadFileUrl"))
			return uploadFileUrl+testRunId+"/"+testCaseId+"/"+testPointId+"/"+testStepId+"?fileName=";
		else
			return null;
		
	}
	
	
	public static Object createJsonForPost(int testCaseId, String actualResult,String status)throws Exception{

		return null;
		
	}
	
	

}
