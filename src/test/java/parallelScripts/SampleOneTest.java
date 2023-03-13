package parallelScripts;

import org.testng.annotations.Test;

public class SampleOneTest {
  @Test
  public void TestOne() {
	  long id=Thread.currentThread().getId();
	  System.out.println("TestOne from sampleOne "+id);
  }
  @Test
  public void TestTwo() {
	  long id=Thread.currentThread().getId();
	  System.out.println("TestTwo from sampleOne "+id);
  }
  @Test
  public void TestThree() {
	  long id=Thread.currentThread().getId();
	  System.out.println("TestThree from sampleOne "+id);
  }
  @Test(threadPoolSize = 3, invocationCount = 6, timeOut = 10000)
  public void TestFour() {
	  long id=Thread.currentThread().getId();
	  System.out.println("Testfour from sampleOne "+id);
  }
}
