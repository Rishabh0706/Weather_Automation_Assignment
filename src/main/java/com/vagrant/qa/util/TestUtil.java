package com.vagrant.qa.util;

import static io.restassured.RestAssured.given;

import java.util.LinkedHashMap;
import java.util.Map;

import com.vagrant.qa.base.TestBase;

import io.restassured.response.Response;

public class TestUtil extends TestBase{
	
	public static LinkedHashMap<String, Integer> getResponseObjectFromApi(String cityName) {
		
		Response response = given().queryParam("q", cityName)
				.queryParam("appid", prop.getProperty("apiKey"))
				.queryParam("units", prop.getProperty("tempUnit"))
				.when().get(prop.getProperty("apiUrl"));
		
		LinkedHashMap<String, Integer> weatherObjectFromApi = new LinkedHashMap<String, Integer>();
		
		int humidValue = response.jsonPath().get("main.humidity");
		Float floatTempValue =  response.jsonPath().get("main.temp");
		int tempValue = floatTempValue.intValue();
		
		weatherObjectFromApi.put("humidityIn%", humidValue);
		weatherObjectFromApi.put("tempInDegrees", tempValue);
		
		System.out.println("Weather Object From Api ----- ");
		for (Map.Entry<String, Integer> m : weatherObjectFromApi.entrySet()) {
			System.out.println(m.getKey() + " : " + m.getValue());
		}
		
		return weatherObjectFromApi;
	}
	
	public static boolean getComparisonResult(LinkedHashMap<String, Integer> weatherObjectFromPage, 
			LinkedHashMap<String, Integer> weatherObjectFromApi, int humidVariance, int tempVariance) throws MatcherException {
		
		int humidFromPage = 0, humidFromApi = 0;
		int tempFromPage = 0, tempFromApi = 0;
		
		for (Map.Entry<String, Integer> m : weatherObjectFromPage.entrySet()) {
			
			if (m.getKey().equals("humidityIn%"))
				humidFromPage = m.getValue();
			
			if (m.getKey().contains("tempInDegrees"))
				tempFromPage = m.getValue();
		}
		
		for (Map.Entry<String, Integer> m : weatherObjectFromApi.entrySet()) {
			
			if (m.getKey().equals("humidityIn%"))
				humidFromApi = m.getValue();
			
			if (m.getKey().contains("tempInDegrees"))
				tempFromApi = m.getValue();
		}
		
		Boolean humidResult = comparator(humidFromPage, humidFromApi, humidVariance);
		System.out.println("Humidity Comparator Result - " + humidResult);
		
		Boolean tempResult = comparator(tempFromPage, tempFromApi, tempVariance);
		System.out.println("Temprature Comparator Result - " + tempResult);
		
		if (humidResult && tempResult)
			return true;
		else
			throw new MatcherException("humidity or temprature difference exceeds the variance");
		
	}
	
	public static boolean comparator(int object1, int object2, int v) {
		
		int diff;
		
		if (object1 > object2)
			diff = object1 - object2;
		else
			diff = object2 - object1;
		
		if (diff <= v)
			return true;
		else
			return false;
	}

}
