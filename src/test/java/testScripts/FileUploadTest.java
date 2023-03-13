package testScripts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FileUploadTest {
  @Test
  public void fileUploadTest() throws InterruptedException {
	  WebDriverManager.chromedriver().setup();
	  WebDriver driver=new ChromeDriver();
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  driver.get("https://blueimp.github.io/jQuery-File-Upload/");
	  Thread.sleep(2000);
	  WebElement addFile= driver.findElement(By.xpath("//input[@type='file']"));
	  String filePath=System.getProperty("user.dir")+"//FullScreen.png";
	  addFile.sendKeys(filePath);
	  driver.findElement(By.xpath("//span[text()='Start upload']")).click();
	  
	  if(driver.findElement(By.xpath("//a[text()='FullScreen.png']")).isDisplayed()) {
		  Assert.assertTrue(true, "ImageUploaded");
	  }else {
		  Assert.assertTrue(false, "Image not Uploaded");
	  }
	  
	  
  }
}
