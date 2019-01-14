package com.intern.project.weather_caller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class OpenWeatherMapClient {

	private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s,US&APPID=%s";
	private static final String API_KEY = "e3072a490add9ea37b2f06bbd0f9ae84";
	private static final String MEDIA_TYPE = "application/json";
	
	private static final String BASE_URL_BY_ID = "http://api.openweathermap.org/data/2.5/weather?q=%s,US&APPID=%s";
	
	private static CloseableHttpClient httpClient;
	private static HttpGet httpGet;
	private static CloseableHttpResponse httpResponse;
	
	public OpenWeatherMapClient() {
		
	}
	
	
	public JSONObject getJsonObject(String cityName) {
		GeographicLocation foundLoc = getGeoLocation(cityName);
		
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
	
	public JSONObject findByID(String cityName) {
		GeographicLocation foundLoc = getGeoLocation(cityName);
		
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
	
	//Made public for testing
	//TODO iterate once, and then lets do a hashmap instead of this garbage.
	//TODO 
	public GeographicLocation getGeoLocation(String inputCity) {
		JSONParser parser = new JSONParser();
		JSONArray newArray = new JSONArray();
		GeographicLocation result = new GeographicLocation();
		try {
			JSONArray originalArr = (JSONArray) parser.parse(new FileReader("C:\\Users\\watis\\Downloads\\cityList\\city.list.json"));
			
			for(int i = 0; i < originalArr.size(); i++) {
				JSONObject tempObj = (JSONObject) originalArr.get(i);
				if(((String)tempObj.get("name")).equals(inputCity)) {
					result.setID((long)tempObj.get("id"));
					result.setCityName((String)tempObj.get("name"));
					result.setCountry((String)tempObj.get("country"));
					
					JSONObject coordObj = (JSONObject) tempObj.get("coord");
					Coordinate coordinate = new Coordinate((double)coordObj.get("lon"), (double)coordObj.get("lan"));
					result.setCoordinate(coordinate);
				}
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
