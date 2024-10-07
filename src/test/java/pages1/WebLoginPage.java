package pages1;

import java.sql.SQLException;


import org.testng.Assert;

import base.BasePage;
import utilities.TestUtil;


	
public class WebLoginPage extends BasePage {
	
    public  OtpPage loginViaOtp(String username, String isDuplicate) throws InterruptedException, SQLException{

        // Fetch member type from the database
        String memberType = getMemberType(username);

        // Common actions for OTP login
        performLoginActions(username);

        // Check member type and proceed accordingly
        if (isPrimaryMember(memberType)) {
            // Check if OTP page appeared for primary members
            if (isOtpPageDisplayed()) {
                log.info("OTP Page appeared for primary member.");
                return new OtpPage(); // OTP page appeared, return OTPPage instance
            } else {
                Assert.fail("Testcase FAILED: OTP Page did not appear for memberType 1, 2, or 3.");
            }
        } else if (isNonMember(memberType)) {
            log.info("User is not a member.");
            if (isOtpPageDisplayed()) {
                Assert.fail("Testcase FAILED: OTP Page appeared for a non-member.");
            }
            log.info("Testcase PASSED: OTP Page did not appear for non-member.");
        } else {
            log.info("Unexpected member_type: " + memberType);
            Assert.fail("Unexpected member_type: " + memberType);
        }

        return null; // Return null if OTP page does not appear
    }

    // Common actions for both members and non-members during OTP login
    private void performLoginActions(String username) throws InterruptedException {
        click("via_OTP_xpath");
        type("mobileNoField_id", username);
        click("sendOTPButton_xpath");
    }

    // Helper method to fetch member type from database
    private String getMemberType(String username) throws SQLException {
        return TestUtil.getMysqlQuery("SELECT member_type FROM matpatra.members WHERE mobile_no = '" + username + "';");
    }

    // Helper method to determine if user is a primary member
    private boolean isPrimaryMember(String memberType) {
        return memberType.equals("1") || memberType.equals("2") || memberType.equals("3");
    }

    // Helper method to determine if user is a non-member
    private boolean isNonMember(String memberType) {
        return memberType.isEmpty();
    }

    // Helper method to check if OTP page is displayed
    private boolean isOtpPageDisplayed() {
        return isElementPresent("OtpPage_xpath");
    }

    // Helper method to fetch OTP from the database and handle duplicates
    public String fetchOtpFromDb(String username, String isDuplicate) throws SQLException, InterruptedException {
        Thread.sleep(5000);
        String otp = TestUtil.getMysqlQuery(
            "SELECT otp FROM matpatra.otp_varification WHERE mobile_number = '" + username + "' ORDER BY created_at DESC LIMIT 1");

        if (isDuplicate.equalsIgnoreCase("TRUE")) {
            otp = "000000"; // Set incorrect OTP for duplicates
            log.info("Duplicate user detected, using incorrect OTP: " + otp);
        }

        return otp;
    }

}




	       
	  
	

				
	  
