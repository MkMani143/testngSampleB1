package testScripts;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExerciseParaBank {
	WebDriver driver;
	String newAccNo;
  @BeforeClass
  public void setup() {
	  WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://parabank.parasoft.com/");
  }
  @Test(priority=1)
  public void Register() throws InterruptedException {
	  
	  	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  	driver.findElement(By.partialLinkText("Register")).click();
	  	
		driver.findElement(By.id("customer.firstName")).sendKeys("mani");
		driver.findElement(By.id("customer.lastName")).sendKeys("R");
		driver.findElement(By.id("customer.address.street")).sendKeys("kalaignar nagar 6th street,karungalpatti");
		driver.findElement(By.id("customer.address.city")).sendKeys("salem");
		driver.findElement(By.id("customer.address.state")).sendKeys("Tamilnadu");
		driver.findElement(By.id("customer.address.zipCode")).sendKeys("636006");
		driver.findElement(By.id("customer.phoneNumber")).sendKeys("9876543210");
		driver.findElement(By.id("customer.ssn")).sendKeys("0099");
		driver.findElement(By.id("customer.username")).sendKeys("susee");
		driver.findElement(By.id("customer.password")).sendKeys("12345");
		driver.findElement(By.xpath("//td/input[@id='repeatedPassword']")).sendKeys("12345");

		driver.findElement(By.xpath("//td/input[@class='button']")).click();
		Thread.sleep(3000);
  }
//  @Test
//  public void customerlogin() {
//	  driver.findElement(By.name("username")).sendKeys("MANI");
//	  driver.findElement(By.name("password")).sendKeys("12345");
//	  driver.findElement(By.xpath("//input[@class='button']")).click();
//  }
  
  @Test(priority=2)
  public void openAcc() throws InterruptedException {
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
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
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  driver.findElement(By.xpath("//a[text()='Accounts Overview']")).click();
	  
	  List<WebElement> list= driver.findElements(By.xpath("//td/a[@class='ng-binding']"));
  	  Assert.assertEquals(list.get(list.size()-1).getText(), newAccNo);
  }
  @Test(dependsOnMethods = "openAcc")
  public void TransferFund() throws InterruptedException {
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  driver.findElement(By.xpath("//a[text()='Transfer Funds']")).click();
	  Thread.sleep(2000);
	  driver.findElement(By.cssSelector("input#amount")).sendKeys("2000");
	  Select fromAcc=new Select(driver.findElement(By.id("fromAccountId")));
	  fromAcc.selectByIndex(1);
	  Select ToAcc=new Select(driver.findElement(By.id("toAccountId")));
	  ToAcc.selectByValue(newAccNo);
	  driver.findElement(By.cssSelector("input.button")).click();
	  
  }
  @AfterClass
  public void close() throws InterruptedException {
	  Thread.sleep(3000);
	  driver.close();
  }
}
