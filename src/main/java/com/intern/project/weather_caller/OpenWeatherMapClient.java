package com.intern.project.weather_caller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class OpenWeatherMapClient {

	private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s,US&APPID=%s";
	private static final String API_KEY = "e3072a490add9ea37b2f06bbd0f9ae84";
	private static final String MEDIA_TYPE = "application/json";
	
	private static CloseableHttpClient httpClient;
	private static HttpGet httpGet;
	private static CloseableHttpResponse httpResponse;
	
	public OpenWeatherMapClient() {
		
	}
	
	
	public JSONObject getJsonObject(String cityName) {
		JSONObject result = new JSONObject();
		httpClient = HttpClientBuilder.create().build();
		httpGet = new HttpGet(String.format(BASE_URL, cityName, API_KEY));
		httpGet.addHeader(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE);
		
		try {
			httpResponse = httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode() == 429) {
				System.out.println("Too many calls in one hour");
			}
			System.out.println(httpResponse.getStatusLine().getStatusCode() + " " 
					+ httpResponse.getStatusLine().getReasonPhrase());
			
			InputStream responseContent = httpResponse.getEntity().getContent();
			JSONParser jsonParser = new JSONParser();
			result = (JSONObject)jsonParser.parse(new InputStreamReader(responseContent, "UTF-8"));
			httpResponse.close(); 
			httpClient.close();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(e);
		}
		return result;
	}
}
