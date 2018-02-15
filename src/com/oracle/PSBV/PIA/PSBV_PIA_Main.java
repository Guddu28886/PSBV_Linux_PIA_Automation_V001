package com.oracle.PSBV.PIA;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PSBV_PIA_Main {

	public static void main(String[] args) throws InterruptedException {
		
		WebDriver driver=PSBV_PIA_Util.getBrowserDriver();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		WebDriverWait waitLess=new WebDriverWait(driver,60);
		WebDriverWait waitLong=new WebDriverWait(driver,200);
		String excelFileName="Param_"+args[2];
		PSBV_PIA_Util.setExcelPath(excelFileName,args[1]);
		if (args[0].contains("PIA_Validation"))
		{
			if(args[0].equalsIgnoreCase("Linux_PIA_Validation"))
			{
				String sheetName="LINUX"+args[3];
				driver.get(PSBV_PIA_Util.getURL(sheetName));
				driver.manage().window().maximize();
				PSBV_PIA_Functionality.login(driver, PSBV_PIA_Util.userID(sheetName), PSBV_PIA_Util.password(sheetName));
				PSBV_PIA_Functionality.processCheck(driver, waitLess);
				PSBV_PIA_Functionality.logout(driver,waitLess);	
				System.exit(1);
			}
			else if(args[0].equalsIgnoreCase("OVA_PIA_Validation"))
			{
				//String sheetName="OVA";
				String sheetName="TECHVAL"+args[3];
				driver.get(PSBV_PIA_Util.getURL(sheetName));
				driver.manage().window().maximize();
				PSBV_PIA_Functionality.login(driver, PSBV_PIA_Util.userID(sheetName), PSBV_PIA_Util.password(sheetName));
				PSBV_PIA_Functionality.processCheck(driver, waitLess);
				PSBV_PIA_Functionality.logout(driver,waitLess);
				System.exit(1);
			}
			else
			{
				System.out.println("The 1st parameter is: "+args[0]+", which is not correct");
				System.out.println("For PIA validation, it should be 'Linux_PIA_Validation' OR 'OVA_PIA_Validation' which is case sensitive");
				System.out.println("Failed");
				System.exit(1);
			}
			
		}
		else if(args[0].contains("WebProfile_Prod_Settings"))
		{
			if(args[0].equalsIgnoreCase("Windows_WebProfile_Prod_Settings"))
			{
				String sheetName="WIN"+args[3];
				driver.get(PSBV_PIA_Util.getURL(sheetName));
				driver.manage().window().maximize();
				PSBV_PIA_Functionality.login(driver, PSBV_PIA_Util.userID(sheetName), PSBV_PIA_Util.password(sheetName));
				PSBV_PIA_Functionality.windowsWebProfileSettings(driver, waitLess);
				PSBV_PIA_Functionality.logout(driver,waitLess);
				System.exit(1);
			}
			else if(args[0].equalsIgnoreCase("OVA_WebProfile_Prod_Settings"))
			{
				//String sheetName="OVA";
				String sheetName="TECHVAL"+args[3];
				driver.get(PSBV_PIA_Util.getURL(sheetName));
				driver.manage().window().maximize();
				PSBV_PIA_Functionality.login(driver, PSBV_PIA_Util.userID(sheetName), PSBV_PIA_Util.password(sheetName));
				PSBV_PIA_Functionality.windowsWebProfileSettings(driver, waitLess);
				PSBV_PIA_Functionality.logout(driver,waitLess);
				System.exit(1);
			}
		}
		else if(args[0].contains("MLMMC"))
		{
			if(args[0].equalsIgnoreCase("WIN_MLMMC"))
			{
				//String sheetName="MLSRC";
				String sheetName="WIN"+args[3];
				driver.get(PSBV_PIA_Util.getURL(sheetName));
				driver.manage().window().maximize();
				PSBV_PIA_Functionality.login(driver, PSBV_PIA_Util.userID(sheetName), PSBV_PIA_Util.password(sheetName));
				PSBV_PIA_Functionality.MLMMC_Loading(driver, waitLong,waitLess);
				PSBV_PIA_Functionality.logout(driver,waitLess);
				System.exit(1);
			}
			else if(args[0].equalsIgnoreCase("TECHVAL_MLMMC"))
			{
				String sheetName="MLMMC"+args[3];
				driver.get(PSBV_PIA_Util.getURL(sheetName));
				driver.manage().window().maximize();
				PSBV_PIA_Functionality.login(driver, PSBV_PIA_Util.userID(sheetName), PSBV_PIA_Util.password(sheetName));
				PSBV_PIA_Functionality.MLMMC_Loading(driver, waitLong,waitLess);
				PSBV_PIA_Functionality.logout(driver,waitLess);
				System.exit(1);
			}
		}
		else if(args[0].equalsIgnoreCase("ENGMMC"))
		{
			String sheetName="ENGMMC"+args[3];
			driver.get(PSBV_PIA_Util.getURL(sheetName));
			driver.manage().window().maximize();
			PSBV_PIA_Functionality.login(driver, PSBV_PIA_Util.userID(sheetName), PSBV_PIA_Util.password(sheetName));
			PSBV_PIA_Functionality.ENGMMC_Loading(driver, waitLong,waitLess);
			PSBV_PIA_Functionality.logout(driver,waitLess);
			System.exit(1);
		}
		else
		{
			System.out.println("Wrong Parameter, enter one of the option: 'Linux_PIA_Validation' OR 'OVA_PIA_Validation' OR 'Windows_WebProfile_Prod_Settings' OR 'OVA_WebProfile_Prod_Settings' OR 'MLMMC' OR 'ENGMMC' & re-run it");
			System.out.println("Failed");
			System.exit(1);
		}
	}

}
