package testScripts;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExDanubeUsingExcel {
	WebDriver driver;
  @BeforeClass
  public void launch() throws IOException {
	  	
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://danube-webshop.herokuapp.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
  }
  
//Read from Excel
public String readExcelData(String objName) throws InvalidFormatException, IOException {
	  String objPath="";
	  String path=System.getProperty("user.dir")+"//src//test//resources//testData//ExerciseDanubeData.xlsx"; 
//	 For .xlsx
	  XSSFWorkbook workbook=new XSSFWorkbook(new File(path));
	  XSSFSheet sheet=workbook.getSheet("Sheet1");
	  for(int i=1;i<=sheet.getLastRowNum();i++) {
		  XSSFRow row=sheet.getRow(i);
		  if(row.getCell(0).getStringCellValue().equalsIgnoreCase(objName)){
			  objPath=row.getCell(1).getStringCellValue();
		  }
	  }
	//	  For .xls -> HSSF*
	  return objPath;
}
  @Test
  public void signup() throws InterruptedException, InvalidFormatException, IOException {
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  driver.findElement(By.id(readExcelData("signupBtn"))).click();
	  driver.findElement(By.id(readExcelData("Name"))).sendKeys("Manikandan");
	  driver.findElement(By.id(readExcelData("surname"))).sendKeys("R");
	  driver.findElement(By.id(readExcelData("email"))).sendKeys("mani30212@gmail.com");
	  driver.findElement(By.id(readExcelData("password"))).sendKeys("12345");
	  driver.findElement(By.id(readExcelData("radioBtn"))).click();
	  driver.findElement(By.id(readExcelData("checkBox"))).click();
	  driver.findElement(By.id(readExcelData("regBtn"))).click();
  }
  @Test(dependsOnMethods = "signup")
  public void searchItem() throws InvalidFormatException, IOException {
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  driver.findElement(By.name(readExcelData("searchItem"))).sendKeys("Parry Hotter");
	  driver.findElement(By.id(readExcelData("searchBtn"))).click();
	  driver.findElement(By.cssSelector(readExcelData("itemclick"))).click();
  }
  @Test(priority=2)
  public void AddtoCart() throws InvalidFormatException, IOException {
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  if(driver.findElement(By.cssSelector(readExcelData("BookDetail"))).isDisplayed()) {
		  driver.findElement(By.xpath(readExcelData("AddtoCart"))).click();
	  }
	  driver.findElement(By.xpath(readExcelData("checkoutBtn"))).click();
  }
  @Test(dependsOnMethods = "AddtoCart")
  public void Checkout() throws InvalidFormatException, IOException {
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  driver.findElement(By.id(readExcelData("Name"))).sendKeys("Manikandan");
	  driver.findElement(By.id(readExcelData("surname"))).sendKeys("R");
	  driver.findElement(By.id(readExcelData("address"))).sendKeys("karungalpatti,6th street");
	  driver.findElement(By.id(readExcelData("zipcode"))).sendKeys("636006");
	  driver.findElement(By.id(readExcelData("city"))).sendKeys("salem");
	  driver.findElement(By.id("single")).click();
//	  driver.findElement(By.id("(//button[@class='call-to-action'])[2]")).click();
	  
  }
  
  



}
