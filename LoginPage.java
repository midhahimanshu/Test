package com.appiumchrome.LoginPage;

import com.appiumchrome.utils.Base;

import static com.appiumchrome.LoginPage.LoginPageObjectRepository.*;

public class LoginPage extends Base {

    private static LoginPageObjectRepository lp;

    public LoginPage() {
        lp = new LoginPageObjectRepository(driver);
    }

    public boolean isLoginPageDisplayed() {
        return isElementVisible(lp.homepageClick);
    }

    public void waitUntilLoginPageIsDisplayed() {
        try {
            System.out.println(driver.getPageSource());
            waitUntilElementIsVisible(lp.homepageClick);
            scrollUpWindow(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean menuButtonClick()
    {
        try
        {
            if(menuClick.isDisplayed()| menuClick.isEnabled())
            {
                menuClick.click();
                return true;
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }





    public boolean removeCookiesPopUpClick()
    {
        try
        {
            if(cookiesOKButtonClick.isDisplayed() | cookiesOKButtonClick.isEnabled())
            {
                cookiesOKButtonClick.click();
                System.out.println("Cookies accepted");
                scrollUpWindow(1000);
                return true;
            }
            else
            {
                System.out.println("No cookies popup displayed");
                scrollUpWindow(1000);
                return false;
            }
        }
        catch (Exception e)
        {
            System.out.println("Some other exception observed");
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static void entersignIndetails() {
        try {
            scrollUpWindow(1000);
            if (usernameTextBox.isEnabled() || usernameTextBox.isDisplayed()) {
                scrollUpWindow(1000);
                homepageClick.click();
                usernameTextBox.sendKeys("test");
                System.out.println("Username Entered");

            } else {
                scrollUpWindow(1000);
                homepageClick.click();
                usernameTextBox.sendKeys("test");
                System.out.println("Username field not Entered!!!");
                System.out.println("Password field not found");
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public static void enterPassword() {
        try {
            if (passwordTextBox.isDisplayed() || passwordTextBox.isEnabled()) {
                passwordTextBox.clear();
                passwordTextBox.sendKeys("test123");
                System.out.println("Password Entered");
            } else {
                System.out.println("Password not Found");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    public static void clickLogInButton() {
        lp.signinButtonClick.click();
    }


    public String getErrorMessage() {
        try {
            if (errorMessage.isDisplayed()) {
                String errormessagedata = errorMessage.getText();
                return errormessagedata;
            } else {
                System.out.println("There is no error message while clicking signin Button");
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;

    }

















}
