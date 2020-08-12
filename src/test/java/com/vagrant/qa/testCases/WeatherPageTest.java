package com.vagrant.qa.testCases;

import org.testng.annotations.Test;

import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.vagrant.qa.base.TestBase;
import com.vagrant.qa.webPages.HomePage;
import com.vagrant.qa.webPages.WeatherPage;

public class WeatherPageTest extends TestBase{
	
	HomePage homePage;
	WeatherPage weatherPage;
	
	@BeforeMethod
	public void setup() {
		initialization();
		homePage = new HomePage();
	}
	
	@Parameters({"cityName"})
	@Test(enabled = false)
	public void validateCityName(String cityName) throws InterruptedException {
		
		weatherPage = homePage.goToWeatherPage();
		
		String cityText = weatherPage.getCityInfo(cityName);
		
		Assert.assertEquals(cityText, cityName);

	}
	
	@Parameters({"cityName", "tempratureListSize"})
	@Test(enabled = false)
	public void validateTempratureInfo(String cityName, int tempratureListSize) throws InterruptedException {
		
		weatherPage = homePage.goToWeatherPage();
		
		int tempListSize = weatherPage.getTempratureInfo(cityName);
		
		Assert.assertEquals(tempListSize, tempratureListSize);

	}
	
	@Parameters({"cityName"})
	@Test(enabled = false)
	public void validateWeatherDetailsPopUpisDisplayed(String cityName) throws InterruptedException {
		
		weatherPage = homePage.goToWeatherPage();
		
		Boolean popUpState = weatherPage.getWeatherDetailPopUp(cityName);
		
		Assert.assertTrue(popUpState, "Weather Details Popup is not displayed");;

	}
	
	@Parameters({"cityName"})
	@Test
	public void comapreWebAndApiWeatherData(String cityName) throws InterruptedException {
		
		weatherPage = homePage.goToWeatherPage();
		
		LinkedHashMap<String, Integer> weatherObjectFromPage = weatherPage.createWeatherDetailObject(cityName);
		

	}
	
	@AfterMethod
	public void clean() throws InterruptedException {
		
		Thread.sleep(1000);
		driver.quit();
	}

}
