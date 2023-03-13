package testScripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;


public class SampleTwoTest {
	WebDriver driver;
	@Test(retryAnalyzer = RetrySampleTest.class)
	  public void cypressSearchTest() {
//		  	System.setProperty("webdriver.chrome.driver", "D:\\WebDrivers\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			ChromeOptions options= new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
//			options.addArguments("-disable notifications");
			DesiredCapabilities cp= new DesiredCapabilities();
			cp.setCapability(ChromeOptions.CAPABILITY, options);
			options.merge(cp);
			driver=new ChromeDriver(options);
			
//			WebDriverManager.edgedriver().setup();
//			driver=new EdgeDriver();
			driver.manage().window().maximize();
			driver.get("https://www.google.com/");
//			System.getProperty("config.properties");
			WebElement SearchBox= driver.findElement(By.name("q"));
//			SoftAssert softassert=new SoftAssert();
//			softassert.assertEquals(driver.getTitle(), "Google page1");
			SearchBox.sendKeys("Cypress Tutorial");
			SearchBox.submit();
			Assert.assertEquals(driver.getTitle(), "Cypress Tutorial - Google Search");
//			softassert.assertAll();
			
	  }

}
