package com.pactera.PacteraHomePageTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PacteraTest { // ***************** This Class contains methods to drive us to pactera home page and search results page **************

	private String keywordToSearch;// *** this variable is read from properties file to search for particular word in pactera search page ***
	private String link;           // *** this variable is read from properties file which holds url to navigate to pactera home page ***
	private String search;		   // *** this variable holds the object property of search text field which is read from properties file ***	
	private String searchbutton;   // *** this variable holds the object property of search button filed which is read from properties fil ***

	public WebDriver driver = null; // *** this holds the web driver object ****
	private final static Logger pacteraLogger = Logger
			.getLogger("devpinoyLogger"); // *** devpinoy logger used to generate log files for the actions performed in this class ***

	private Properties objectRepository = null; // *** this holds object repository properties which loads dynamically ***

	@BeforeTest
	public void gotohomepageTest() throws IOException { // *** this method initialize  the driver and navigates to pactera home page and generates logs and screen shot ***
		PropertyConfigurator.configure("log4j.properties");
		pacteraLogger.info("Initialising the driver");

		FileInputStream ip = new FileInputStream(
				System.getProperty("user.dir")
						+ "\\src\\test\\java\\com\\pactera\\PacteraHomePageTest\\object.properties");
		objectRepository = new Properties();
		objectRepository.load(ip);
		link = objectRepository.getProperty("url");
		keywordToSearch = objectRepository.getProperty("searchWord");
		driver = new FirefoxDriver();

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		pacteraLogger.info("successfully initialised the firefox driver");
		driver.navigate().to(link);
		driver.manage().window().maximize();
		try {
			Assert.assertTrue(driver.getTitle().contains("Pactera"));
			pacteraLogger.info("Navigated to  pactera site URL :  " + link);
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(
					".\\ScreenShots\\Pacterahomepage.png"));

		} catch (Throwable t) {
			pacteraLogger.info("Not able to navigate to  pactera site URL :  "
					+ link);
		}

	}

	@Test
	public void PacteraSearch() { // *** this method used to search the keyword passed from properties file and search for that keyword in pactera site generates log and screen shots **

		
		search = objectRepository.getProperty("search");
		searchbutton = objectRepository.getProperty("searchbutton");
		try {
			driver.findElement(By.xpath(search)).sendKeys(keywordToSearch);
			pacteraLogger.info("entered search key word as ---->"
					+ keywordToSearch);
			File scrFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(
					".\\ScreenShots\\searchedpage.png"));

			pacteraLogger.info("entereted the word : test automation");
			driver.findElement(By.xpath(searchbutton)).click();
			pacteraLogger.info("clicked search button link");

			try {
				Assert.assertTrue(driver.getTitle().contains(keywordToSearch));

				pacteraLogger.info("Verified test automation page");
				File scrFile2 = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile2, new File(
						".\\ScreenShots\\Resultpage.png"));

			} catch (Throwable t) {
				pacteraLogger
						.error("Verification failed for search page not loaded ");
			}

			// String s=driver.getTitle();
		} catch (Exception e) {
			pacteraLogger.info("failed due to " + e.getMessage());
		}
		// System.out.println(s);
	}

	@AfterTest
	public void CloseBrowserTest() { // *** this method closes the driver object ***
		driver.quit();
		pacteraLogger.info("Closed the browser as test completed");

	}

}
