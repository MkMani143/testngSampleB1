package testScripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExDanubeReport {
	WebDriver driver;
	Properties prop;
	
	ExtentReports reports;
	ExtentSparkReporter spark;
	ExtentTest extentTest;
	@BeforeClass
	public void ExtentSetup() {
		reports=new ExtentReports();
		spark=new ExtentSparkReporter("target\\DanubeReport.html");
		reports.attachReporter(spark);
	}
	@BeforeTest
	  public void launch() throws IOException {
		  	//Getting from the property file
			String path=System.getProperty("user.dir")+"//src//test//resources//configFiles//config.properties";
			prop=new Properties();
			FileInputStream fileIn=new FileInputStream(path);
			prop.load(fileIn);
				
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			driver.get("https://danube-webshop.herokuapp.com/");
	  }
	@Test
	  public void signup() throws InterruptedException {
		extentTest=reports.createTest("SignUp Test");
		  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//		  driver.get("https://danube-webshop.herokuapp.com/");
		  driver.findElement(By.id("signup")).click();
		  driver.findElement(By.id("s-name")).sendKeys(prop.getProperty("Name"));
		  driver.findElement(By.id("s-surname")).sendKeys(prop.getProperty("Surname"));
		  driver.findElement(By.id("s-email")).sendKeys(prop.getProperty("Email"));
		  driver.findElement(By.id("s-password2")).sendKeys(prop.getProperty("Password"));
		  driver.findElement(By.id("myself")).click();
		  driver.findElement(By.id("privacy-policy")).click();
		  driver.findElement(By.id("register-btn")).click();
	  }
	@Test(dependsOnMethods = "signup")
	  public void searchItem() {
		extentTest=reports.createTest("SearchItem Test");
		  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//		  driver.get("https://danube-webshop.herokuapp.com/search?");
		  driver.findElement(By.name("searchbar")).sendKeys(prop.getProperty("ItemName"));
		  driver.findElement(By.id("button-search")).click();
		  driver.findElement(By.cssSelector("li.preview")).click();
	  }
	 @Test(priority=2)
	  public void AddtoCart() {
		 extentTest=reports.createTest("AddtoCart Test");
		  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//		  driver.get("https://danube-webshop.herokuapp.com/books/2");
		  if(driver.findElement(By.cssSelector("div.detail-text-content")).isDisplayed()) {
			  driver.findElement(By.xpath("(//button[@class='call-to-action'])[2]")).click();
		  }
		  driver.findElement(By.xpath("(//button[@class='call-to-action'])[2]")).click();
	  }
	 @Test(dependsOnMethods = "AddtoCart")
	  public void Checkout() {
		 extentTest=reports.createTest("CheckOut Test");
		  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//		  driver.get("https://danube-webshop.herokuapp.com/checkout");
		  driver.findElement(By.id("s-name")).sendKeys(prop.getProperty("Name"));
		  driver.findElement(By.id("s-surname")).sendKeys(prop.getProperty("Surname"));
		  driver.findElement(By.id("s-address")).sendKeys(prop.getProperty("Address"));
		  driver.findElement(By.id("s-zipcode")).sendKeys(prop.getProperty("zipcode"));
		  driver.findElement(By.id("s-city")).sendKeys(prop.getProperty("city"));
		  driver.findElement(By.id("single")).click();
//		  driver.findElement(By.id("(//button[@class='call-to-action'])[2]")).click();
	  }
	 @AfterMethod
	  public void close(ITestResult result) throws InterruptedException {
		  if(ITestResult.FAILURE==result.getStatus()) {
			  extentTest.log(Status.FAIL, result.getThrowable().getMessage());
		  }
//		  driver.close();
	  }
	 @AfterTest
	  public void close() throws InterruptedException {
		  Thread.sleep(2000);
		  driver.close();
		  reports.flush();
	  }
//	 @AfterClass
//	 public void finishExtent() {
//		 reports.flush();
//	 }
}
