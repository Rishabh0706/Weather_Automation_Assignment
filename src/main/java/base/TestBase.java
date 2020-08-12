package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBase {
	
	protected static WebDriver driver;
	protected static Properties prop;
	protected static Logger log = Logger.getLogger(TestBase.class);
	
	private static long PAGE_LOAD_TIMEOUT = 20;
	private static long IMPLICIT_WAIT = 40;
	
	public TestBase() {
		
		log.info("Reading Properties File");
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	protected static void initialization() {
		
		String browserName = prop.getProperty("browser");
		
		File driversPath = new File(System.getProperty("user.dir"));
		
		log.info("Opening Browser");
		if(browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", driversPath+"/chromedriver");	
			driver = new ChromeDriver(); 
		} else if(browserName.equals("FF")) {
			System.setProperty("webdriver.gecko.driver", driversPath+"/geckodriver");	
			driver = new FirefoxDriver(); 
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		log.info("Opening URL - " + prop.getProperty("pageUrl"));
		driver.get(prop.getProperty("pageUrl"));
	}

}
