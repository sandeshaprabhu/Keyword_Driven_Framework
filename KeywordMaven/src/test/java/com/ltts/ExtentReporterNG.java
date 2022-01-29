package com.ltts;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
/**
 * ExtentReporterNG has all methods related to the extent reports. 
 * @author Sandesh Prabhu
 * @author DigenderKumar Sahu
 * @version 1.0.0
 * @since 1.0.0
 */
public class ExtentReporterNG {
	
	public static ExtentReports extent;
	//Parent Test(Test case)
	ThreadLocal<ExtentTest> ParentTest = new ThreadLocal<ExtentTest>();
	//Child test(Test Steps)
	ThreadLocal<ExtentTest> test=new ThreadLocal<ExtentTest>();
	
	String projectPath=System.getProperty("user.dir");
	
	/**
	 * Setup the title,required information on the extent report.
	 * @return ExtentReport setup by all the required information.
	 */
	public static ExtentReports extentReportGenerator()
	{
		//Extent Reports
		String reportFilePath=System.getProperty("user.dir")+"\\reports\\index.html";
		ExtentSparkReporter reporter=new ExtentSparkReporter(reportFilePath);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");
				
		extent=new ExtentReports();
		extent.attachReporter(reporter);
		
		reporter.config().setTheme(Theme.DARK);
		
		//Adding extra information to the extent Report
		extent.setSystemInfo("Tester", "Sandesh Prabhu");
		extent.setSystemInfo("Tester", "Digendra Kumar Sahu");
		extent.setSystemInfo("URL", TestNgFile.URL);
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("Browser",TestNgFile.browserName);
		if(TestNgFile.groupExecution.equalsIgnoreCase("Yes"))
		{
			extent.setSystemInfo("Group Name", TestNgFile.groupName);
			reporter.config().setDocumentTitle("Test Results of "+TestNgFile.groupName);
		}
		return(extent);
	}
	
	/**
	 * Set the parent extent test(test Cases) in the extent report.
	 * @param extentParentTestObject Name of the test cases to log in extent report.
	 */
	public void setParentExtentTest(ExtentTest extentParentTestObject)
	{
		ParentTest.set(extentParentTestObject);
	}
	
	/**
	 * To get the Current testCase name in the extent report.
	 * @return current test case in the extent report.
	 */
	public ExtentTest getParentExtentTest()
	{
		return(this.ParentTest.get());
	}
	
	/**
	 * Set the child extent test(test Steps) in the extent report.
	 * @param extentTestObject Name of the testStep to log in extent report.
	 */
	public void setExtentTest(ExtentTest extentTestObject)
	{
		test.set(extentTestObject);
	}
	
	/**
	 * To get the current testStep in the extent report.
	 * @return Current test step.
	 */
	public ExtentTest getExtentTest()
	{
		return(this.test.get());
	}
	
	
	/**
	 * Captures the screen shot of the webpage.
	 * @param fileName Name of the screenshot file.
	 * @param driver Current WebDriver of the browser.
	 * @return Path of screenShot file where it is saved.
	 */
	public String getScreenShot(String fileName,WebDriver driver)
	{
		String screenshotPath=projectPath+"\\screenshots\\"+fileName+".jpg";
		try {
			File screenshotFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(	screenshotFile, new File(projectPath+"\\screenshots\\"+fileName+".jpg"));
			screenshotPath=projectPath+"\\screenshots\\"+fileName+".jpg";
			return(screenshotPath);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to take screenShot!!!");
		}
		return(screenshotPath);
	}

}
