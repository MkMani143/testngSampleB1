package parallelScripts;

import org.testng.annotations.Test;

public class SampleThreeTest {
	@Test
	  public void TestOne() {
		  long id=Thread.currentThread().getId();
		  System.out.println("TestOne from sample3 "+ id);
	  }
	  @Test
	  public void TestTwo() {
		  long id=Thread.currentThread().getId();
		  System.out.println("TestTwo from sample3 "+ id);
	  }
	  @Test
	  public void TestThree() {
		  long id=Thread.currentThread().getId();
		  System.out.println("TestThree from sample3 "+ id);
	  }
	  @Test
	  public void TestFour() {
		  long id=Thread.currentThread().getId();
		  System.out.println("Testfour from sample3 "+ id);
	  }
}
