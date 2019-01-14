package com.intern.project.weather_caller;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MainTest extends TestCase {
	
	
	@Rule
	public final ExpectedException thrown = ExpectedException.none(); 

	@BeforeAll
	public void setUp() {
		
	}

	@Test
	public void FindCititesIDs_Success() {
		OpenWeatherMapClient owmC = new OpenWeatherMapClient();
		GeographicLocation geoLoc1 = owmC.getGeoLocation("Des Moines");
		Assert.assertTrue(geoLoc1.getID() == 4853828);
		
		GeographicLocation geoLoc2 = owmC.getGeoLocation("Chicago");
		Assert.assertTrue(geoLoc2.getID() == 4887398);
		
		
		
	}

	
	
}
