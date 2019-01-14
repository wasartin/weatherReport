package com.intern.project.weather_caller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Playground {

	public static void main(String[] args) {
		
		JSONParser parser = new JSONParser();
		JSONArray newArray = new JSONArray();
		try {
			JSONArray originalArr = (JSONArray) parser.parse(new FileReader("C:\\Users\\watis\\Downloads\\cityList\\city.list.json"));
			
			for(int i = 0; i < originalArr.size(); i++) {
				JSONObject tempObj = (JSONObject) originalArr.get(i);
				if(((String)tempObj.get("country")).equals("US")) {
					newArray.add(tempObj);
				}
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("Correct length = 19,972" + ", acutal is: " + newArray.size()); 
		
		//write new file to use.
		try {
			FileWriter file = new FileWriter("C:\\Users\\watis\\Downloads\\cityList\\usCities.json");
			file.write(newArray.toJSONString());
			file.flush();
		} catch(Exception e) {
			System.err.println(e);
		}
	}
}