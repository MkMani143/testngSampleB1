package testScripts;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExDanubeUsingXML {
	WebDriver driver;
	@BeforeClass
	public void launch() {
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://danube-webshop.herokuapp.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}
	
//  Read from Xml file
  public String readXmlData(String tagname) throws ParserConfigurationException, SAXException, IOException {
	  String path=System.getProperty("user.dir")+"//src//test//resources//testData//ExDanubeUsingXml.xml";
	  File file=new File(path);
	  DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
	  DocumentBuilder build=factory.newDocumentBuilder();
	  Document document=build.parse(file);
	  NodeList list=document.getElementsByTagName("ObjRepo");
	  Node node1=list.item(0);
	  Element elem=(Element)node1;
//	  Element elem=(Element) list.item(0);
	  return elem.getElementsByTagName(tagname).item(0).getTextContent();
	//Document->NodeList->Nodes->Elements
  }  
	  
  @Test
  public void signup() throws InterruptedException, InvalidFormatException, IOException, ParserConfigurationException, SAXException {
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  driver.findElement(By.id(readXmlData("signupBtn"))).click();
	  driver.findElement(By.id(readXmlData("Name"))).sendKeys("Manikandan");
	  driver.findElement(By.id(readXmlData("surname"))).sendKeys("R");
	  driver.findElement(By.id(readXmlData("email"))).sendKeys("mani30212@gmail.com");
	  driver.findElement(By.id(readXmlData("password"))).sendKeys("12345");
	  driver.findElement(By.id(readXmlData("radioBtn"))).click();
	  driver.findElement(By.id(readXmlData("checkBox"))).click();
	  driver.findElement(By.id(readXmlData("regBtn"))).click();
  }
  @Test(dependsOnMethods = "signup")
  public void searchItem() throws InvalidFormatException, IOException, ParserConfigurationException, SAXException {
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  driver.findElement(By.name(readXmlData("searchItem"))).sendKeys("Parry Hotter");
	  driver.findElement(By.id(readXmlData("searchBtn"))).click();
	  driver.findElement(By.cssSelector(readXmlData("itemclick"))).click();
  }
  @Test(priority=2)
  public void AddtoCart() throws InvalidFormatException, IOException, ParserConfigurationException, SAXException {
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  if(driver.findElement(By.cssSelector(readXmlData("BookDetail"))).isDisplayed()) {
		  driver.findElement(By.xpath(readXmlData("AddtoCart"))).click();
	  }
	  driver.findElement(By.xpath(readXmlData("checkoutBtn"))).click();
  }
  @Test(dependsOnMethods = "AddtoCart")
  public void Checkout() throws InvalidFormatException, IOException, ParserConfigurationException, SAXException {
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	  driver.findElement(By.id(readXmlData("Name"))).sendKeys("Manikandan");
	  driver.findElement(By.id(readXmlData("surname"))).sendKeys("R");
	  driver.findElement(By.id(readXmlData("address"))).sendKeys("karungalpatti,6th street");
	  driver.findElement(By.id(readXmlData("zipcode"))).sendKeys("636006");
	  driver.findElement(By.id(readXmlData("city"))).sendKeys("salem");
	  driver.findElement(By.id("single")).click();
//	  driver.findElement(By.id("(//button[@class='call-to-action'])[2]")).click();
	  
  }
}
