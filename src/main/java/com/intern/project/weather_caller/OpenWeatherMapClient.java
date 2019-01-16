package com.intern.project.weather_caller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.http.HttpException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class immediately loads the .json file into a JSONArray that can be accessed throughout
 * the lifetime of the object.
 * @author watis
 *
 */
public class OpenWeatherMapClient {
	private static Map<Long, Integer> cachedMap = new HashMap<Long, Integer>();
	
	private static final String INPUT_FILE = "usCities.json";
	
	private static JSONArray usCities;
	private static DataSource dataSource;
	
	static {
		JSONParser parser = new JSONParser();
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			File file = new File(classLoader.getResource(INPUT_FILE).getFile());
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
		dataSource = new DataSource();
	}
	
	public JSONObject findByID(long id) throws HttpException {
		return dataSource.getResponse(id);//Double check
	}
	
	/**
	 * https://openweathermap.org/appid, under tips, recommends not making multiple calls for the same city.
	 * To ensure that doesn't happen, I store the city's id and temp in a Map
	 * @param cityName
	 * @return temperature
	 * @throws HttpException 
	 */
	public int getTempForCity(String cityName) throws HttpException {
		GeographicLocation foundLoc = getGeoLocation(cityName);
		if(cachedMap.containsKey(foundLoc.getID())) {
			return cachedMap.get(foundLoc.getID());
		}
		
		JSONObject foundCity = (JSONObject) findByID(foundLoc.getID()).get("main");
		
		Double temp = (Double) foundCity.get("temp");
		Double fahTemp = (Double) ((temp - 273.6) * (9.0/5) + 32);
		
		cachedMap.put(foundLoc.getID(), fahTemp.intValue());
		return fahTemp.intValue();
	}
	
	/**
	 * https://openweathermap.org/appid, under tips, recommends to only search for cities by their ID.
	 * This creates a GeographicLocation of the JSONObject found from the JSONArray usCities.
	 * @param inputCity
	 * @return geographicLocation
	 */
	public GeographicLocation getGeoLocation(String inputCity) throws NoSuchElementException{
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
				return result;
			}
		}	
		throw new NoSuchElementException("Could not find: " + inputCity);
	}
}