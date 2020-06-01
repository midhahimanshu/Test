package com.appiumchrome.utils;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by codecraft on 08/11/16.
 */
public class InitiateDriver {
    private RemoteWebDriver driver;
    private AppiumDriver appiumDriver;
    private HashMap<String,String> getProperties;
    public static String appUrl = System.getenv("BROWSERSTACK_APP_ID");

    public InitiateDriver(String user, String key, String server, String runOn, String device, String os, String deviceId)
    {
        try
        {
            getProperties = PropertyReader.getPropValues("config.properties");
            //String runOn = System.getProperty("runOn") == null ? getProperties.get("RUN_ON") : System.getProperty("runOn");
            String platform = System.getProperty("platform") == null ? getProperties.get("PLATFORM") : System.getProperty("platform");
            String url = System.getProperty("url") == null ? getProperties.get("SELENIUMSERVERURL") : System.getProperty("url");
            String mUrl =System.getProperty("mUrl") == null ? getProperties.get("mURL") : System.getProperty("mUrl");
            System.out.println(runOn);
            String setup = getProperties.get("SETUP");
            if(setup.equalsIgnoreCase("local")){
                if (runOn.equalsIgnoreCase("IOS_APP")){
                    appUrl = getProperties.get("iOSAppPath")+"/"+getProperties.get("iOSAppName");
                }else{
                    appUrl = getProperties.get("AppPath")+"/"+getProperties.get("AppName");
                }

            }
            String browser = null;

            if(platform.equalsIgnoreCase("WINDOWS"))
            {
                if (runOn.equalsIgnoreCase("WEBSITE"))
                {
                    browser = System.getProperty("browser") == null ? getProperties.get("BROWSER") : System.getProperty("browser");
                    System.out.println(url);
                    driver = new RemoteWebDriver(new URL(url), getBrowserCapabilities(browser, runOn,  device,  os, deviceId));
                    System.out.println("Driver is: "+driver);
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                }
            }
            else if(platform.equalsIgnoreCase("ANDROID"))
            {
                if (runOn.equalsIgnoreCase("ANDROID_SITE"))
                {
                    browser = System.getProperty("mBrowser") == null ? getProperties.get("MOBILE_BROSWER") : System.getProperty("mBrowser");
                    System.out.println(browser);
                    System.out.println(runOn);
                    System.out.println(device);
                    System.out.println(os);
                    System.out.println(deviceId);
                    appiumDriver = new AndroidDriver(new URL(mUrl), getBrowserCapabilities(browser, runOn,  device,  os, deviceId));

                    appiumDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                }
                else if (runOn.equalsIgnoreCase("ANDROID_APP")) {
                    appiumDriver = new AndroidDriver(new URL(getProperties.get("AndroidAppURL")), getBrowserCapabilities(browser, runOn,device,os, deviceId));
                    //appiumDriver = new AndroidDriver(new URL("https://"+user+":"+key+"@"+server+"/wd/hub"), getBrowserCapabilities(browser, runOn, device, os, deviceId));

                    appiumDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                }

            }
            else if(platform.equalsIgnoreCase("IOS"))
            {
                if (runOn.equalsIgnoreCase("IOS_SITE"))
                {
                    browser = System.getProperty("mBrowser") == null ? getProperties.get("MOBILE_BROSWER") : System.getProperty("mBrowser");
                    appiumDriver = new IOSDriver(new URL(mUrl), getBrowserCapabilities(browser, runOn,  device,  os, deviceId));
                    appiumDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                }
                else if (runOn.equalsIgnoreCase("IOS_APP")) {
                    appiumDriver = new IOSDriver(new URL(getProperties.get("iOSAppURL")), getBrowserCapabilities(browser, runOn,  device,  os, deviceId));
                    appiumDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public RemoteWebDriver getDriver()
    {
        if(driver==null)
            throw new RuntimeException("Driver has not been Instantiated");

        return driver;
    }

    public AppiumDriver getAppiumDriver()
    {
        if(appiumDriver==null)
            throw new RuntimeException("Driver has not been instantiated");

        return appiumDriver;
    }

    private DesiredCapabilities getBrowserCapabilities(String browser, String runOn, String device, String os, String deviceId)
    {
        DesiredCapabilities capabilities = null;
        String appName = System.getProperty("appName") == null ? getProperties.get("AppName") : System.getProperty("appName");
        String appPath =System.getProperty("appPath") == null ? getProperties.get("AppPath") : System.getProperty("appPath");

        try
        {
            if (runOn.equalsIgnoreCase("WEBSITE") && browser.equalsIgnoreCase("Firefox"))
            {
                capabilities = DesiredCapabilities.firefox();
                capabilities.setBrowserName("firefox");
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setPlatform(Platform.ANY);
            }
            else if (runOn.equalsIgnoreCase("WEBSITE") && browser.equalsIgnoreCase("IE"))
            {
                //InternetExplorerDriverManager.getInstance().setup();
                //System.setProperty("webdriver.ie.driver", "../../../../resources/IEDriverServer.exe");
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setBrowserName("internet explorer");
                capabilities.setPlatform(Platform.WINDOWS);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability("ie.ensureCleanSession", true);
            }
            else if (runOn.equalsIgnoreCase("WEBSITE") && browser.equalsIgnoreCase("Chrome"))
            {
                //ChromeDriverManager.getInstance().setup();
                //System.setProperty("webdriver.chrome.driver","/home/" + System.getProperty("user.name") + "/Documents/chromedriver");
                capabilities = DesiredCapabilities.chrome();
                ChromeOptions options = new ChromeOptions();
                //options.addArguments("user-data-dir=/Users/codecraft/Library/Application Support/Google/Chrome");
                //options.addArguments("user-data-dir=/Users/codecraft/Library/Application Support/Google/Chrome/Profile 2");
                options.setExperimentalOption("excludeSwitches", Arrays.asList("disable-component-update")); //To Play DRM Content
                capabilities.setBrowserName("chrome");
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                capabilities.setPlatform(Platform.ANY);
            }
            else if (runOn.equalsIgnoreCase("ANDROID_SITE"))
            {

                                capabilities = DesiredCapabilities.android();
                capabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));


                // set the capability to execute test in chrome browser

                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");


                capabilities.setCapability(MobileCapabilityType.PLATFORM,Platform.ANDROID);


                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");


                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,device);

                // set the android version as well
                capabilities.setCapability(MobileCapabilityType.VERSION,"7.1.1");


//                capabilities = DesiredCapabilities.android();
//                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1.1");
//                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
//                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
//                capabilities.setCapability("deviceName", device);
//                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "");
//                System.out.println("test");
            }
            else if (runOn.equalsIgnoreCase("IOS_SITE"))
            {
                capabilities = DesiredCapabilities.iphone();
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.3");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browser);
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "");
            }
            else if (runOn.equalsIgnoreCase("ANDROID_APP"))
            {

                //String version=Base.getAndroidVersion().trim();
                capabilities=new DesiredCapabilities();
                //capabilities.setCapability();
                capabilities.setCapability("deviceName", device);
                capabilities.setCapability("platformName", "Android");
                capabilities.setCapability("platformVersion", os);
                capabilities.setCapability("name", getProperties.get("TestName"));
                //capabilities.setCapability("browserstack.debug", "true");
                //capabilities.setCapability("app", "bs://8adb16b588ee380966ec2a6a1dbc134ef2a67044");
                //capabilities.setCapability("app", appUrl);   //need for local run
                capabilities.setCapability("appPackage", getProperties.get("AndroidAppPackage"));
                capabilities.setCapability("appActivity", getProperties.get("AndroidAppActivity"));
                capabilities.setCapability("automationName","UiAutomator2");
            }
            else if (runOn.equalsIgnoreCase("IOS_APP"))
            {
                capabilities=new DesiredCapabilities();

                //  capabilities.setCapability("autoAcceptAlerts", true);
                capabilities.setCapability("platformVersion", os);
                capabilities.setCapability("platformName", "iOS");
                capabilities.setCapability("deviceName", device);
                if(getProperties.get("SETUP").equalsIgnoreCase("Local")){
                    capabilities.setCapability("udid", deviceId);
                }
                capabilities.setCapability("app", appUrl);    //need for Local run
                System.out.println("App url is:" +appUrl);
                capabilities.setCapability("waitForAppScript", "$.delay(8000);$.acceptAlert()");
                capabilities.setCapability("name", getProperties.get("TestName"));
                capabilities.setCapability("browserstack.debug", "true");

            }
            else
            {
                // default is firefox
                capabilities = DesiredCapabilities.firefox();
                capabilities.setBrowserName("firefox");
                capabilities.setPlatform(Platform.ANY);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return capabilities;
    }
}
