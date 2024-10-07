package testBase1;



import org.testng.annotations.AfterClass;

import base.BasePage;













public class BaseTest extends BasePage{

	@AfterClass 
	public static void quit(){
		
		driver.quit();
		
	}
}

	
	
		

		

		

		
	             


