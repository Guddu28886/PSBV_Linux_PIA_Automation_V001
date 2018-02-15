package com.oracle.PSBV.PIA;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
//import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class PSBV_PIA_Functionality {
	
	public static void login(WebDriver driver, String userId, String password) throws InterruptedException
	{
		driver.findElement(By.id("userid")).clear();
		driver.findElement(By.id("userid")).sendKeys(userId);
		driver.findElement(By.id("pwd")).clear();
		driver.findElement(By.id("pwd")).sendKeys(password);
		driver.findElement(By.name("Submit")).click();
		if(driver.findElements(By.id("login_error")).size()>0)
		{
			
			if(driver.findElement(By.id("login_error")).getText().contains("User ID and Password are required."))
			{
				System.out.println("User ID and Password are required. It can't be blank");
				System.exit(1);
			}
			else if(driver.findElement(By.id("login_error")).getText().contains("Your User ID and/or Password are invalid."))
			{
				System.out.println("Your User ID and/or Password are invalid. Check again.");
				System.exit(1);
			}
		}
		else
		{
			System.out.println("Login is successful");
		}
		
	}
	public static void processCheck(WebDriver driver, WebDriverWait wait) throws InterruptedException
	{
		System.out.println("*****PSBV PIA validation step started*****");
		Random r=new Random();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='PT_NAVBAR']/img")));
		//Click on 'NavBar' image
		driver.findElement(By.xpath("//*[@id='PT_NAVBAR']/img")).click();
		System.out.println("Nav Bar Icon clicked");
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(driver.findElement(By.id("psNavBarIFrame"))));
		//Click on 'Navigator'
		//driver.findElement(By.xpath("//img[@id='PTNUI_MENU_ICN$2']")).click();
		driver.findElement(By.xpath("//span[text()='Navigator']")).click();
		System.out.println("Navigator Icon clicked");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='NavBar: Navigator']")));
		WebElement element =driver.findElement(By.linkText("PeopleTools"));
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element); 
		//Click on 'PeopleTools'
		element.sendKeys(Keys.ENTER);
		System.out.println("PeopleTools menu clicked");
		//Click on 'Process Scheduler'
		driver.findElement(By.linkText("Process Scheduler")).sendKeys(Keys.ENTER);
		System.out.println("Process Scheduler menu clicked");
		//Click on 'System Process Request'
		driver.findElement(By.linkText("System Process Requests")).sendKeys(Keys.ENTER);
		System.out.println("System Process Requests menu clicked");
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("ptifrmtgtframe")));
		System.out.println("Navigated to System Process Request page");
		//Click on 'Add a New Value' tab
		driver.findElement(By.id("ICTAB_1")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a/span[text()='Add a New Value']")));
		String runcontrol_Id="Test"+r.nextInt(50);
		driver.findElement(By.id("PRCSRUNCNTL_RUN_CNTL_ID")).clear();
		driver.findElement(By.id("PRCSRUNCNTL_RUN_CNTL_ID")).sendKeys(runcontrol_Id);
		System.out.println("Run Control Id: "+runcontrol_Id);
		//click on 'Add' button
		driver.findElement(By.id("#ICSearch")).click();
		//Click on 'Run' Button
		driver.findElement(By.id("PRCSRQSTDLG_WRK_LOADPRCSRQSTDLGPB")).click();
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("ptModFrame_0")));
		Thread.sleep(3000);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Saved']")));
		//driver.findElement(By.xpath("//tr[td[div[span[text()='DDDAUDIT']]]]/td/div/input[2]")).click();
		driver.findElement(By.xpath("(//tr[td[div[span[text()='XRFWIN']]]]/td/div/input[2])[1]")).click();
		driver.findElement(By.id("#ICSave")).click();
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("ptifrmtgtframe")));
		try{
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[text()='Saved']")));
		}
		catch(Exception e)
		{
			
		}
		String instance=driver.findElement(By.id("PRCSRQSTDLG_WRK_DESCR100")).getText();
		System.out.println("Instance:"+instance+"\n");
		String prc_Instance="";
		prc_Instance=instance.substring(instance.indexOf(":") + 1);
		//Clicking on process monitor link
		driver.findElement(By.xpath("//a[text()='Process Monitor']")).click(); 
		System.out.println("Process Monitor link clicked\n");
		//Sending process instance number to "Instance" field
		driver.findElement(By.xpath("//*[@id='PMN_DERIVED_PRCSINSTANCE']")).clear();
		driver.findElement(By.xpath("//*[@id='PMN_DERIVED_PRCSINSTANCE']")).sendKeys(prc_Instance);
		
		//Clicking "Refresh" button
		driver.findElement(By.xpath("//*[@id='REFRESH_BTN']")).click();
		Thread.sleep(2000);
		System.out.println("Instance: "+prc_Instance+" is searched\n");
		//Checking the Environment sync status continuously
		System.out.println("Process running..........\n");
		while(true)
		{
			Thread.sleep(20000);
			driver.findElement(By.xpath("//*[@id='REFRESH_BTN']")).click();
			Thread.sleep(5000);
			if(driver.findElement(By.xpath("//*[@id='PMN_PRCSLIST_RUNSTATUSDESCR$0']")).getText().equalsIgnoreCase("Success")==true)
			{
				System.out.println("Process Run Status is Success for the process: "+prc_Instance+"\n");
				if((driver.findElement(By.xpath("//*[@id='PMN_PRCSLIST_DISTSTATUS$0']")).getText().equalsIgnoreCase("Posted")))
					
				{
					System.out.println("Distribution Status is 'Posted'");
					System.out.println("'Report Node' created");
					System.out.println("*****PSBV PIA validation step ended*****");
					break;
				}
				else
				{
					continue;
				}
			}
			else if((driver.findElement(By.xpath("//*[@id='PMN_PRCSLIST_RUNSTATUSDESCR$0']")).getText().equalsIgnoreCase("No Success")==true))
			{
				System.out.println("Failed: Process is 'No Success' for process: "+prc_Instance+", so check & re-run\n");
				break;
			}
			
		  }
	}
	public static void windowsWebProfileSettings(WebDriver driver, WebDriverWait wait) throws InterruptedException
	{
		System.out.println("*****PSBV Windows Web Profile Settings process started*****");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='PT_NAVBAR']/img")));
		//Click on 'NavBar' image
		driver.findElement(By.xpath("//*[@id='PT_NAVBAR']/img")).click();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(driver.findElement(By.id("psNavBarIFrame"))));
		System.out.println("NavBar image clicked");
		//Click on 'Navigator'
		//driver.findElement(By.xpath("//img[@id='PTNUI_MENU_ICN$2']")).click();
		driver.findElement(By.xpath("//span[text()='Navigator']")).click();
		System.out.println("Navigator Icon clicked");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='NavBar: Navigator']")));
		System.out.println("Navigated to 'Navigator'");
		driver.findElement(By.linkText("PeopleTools")).sendKeys(Keys.ENTER);
		System.out.println("Navigated to 'PeoppleTools'");
		driver.findElement(By.linkText("Web Profile")).sendKeys(Keys.ENTER);
		System.out.println("Navigated to 'Web Profie'");
		driver.findElement(By.linkText("Web Profile Configuration")).sendKeys(Keys.ENTER);
		System.out.println("Navigated to 'Web Profile Configuration'");
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(driver.findElement(By.id("ptifrmtgtframe"))));
		driver.findElement(By.id("PSWEBPROF_SRCH_WEBPROFILENAME")).sendKeys("PROD");
		driver.findElement(By.id("#ICSearch")).click();
		System.out.println("Profile Name searched as 'PROD'");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("app_label")));
		//Navigate to Debugging tab
		driver.findElement(By.xpath("//span[text()='Debugging']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[label[text()='Show Connection & Sys Info']]/input[@id='PSWEBPROFWRK_CONNECTINFO']")));
		System.out.println("Navigated to Debugging tab");
		//'Show Connection & Sys Info' checkbox processing
		if (driver.findElement(By.xpath("//div[label[text()='Show Connection & Sys Info']]/input[@id='PSWEBPROFWRK_CONNECTINFO']")).isSelected()!=true)
		{
			driver.findElement(By.xpath("//div[label[text()='Show Connection & Sys Info']]/input[@id='PSWEBPROFWRK_CONNECTINFO']")).click();
			System.out.println(driver.findElement(By.id("PSWEBPROFWRK_CONNECTINFO_LBL")).getText()+" checkbox was not selected, so selected");
		}
		else
		{
			System.out.println(driver.findElement(By.id("PSWEBPROFWRK_CONNECTINFO_LBL")).getText()+" checkbox is already selected");
		}
		//'Show Trace Link at Signon' checkbox processing
		if (driver.findElement(By.xpath("//div[label[text()='Show Trace Link at Signon']]/input[@id='PSWEBPROFWRK_ENABLETRACE']")).isSelected()!=true)
		{
			driver.findElement(By.xpath("//div[label[text()='Show Trace Link at Signon']]/input[@id='PSWEBPROFWRK_ENABLETRACE']")).click();
			System.out.println(driver.findElement(By.id("PSWEBPROFWRK_ENABLETRACE_LBL")).getText()+" checkbox was not selected, so selected");
		}
		else
		{
			System.out.println(driver.findElement(By.id("PSWEBPROFWRK_ENABLETRACE_LBL")).getText()+" checkbox is already selected");
		}
		//'Generate HTML for Testing' checkbox processing
		if (driver.findElement(By.xpath("//div[label[text()='Generate HTML for Testing']]/input[@id='PSWEBPROFWRK_TESTING']")).isSelected()!=true)
		{
			driver.findElement(By.xpath("//div[label[text()='Generate HTML for Testing']]/input[@id='PSWEBPROFWRK_TESTING']")).click();
			System.out.println(driver.findElement(By.id("PSWEBPROFWRK_TESTING_LBL")).getText()+" checkbox was not selected, so selected");
		}
		else
		{
			System.out.println(driver.findElement(By.id("PSWEBPROFWRK_TESTING_LBL")).getText()+" checkbox is already selected");
		}
		//'Write Dump File' checkbox processing
		if (driver.findElement(By.xpath("//div[label[text()='Write Dump File']]/input[@id='PSWEBPROFWRK_ENABLEDEBUGDUMPFL']")).isSelected()!=true)
		{
			driver.findElement(By.xpath("//div[label[text()='Write Dump File']]/input[@id='PSWEBPROFWRK_ENABLEDEBUGDUMPFL']")).click();
			System.out.println(driver.findElement(By.id("PSWEBPROFWRK_ENABLEDEBUGDUMPFL_LBL")).getText()+" checkbox was not selected, so selected");
		}
		else
		{
			System.out.println(driver.findElement(By.id("PSWEBPROFWRK_ENABLEDEBUGDUMPFL_LBL")).getText()+" checkbox is already selected");
		}
		
		//Clicking 'Save' button
		driver.findElement(By.id("#ICSave")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Saved']")));
		System.out.println("*****PSBV Windows Web Profile Settings process ended*****");
	}
	
	public static void MLMMC_Loading(WebDriver driver, WebDriverWait wait,WebDriverWait waitLess) throws InterruptedException
	{
		
		System.out.println("*****MLMMC loading process started*****");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='PT_NAVBAR']/img")));
		//Click on 'NavBar' image
		driver.findElement(By.xpath("//*[@id='PT_NAVBAR']/img")).click();
		System.out.println("Nav icon clicked");
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(driver.findElement(By.id("psNavBarIFrame"))));
		//Click on 'Navigator'
		//driver.findElement(By.xpath("//img[@id='PTNUI_MENU_ICN$2']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='NavBar']")));
		driver.findElement(By.xpath("//span[text()='Navigator']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='NavBar: Navigator']")));
		System.out.println("Navigator icon clicked");
		//WebElement element =driver.findElement(By.linkText("PeopleTools")).sendKeys(Keys.ENTER);
		//element.sendKeys(Keys.ENTER);
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element); 
		//Click on 'PeopleTools'
		driver.findElement(By.linkText("PeopleTools")).sendKeys(Keys.ENTER);
		System.out.println("PeopleTools clicked");
		//Click on 'Lifecycle Tools'
		driver.findElement(By.linkText("Lifecycle Tools")).sendKeys(Keys.ENTER);
		System.out.println("Lifecycle Tools clicked");
		//Click on 'Update Manager'
		driver.findElement(By.linkText("Update Manager")).sendKeys(Keys.ENTER);
		System.out.println("Update Manager clicked");
		//Click on 'Update Manager Dashboard'
		driver.findElement(By.linkText("Update Manager Dashboard")).sendKeys(Keys.ENTER);
		System.out.println("Update Manager Dashboard clicked");
		driver.switchTo().defaultContent();
		//Expand 'Define Change Package'
		driver.findElement(By.id("$ICField23")).click();
		System.out.println("Define Change Package expanded");
		//Click on 'Define Change Package' 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a/span[text()='Define Change Package']")));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a/span[text()='Define Change Package']")).click();
		System.out.println("Define Change Package clicked");
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("main_target_win0")));
		//Select Create radio button
		try
		{
			driver.findElement(By.xpath("//div[label[text()='Create']]/input[@id='PTIASP_CPWF_WRK_PTIASP_CRUD_MODE']")).click();
			Thread.sleep(2000);
		}
		//If there is no target DB then exit
		catch(Exception e)
		{
			System.out.println("There is no target database, please check & re-rurn");
			System.out.println("Failed");
			System.exit(1);
		}
		//Enter "Package Name" field value
		String package_Name="MLMMC";
		int i=0;
		while(true)
		{
			
			try{
				driver.findElement(By.id("DERIVED_PTIASP_PTIASPPKGID")).clear();
				driver.findElement(By.id("DERIVED_PTIASP_PTIASPPKGID")).sendKeys(package_Name);
				Thread.sleep(2000);
				driver.findElement(By.xpath("//span[text()='No matching values were found.']"));
				System.out.println("New Package Created: "+package_Name);
				break;
			}
			catch(Exception e)
			{
				System.out.println("Already Processed Packages: "+package_Name);
				i++;
				package_Name="MLMMC"+i;
				
			}
		}
		//Next button clicked in Step 2 of 6
		driver.findElement(By.id("PTPPB_NEXT")).click();
		//Code to check MLMMC loading is completed
		List <WebElement> l=driver.findElements(By.xpath("//span[text()='Step 3 of 6']"));
		if(l.size()>0)
		{
			System.out.println("MLMMC loading is already ompleted for all the langugaes OR enable other laguages, please check");
			System.exit(1);
		}
		try
		{
			driver.switchTo().defaultContent();
			waitLess.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("ptModFrame_0")));
		}
		catch(Exception e)
		{
			driver.switchTo().defaultContent();
			waitLess.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("main_target_win0")));
		}
		
		//driver.findElement(By.id("DERIVED_PTIASP_OK_BTN")).click();
		//For Access ID
		driver.findElement(By.id("OPRID")).sendKeys("SYSADM");
		//For Access Password
		driver.findElement(By.id("OPRPSWD")).sendKeys("SYSADM");
		//For Confirm Password
		driver.findElement(By.id("OPRPSWDCONF")).sendKeys("SYSADM");
		//For OK button
		driver.findElement(By.id("DERIVED_PTIASP_OK_BTN")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("HTMLCTLEVENT")));
		String percent=null;
		while(true)
		{
			Thread.sleep(25000);
			driver.findElement(By.id("HTMLCTLEVENT")).click();
			percent=driver.findElement(By.id("percent")).getText();
			if(percent.equalsIgnoreCase("100%"))
			{
				System.out.println("Install Languages Process is completed "+percent);
				while(true)
				{
					if(driver.findElement(By.id("DERIVED_PTIASP_OK_BTN")).isEnabled())
					{
						driver.findElement(By.id("DERIVED_PTIASP_OK_BTN")).click();
						break;
					} 
					else
					{
						driver.findElement(By.id("HTMLCTLEVENT")).click();
						Thread.sleep(2000);
						continue;
						//System.out.println("Progress is 100% but the 'OK' button is not enabled.");
						//System.out.println("Failed: MLMMC process failed");
						//break;
					}
				}
				break;
			}
			else
			{
				System.out.println("Progress persentage is :"+percent);
			}
		}
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("main_target_win0")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Step 3 of 6']")));
		//Select All Updates Not Applied radio button
		driver.findElement(By.xpath("//div[label[text()='All Updates Not Applied']]/input[@id='PTIASPSEARCHSCOPE1']")).click();
		//wait.until(ExpectedConditions.elementSelectionStateToBe(By.xpath("//div[label[text()='All Updates Not Applied']]/input[@id='PTIASPSEARCHSCOPE1']"), true));
		Thread.sleep(3000);
		driver.findElement(By.id("PTPPB_NEXT")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Step 5 of 6']")));
		driver.findElement(By.id("PTPPB_NEXT")).click();
		//To handle Warning Pop-up which come sometimes
		try
		{
			driver.switchTo().defaultContent();
			waitLess.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("ptModFrame_1")));
			System.out.println("The alert pop-up contains text as: ");
			System.out.println(driver.findElement(By.id("DERIVED_PTIASP_PTIASPLANGDSPY1")).getText());
			System.out.println(driver.findElement(By.id("DERIVED_PTIASP_PTIASPLANGDSPY2")).getText());
			driver.findElement(By.id("DERIVED_PTIASP_OK_BTN")).click();
		}
		/* catch(NoSuchFrameException fe)
		{
			driver.switchTo().defaultContent();
			waitLess.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("main_target_win0")));
			System.out.println("The warning contains text as: ");
			System.out.println(driver.findElement(By.id("DERIVED_PTIASP_PTIASPLANGDSPY1")).getText());
			System.out.println(driver.findElement(By.id("DERIVED_PTIASP_PTIASPLANGDSPY2")).getText());
			driver.findElement(By.id("DERIVED_PTIASP_OK_BTN")).click();
		} */
		catch(Exception e)
		{
			System.out.println("There is no alert pop-up message so proceeding.....");
		} 
		//To handle Warning message which come sometimes
		try
		{
			driver.switchTo().defaultContent();
			waitLess.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("main_target_win0")));
			System.out.println("The alert page contains text as: ");
			System.out.println(driver.findElement(By.id("DERIVED_PTIASP_PTIASPLANGDSPY1")).getText());
			System.out.println(driver.findElement(By.id("DERIVED_PTIASP_PTIASPLANGDSPY2")).getText());
			driver.findElement(By.id("DERIVED_PTIASP_OK_BTN")).click();
		}
		catch(Exception e)
		{
			System.out.println("There is no alert message so proceeding.....");
		}  
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("main_target_win0")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Step 6 of 6']")));
		
		if(driver.findElement(By.xpath("//*[@id='win0div$ICField44']/span")).getText().contains("Successful"))
		{
			System.out.println(driver.findElement(By.xpath("//*[@id='win0div$ICField44']/span")).getText());
			System.out.println(driver.findElement(By.xpath("//span[@id='DERIVED_PTIASP_TEXT254']")).getText());
			System.out.println("*****MLMMC loading process completed*****");
			System.out.println("Passed");
		}
		else
		{
			System.out.println("*****MLMMC loading process is not completed*****");
			System.out.println("Falied");
		}
	}

	//ENGMMC process
	public static void ENGMMC_Loading(WebDriver driver, WebDriverWait wait,WebDriverWait waitLess) throws InterruptedException
	{
		
		System.out.println("*****ENGMMC loading process started*****");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='PT_NAVBAR']/img")));
		//Click on 'NavBar' image
		driver.findElement(By.xpath("//*[@id='PT_NAVBAR']/img")).click();
		System.out.println("Nav icon clicked");
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(driver.findElement(By.id("psNavBarIFrame"))));
		//Click on 'Navigator'
		//driver.findElement(By.xpath("//img[@id='PTNUI_MENU_ICN$2']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='NavBar']")));
		driver.findElement(By.xpath("//span[text()='Navigator']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='NavBar: Navigator']")));
		System.out.println("Navigator icon clicked");
		//WebElement element =driver.findElement(By.linkText("PeopleTools")).sendKeys(Keys.ENTER);
		//element.sendKeys(Keys.ENTER);
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element); 
		//Click on 'PeopleTools'
		driver.findElement(By.linkText("PeopleTools")).sendKeys(Keys.ENTER);
		System.out.println("PeopleTools clicked");
		//Click on 'Lifecycle Tools'
		driver.findElement(By.linkText("Lifecycle Tools")).sendKeys(Keys.ENTER);
		System.out.println("Lifecycle Tools clicked");
		//Click on 'Update Manager'
		driver.findElement(By.linkText("Update Manager")).sendKeys(Keys.ENTER);
		System.out.println("Update Manager clicked");
		//Click on 'Update Manager Dashboard'
		driver.findElement(By.linkText("Update Manager Dashboard")).sendKeys(Keys.ENTER);
		System.out.println("Update Manager Dashboard clicked");
		driver.switchTo().defaultContent();
		//Expand 'Define Change Package'
		driver.findElement(By.id("$ICField23")).click();
		System.out.println("Define Change Package expanded");
		//Click on 'Define Change Package' 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a/span[text()='Define Change Package']")));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a/span[text()='Define Change Package']")).click();
		System.out.println("Define Change Package clicked");
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("main_target_win0")));
		//Select Create radio button
		try
		{
			driver.findElement(By.xpath("//div[label[text()='Create']]/input[@id='PTIASP_CPWF_WRK_PTIASP_CRUD_MODE']")).click();
			Thread.sleep(2000);
		}
		//If there is no target DB then exit
		catch(Exception e)
		{
			System.out.println("There is no target database, please check & re-rurn");
			System.out.println("Failed");
			System.exit(1);
		}
		//Enter "Package Name" field value
		String package_Name="ENGMMC";
		int i=0;
		while(true)
		{
			
			try{
				driver.findElement(By.id("DERIVED_PTIASP_PTIASPPKGID")).clear();
				driver.findElement(By.id("DERIVED_PTIASP_PTIASPPKGID")).sendKeys(package_Name);
				Thread.sleep(2000);
				driver.findElement(By.xpath("//span[text()='No matching values were found.']"));
				System.out.println("New Package Created: "+package_Name);
				break;
			}
			catch(Exception e)
			{
				System.out.println("Already Processed Packages: "+package_Name);
				i++;
				package_Name="ENGMMC"+i;
				
			}
		}
		//Next button clicked in Step 2 of 6
		driver.findElement(By.id("PTPPB_NEXT")).click();
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("main_target_win0")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Step 3 of 6']")));
		//Code to check MLMMC loading is completed
		if(driver.findElement(By.id("PTPPB_NEXT")).isEnabled()!=true)
		{
			System.out.println("ENGMMC loading is already completed, please check");
			System.exit(1);
		}
				
		
		//Select All Updates Not Applied radio button
		driver.findElement(By.xpath("//div[label[text()='All Updates Not Applied']]/input[@id='PTIASPSEARCHSCOPE1']")).click();
		//wait.until(ExpectedConditions.elementSelectionStateToBe(By.xpath("//div[label[text()='All Updates Not Applied']]/input[@id='PTIASPSEARCHSCOPE1']"), true));
		Thread.sleep(3000);
		driver.findElement(By.id("PTPPB_NEXT")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Step 5 of 6']")));
		driver.findElement(By.id("PTPPB_NEXT")).click();
		//To handle Warning Pop-up which come sometimes
		try
		{
			driver.switchTo().defaultContent();
			waitLess.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("ptModFrame_1")));
			System.out.println("The alert pop-up contains text as: ");
			System.out.println(driver.findElement(By.id("DERIVED_PTIASP_PTIASPLANGDSPY1")).getText());
			System.out.println(driver.findElement(By.id("DERIVED_PTIASP_PTIASPLANGDSPY2")).getText());
			driver.findElement(By.id("DERIVED_PTIASP_OK_BTN")).click();
		}
		
		catch(Exception e)
		{
			System.out.println("There is no alert pop-up so proceeding.....");
		} 
		//To handle Warning message which come sometimes
		try
		{
			driver.switchTo().defaultContent();
			waitLess.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("main_target_win0")));
			System.out.println("The alert page contains text as: ");
			System.out.println(driver.findElement(By.id("DERIVED_PTIASP_PTIASPLANGDSPY1")).getText());
			System.out.println(driver.findElement(By.id("DERIVED_PTIASP_PTIASPLANGDSPY2")).getText());
			driver.findElement(By.id("DERIVED_PTIASP_OK_BTN")).click();
		}
		catch(Exception e)
		{
			System.out.println("There is no alert message so proceeding.....");
		}  
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("main_target_win0")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Step 6 of 6']")));
		
		if(driver.findElement(By.xpath("//*[@id='win0div$ICField44']/span")).getText().contains("Successful"))
		{
			System.out.println(driver.findElement(By.xpath("//*[@id='win0div$ICField44']/span")).getText());
			System.out.println(driver.findElement(By.xpath("//span[@id='DERIVED_PTIASP_TEXT254']")).getText());
			System.out.println("*****MLMMC loading process completed*****");
			System.out.println("Passed");
		}
		else
		{
			System.out.println("*****ENGMMC loading process is not completed*****");
			System.out.println("Falied");
		}
	}

	
	public static void logout(WebDriver driver,WebDriverWait wait) throws InterruptedException 
	{
		try
		{
			driver.switchTo().defaultContent();
			driver.findElement(By.id("pthdr2ActionList")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Sign Out']")));
			driver.findElement(By.xpath("//span[text()='Sign Out']")).click();
			System.out.println("Logout Successful");
			driver.close();
			
		}
		catch(Exception e)
		{
			driver.switchTo().defaultContent();
			driver.findElement(By.xpath("//*[@id='PT_ACTION_MENU$PIMG']/img")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("PT_LOGOUT_MENU")));
			driver.findElement(By.id("PT_LOGOUT_MENU")).click();
			System.out.println("Logout Successful");
			driver.close();
		}
	}

}
