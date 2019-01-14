package com.intern.project.weather_caller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
	private boolean commandSuccessful = false;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("What's the Weather?");
		//init();
		
		GridPane gridPane = new GridPane();
		gridPane.setVgap(5); 
	    gridPane.setHgap(5);
		gridPane.setPadding(new Insets(10, 10, 10, 10)); 
		gridPane.setAlignment(Pos.BASELINE_CENTER);
		
		Label instruction = new Label("Enter a City and State below.");
		TextField userInputBox = new TextField();
		Button btn = new Button("Go!");
		//this allows the user to either click the button or just press enter in the textbox
		userInputBox.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				btn.fire();
			}
		});
		btn.setOnAction(e -> {
			//Parse user input
			String input = userInputBox.getText();
			OpenWeatherMapClient client = new OpenWeatherMapClient();
			JSONObject object = client.getJsonObject(input);
			//Only allows US requests.
			String result = (String) object.get("name");
			System.out.println(result);
			System.out.println(object.toJSONString());
			JSONObject jA = (JSONObject) object.get("main");
			System.out.println(jA.toJSONString());
			Double temp = (Double) jA.get("temp");
			Double fahTemp = (Double) ((temp - 273.6) * (9.0/5) + 32);
			System.out.println("Temperture is " + fahTemp.shortValue() + " degrees fahrenheit.");
			commandSuccessful = true;
			if (commandSuccessful) {
				//Show info
			} else {
				//ask again
			}
		});

		gridPane.add(instruction, 0, 0);
		gridPane.add(userInputBox, 0, 1);
		gridPane.add(btn, 1, 1);
		primaryStage.setScene(new Scene(gridPane, 647, 400));//gotta have that golden ratio
		primaryStage.show();

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
