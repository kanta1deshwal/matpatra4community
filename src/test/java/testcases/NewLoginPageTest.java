package testcases;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BasePage;
import testBase1.BaseTest;
import pages1.NewWebLoginPage;
import utilities.TestUtil;

public class NewLoginPageTest extends BaseTest {

    @Test(dataProvider = "dp", dataProviderClass = TestUtil.class, description = "WebSignInData")
    public void loginTest(String username, String password, String expectedMessage) {
        try {
            driver.get(config.getProperty("testWebUrl"));
        } catch (Throwable t) {
            log.info(t.getMessage());
            t.printStackTrace();
        }
        
        NewWebLoginPage loginPage = new NewWebLoginPage();

        // **Step 1: Handle Different Login Scenarios**
        if (username.isEmpty() && password.isEmpty()) {
            loginPage.clickSignInWithoutFillingFields();
        } else {
            loginPage.signin(username, password);
        }

        // **Step 2: Validate Empty Field Error Messages**
        if (username.isEmpty() || password.isEmpty()) {
            String actualUsernameError = loginPage.getUsernameError();
            String actualPasswordError = loginPage.getPasswordError();

            if (username.isEmpty() && password.isEmpty()) {
                // Check both error messages when both fields are empty
                String[] expectedMessages = expectedMessage.split("\\|"); // Assume expected message contains both errors separated by '|'
                
                Assert.assertEquals(actualUsernameError, expectedMessages[0], "Username error message does not match!");
                Assert.assertEquals(actualPasswordError, expectedMessages[1], "Password error message does not match!");
                
                log.info("Both username and password error messages are correct.");
            } 
            else if (username.isEmpty()) {
                Assert.assertEquals(actualUsernameError, expectedMessage, "Username error message does not match!");
                log.info("Username error message is correct: " + actualUsernameError);
            } 
            else if (password.isEmpty()) {
                Assert.assertEquals(actualPasswordError, expectedMessage, "Password error message does not match!");
                log.info("Password error message is correct: " + actualPasswordError);
            }
        } 
        if (!username.isEmpty() && !password.isEmpty() && !expectedMessage.equals("Login Successfully")) {
            String actualErrorMessage = loginPage.getInvalidCredentialsError();
            Assert.assertEquals(actualErrorMessage, expectedMessage, "Invalid login error message does not match!");
            log.info("Invalid login error message is correct: " + actualErrorMessage);
        }

        
        // **Step 4: Validate Successful Login with Toast Message**
        else if (expectedMessage.equals("Login Successfully")) {
             loginPage.isTimelineDisplayed();

            if (loginPage.isTimelineDisplayed()) {
                log.info("Timeline displayed");
               
            } else {
                Assert.fail("Timeline not displayed.");
            }
        }
    }
}
