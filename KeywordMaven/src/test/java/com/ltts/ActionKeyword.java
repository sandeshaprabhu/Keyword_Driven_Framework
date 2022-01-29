package com.ltts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
/**
 * ActionKeyword class has methods of all keywords.
 * @author Sandesh Prabhu
 * @author DigendraKumar Sahu
 * @version 1.0.0
 * @since 1.0.0
 */
public class ActionKeyword {
	/**
	 * Launch the specified URL.
	 * @param driver WebDriver of the browser. 
	 * @param URL URL to be launched.
	 */
	public static void launchURL(WebDriver driver, String URL) {
		driver.get(URL);
	}
	/**
	 * Enters the text in the specified locator. 
	 * @param driver WebDriver of the browser.
	 * @param locator Locator to identify a web element. 
	 * @param locatorValue The value of the locator attribute.
	 * @param value The text to entered.
	 */
	public static void enterText(WebDriver driver, String locator, String locatorValue, String value) {
		driver.findElement(getLocator(locator, locatorValue)).sendKeys(value);
	}
	
	/**
	 * Clears the field specified by the locator.
	 * @param driver WebDriver of the browser.
	 * @param locator Locator to identify a web element.
	 * @param locatorValue The value of the locator attribute.
	 */
	public static void clearField(WebDriver driver, String locator, String locatorValue) {
		driver.findElement(getLocator(locator, locatorValue)).clear();
	}
	
	/**
	 * Clicks on a web element specified by the locator. 
	 * @param driver WebDriver of the browser.
	 * @param locator Locator to identify a web element.
	 * @param locatorValue The value of the locator attribute.
	 */
	public static void Click(WebDriver driver, String locator, String locatorValue) {
		driver.findElement(getLocator(locator, locatorValue)).click();
	}
	
	/**
	 * Double clicks on a web element specified by the locator.
	 * @param driver WebDriver of the browser.
	 * @param locator Locator to identify a web element.
	 * @param locatorValue The value of the locator attribute.
	 */
	public static void doubleClick(WebDriver driver, String locator, String locatorValue) {
		WebElement element = driver.findElement(getLocator(locator, locatorValue));
		Actions actions = new Actions(driver);
		actions.doubleClick(element).perform();
	}
	
	/**
	 * Right clicks on a web element specified by the locator.
	 * @param driver WebDriver of the browser.
	 * @param locator Locator to identify a web element.
	 * @param locatorValue The value of the locator attribute.
	 */
	public static void rightClick(WebDriver driver, String locator, String locatorValue) {
		WebElement element = driver.findElement(getLocator(locator, locatorValue));
		Actions actions = new Actions(driver);
		actions.contextClick(element).perform();
	}

	// Mouse movement
	/**
	 * Move the mouse pointer over a web element specified by the locator.
	 * @param driver WebDriver of the browser.
	 * @param locator Locator to identify a web element.
	 * @param locatorValue The value of the locator attribute.
	 */
	public static void mouseMovement(WebDriver driver, String locator, String locatorValue) {
		WebElement element = driver.findElement(getLocator(locator, locatorValue));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
	}
	
	/**
	 * Selects an option by visible text from the dropdown menu.
	 * @param driver WebDriver of the browser.
	 * @param locator Locator to identify a web element.
	 * @param locatorValue The value of the locator attribute.
	 * @param value Option to be selected from the dropdown menu.
	 */
	public static void dropDown(WebDriver driver, String locator, String locatorValue, String value) {
		Select s = new Select(driver.findElement(getLocator(locator, locatorValue)));
		s.selectByVisibleText(value);
	}

	/**
	 * DeSelects the option from the dropdown menu.
	 * @param driver WebDriver of the browser.
	 * @param locator Locator to identify a web element.
	 * @param locatorValue The value of the locator attribute.
	 * @param value Option to be deselected from the dropdown menu.
	 */
	public static void deSelectDropdown(WebDriver driver, String locator, String locatorValue, String value) {
		Select s = new Select(driver.findElement(getLocator(locator, locatorValue)));
		s.deselectByValue(value);
	}

	/**
	 * Waits till the web element is displayed.
	 * @param driver WebDriver of the browser.
	 * @param locator Locator to identify a web element.
	 * @param locatorValue The value of the locator attribute.
	 */
	public static void waitForElement(WebDriver driver, String locator, String locatorValue) {
		WebDriverWait w = new WebDriverWait(driver, 5);
		w.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator, locatorValue)));
	}

	/**
	 * Refreshes the current webpage.
	 * @param driver WebDriver of the browser.
	 */
	public static void refreshPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	/**
	 * Navigates to the previous webpage. 
	 * @param driver WebDriver of the browser.
	 */
	public static void navigateBack(WebDriver driver) {
		driver.navigate().back();
	}

	/**
	 * Navigates to the next Webpage.
	 * @param driver WebDriver of the browser.
	 */
	public static void navigateForward(WebDriver driver) {
		driver.navigate().forward();
	}

	// javaScripts
	/**
	 * Accepts or dismiss the javaScript alert based on value.
	 * @param driver WebDriver of the browser.
	 * @param value accept or dismiss.
	 */
	public static void alertAcceptDissmiss(WebDriver driver, String value) {
		if (value.equalsIgnoreCase("accept")) {
			driver.switchTo().alert().accept();
		} else {
			if (value.equalsIgnoreCase("dismiss")) {
				driver.switchTo().alert().dismiss();
			}
		}

	}

	/**
	 * Enters text in the alert text box.
	 * @param driver WebDriver of the browser.
	 * @param value Text to be entered in alert box.
	 */
	public static void alertEnterText(WebDriver driver, String value) {
		driver.switchTo().alert().sendKeys(value);
	}

	// slider
	/**
	 * Drag and drop the webElement from one place to another in webpage. 
	 * @param driver WebDriver of the browser.
	 * @param target Where WebElement is to be dropped.
	 * @param source Locator of webElement TO BE dragged.
	 */
	public static void dragAndDrops(WebDriver driver, String target, String source) {
		WebElement ele_Target = driver.findElement(By.xpath(target));
		WebElement ele_Source = driver.findElement(By.xpath(source));
		Actions actions = new Actions(driver);
		actions.dragAndDrop(ele_Source, ele_Target).build().perform();
	}

	// closing the browser
	/**
	 * Closes the current webpage. 
	 * @param driver WebDriver of the browser.
	 */
	public static void closeBrowser(WebDriver driver) {
		driver.close();
	}
	
	//Verification Methods :
	
	/**
	 * Verifies the title of the webpage.
	 * @param driver WebDriver of the browser.
	 * @param expected Expected title of the webpage
	 * @param test ExtentTest variable to Log actual title in extent report.  
	 * @return true if actual title matches the expected,else return false.
	 */
	public static boolean verifyTitle(WebDriver driver, String expected,ExtentTest test) {
		String actualTitle = driver.getTitle();
		test.info("Actual Title : "+actualTitle);
		try {
			Assert.assertEquals(actualTitle, expected);
			return true;
		} catch (AssertionError e) {
			return false;
		}
	}

	/**
	 * Verifies the URL launched.
	 * @param driver WebDriver of the browser.
	 * @param expected Expected Url of the webpage.
	 * @param test ExtentTest variable to Log actual URL in extent report.
	 * @return true if actual URL matches the expected,else return false.
	 */
	public static boolean verifyURL(WebDriver driver, String expected,ExtentTest test) {
		String actualURL = driver.getCurrentUrl();
		test.info("Actual URL : "+actualURL);
		try {
			Assert.assertEquals(actualURL, expected);
			return true;
		} catch (AssertionError e) {
			return false;
		}
	}

	/**
	 * Verifies the presence of webElement in a webpage.
	 * @param driver WebDriver of the browser.
	 * @param locator Locator to identify a web element.
	 * @param locatorValue The value of the locator attribute.
	 * @return true if the webElement specified by locator is present,else return false.
	 */
	public static boolean verifyElementPresence(WebDriver driver, String locator, String locatorValue) {
		try {
			Assert.assertTrue(driver.findElement(getLocator(locator, locatorValue)).isDisplayed());
			return true;
		} catch (AssertionError e) {
			return false;
		}
	}

	/**
	 * Verifies if webElement is enabled or not.
	 * @param driver WebDriver of the browser.
	 * @param locator Locator to identify a web element.
	 * @param locatorValue The value of the locator attribute.
	 * @return true if the webElement specified by locator is enabled,else return false.
	 */
	public static boolean verifyElementEnalble(WebDriver driver, String locator, String locatorValue) {
		try {
			Assert.assertTrue(driver.findElement(getLocator(locator, locatorValue)).isEnabled());
			return true;
		} catch (AssertionError e) {
			return false;
		}
	}

	/**
	 * Verifies selection of element in a webpage.
	 * @param driver WebDriver of the browser.
	 * @param locator Locator to identify a web element.
	 * @param locatorValue The value of the locator attribute.
	 * @return true if the webElement specified by locator is selected,else return false.
	 */
	public static boolean verifySelection(WebDriver driver, String locator, String locatorValue) {
		try {
			Assert.assertTrue(driver.findElement(getLocator(locator, locatorValue)).isSelected());
			return true;
		} catch (AssertionError e) {
			return false;
		}
	}

	/**
	 * Verifies the text present in the text field of webpage.
	 * @param driver WebDriver of the browser.
	 * @param expected Expected text to verify on the webPage.
	 * @param locator Locator to identify a web element to verify text.
	 * @param locatorValue The value of the locator attribute.
	 * @param test ExtentTest variable to Log actual text in extent report.
	 * @return true if the text present in the text field is equal to expected text,else return false.
	 */
	public static boolean verifyFieldText(WebDriver driver, String expected, String locator, String locatorValue,ExtentTest test) {
		String actualText = driver.findElement(getLocator(locator, locatorValue)).getAttribute("value");
		test.info("Actual Value is : "+actualText);
		try {
			Assert.assertEquals(actualText, expected);
			return true;
		} catch (AssertionError e) {
			return false;
		}
	}
	
	/**
	 * Verifies the text present in the webpage.
	 * @param driver WebDriver of the browser.
	 * @param expected Expected text to verify on the webPage.
	 * @param locator Locator to identify a web element to verify text.
	 * @param locatorValue The value of the locator attribute.
	 * @param test ExtentTest variable to Log actual text in extent report.
	 * @return true if the text present in webElement is equal to expected,else return false.
	 */
	public static boolean verifyText(WebDriver driver, String expected, String locator, String locatorValue,ExtentTest test) {
		String actualText = driver.findElement(getLocator(locator, locatorValue)).getText();
		test.info("Actual Value is : "+actualText);
		try {
			Assert.assertEquals(actualText, expected);
			return true;
		} catch (AssertionError e) {
			return false;
		}
	}

	/**
	 * Identifies the webElement based on the locator.
	 * @param locator Locator to identify a web element(id,xpath,etc).
	 * @param locatorValue The value of the locator attribute.
	 * @return By type variable which identified webElement based on the locator.
	 */
	public static By getLocator(String locator, String locatorValue) {
		/**
		 * Variable to store the located WebElement by locator.
		 */
		By findElementBy;
		switch (locator) {
		case "id":
			findElementBy = By.id(locatorValue);
			break;

		case "name":
			findElementBy = By.name(locatorValue);
			break;

		case "xpath":
			findElementBy = By.xpath(locatorValue);
			break;

		case "className":
			findElementBy = By.className(locatorValue);
			break;

		case "cssSelector":
			findElementBy = By.cssSelector(locatorValue);
			break;

		case "linkText":
			findElementBy = By.linkText(locatorValue);
			break;

		case "partialLinkText":
			findElementBy = By.partialLinkText(locatorValue);
			break;

		case "tagName":
			findElementBy = By.tagName(locatorValue);
			break;

		default:
			findElementBy = By.xpath(locatorValue);
			break;
		}
		return (findElementBy);
	}

}
