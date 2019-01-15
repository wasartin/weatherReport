package com.intern.project.weather_caller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
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

	private static final String API_KEY = "e3072a490add9ea37b2f06bbd0f9ae84";
	private static final String MEDIA_TYPE = "application/json";
	private static final String BASE_URL_BY_ID = "http://api.openweathermap.org/data/2.5/weather?id=%s&APPID=%s";
	
	//This should go into its own class, like a singleton for a Connection class
	private static CloseableHttpClient httpClient;
	private static HttpGet httpGet;
	private static CloseableHttpResponse httpResponse;
	
	private static Map<Long, Integer> cachedMap = new HashMap<Long, Integer>();
	
	public OpenWeatherMapClient() {
		
	}
	
	public JSONObject findByID(long id) {
		JSONObject result = new JSONObject();
		
		httpClient = HttpClientBuilder.create().build();
		httpGet = new HttpGet(String.format(BASE_URL_BY_ID, id, API_KEY));
		httpGet.addHeader(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE);
		
		try {
			httpResponse = httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.println(httpResponse.getStatusLine().getStatusCode());
			}
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
	
	public int getTempForCity(String cityName) {
		GeographicLocation foundLoc = getGeoLocation(cityName);
		if(cachedMap.containsKey(foundLoc.getID())) {
			return cachedMap.get(foundLoc.getID());
		}
		JSONObject jA = findByID(foundLoc.getID());
		
		//Result
		Double temp = (Double) jA.get("temp");
		Double fahTemp = (Double) ((temp - 273.6) * (9.0/5) + 32);
		
		//Website recomended just cached all the results, b/c there is a limit to number of 
		//calls to one city (20)
		cachedMap.put(foundLoc.getID(), fahTemp.intValue());
		
		return fahTemp.intValue();
	}
	
	//Made public for testing
	//TODO iterate once, and then lets do a hashmap instead of this garbage.
	//TODO regex matching would be awesome
	public GeographicLocation getGeoLocation(String inputCity) {
		JSONParser parser = new JSONParser();
		GeographicLocation result = new GeographicLocation();
		try {
			//find the usCitiesJson file
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("usCities.json").getFile());
			JSONArray originalArray = (JSONArray) parser.parse(new FileReader(file.getAbsolutePath()));
			
			for(int i = 0; i < originalArray.size(); i++) {
				JSONObject tempObj = (JSONObject) originalArray.get(i);
				if(((String)tempObj.get("name")).equalsIgnoreCase(inputCity)) {
					result.setID((long)tempObj.get("id"));
					result.setCityName((String)tempObj.get("name"));
					result.setCountry((String)tempObj.get("country"));
					
					JSONObject coordObj = (JSONObject) tempObj.get("coord");
					Coordinate coordinate = new Coordinate(coordObj.get("lon"), coordObj.get("lan"));
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