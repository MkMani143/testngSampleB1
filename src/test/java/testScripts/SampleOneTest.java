package testScripts;


import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SampleOneTest {
	
	WebDriver driver;
	Properties prop;
	@Parameters("browser")
//	@BeforeMethod
	@BeforeClass
	public void setup(String strBrowser) throws IOException {
		//Getting from the property file
//		String path=System.getProperty("user.dir")+"//src//test//resources//configFiles//config.properties";
//		prop=new Properties();
//		FileInputStream fileIn=new FileInputStream(path);
//		prop.load(fileIn);
		
		if(strBrowser.equalsIgnoreCase("chrome")) {
		WebDriverManager.chromedriver().setup();
//		WebDriverManager.edgedriver().setup();
		driver=new ChromeDriver();
		}
		driver.manage().window().maximize();
		
	}
//	@Test
//  public void JavaSearchTest() {
//	  
//		driver.get("https://www.google.com/");
//		WebElement SearchBox= driver.findElement(By.name("q"));
//		SoftAssert softassert=new SoftAssert();
//		//Hard Assert
//		Assert.assertEquals(driver.getTitle(), "Google");
//		
//		//Soft Assert
//		softassert.assertEquals(driver.getTitle(), "Google page");
//		SearchBox.sendKeys("Java Tutorial");
//		SearchBox.submit();
//		Assert.assertEquals(driver.getTitle(), "Java Tutorial - Google Search");
//		softassert.assertAll();
		
//  }
  @Test(retryAnalyzer = RetrySampleTest.class)
  public void JavaSearchTest() {
	  
		driver.get(prop.getProperty("url"));
		WebElement SearchBox= driver.findElement(By.name("q"));
		SearchBox.sendKeys("Java Tutorial");
		SearchBox.submit();
		Assert.assertEquals(driver.getTitle(), "Java Tutorial - Google Search");
  }
//  @Test(priority=1)
//  @Test(alwaysRun = true, dependsOnMethods = "JavaSearchTest")
  @Test(dependsOnMethods = "JavaSearchTest")
  public void SeleniumSearchTest(){
	
		driver.get(prop.getProperty("url"));
		WebElement SearchBox= driver.findElement(By.name("q"));
		SearchBox.sendKeys("Selenium Tutorial");
		SearchBox.submit();
		Assert.assertEquals(driver.getTitle(), "Selenium Tutorial - Google Search page");
		
  }
//  @Test(enabled = false)
////  @Test(priority=1)
//  public void CucumberSearchTest(){
//	
//		driver.get("https://www.google.com/");
//		WebElement SearchBox= driver.findElement(By.name("q"));
//		SearchBox.sendKeys("Cucumber Tutorial");
//		SearchBox.submit();
//		Assert.assertEquals(driver.getTitle(), "Cucumber Tutorial - Google Search");
//		
//  }
  
  
  @AfterClass
//  @AfterMethod
  public void teardown() {
	  driver.close();
  }
  
}
