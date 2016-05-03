package config;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import com.thoughtworks.selenium.webdriven.commands.*;

import executionEngine.DriverScript;
import utility.Log;

public class ActionKeywords {
	
		public static WebDriver driver;
			
	public static void openBrowser(String object,String objectType,String data){		
		Log.info("Opening Browser");
		try{				
			if(data.equals("Mozilla")){
				driver=new FirefoxDriver();
				Log.info("Mozilla browser started");				
				}
			else if(data.equals("IE")){
				//Dummy Code, Implement you own code
				driver=new InternetExplorerDriver();
				Log.info("IE browser started");
				}
			else if(data.equals("Chrome")){
				//Dummy Code, Implement you own code
				driver=new ChromeDriver();
				Log.info("Chrome browser started");
				}
			
			int implicitWaitTime=(10);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		}catch (Exception e){
			Log.info("Not able to open the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void navigate(String object, String objectType,String data){
		try{
			Log.info("Navigating to URL");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.navigate().to(data);
		}catch(Exception e){
			Log.info("Not able to navigate --- " + e.getMessage());
			DriverScript.bResult = false;
			}
		}
	
	public static void click(String object,String objectType, String data){
		try{
			Log.info("Clicking on Webelement "+ object);
			By locator;
			locator = locatorValue(objectType, object);
			WebElement element = driver.findElement(locator);
			element.click();
		 }catch(Exception e){
 			Log.error("Not able to click --- " + e.getMessage());
 			DriverScript.bResult = false;
         	}
		}
	
	public static void input(String object,String objectType, String data){
		try{
			Log.info("Entering the text in " + object);
			By locator;
			locator = locatorValue(objectType, object);
			WebElement element = driver.findElement(locator);
			element.sendKeys(data);
		 }catch(Exception e){
			 Log.error("Not able to Enter UserName --- " + e.getMessage());
			 DriverScript.bResult = false;
		 	}
		}
	

	public static void waitFor(String object,String objectType, String data) throws Exception{
		try{
			Log.info("Wait for 5 seconds");
			Thread.sleep(5000);
		 }catch(Exception e){
			 Log.error("Not able to Wait --- " + e.getMessage());
			 DriverScript.bResult = false;
         	}
		}

	public static void closeBrowser(String object,String objectType, String data){
		try{
			Log.info("Closing the browser");
			driver.quit();
		 }catch(Exception e){
			 Log.error("Not able to Close the Browser --- " + e.getMessage());
			 DriverScript.bResult = false;
         	}
		}
	
	public static void verifyTextPresent(String object,String objectType, String data){
		try{
			Log.info("Verifying that text " + data + " exist in the page ");
		    if(driver.getPageSource().contains(data))
		    {
		    	Log.info("The page contains the text " + data);
		    }else
		    {
		    	Log.error("The page DO NOT contains the text " + data);
		    	DriverScript.bResult = false;
		    }
		   
		 }catch(Exception e){
			 Log.error("Not able to access the page --- " + e.getMessage());
			 DriverScript.bResult = false;
         	}
		}
	
//	public static void AssertTextPresent(String object,String objectType, String data){
//		try{
//			Log.info("Asserting that text " + data + " exist in the page ");
//		    Assert.assertTrue(driver.getPageSource().contains(data));
//		 }catch(Exception e){
//			 Log.error("Not able to access the page --- " + e.getMessage());
//			 DriverScript.bResult = false;
//         	}
//		}
	
	public static void verifyElementPresent(String object,String objectType, String data){
		try{
			Log.info("Verifying element");
			By locator;
			locator = locatorValue(objectType, object);
			WebElement element = driver.findElement(locator);
			if(element.isDisplayed())
		    {
		    	Log.info("The page contains the element " + object);
		    }else
		    {
		    	Log.error("The page DO NOT contains the element " + object);
		    	DriverScript.bResult = false;
		    }
			
		 }catch(Exception e){
			 Log.error("Not able to find the object --- " + e.getMessage());
			 DriverScript.bResult = false;
         	}
		}
	
	public static By locatorValue(String locatorTpye, String value) {
		By by;
		switch (locatorTpye) {
		case "id":
			by = By.id(value);
			break;
		case "name":
			by = By.name(value);
			break;
		case "xpath":
			by = By.xpath(value);
			break;
		case "css":
			by = By.cssSelector(value);
			break;
		case "linkText":
			by = By.linkText(value);
			break;
		case "partialLinkText":
			by = By.partialLinkText(value);
			break;
		default:
			by = null;
			break;
		}
		return by;
	}

	}