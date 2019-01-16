package com.intern.project.weather_caller;

import org.apache.http.HttpException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("What's the Weather?");
		
		GridPane gridPane = new GridPane();
		gridPane.setVgap(5); 
	    gridPane.setHgap(5);
		gridPane.setPadding(new Insets(10, 10, 10, 10)); 
		gridPane.setAlignment(Pos.BASELINE_CENTER);
		
		Label instruction = new Label("Enter a City located in US below.");
		TextField userInputBox = new TextField();
		Button btn = new Button("Go!");
		Text userInputCity = new Text();
		Text tempResult = new Text();
		
		userInputBox.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				btn.fire();
			}
		});
		btn.setOnAction(e -> {
			String input = userInputBox.getText();
			OpenWeatherMapClient client = new OpenWeatherMapClient();
			
			userInputCity.setText(input + " weather:");
			try {
				tempResult.setText(client.getTempForCity(input) + " degrees Fahrenheit.");
			} catch (HttpException e1) {
				e1.printStackTrace();
			}
		});

		gridPane.add(instruction, 0, 0);
		gridPane.add(userInputBox, 0, 1);
		gridPane.add(btn, 1, 1);
		gridPane.add(userInputCity, 0, 2);
		gridPane.add(tempResult, 0, 3);
		primaryStage.setScene(new Scene(gridPane, 647, 400));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
