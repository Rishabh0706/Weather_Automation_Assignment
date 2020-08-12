package com.vagrant.qa.webPages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vagrant.qa.base.TestBase;

public class WeatherPage extends TestBase {
	
	@FindBy(className = "searchBox")
	static WebElement searchBox;
	
	@FindBy(xpath = "//div[@class='message' and not (@style)]//input")
	static WebElement cityCheckBox;
	
	public WeatherPage() {
		PageFactory.initElements(driver, this);
	}
	
	public static void selectCity(String cityName) throws InterruptedException {
		
		Thread.sleep(2000);
		
		searchBox.sendKeys(cityName);
		
		cityCheckBox.click();
		
	}
	
	
	public String getCityInfo(String cityName) throws InterruptedException {
		
		selectCity(cityName);
		
		String cityText = driver.findElement(By.xpath("//div[@title='" + cityName + "']/div[@class='cityText']")).getText();
		
		System.out.println("city name - " + cityText);
		
		return cityText;
	}
	
	
	public int getTempratureInfo(String cityName) throws InterruptedException {
		
		selectCity(cityName);
		
		List<WebElement> tempratures = driver.findElements(By.xpath("//div[@title='" + cityName + "']/div[@class='temperatureContainer']/span"));
		
		for (WebElement e : tempratures) {
			System.out.println("temprature - " + e.getText());
		}
		
		return tempratures.size();
	} 
	
	
	public boolean getWeatherDetailPopUp(String cityName) throws InterruptedException {
		
		selectCity(cityName);
		
		driver.findElement(By.xpath("//div[@title='" + cityName + "']")).click();
		
		WebElement weatherPopUp = driver.findElement(By.xpath("//span[contains(text(),'" + cityName + "')]"
				+ "//ancestor::div[@class='leaflet-popup-content-wrapper']"));
		
		return weatherPopUp.isDisplayed();
		
	}
	
	public LinkedHashMap<String, Integer> createWeatherDetailObject(String cityName) throws InterruptedException {
		
		selectCity(cityName);
		
		driver.findElement(By.xpath("//div[@title='" + cityName + "']")).click();
		
		List<WebElement> weatherDetailList = driver.findElements(By.xpath("//span[contains(text(),'" + cityName + "')]"
				+ "//parent::div//following-sibling::span[@class='heading']"));
		
		LinkedHashMap<String, Integer> weatherObjectFromPage = new LinkedHashMap<String, Integer>();
		
		for(WebElement e : weatherDetailList) {
			
			String detail = e.getText();
			
			if (detail.contains("Humidity")) {
				String[] humidity = detail.replace("%", "").split(": ");
				int humidValue = Integer.parseInt(humidity[1]);
				weatherObjectFromPage.put("humidityIn%", humidValue);
			}
			
			if (detail.contains("Degrees")) {
				String[] temp = detail.split(": ");
				int tempValue = Integer.parseInt(temp[1]);
				weatherObjectFromPage.put("tempInDegrees", tempValue);
			}
		}
		
		System.out.println("Weather Object From web Page ----- ");
		for (Map.Entry<String, Integer> m : weatherObjectFromPage.entrySet()) {
			
			System.out.println(m.getKey() + " : " + m.getValue());
		}
		
		return weatherObjectFromPage;
		
	}

}
