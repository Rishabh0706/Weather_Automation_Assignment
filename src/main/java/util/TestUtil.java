package util;

import static io.restassured.RestAssured.given;

import java.util.LinkedHashMap;
import java.util.Map;

import base.TestBase;
import io.restassured.response.Response;

public class TestUtil extends TestBase{
	
	
	/**
	 * Get weather detail object from API
	 * @param cityName
	 * @return
	 */
	public static LinkedHashMap<String, Integer> getResponseObjectFromApi(String cityName) {
		
		log.info("getting response from API");
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
		
		log.info("Weather Object From Api ----- ");
		for (Map.Entry<String, Integer> m : weatherObjectFromApi.entrySet()) {
			log.info(m.getKey() + " : " + m.getValue());
		}
		
		return weatherObjectFromApi;
	}
	
	
	
	/**
	 * Compares two key-value(String-Integer) objects as per given variance
	 * @param weatherObjectFromPage - weather detail object from web Page
	 * @param weatherObjectFromApi - weather detail object from API
	 * @param humidVariance - variance range for humidity
	 * @param tempVariance - variance range for temperature
	 * @return true if all values matched correctly as per variance range
	 * @throws MatcherException
	 */
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
		log.info("Humidity Comparator Result - " + humidResult);
		
		Boolean tempResult = comparator(tempFromPage, tempFromApi, tempVariance);
		log.info("Temprature Comparator Result - " + tempResult);
		
		if (humidResult && tempResult)
			return true;
		else
			throw new MatcherException("humidity or temprature difference exceeds the variance");
		
	}
	
	
	/**
	 * This method compares two integer objects and returns result as per given variance
	 * @param object1 - first object to be compared
	 * @param object2 - first object to be compared
	 * @param variance - acceptable difference range
	 * @return boolean
	 */
	public static boolean comparator(int object1, int object2, int variance) {
		
		log.info("comparing values in comparator");
		int diff;
		
		if (object1 > object2)
			diff = object1 - object2;
		else
			diff = object2 - object1;
		
		if (diff <= variance)
			return true;
		else
			return false;
	}

}
