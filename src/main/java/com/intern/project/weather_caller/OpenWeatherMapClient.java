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

/**
 * This class immedialtey loads the .json file into a JSONArray that can be accessed throughout
 * the lifetime of the object.
 * @author watis
 *
 */
public class OpenWeatherMapClient {

	private static final String API_KEY = "e3072a490add9ea37b2f06bbd0f9ae84";
	private static final String MEDIA_TYPE = "application/json";
	private static final String BASE_URL_BY_ID = "http://api.openweathermap.org/data/2.5/weather?id=%s&APPID=%s";
	
	//This should go into its own class, like a singleton for a Connection class
	private static CloseableHttpClient httpClient;
	private static HttpGet httpGet;
	private static CloseableHttpResponse httpResponse;
	
	private static Map<Long, Integer> cachedMap = new HashMap<Long, Integer>();
	
	private static JSONArray usCities;
	
	//Load jsonFile into usCities array
	static {
		JSONParser parser = new JSONParser();
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			File file = new File(classLoader.getResource("usCities.json").getFile());
			usCities = (JSONArray) parser.parse(new FileReader(file.getAbsolutePath()));	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public OpenWeatherMapClient() {
		
	}
	
	public JSONObject findByID(long id) {
		JSONObject result = new JSONObject();
		DataSource ds = new DataSource();
		return ds.getResponse(id);//Double check
	}
	
	public int getTempForCity(String cityName) {
		GeographicLocation foundLoc = getGeoLocation(cityName);
		if(cachedMap.containsKey(foundLoc.getID())) {
			return cachedMap.get(foundLoc.getID());
		}
		
		JSONObject foundCity = findByID(foundLoc.getID());
		Double temp = (Double) foundCity.get("temp");
		Double fahTemp = (Double) ((temp - 273.6) * (9.0/5) + 32);
		
		//Website recomended just cached all the results, b/c there is a limit to number of 
		//calls to one city (20)
		cachedMap.put(foundLoc.getID(), fahTemp.intValue());
		
		return fahTemp.intValue();
	}
	
	//Made public for testing
	public GeographicLocation getGeoLocation(String inputCity) {
		GeographicLocation result = new GeographicLocation();
		for(int i = 0; i < usCities.size(); i++) {
			JSONObject tempObj = (JSONObject) usCities.get(i);
			if(((String)tempObj.get("name")).equalsIgnoreCase(inputCity)) {//ugly
				result.setID((long)tempObj.get("id"));
				result.setCityName((String)tempObj.get("name"));
				result.setCountry((String)tempObj.get("country"));
				
				JSONObject coordObj = (JSONObject) tempObj.get("coord");
				Coordinate coordinate = new Coordinate(coordObj.get("lon"), coordObj.get("lan"));
				result.setCoordinate(coordinate);
			}
		}	
		return result;
	}
}