package com.ltts;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;
/**
 * DriverGenerator class has methods to setup Thread safe WebDriver of selected browser.
 * @author Sandesh Prabhu
 * @author DigenderKumar Sahu
 * @version 1.0.0
 * @since 1.0.0
 */
public class DriverGenerator extends ExtentReporterNG {

	public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

	/**
	 * Setup the WebDriver for the specified browser with head or headless depending on initial setup.
	 */
	@BeforeMethod
	public void setUp() {
		switch (TestNgFile.browserName) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			if (TestNgFile.browserHead.equalsIgnoreCase("no")) {
				options.addArguments("headless");
			}
			driver.set(new ChromeDriver(options));
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options1 = new FirefoxOptions();
			if (TestNgFile.browserHead.equalsIgnoreCase("no")) {
				options1.addArguments("headless");
			}
			driver.set(new FirefoxDriver(options1));
			break;
		}
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	/**
	 * To get the current thread WebDriver of the browser. 
	 * @return Returns thread safe web driver of the selected browser
	 */
	public WebDriver getDriver() {
		return (driver.get());
	}

	/**
	 * To close the WebDriver after all test steps are executed. 
	 */
	@AfterMethod
	public void driverClose() {
		getDriver().quit();
	}
}
