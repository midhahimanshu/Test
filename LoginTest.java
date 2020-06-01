package com.appiumchrome.LoginTest;


import com.appiumchrome.Pages;
import com.appiumchrome.TestBase;

import org.testng.Assert;
import org.testng.annotations.Test;


public class LoginTest extends TestBase {




    @Test(description = "To verify Signin option when there is no account created", priority = 0)
    public void verifyLoginPage() {
        try {
            driver.resetApp();
            driver.get("https://www.lsac.org/lsat-prep-get-familiar");
            Pages.LoginPage().waitUntilLoginPageIsDisplayed();
            Pages.LoginPage().removeCookiesPopUpClick();
            Pages.LoginPage().entersignIndetails();
            Pages.LoginPage().enterPassword();
            Pages.LoginPage().clickLogInButton();
            Assert.assertTrue(Pages.LoginPage().isLoginPageDisplayed());
        } catch (Exception e) {
            Assert.fail("Verify Login page is Displayed" + e);
        }


    }






    @Test
    public void verifyErrorMessageWhileLogin()
    {
        try
        {
            driver.resetApp();
            driver.get("https://www.lsac.org/lsat-prep-get-familiar");
            Pages.LoginPage().waitUntilLoginPageIsDisplayed();
            Pages.LoginPage().removeCookiesPopUpClick();
            Pages.LoginPage().entersignIndetails();
            Pages.LoginPage().enterPassword();
            Pages.LoginPage().clickLogInButton();
            String expected = "We can't seem to find your account";
            String actual = Pages.LoginPage().getErrorMessage();
            Assert.assertEquals(actual, expected);

        }
        catch (Exception e)
        {
            Assert.fail("Failed to get error message");
        }
    }


    @Test
    public void verifyMenuPage()
    {
        try
        {
            driver.resetApp();
            driver.get("https://www.lsac.org/lsat-prep-get-familiar");
            Pages.LoginPage().waitUntilLoginPageIsDisplayed();
            Pages.LoginPage().menuButtonClick();
            Assert.assertTrue(driver.getPageSource().contains("Events"));

        }
        catch (Exception e)
        {
            Assert.fail();
        }
    }








}










