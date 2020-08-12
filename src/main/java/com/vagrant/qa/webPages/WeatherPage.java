package com.vagrant.qa.webPages;

import java.util.List;

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

}
