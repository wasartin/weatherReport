package com.intern.project.weather_caller;

import org.apache.http.HttpException;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DataSourceTest {
	
	private static DataSource dataSource;
	
	@Rule
	public final ExpectedException thrown = ExpectedException.none(); 

	@Test
	public void getResponseTest_Success() throws HttpException {
		dataSource = new DataSource();
		long desMoinesID = 4853828;
		String expectedCityName = "Des Moines";
		JSONObject jsonObject = dataSource.getResponse(desMoinesID);
		String actualCityName = (String)jsonObject.get("name");
		Assert.assertEquals(expectedCityName, actualCityName);
	}
	
	@Test
	public void getResponseTest_Fail() throws HttpException {
		dataSource = new DataSource();
		long idNotInFile = 4598651;
		thrown.expect(HttpException.class);
		dataSource.getResponse(idNotInFile);
	}
}
