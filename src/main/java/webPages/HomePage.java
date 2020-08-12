package webPages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;

public class HomePage extends TestBase {
	
	@FindBy(className = "topnavmore")
	WebElement subMenuDots;
	
	@FindBy(xpath = "//a[text()='WEATHER']")
	WebElement weatherButton;
	
	public HomePage() {
		log.info("initializing home page factory");
		PageFactory.initElements(driver, this);
	}
	
	public WeatherPage goToWeatherPage() {
		
		log.info("going to weather page");
		subMenuDots.click();
		weatherButton.click();
		
		return new WeatherPage();
	}

}
