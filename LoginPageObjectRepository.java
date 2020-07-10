package com.appiumchrome.LoginPage;

import com.appiumchrome.utils.Object_Base;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageObjectRepository extends Object_Base {

    public LoginPageObjectRepository(AppiumDriver driver){
        super(driver);
    }



    @FindBy(xpath = "//android.widget.Button[@text='Open navigation menu']")
    static protected WebElement menuClick;


    @FindBy(xpath = "//android.view.View[@text='Get Started Today']")
    static protected WebElement homepageClick;


    @FindBy(id = "CybotCookiebotDialogBodyLevelButtonAccept")
    static protected WebElement cookiesOKButtonClick;

    @FindBy(id = "logonIdentifier")
    static protected WebElement usernameTextBox;

    @FindBy(id = "password")
    static protected WebElement passwordTextBox;

    @FindBy(id = "next")
    static protected WebElement signinButtonClick;

    @FindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.webkit.WebView/android.view.View/android.view.View[3]/android.view.View[4]/android.view.View/android.view.View[1]/android.view.View\n")
    static protected WebElement errorMessage;


    @FindBy(id = "createAccount")
    static protected WebElement createAccountClick;







//    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTextField[@value='email']")
//    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='com.enphase.installer:id/etEmail']")
//    protected static WebElement emailField;
//
//
//    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"QA\"]\n")
//    protected static WebElement pointingtoQaBuild;






}
