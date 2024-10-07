package pages1;



import org.testng.Assert;

import base.BasePage;



public class OtpPage extends BasePage {

	    public void verifyOtp(String otp) {
	        type("otpField_id", otp);
	        click("verifyButton_xpath");

	        if (isTimelineDisplayed()) {
	            log.info("OTP Verified successfully. User logged in. TestCase Passed.");
	        } else {
	            log.info("Invalid OTP entered, timeline did not appear. TestCase Failed.");
	            Assert.fail("Invalid OTP entered, login failed. Timeline not appearing.");
	        }
	    }

	    // Helper method to check if the timeline is displayed after OTP verification
	    public boolean isTimelineDisplayed() {
	        return isElementPresent("web_hello_text_xpath");
	    }
	}	
	
	
	
	
	
	
	
	
	

