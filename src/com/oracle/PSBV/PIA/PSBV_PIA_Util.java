package com.oracle.PSBV.PIA;

import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PSBV_PIA_Util {
	
	//public static String excelFilePath="./workbook.xlsx";
	//public static String excelFilePath=System.getProperty("user.dir")+"\\workbook.xlsx";
	public static String excelFilePath="";
	//public static String linuxSheetName="LINUX";
	//public static String chromeDriverPath="D:\\Selenium Softwares\\Selenium_Drivers\\chromedriver.exe";
	//public static String firefoxDriverPath="D:\\Selenium Softwares\\Selenium_Drivers\\geckodriver.exe";
	public static String firefoxDriverPath="\\"+"\\psbldfs\\dfs\\relops\\em-staging\\Utilities\\SAT-Staging_Automation_Tool\\Production\\scripts\\BASS_PIA_Process\\geckodriver.exe";
	public static void setExcelPath(String excelFileName, String appName)
	{
		//excelFilePath="C:\\temp\\"+excelFileName+".xlsx";
		excelFilePath=System.getProperty("user.dir")+"\\Inputs\\"+appName+"\\"+excelFileName+".xlsx";
		System.out.println(excelFilePath);
		//excelFilePath="C:\\temp\\workbook.xlsx";
	}
	public static WebDriver getBrowserDriver()
	{
		//System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		//WebDriver driver=new ChromeDriver();
		System.setProperty("webdriver.firefox.marionette", firefoxDriverPath);
		WebDriver driver=new FirefoxDriver();
		return driver;
	}
		
	public static String getURL(String sheetName)
	{
		String machineName=PSBV_GetData.getExcelCellValue(excelFilePath,sheetName,1,1);
		String url=machineName+".us.oracle.com:8000/psp/ps/?cmd=login&languageCd=ENG&";
		return url;
	}
	
	public static String getDBName(String sheetName)
	{
		String DBName=null;
		if (sheetName.contains("LINUX"))
		{
			DBName=PSBV_GetData.getExcelCellValue(excelFilePath,sheetName,6,1);
			return DBName;
		}
		else if(sheetName.contains("WIN"))
		{
			DBName=PSBV_GetData.getExcelCellValue(excelFilePath,sheetName,5,1);
			return DBName;
		}
		else if(sheetName.contains("TECHVAL"))
		{
			DBName=PSBV_GetData.getExcelCellValue(excelFilePath,sheetName,19,1);
			return DBName;
		}
		else if(sheetName.contains("ENGMMC"))
		{
			DBName=PSBV_GetData.getExcelCellValue(excelFilePath,sheetName,19,1);
			return DBName;
		}
		else if(sheetName.contains("MLMMC"))
		{
			DBName=PSBV_GetData.getExcelCellValue(excelFilePath,sheetName,19,1);
			return DBName;
		}
		return DBName;
	}
	
	public static String userID(String sheetName)
	{
		if(getDBName(sheetName).contains("EP")|getDBName(sheetName).contains("CR")|getDBName(sheetName).contains("IH"))
		{
			return "VP1";
		}
		else if(getDBName(sheetName).contains("HR")|getDBName(sheetName).contains("CS")|getDBName(sheetName).contains("LM"))
		{
			return "PS";
		}
		return null;
		
	}
	public static String password(String sheetName)
	{
		if(getDBName(sheetName).contains("EP")||getDBName(sheetName).contains("CR")||getDBName(sheetName).contains("IH"))
		{
			return "VP1";
		}
		else if(getDBName(sheetName).contains("HR")||getDBName(sheetName).contains("CS")||getDBName(sheetName).contains("LM"))
		{
			return "PS";
		}
		return null;
		
	}

}
