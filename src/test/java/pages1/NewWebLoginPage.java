package pages1;

import org.openqa.selenium.WebElement;

import base.BasePage;

public class NewWebLoginPage extends BasePage {
    
    // **Login Action**
    public void signin(String username, String password) {
        type("username_id", username);
        type("password_id", password);
        click("sign_in_xpath");
    }

    // **Click Sign-in Without Entering Credentials**
    public void clickSignInWithoutFillingFields() {
        click("sign_in_xpath");
    }

    // **Get Error Message for Username Field**
    public String getUsernameError() {
        return getErrorMessage("usernameError_xpath");
    }

    // **Get Error Message for Password Field**
    public String getPasswordError() {
        return getErrorMessage("passwordError_xpath");
    }

    // **Get Invalid Login Credentials Error Message**
    public String getInvalidCredentialsError() {
        return getErrorMessage("invalidLoginError_xpath");
    }

    // **Get Toast Message**
    public boolean isTimelineDisplayed() {
        return isElementPresent("web_hi_text_xpath");
    }
}
