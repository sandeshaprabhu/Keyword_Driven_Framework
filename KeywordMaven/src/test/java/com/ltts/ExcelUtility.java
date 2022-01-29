package com.ltts;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * ExcelUtility class has methods related to setting up the excel file,reading the excel sheets and getting row and column counts.
 * @author Sandesh Prabhu
 * @author DigendraKumar Sahu
 * @version 1.0.0
 * @since 1.0.0
 */
public class ExcelUtility {
	/**
	 * Variable to store excel workbook.
	 */
	static XSSFWorkbook workbook;

	/**
	 * Setup the excel file to read.
	 * @param excelFilePath Path of the excel file.
	 */
	public static void setExcel(String excelFilePath) {
		try {
			File source = new File(excelFilePath);
			FileInputStream fileInput = new FileInputStream(source);
			workbook = new XSSFWorkbook(fileInput);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Gets cell data as object
	 * @param sheet Sheet of the excel workbook to get the data.
	 * @param rownum Row number of the cell. 
	 * @param cellnum Column number of the cell.
	 * @return Cell data of the specified cell.
	 */
	public static Object getData(XSSFSheet sheet, int rownum, int cellnum) {
		Cell cell = sheet.getRow(rownum).getCell(cellnum);
		DataFormatter formatter = new DataFormatter();
		Object data = formatter.formatCellValue(cell);
		return (data);
	}

	/**
	 * Gets cell data as String.
	 * @param sheet Sheet of the excel workbook to get the data.
	 * @param rownum Row number of the cell.
	 * @param cellnum Column number of the cell.
	 * @return Cell data of the specified cell in String format.
	 */
	public static String getDataString(XSSFSheet sheet, int rownum, int cellnum) {
		Cell cell = sheet.getRow(rownum).getCell(cellnum);
		DataFormatter formatter = new DataFormatter();
		Object data = formatter.formatCellValue(cell);
		return (data.toString());
	}

	/**
	 * Gets the total row count of the specified sheet.
	 * @param sheet Excel sheet to get row count. 
	 * @return Total number of row count in the excel sheet. 
	 */
	public static int getRowCount(XSSFSheet sheet) {
		int rowCount = 0;
		try {
			rowCount = sheet.getPhysicalNumberOfRows();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
		}
		return (rowCount);
	}

	/**
	 * Gets the total column count in the specified excel sheet.
	 * @param sheet Excel sheet to get Column count.
	 * @return Total number of column count in the specified excel sheet.
	 */
	public static int getColCount(XSSFSheet sheet) {
		int colCount = 0;
		try {
			colCount = sheet.getRow(0).getPhysicalNumberOfCells();
			System.out.println();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
		}
		return (colCount);
	}

	/**
	 * To get the starting row a specified test Case.
	 * @param sheet Excel sheet to get starting row of test case.
	 * @param testCaseName Name of the test case.
	 * @return Starting row in the excel sheet where the test case starts.
	 */
	public static int getStartRow(XSSFSheet sheet, String testCaseName) {
		int testCasestartRow = 0;
		int rowCount = getRowCount(sheet);
		for (int row = 0; row < rowCount; row++) {
			String cellData = getDataString(sheet, row, 0);
			if (cellData.equalsIgnoreCase(testCaseName)) {
				testCasestartRow = row;
				break;
			}
		}
		return (testCasestartRow);
	}

	/**
	 * Gets the last row of specified test case.
	 * @param sheet Excel sheet to get Ending row of test case.
	 * @param testCaseName Name of the test case.
	 * @return Ending row in the excel sheet where the test case ends.
	 */
	public static int getLastRow(XSSFSheet sheet, String testCaseName) {
		int testCaseEndRow = 0;
		int rowCount = getRowCount(sheet);
		for (int row = 0; row < rowCount; row++) {
			String cellData = getDataString(sheet, row, 0);
			if (cellData.equalsIgnoreCase(testCaseName)) {
				testCaseEndRow = row;
			}

		}
		return (testCaseEndRow);
	}

	/**
	 * Gets the column header and its index of the specified excel sheet. 
	 * @param sheet Excel sheet to get column headers.
	 * @return  Returns column headers with its index in hash map
	 */
	public static HashMap<String, Integer> getHeaders(XSSFSheet sheet) {
		final HashMap<String, Integer> headers = new HashMap<String, Integer>();
		sheet.getRow(0).forEach(cell -> {
			headers.put(cell.getStringCellValue(), cell.getColumnIndex());
		});
		return (headers);
	}

}
