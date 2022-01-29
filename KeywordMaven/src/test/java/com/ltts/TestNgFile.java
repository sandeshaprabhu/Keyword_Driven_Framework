package com.ltts;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;

/**
 * TestNgFile class will read the keywords and from excel sheet and execute it.
 * @author Sandesh Prabhu
 * @author DigendraKumar Sahu
 * @version 1.0.0
 * @since 1.0.0
 */
public class TestNgFile extends DriverGenerator {

	/**
	 * Path of the project folder.
	 */
	static String projectPath = System.getProperty("user.dir");
	
	/**
	 * Path of excel file.
	 */
	static String excelFilePath = projectPath + "\\DataEnginemacro.xlsm";

	/**
	 * Browser setup variables.
	 */
	public static String browserName,URL,browserHead,groupExecution,groupName;
	
	/**
	 * Delimiters to separates test Steps of test case.
	 */
	public static String testStepRowDelimiter=";";
	public static String testStepColumnDelimiter="$~|$";

	/**
	 * Variables to handle excel sheet.
	 */
	static XSSFSheet Mastersheet,TestCasesheet,ConfigureSheet;
	static String masterSheetName,testSheetName;

	/**
	 * Test case sheet column headers with its index.
	 */
	public static HashMap<String, Integer> testCaseColumnHeaders;

	/**
	 * Variable to store extent report.
	 */
	public static ExtentReports extent;
	
	/**
	 * To set the date format for screenshots file name.
	 */
	public static DateFormat dateformat=new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss");

	/**
	 * Setup the excel sheet and extent reports.
	 */
	@BeforeTest
	public void setup() {
		//Setting up the excel file to read.
		ExcelUtility.setExcel(excelFilePath);
		ConfigureSheet = ExcelUtility.workbook.getSheet("ConfigureSheet");
		configure();

		// Initializing master sheet and test case sheet.
		Mastersheet = ExcelUtility.workbook.getSheet(masterSheetName);
		TestCasesheet = ExcelUtility.workbook.getSheet(testSheetName);

		// Initializing test case headers
		testCaseColumnHeaders = ExcelUtility.getHeaders(TestCasesheet);

		// extent report configuration.
		extent = ExtentReporterNG.extentReportGenerator();
	}

	/**
	 * Calls the specified keyword method based on keyword and logs the extent report.
	 * @param test String variable which contains testSteps of a testCase separated by delimiter.
	 */
	@Test(dataProvider = "testData")
	public void mainTest(String test) {
		int testOccurance = 0; // used to setup testName for extent report only once.
		String testName = "";
		String testStepId="";
		String action = "";
		
		/**
		 *  To get the system date for setting up screenshot file name.
		 */
		Date currentDateAndTime;
		String formatedDateAndTime;
		
		try {
			ArrayList<ArrayList<String>> testStepsList = StringSplit(test, testStepRowDelimiter, testStepColumnDelimiter);
			Iterator<ArrayList<String>> testStepIterator = testStepsList.iterator();
			while (testStepIterator.hasNext()) {
				ArrayList<String> testStep = testStepIterator.next();
				testName = testStep.get(testCaseColumnHeaders.get("TestCaseName"));

				testStepId = testStep.get(testCaseColumnHeaders.get("TestStepID"));

				String testStepExecution = testStep.get(testCaseColumnHeaders.get("IsExecutable"));

				action = testStep.get(testCaseColumnHeaders.get("Action"));

				String locator = testStep.get(testCaseColumnHeaders.get("Locator"));

				String locatorValue = testStep.get(testCaseColumnHeaders.get("LocatorValue"));

				String value = testStep.get(testCaseColumnHeaders.get("Value"));

				String expectedValue = testStep.get(testCaseColumnHeaders.get("Expected"));
				String description = testStep.get(testCaseColumnHeaders.get("Description"));

				// to setup the test name for extent reporting(This if block will run only once per test case).
				if (testOccurance == 0) {
					setParentExtentTest(extent.createTest(testName));
					if(groupExecution.equalsIgnoreCase("Yes"))
					{
						getParentExtentTest().assignCategory(groupName);
					}
					testOccurance = 1;
				}
				
				/**
				 * To store the verified result.
				 */
				boolean result;
				
				if (testStepExecution.equalsIgnoreCase("yes")) {
					// Choosing the action to perform.

					switch (action) {
					
					case "LaunchURL":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.launchURL(getDriver(), URL);
						getExtentTest().log(Status.PASS, testStepId +" :"+URL+ " Launched Successfully.");
						break;

					case "EnterText":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.enterText(getDriver(), locator, locatorValue, value);
						getExtentTest().log(Status.PASS, testStepId +" :" +"\""+value+"\" Entered Successfully.");
						break;

					case "clearField":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.clearField(getDriver(), locator, locatorValue);
						getExtentTest().log(Status.PASS, testStepId + " :Clearing Feild");
						break;

					case "Click":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.Click(getDriver(), locator, locatorValue);
						getExtentTest().log(Status.PASS, testStepId + " :Click");
						break;

					case "doubleClick":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.doubleClick(getDriver(), locator, locatorValue);
						getExtentTest().log(Status.PASS, testStepId + " :DoubleClick");
						break;

					case "rightClick":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.rightClick(getDriver(), locator, locatorValue);
						getExtentTest().log(Status.PASS, testStepId + " :RightClick");
						break;

					case "dropDown":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.dropDown(getDriver(), locator, locatorValue, value);
						getExtentTest().log(Status.PASS, testStepId + " :DropDown");
						break;

					case "deSelectDropDown":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.deSelectDropdown(getDriver(), locator, locatorValue, value);
						getExtentTest().log(Status.PASS, testStepId + " :DeSelectDropDown");
						break;

					case "WaitForElement":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.waitForElement(getDriver(), locator, locatorValue);
						getExtentTest().log(Status.PASS, testStepId + " :WaitForElement");
						break;

					case "refreshWebPage":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.refreshPage(getDriver());
						getExtentTest().log(Status.PASS, testStepId + " :RefreshWebPage");
						break;

					case "navigateBack":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.navigateBack(getDriver());
						getExtentTest().log(Status.PASS, testStepId + " :NavigateBack");
						break;

					case "navigateForward":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.navigateForward(getDriver());
						getExtentTest().log(Status.PASS, testStepId + " :NavigateForward");
						break;

					case "alertAcceptDismiss":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.alertAcceptDissmiss(getDriver(), value);
						getExtentTest().log(Status.PASS, testStepId + " :Alert Accept or Dismiss");
						break;

					case "alertBoxEnterText":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.alertEnterText(getDriver(), value);
						getExtentTest().log(Status.PASS, testStepId + " :Enter text in alert box");
						break;

					case "dragAndDrop":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.dragAndDrops(getDriver(), locatorValue, value);
						getExtentTest().log(Status.PASS, testStepId + " :Drag and drop the element in web page");
						break;

					case "closeBrowser":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						ActionKeyword.closeBrowser(getDriver());
						getExtentTest().log(Status.PASS, testStepId + " :CloseBrowser");
						break;

					case "verifyTitle":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						result = ActionKeyword.verifyTitle(getDriver(), expectedValue,getExtentTest());
						getExtentTest().info("Expected Title : "+expectedValue);
						//adding date to name of screenshots
						currentDateAndTime=new Date();
						formatedDateAndTime=dateformat.format(currentDateAndTime);
						getExtentTest().addScreenCaptureFromPath(getScreenShot(testName +"."+testStepId +"." + action+formatedDateAndTime, getDriver()));
						if (result) {
							getExtentTest().log(Status.PASS, testStepId + " :Title Verified Successfully.");
							
						} else {
							getExtentTest().log(Status.FAIL, testStepId + " :Title is not Matching.");
							return;
						}
						break;

					case "verifyURL":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						result = ActionKeyword.verifyURL(getDriver(), expectedValue,getExtentTest());
						getExtentTest().info("Expected Value : "+expectedValue);
						//adding date to name of screenshots
						currentDateAndTime=new Date();
						formatedDateAndTime=dateformat.format(currentDateAndTime);
						getExtentTest().addScreenCaptureFromPath(getScreenShot(testName +"."+testStepId +"." + action+formatedDateAndTime, getDriver()));
						if (result) {
							getExtentTest().log(Status.PASS, testStepId + " :URL Verified Successfully.");
						} else {
							getExtentTest().log(Status.FAIL, testStepId + " :URL is not Correct.");
							return;
						}
						break;

					case "verifyElementPresence":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						result = ActionKeyword.verifyElementPresence(getDriver(), locator, locatorValue);
						//adding date to name of screenshots
						currentDateAndTime=new Date();
						formatedDateAndTime=dateformat.format(currentDateAndTime);
						getExtentTest().addScreenCaptureFromPath(getScreenShot(testName +"."+testStepId +"." + action+formatedDateAndTime, getDriver()));
						if (result) {
							getExtentTest().log(Status.PASS, testStepId + " :Element Presence Verified Successfully.");
						} else {
							getExtentTest().log(Status.FAIL, testStepId + " :Element Not Found.");
							return;
						}
						break;

					case "verifySelection":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						result = ActionKeyword.verifySelection(getDriver(), locator,locatorValue);
						//adding date to name of screenshots
						currentDateAndTime=new Date();
						formatedDateAndTime=dateformat.format(currentDateAndTime);
						getExtentTest().addScreenCaptureFromPath(getScreenShot(testName +"."+testStepId +"." + action+formatedDateAndTime, getDriver()));
						if (result) {
							getExtentTest().log(Status.PASS, testStepId + " :Selection occured successfully");
						} else {
							getExtentTest().log(Status.FAIL, testStepId + " :Selection Not found");
							return;
						}
						break;

					case "verifyElementEnabled":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						result = ActionKeyword.verifyElementEnalble(getDriver(), locator,locatorValue);
						//adding date to name of screenshots
						currentDateAndTime=new Date();
						formatedDateAndTime=dateformat.format(currentDateAndTime);
						getExtentTest().addScreenCaptureFromPath(getScreenShot(testName +"."+testStepId +"." + action+formatedDateAndTime, getDriver()));
						if (result) {
							getExtentTest().log(Status.PASS, testStepId + " :Element is Enabled Successfully.");
						} else {
							getExtentTest().log(Status.FAIL, testStepId + " :Element is not Enabled.");
						}
						break;

					case "verifyFieldText":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						result = ActionKeyword.verifyFieldText(getDriver(), expectedValue, locator,locatorValue,getExtentTest());
						getExtentTest().info("Expected Value : "+expectedValue);
						//adding date to name of screenshots
						currentDateAndTime=new Date();
						formatedDateAndTime=dateformat.format(currentDateAndTime);
						getExtentTest().addScreenCaptureFromPath(getScreenShot(testName +"."+testStepId +"." + action+formatedDateAndTime, getDriver()));
						if (result) {
							getExtentTest().log(Status.PASS, testStepId + " :Entered Text verified successfully");
						} else {
							getExtentTest().log(Status.FAIL, testStepId + " :Entered text is not correct");
						}
						break;
						
					case "verifyText":
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						result = ActionKeyword.verifyText(getDriver(), expectedValue, locator,locatorValue,getExtentTest());
						getExtentTest().info("Expected Value : "+expectedValue);
						//adding date to name of screenshots
						currentDateAndTime=new Date();
						formatedDateAndTime=dateformat.format(currentDateAndTime);
						getExtentTest().addScreenCaptureFromPath(getScreenShot(testName +"."+testStepId +"." + action+formatedDateAndTime, getDriver()));
						if (result) {
							getExtentTest().log(Status.PASS, testStepId + " :Text verified successfully");
						} else {
							getExtentTest().log(Status.FAIL, testStepId + " :Text is not as expected");
						}
						break;

					default:
						setExtentTest(getParentExtentTest().createNode(action+"("+description+")"));
						getExtentTest().log(Status.FAIL, action);
						getExtentTest().info("Invalid keyword!!!");
						//adding date to name of screenshots
						currentDateAndTime=new Date();
						formatedDateAndTime=dateformat.format(currentDateAndTime);
						getExtentTest().addScreenCaptureFromPath(getScreenShot(testName +"."+testStepId +"." + action+formatedDateAndTime, getDriver()));
						return;

					}
				}
			}
		} catch (Exception e) {
			getExtentTest().log(Status.FAIL, action + "\n:" + e);
			try {
				//adding date to name of screenshots
				currentDateAndTime=new Date();
				formatedDateAndTime=dateformat.format(currentDateAndTime);
				getExtentTest().addScreenCaptureFromPath(getScreenShot(testName +"."+testStepId +"." + action+formatedDateAndTime, getDriver()));
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("Failed to save screenshot!!!");
			}
		}
	}

	/**
	 * After the test is finished extent reports are generated.
	 */
	@AfterTest
	public void tearDown() {
		extent.flush();
	}
	
	/**
	 * Configuring the browser, URL and sheet name of excel files.
	 */
	public void configure() {
		int configureSheetRowCount = ExcelUtility.getRowCount(ConfigureSheet);
		for (int row = 1; row < configureSheetRowCount; row++) {
			String cellData = ExcelUtility.getDataString(ConfigureSheet, row, 0);
			cellData = cellData.toLowerCase();
			switch (cellData) {
			case "url":
				URL = ExcelUtility.getDataString(ConfigureSheet, row, 1);
				break;

			case "browser":
				browserName = ExcelUtility.getDataString(ConfigureSheet, row, 1);
				break;

			case "browserhead":
				browserHead = ExcelUtility.getDataString(ConfigureSheet, row, 1);
				break;

			case "mastersheetname":
				masterSheetName = ExcelUtility.getDataString(ConfigureSheet, row, 1);
				break;

			case "testsheetname":
				testSheetName = ExcelUtility.getDataString(ConfigureSheet, row, 1);
				break;

			case "grouptest":
				groupExecution = ExcelUtility.getDataString(ConfigureSheet, row, 1);
				break;

			case "groupname":
				groupName = ExcelUtility.getDataString(ConfigureSheet, row, 1);
				break;
			default:
				System.out.println(cellData + "is a Invalid Entry !!!:");
				break;
			}
		}
	}

	/**
	 * Separates test steps of a test case based on delimiter. 
	 * @param testData String variable which contains testSteps of a testCase separated by delimiter.
	 * @param delimiterRow Delimiter which separates each test step of a test case.
	 * @param delimiterCell Delimiter which separates column of each test step.
	 * @return ArrayList of ArrayList where each element is a test Step of a testCase.
	 */
	public static ArrayList<ArrayList<String>> StringSplit(String testData, String delimiterRow, String delimiterCell) {
		ArrayList<String> RowValue = new ArrayList<String>();
		ArrayList<String> CellValue = new ArrayList<String>();
		ArrayList<ArrayList<String>> ConsolidatedData = new ArrayList<ArrayList<String>>();

		String[] rowSplit = testData.split(delimiterRow);

		for (int row = 0; row < rowSplit.length; row++) {
			RowValue.add(rowSplit[row]);
			String cellData = RowValue.get(row);
			StringTokenizer multiTokenizer = new StringTokenizer(cellData, delimiterCell);
			while (multiTokenizer.hasMoreTokens()) {
				CellValue.add(multiTokenizer.nextToken());

			}

			ConsolidatedData.add(new ArrayList<String>(CellValue));
			CellValue.clear();

		}
		return ConsolidatedData;
	}

	/**
	 * Reads the data from excel sheet and sends to main test.
	 * @return 2D object array containing test Steps of test cases separated by a delimiter where each row is a test case.
	 */
	@DataProvider(name = "testData", parallel = true)
	public Object[][] getExcelData() {
		//checks if group execution is enabled.
		if (groupExecution.equalsIgnoreCase("Yes")) {
			Object[][] group = groupTesting();
			return (group);
		}
		int masterSheetTotalRowCount = ExcelUtility.getRowCount(Mastersheet);
		int testCaseSheetTotalRowCount = TestCasesheet.getPhysicalNumberOfRows();
		int testCaseSheetTotalColumnCount = (TestCasesheet.getRow(0).getPhysicalNumberOfCells());
		int testCaseStartRow = 0;
		int testCaseEndRow = 0;
		int testCaseYesCount = 0;

		// login related variables.
		int loginStartRow = ExcelUtility.getStartRow(TestCasesheet, "Login");
		int loginEndRow = ExcelUtility.getLastRow(TestCasesheet, "Login");

		// counting no of testCases that has execution as yes.
		for (int masterRow = 1; masterRow < masterSheetTotalRowCount; masterRow++) {
			if (ExcelUtility.getDataString(Mastersheet, masterRow, 1).equalsIgnoreCase("yes")) {
				testCaseYesCount++;
			}
		}
		int row = 0, column = 0; //Row and column of testStepArray 
		Object[][] testStepArray = new Object[testCaseSheetTotalRowCount][testCaseSheetTotalColumnCount];
		
		int testStepRow, testStepColumn; //Row and column of test steps in testCase sheet.
		
		int testCaseRow, testCaseColumn; //Row and column of testCase in testCase sheet.
		
		// to store test steps of each test case in a row separated by a delimiter
		Object[][] testCaseArray = new Object[testCaseYesCount][1];
		int arrayRow = 0, arrayColumn = 0; //row and column of testCaseArray.
		for (int masterRow = 1; masterRow < masterSheetTotalRowCount; masterRow++) {
			if (ExcelUtility.getDataString(Mastersheet, masterRow, 1).equalsIgnoreCase("yes")) {

				String testCaseName = (String) ExcelUtility.getDataString(Mastersheet, masterRow, 0);
				testCaseStartRow = ExcelUtility.getStartRow(TestCasesheet, testCaseName);
				testCaseEndRow = ExcelUtility.getLastRow(TestCasesheet, testCaseName);

				row = 0;
				column = 0;
				//To add login related keywords.
				if ((ExcelUtility.getDataString(TestCasesheet, testCaseStartRow, testCaseColumnHeaders.get("Action")))
						.equalsIgnoreCase("verifyLogin")) {
					for (testStepRow = loginStartRow; testStepRow <= loginEndRow; testStepRow++) {
						for (testStepColumn = 0, column = 0; testStepColumn < testCaseSheetTotalColumnCount; testStepColumn++, column++) {
							if ((testStepRow == loginStartRow) && (testStepColumn == 0)) {
								testStepArray[row][column] = testCaseName;
							} else {
								testStepArray[row][column] = ExcelUtility.getData(TestCasesheet, testStepRow,
										testStepColumn);
							}
						}
						row++;
					}
					testCaseStartRow++;
				}
				
				// Iterating through the test case and storing all test steps.
				for (testStepRow = testCaseStartRow; testStepRow <= testCaseEndRow; testStepRow++) {
					if (ExcelUtility.getDataString(TestCasesheet, testStepRow, 0).equalsIgnoreCase(testCaseName)) {
						for (testStepColumn = 0, column = 0; testStepColumn < testCaseSheetTotalColumnCount; testStepColumn++, column++) {
							testStepArray[row][column] = ExcelUtility.getData(TestCasesheet, testStepRow,
									testStepColumn);
						}
						row++;
					}
				}
				arrayColumn = 0;
				// Merging the test steps into a single row separated by a delimiter
				for (testCaseRow = 0; testCaseRow < row; testCaseRow++) {
					for (testCaseColumn = 0; testCaseColumn < testCaseSheetTotalColumnCount; testCaseColumn++) {
						if ((testCaseRow == 0) && (testCaseColumn == 0)) {
							testCaseArray[arrayRow][arrayColumn] = testStepArray[testCaseRow][testCaseColumn]
									+ testStepColumnDelimiter;
						} else {
							if (testCaseColumn == (testCaseSheetTotalColumnCount - 1)) {
								testCaseArray[arrayRow][arrayColumn] = (String) testCaseArray[arrayRow][arrayColumn]
										+ testStepArray[testCaseRow][testCaseColumn];
							} else {
								testCaseArray[arrayRow][arrayColumn] = (String) testCaseArray[arrayRow][arrayColumn]
										+ testStepArray[testCaseRow][testCaseColumn] + testStepColumnDelimiter;
							}
						}
					}
					testCaseArray[arrayRow][arrayColumn] = (String) testCaseArray[arrayRow][arrayColumn] + testStepRowDelimiter;
				}
				arrayRow++;

			}
		}
		return (testCaseArray);
	}

	/**
	 * Reads the test cases of the specified group. 
	 * @return 2D Object array of the test cases related to the specified group.
	 */
	public static Object[][] groupTesting() {
		int masterSheetRowCount = Mastersheet.getPhysicalNumberOfRows();
		int countGroupTestCase = 0;
		int testCaseSheetRowCount = ExcelUtility.getRowCount(TestCasesheet);
		int testCaseSheetColumnCount = (TestCasesheet.getRow(0).getPhysicalNumberOfCells());
		for (int masterRow = 1; masterRow < masterSheetRowCount; masterRow++) {
			
			String groups = ExcelUtility.getDataString(Mastersheet, masterRow, 2);
			String groupNameRemovedSpace=groups.replaceAll("\\s", "");
			List<String> groupList = Arrays.asList(groupNameRemovedSpace.split(","));	
			if ((groupList.contains(groupName))
					&& (ExcelUtility.getDataString(Mastersheet, masterRow, 1).equalsIgnoreCase("Yes"))) {
				countGroupTestCase++;
			}
		}

		// Login related variables.
		int loginStartRow = ExcelUtility.getStartRow(TestCasesheet, "Login");
		int loginEndRow = ExcelUtility.getLastRow(TestCasesheet, "Login");
		
		//TestStep array hold all testSteps of a testCase.
		Object[][] testStepArray = new Object[testCaseSheetRowCount][testCaseSheetColumnCount];
		int row = 0, column = 0; //Row and column of testStepArray 
		int testStepColumn, testStepRow; //Row and column of test steps in testCase sheet.
		int testCaseRow, testCaseColumn; //Row and column of testCase in testCase sheet.

		// Object array to store all test steps in a single row separated by a delimiter
		Object[][] groupArray = new Object[countGroupTestCase][1];
		int groupArrayRow = 0, groupArrayColumn = 0; //row and column of groupArray.

		for (int masterRow = 1; masterRow < masterSheetRowCount; masterRow++) {
			String groups = ExcelUtility.getDataString(Mastersheet, masterRow, 2);
			//to remove the spaces in the group name.
			String groupNameRemovedSpace=groups.replaceAll("\\s", ""); 
			List<String> groupList = Arrays.asList(groupNameRemovedSpace.split(","));
			if ((groupList.contains(groupName))
					&& (ExcelUtility.getDataString(Mastersheet, masterRow, 1).equalsIgnoreCase("Yes"))) {
				String testCaseName = ExcelUtility.getDataString(Mastersheet, masterRow, 0);

				int testCaseStartRow = ExcelUtility.getStartRow(TestCasesheet, testCaseName);
				int testCaseEndRow = ExcelUtility.getLastRow(TestCasesheet, testCaseName);

				row = 0;
				column = 0;

				if ((ExcelUtility.getDataString(TestCasesheet, testCaseStartRow, testCaseColumnHeaders.get("Action")))
						.equalsIgnoreCase("verifyLogin")) {
					for (testStepRow = loginStartRow; testStepRow <= loginEndRow; testStepRow++) {
						for (testStepColumn = 0, column = 0; testStepColumn < testCaseSheetColumnCount; testStepColumn++, column++) {
							if((testStepRow==loginStartRow) && (testStepColumn==0) )
							{
								testStepArray[row][column]=testCaseName;
							}
							else
							{
								testStepArray[row][column] = ExcelUtility.getData(TestCasesheet, testStepRow, testStepColumn);
							}
							
						}
						row++;
					}
					testCaseStartRow++;
				}

				// Iterating through the test case and storing all test steps.
				for (testStepRow = testCaseStartRow; testStepRow <= testCaseEndRow; testStepRow++) {
					if (ExcelUtility.getDataString(TestCasesheet, testStepRow, 0).equalsIgnoreCase(testCaseName)) {
						column = 0;
						for (testStepColumn = 0; testStepColumn < testCaseSheetColumnCount; testStepColumn++, column++) {
							testStepArray[row][column] = ExcelUtility.getDataString(TestCasesheet, testStepRow, testStepColumn);
						}
						row++;
					}
				}

				// Merging the test steps into a single row separated by a delimiter
				groupArrayColumn = 0;
				for (testCaseRow = 0; testCaseRow < row; testCaseRow++) {
					for (testCaseColumn = 0; testCaseColumn < testCaseSheetColumnCount; testCaseColumn++) {
						if ((testCaseRow == 0) && (testCaseColumn == 0)) {
							groupArray[groupArrayRow][groupArrayColumn] = testStepArray[testCaseRow][testCaseColumn]
									+ testStepColumnDelimiter;
						} else {
							if (testCaseColumn == (testCaseSheetColumnCount - 1)) {
								groupArray[groupArrayRow][groupArrayColumn] = (String) groupArray[groupArrayRow][groupArrayColumn]
										+ testStepArray[testCaseRow][testCaseColumn];
							} else {
								groupArray[groupArrayRow][groupArrayColumn] = (String) groupArray[groupArrayRow][groupArrayColumn]
										+ testStepArray[testCaseRow][testCaseColumn] + testStepColumnDelimiter;
							}
						}
					}
					groupArray[groupArrayRow][groupArrayColumn] = (String) groupArray[groupArrayRow][groupArrayColumn]
							+ testStepRowDelimiter;
				}
				groupArrayRow++;
			}
		}
		return (groupArray);
	}
}
