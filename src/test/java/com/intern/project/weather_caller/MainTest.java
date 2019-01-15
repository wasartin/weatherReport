package com.intern.project.weather_caller;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MainTest {
	
	
	@Rule
	public final ExpectedException thrown = ExpectedException.none(); 

	@Test
	public void FindCititesIDs_Success() {
		OpenWeatherMapClient owmC = new OpenWeatherMapClient();
		GeographicLocation geoLoc1 = owmC.getGeoLocation("Des Moines");
		System.out.println("Expected: 5792244" + ", actual " + geoLoc1.getID());
		Assert.assertTrue(geoLoc1.getID() == 5792244);
		
		GeographicLocation geoLoc2 = owmC.getGeoLocation("Chicago");
		Assert.assertTrue(geoLoc2.getID() == 4887398);
		
		
		
	}

	
	
}
