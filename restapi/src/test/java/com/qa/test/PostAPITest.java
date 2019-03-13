package com.qa.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostAPITest extends TestBase {
	
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
	
	
	
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException
	{
		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		//jackson API
		
		ObjectMapper mapper = new ObjectMapper();
		Users users =new Users("VivekR" , "TestEngineer");  //Expected Users object 
		
		//marsheling -- converting object to JSON file.
		
		mapper.writeValue(new File("D:\\Vivek\\SeleniumWorkSpace\\restapi\\src\\main\\java\\com\\qa\\data\\users.json"), users);
		
		//Object to JSON String--- Marshelling
		
		String userJsonString = mapper.writeValueAsString(users);
		System.out.println(userJsonString);
		
		closableHttpResponse = restClient.post(url, userJsonString, headerMap);
		
		
		//***Validate response from API***//
		
		//1. Status Code
		int statusCode = closableHttpResponse.getStatusLine().getStatusCode();
		
		Assert.assertEquals(statusCode,testBase.RESPONSE_STATUS_CODE_201);
		
		//2. JSON String is correct or Not
		
		String responseString = EntityUtils.toString(closableHttpResponse.getEntity(), "UTF-8");
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("The response from API is: "+responseJson);
		
		//3. JSON to JAVA object  : Unmarshelling
		Users userResObj = mapper.readValue(responseString, Users.class);  //Actual users object we are getting from API
		System.out.println(userResObj);
		
		
		Assert.assertTrue(users.getName().equals(userResObj.getName()));
		Assert.assertTrue(users.getJob().equals(userResObj.getJob()));
		
		
		System.out.println(userResObj.getId());
		System.out.println(userResObj.getCreatedAt());
		
		
	}
	
}
