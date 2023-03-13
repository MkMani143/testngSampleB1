package parallelScripts;

import org.testng.annotations.Test;

public class SampleTwoTest {
	@Test
	  public void TestOne() {
		long id=Thread.currentThread().getId();
		  System.out.println("TestOne from sampleTwo "+id);
	  }
	  @Test
	  public void TestTwo() {
		  long id=Thread.currentThread().getId();
		  System.out.println("TestTwo from sampleTwo "+ id);
	  }
	  @Test
	  public void TestThree() {
		  long id=Thread.currentThread().getId();
		  System.out.println("TestThree from sampleTwo "+ id);
	  }
	  @Test
	  public void TestFour() {
		  long id=Thread.currentThread().getId();
		  System.out.println("Testfour from sampleTwo "+id);
	  }
}
