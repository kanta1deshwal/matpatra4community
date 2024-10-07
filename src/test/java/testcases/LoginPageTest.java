package testcases;

import java.sql.SQLException;

import org.testng.annotations.Test;

import pages1.OtpPage;
import pages1.WebLoginPage;
import testBase1.BaseTest;
import utilities.TestUtil;



public class LoginPageTest extends BaseTest {

	@Test(dataProvider = "dp", dataProviderClass = TestUtil.class, description = "MatpatraAdminViaOTPTest") // Match DataProvider name and add a description
    public void testLogin(String username, String isDuplicate) throws InterruptedException, SQLException, ClassNotFoundException {
        try {
            driver.get(config.getProperty("testWebUrl"));
        } catch (Throwable t) {
            log.info(t.getMessage());
            t.printStackTrace();
        }

        TestUtil.setMysqlDbConnection();

        // Create instance of LoginPage
        WebLoginPage loginPage = new WebLoginPage();

        // Perform login via OTP and receive OTPPage instance
        OtpPage otpPage = loginPage.loginViaOtp(username, isDuplicate);

        if (otpPage != null) {
            // Fetch OTP and verify
            String otp = loginPage.fetchOtpFromDb(username, isDuplicate);
            otpPage.verifyOtp(otp);
        }
    }
}
		

		
		
	
	
	
	
	
	
	
	

