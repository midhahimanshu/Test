package com.appiumchrome;



import com.appiumchrome.utils.Base;
import com.appiumchrome.utils.InitiateDriver;
import com.appiumchrome.utils.PropertyReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.events.EventFiringWebDriverFactory;
import org.json.simple.parser.JSONParser;
import org.apache.commons.lang3.SystemUtils;
import org.json.simple.JSONObject;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
//import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.FileReader;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Himanshu on 29/05/20.
 */


public class TestBase{


    public AppiumDriver driver;
    public static HashMap<String,String> configProperties;
    public static HashMap<String,String> credentialsProperties;
    public static HashMap<String,String> getProperties;
    public static HashMap<String,String> errorMessages;
    public static String windowsPath=System.getProperty("user.dir")+"\\src\\main\\resources\\"; //For Windows Properties path
    public static String linux_MacPath=System.getProperty("user.dir")+"/src/main/resources/";  //For Linux Properties path

    public TestBase() {
        configProperties = PropertyReader.getPropValues("config.properties");
        credentialsProperties = PropertyReader.getPropValues("credentials.properties");
        errorMessages = PropertyReader.getPropValues("errorMessages.properties");
    }

    @BeforeTest(alwaysRun=true)
    @org.testng.annotations.Parameters(value={"config", "environment"})
    public void setup(String config_file, String environment)
    {
        try {
            getProperties = PropertyReader.getPropValues("config.properties");
            String url = getProperties.get("url");
            //For Local Running
            //InitiateDriver initiateDriver = new InitiateDriver();
            //driver = initiateDriver.getAppiumDriver();

            //For AWS
            //final String URL_STRING = "http://127.0.0.1:4723/wd/hub";

            //URL url_app = new URL(URL_STRING);
            JSONParser parser = new JSONParser();
            System.out.println(config_file);
            JSONObject config = (JSONObject) parser.parse(new FileReader(config_file));
            JSONObject envs = (JSONObject) config.get("environments");
            //Use a empty DesiredCapabilities object


            Map<String, String> envCapabilities = (Map<String, String>) envs.get(environment);
            String os = ((Map<String, String>) envs.get(environment)).get("os");
            String device = ((Map<String, String>) envs.get(environment)).get("device");
            String runOn = ((Map<String, String>) envs.get(environment)).get("runon");
            String deviceId = ((Map<String, String>) envs.get(environment)).get("deviceid");
            String platform = ((Map<String, String>) envs.get(environment)).get("platform");
            String server = (String) config.get("server");
            String user = (String) config.get("user");
            String key = (String) config.get("key");
            InitiateDriver initiateDriver = new InitiateDriver(user, key, server, runOn, device, os, deviceId);
            driver = initiateDriver.getAppiumDriver();
            //Use a higher value if your mobile elements take time to show up
//        driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            driver = EventFiringWebDriverFactory.getEventFiringWebDriver(driver, new EventListener());

            Base.driver = driver;
            //sleep(20);
            //driver.findElement(By.xpath("//input[@id='email']")).sendKeys(credentialsProperties.get("email"));
            //Pages.LoginPage().waitUntilLoginPageIsDisplayed();
        }
        catch(Exception e) {
            throw new RuntimeException("Driver setup has failed");
        }

    }


    @AfterTest(alwaysRun = true)
    public void teardown()
    {
        try {
            driver.closeApp();
            driver.quit();
        }
        catch(Exception e){

        }

    }

//    public void sleep(int duration)
//    {
//        try {
//            Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(duration));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }




    public HashMap<String,String> getCaregiverUsernameAndPassword(){
        String path=checkOS();
        HashMap<String,String> loginDetails=new HashMap();
        if(System.getProperty("caregiver_email")!=null &&System.getProperty("caregiver_password")!=null) {
            loginDetails.put("Username", System.getProperty("caregiver_email"));
            loginDetails.put("Password", System.getProperty("caregiver_password"));
            PropertyReader.setPropsValue(path + "credentials.properties", "caregiver_email", loginDetails.get("Username"));
            PropertyReader.setPropsValue(path + "credentials.properties", "caregiver_password", loginDetails.get("Password"));
        }else{
            throw new NullPointerException("Caregiver credentials were not found in environment variables");
        }
        return loginDetails;
    }

    public static String checkOS(){
        if (SystemUtils.IS_OS_WINDOWS) {
            return windowsPath;
        } else if (SystemUtils.IS_OS_MAC) {
            return linux_MacPath;
        } else if (SystemUtils.IS_OS_LINUX) {
            return linux_MacPath;
        } else{
            return null;
        }
    }





}
