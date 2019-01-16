package com.intern.project.weather_caller;

import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import junit.framework.Assert;

public class OpenWeatherMapClientTest {
	
	private static OpenWeatherMapClient owmC;
	
	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Test
	public void getGeoLocLocationTest_Success() {
		owmC = new OpenWeatherMapClient();
		GeographicLocation geoLoc1 = owmC.getGeoLocation("Des Moines");
		
		long desMoinesID = 4853828;
		Assert.assertTrue(geoLoc1.getID() == desMoinesID);
		
		long chicagoID = 4887398;
		GeographicLocation geoLoc2 = owmC.getGeoLocation("Chicago");
		Assert.assertTrue(geoLoc2.getID() == chicagoID);
	}
	
	@Test
	public void getGeoLoctionTest_Fail() throws NoSuchElementException{
		owmC = new OpenWeatherMapClient();
		thrown.expect(NoSuchElementException.class);
		thrown.expectMessage("Could not find: Tim");
		@SuppressWarnings("unused")
		GeographicLocation geoLocNotInFile = owmC.getGeoLocation("Tim");
	}

}
