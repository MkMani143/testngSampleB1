package testScripts;

import java.io.FileInputStream;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExerciseDanube {
	WebDriver driver;
	Properties prop;
  @BeforeClass
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
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  driver.findElement(By.id("signup")).click();
	  driver.findElement(By.id("s-name")).sendKeys(prop.getProperty("Name"));
	  driver.findElement(By.id("s-surname")).sendKeys(prop.getProperty("Surname"));
	  driver.findElement(By.id("s-email")).sendKeys(prop.getProperty("Email"));
	  driver.findElement(By.id("s-password2")).sendKeys(prop.getProperty("Password"));
	  driver.findElement(By.id("myself")).click();
	  driver.findElement(By.id("privacy-policy")).click();
	  driver.findElement(By.id("register-btn")).click();
  }
  
//  @Test
//  public void login() {
//	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//	  driver.findElement(By.id("login")).click();
//	  driver.findElement(By.id("n-email")).sendKeys(prop.getProperty("Email"));
//	  driver.findElement(By.id("n-password2")).sendKeys(prop.getProperty("Password"));
//	  driver.findElement(By.id("goto-signin-btn")).click();
//  }
  
  @Test(dependsOnMethods = "signup")
  public void searchItem() {
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  driver.findElement(By.name("searchbar")).sendKeys(prop.getProperty("ItemName"));
	  driver.findElement(By.id("button-search")).click();
	  driver.findElement(By.cssSelector("li.preview")).click();
  }
  
  @Test(priority=2)
  public void AddtoCart() {
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  if(driver.findElement(By.cssSelector("div.detail-text-content")).isDisplayed()) {
		  driver.findElement(By.xpath("(//button[@class='call-to-action'])[2]")).click();
	  }
	  driver.findElement(By.xpath("(//button[@class='call-to-action'])[2]")).click();
  }
  
  @Test(dependsOnMethods = "AddtoCart")
  public void Checkout() {
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  driver.findElement(By.id("s-name")).sendKeys(prop.getProperty("Name"));
	  driver.findElement(By.id("s-surname")).sendKeys(prop.getProperty("Surname"));
	  driver.findElement(By.id("s-address")).sendKeys(prop.getProperty("Address"));
	  driver.findElement(By.id("s-zipcode")).sendKeys(prop.getProperty("zipcode"));
	  driver.findElement(By.id("s-city")).sendKeys(prop.getProperty("city"));
	  driver.findElement(By.id("single")).click();
//	  driver.findElement(By.id("(//button[@class='call-to-action'])[2]")).click();
  }
  @AfterClass
  public void close() {
	  driver.close();
  }
 
}
