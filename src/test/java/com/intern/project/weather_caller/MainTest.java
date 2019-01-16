package com.intern.project.weather_caller;

import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import junit.framework.Assert;

public class MainTest {
	
	private static OpenWeatherMapClient owmC;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@BeforeAll
	public static void setUp() {
		owmC = new OpenWeatherMapClient();
	}

	@Test
	public void FindCitybyID_Success() {
		GeographicLocation geoLoc1 = owmC.getGeoLocation("Des Moines");
		
		long desMoinesID = 4853828;
		System.out.println(geoLoc1.getID());
		Assert.assertTrue(geoLoc1.getID() == desMoinesID);
		
		long chicagoID = 4887398;
		GeographicLocation geoLoc2 = owmC.getGeoLocation("Chicago");
		Assert.assertTrue(geoLoc2.getID() == chicagoID);
	}
	
	
	@Test
	public void FindCityByID_Fail() {
		exception.expect(NoSuchElementException.class);//doesn't work atm
		GeographicLocation geoLocNotInFile = owmC.getGeoLocation("Tim");
	}

}
