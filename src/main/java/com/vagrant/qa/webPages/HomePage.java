package com.vagrant.qa.webPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vagrant.qa.base.TestBase;

public class HomePage extends TestBase {
	
	@FindBy(className = "topnavmore")
	WebElement subMenuDots;
	
	@FindBy(xpath = "//a[text()='WEATHER']")
	WebElement weatherButton;
	
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	public WeatherPage goToWeatherPage() {
		
		subMenuDots.click();
		weatherButton.click();
		
		return new WeatherPage();
	}

}
