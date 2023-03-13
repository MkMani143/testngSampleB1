package testScripts;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExDanubeUsingCSV {
	WebDriver driver;
  @BeforeClass
  public void launch() throws IOException {
			
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://danube-webshop.herokuapp.com/");
  }

  @Test(dataProvider = "DanubeData")
  public void signup(String name, String surname, String email, String pass) throws InterruptedException {
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  driver.findElement(By.id("signup")).click();
	  driver.findElement(By.id("s-name")).sendKeys(name);
	  driver.findElement(By.id("s-surname")).sendKeys(surname);
	  driver.findElement(By.id("s-email")).sendKeys(email);
	  driver.findElement(By.id("s-password2")).sendKeys(pass);
	  driver.findElement(By.id("myself")).click();
	  driver.findElement(By.id("privacy-policy")).click();
	  driver.findElement(By.id("register-btn")).click();
  }
  
//Read from CSV
@DataProvider(name="DanubeData")
public Object[][] getData() throws CsvValidationException, IOException{
	  String path=System.getProperty("user.dir")+"//src//test//resources//testData//Danube.csv";
	  CSVReader reader=new CSVReader(new FileReader(path));
	  ArrayList<Object> datalist=new ArrayList<Object>();
	  String[] cols;
	  while((cols=reader.readNext())!=null) {
		  Object[] record= {cols[0],cols[1],cols[2],cols[3]};
		  datalist.add(record);
	  }
	  return datalist.toArray(new Object[datalist.size()][]);
}
}
