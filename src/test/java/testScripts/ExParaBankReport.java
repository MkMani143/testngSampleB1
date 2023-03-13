package testScripts;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
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

public class ExParaBankReport {
	WebDriver driver;
	String newAccNo;
	
	ExtentReports reports;
	ExtentSparkReporter spark;
	ExtentTest extentTest;
	@BeforeClass
	public void ExtentSetup() {
		reports=new ExtentReports();
		spark=new ExtentSparkReporter("target\\ParaBankReport.html");
		reports.attachReporter(spark);
	}
  @BeforeTest
  public void setup() {
	  WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://parabank.parasoft.com/parabank/index.htm");
  }
  @Test(priority=1)
  public void Register() throws InterruptedException {
	  	extentTest=reports.createTest("Register Page Test");
	  	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	  	driver.findElement(By.partialLinkText("Register")).click();
	  	
		driver.findElement(By.id("customer.firstName")).sendKeys("mani");
		driver.findElement(By.id("customer.lastName")).sendKeys("R");
		driver.findElement(By.id("customer.address.street")).sendKeys("kalaignar nagar 6th street,karungalpatti");
		driver.findElement(By.id("customer.address.city")).sendKeys("salem");
		driver.findElement(By.id("customer.address.state")).sendKeys("Tamilnadu");
		driver.findElement(By.id("customer.address.zipCode")).sendKeys("636006");
		driver.findElement(By.id("customer.phoneNumber")).sendKeys("9876543210");
		driver.findElement(By.id("customer.ssn")).sendKeys("0099");
		driver.findElement(By.id("customer.username")).sendKeys("Mani");
		driver.findElement(By.id("customer.password")).sendKeys("12345");
		driver.findElement(By.xpath("//td/input[@id='repeatedPassword']")).sendKeys("12345");

		driver.findElement(By.xpath("//td/input[@class='button']")).click();
		Thread.sleep(1000);
  }
//@Test
//public void customerlogin() {
//  	extentTest=reports.createTest("Customer Login Test");
//	  driver.findElement(By.name("username")).sendKeys("MANI");
//	  driver.findElement(By.name("password")).sendKeys("12345");
//	  driver.findElement(By.xpath("//input[@class='button']")).click();
//}
  @Test(priority=2)
  public void openAcc() throws InterruptedException {
	  extentTest=reports.createTest("Open Account Test");
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	  driver.findElement(By.xpath("//a[text()='Open New Account']")).click();
	  Select acc=new Select(driver.findElement(By.cssSelector("select#type")));
	  Thread.sleep(2000);
	  acc.selectByVisibleText("SAVINGS");
	  driver.findElement(By.xpath("//input[@class='button']")).submit();
	  
	  String message=driver.findElement(By.xpath("//h1[text()='Account Opened!']")).getText();
	  String confirmMsg=driver.findElement(By.xpath("//b[text()='Your new account number:']")).getText();
	  newAccNo=driver.findElement(By.cssSelector("a#newAccountId")).getText();
	  System.out.println(message+"\n"+confirmMsg+newAccNo);
  }
  @Test(priority=3)
  public void AccOverview() {
	  extentTest=reports.createTest("Account Overview Test");
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	  driver.findElement(By.xpath("//a[text()='Accounts Overview']")).click();
	  
	  List<WebElement> list= driver.findElements(By.xpath("//td/a[@class='ng-binding']"));
  	  Assert.assertEquals(list.get(list.size()-1).getText(), newAccNo);
  }
  @Test(dependsOnMethods = "openAcc")
  public void TransferFund() throws InterruptedException {
	  extentTest=reports.createTest("Tranfer Fund Test");
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	  driver.findElement(By.xpath("//a[text()='Transfer Funds']")).click();
	  Thread.sleep(2000);
	  driver.findElement(By.cssSelector("input#amount")).sendKeys("2000");
	  Select fromAcc=new Select(driver.findElement(By.id("fromAccountId")));
	  fromAcc.selectByIndex(0);
	  Select ToAcc=new Select(driver.findElement(By.id("toAccountId")));
	  ToAcc.selectByValue(newAccNo);
	  driver.findElement(By.cssSelector("input.button")).click();
  }
  @AfterMethod
  public void close(ITestResult result) throws InterruptedException {
	  if(ITestResult.FAILURE==result.getStatus()) {
		  extentTest.log(Status.FAIL, result.getThrowable().getMessage());
	  }
//	  driver.close();
  }
  @AfterTest
  public void close() throws InterruptedException {
	  Thread.sleep(2000);
	  driver.close();
	  reports.flush();
  }
//  @AfterClass
//	 public void finishExtent() {
//		 reports.flush();
//	 }
  
}
