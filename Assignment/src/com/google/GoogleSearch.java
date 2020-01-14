package com.google;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

public class GoogleSearch {
	WebDriver driver;
  @Test(dataProvider = "dp")
  public void doSearch(String sKeys) {
	  WebElement ele1 = driver.findElement(By.xpath("//*[@name='q']"));
	  ele1.sendKeys(sKeys);
	  ele1.submit();
	  
	  int pageNos = driver.findElements(By.cssSelector("[valign='top'] > td")).size();
	  System.out.println("No. of pages : "+pageNos);
	  
	  outer : for (int i=1; i < pageNos; i++) {
		  if (i > 1) {
			  driver.findElement(By.cssSelector("[aria-label='Page " + i + "']")).click();
			  System.out.println("\nPage no. " +i);
		  }
		  
		  List <WebElement> allSearchResult = driver.findElements(By.tagName("cite"));
		  System.out.println("Number of searches in page " +allSearchResult.size());
		  String sKeyword = "finzy";
		  
		  for (int j=0; j < allSearchResult.size(); j++) {
			  String str = allSearchResult.get(j).getText();
			  System.out.println(str);
			  
			  if (str.contains(sKeyword)) {
				System.out.println("Match found");
				System.out.println("Match found on page no. " +i);
				break outer;
			  }
		  }
	  }
	  
	  
  }
  @Parameters ("browser")
  @BeforeMethod
  public void setup(String browser) {
	  if(browser.equalsIgnoreCase("ie")) {
		  System.setProperty("webdriver.ie.driver", "C:\\Users\\home\\Desktop\\AB\\IEDriverServer_Win32_3.150.1\\IEDriverServer.exe");
	  }
	  
	  else if (browser.equalsIgnoreCase("chrome")) {
		  
	  }
	  
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  driver.manage().deleteAllCookies();
	  driver.manage().window().maximize();
	  String baseUrl = "https://google.com";
	  driver.get(baseUrl);
  }

  @AfterMethod
  public void afterMethod() {
	  driver.quit();
  }


  @DataProvider
  public static Object[] dp() {
    Object[] keys = new Object[3];
    keys[0] = "peer-to-peer lending";
    keys[1] = "finzy";
    keys[2] = "low risk investments";
    
    return keys;
    };
  
}

