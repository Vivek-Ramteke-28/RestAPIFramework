package com.qa.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase {
	
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closableHttpResponse;
	
	
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException
	{
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		
		//https://reqres.in/api/users
		
		url = serviceUrl + apiUrl;
	}
	
	@Test(priority=1)
	public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException
	{
		restClient  = new RestClient();
		closableHttpResponse = restClient.get(url);
		
		//a. Status code
		int statusCode = closableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code ----> "+ statusCode);	

		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200 , "Status code is not 200");
		
		
		//b. JSON String
		String responseString = EntityUtils.toString(closableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Respomse JSON from API---->"+ responseJson);
		
		//Single value assertion---- Fetching single data JSON 
		//Per page
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of per page is --->" + perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);
		
		//Total value of records
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("Total value is --->" + totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		
		//Get the value from JSON Array---- Fetching multiple data JSON 
		
		String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");


		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);


		//c. All headers
		Header[] headerArray =closableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for (Header header: headerArray)
		{
			allHeaders.put(header.getName(), header.getValue());
		}
			
		System.out.println("Headers Array---->"+ allHeaders);
		}
		
		
	
	@Test(priority=2)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException
	{
		restClient  = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
//		headerMap.put("userName", "test@rnr.com");
//		headerMap.put("password", "test123");
//		headerMap.put("Auth Token", "12345");

		
		closableHttpResponse = restClient.get(url, headerMap);
		
		//a. Status code
		int statusCode = closableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code ----> "+ statusCode);	

		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200 , "Status code is not 200");
		
		
		
		
		//b. JSON String
		String responseString = EntityUtils.toString(closableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Respomse JSON from API---->"+ responseJson);
		
		//Single value assertion
		//Per page
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of per page is --->" + perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);
		
		//Total value of records
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("Total value is --->" + totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		
		//Get the value from JSON Array
		
		String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");


		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);


		//c. All headers
		Header[] headerArray =closableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for (Header header: headerArray)
		{
			allHeaders.put(header.getName(), header.getValue());
		}
			
		System.out.println("Headers Array---->"+ allHeaders);
		}
	
	
	
	
	
	
	

}
