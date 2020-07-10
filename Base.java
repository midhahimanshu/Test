package com.appiumchrome.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

/**
 * Created by codecraft on 08/11/16.
 */
public class Base {

    public static long wait=90;
    public static AppiumDriver driver;
    public static String windowsPath="C:\\Users\\"+System.getProperty("user.name")+"\\AppData\\Local\\Android\\android-sdk\\platform-tools\\";
    public static String linuxPath="/home/"+"System.getProperty(\"user.name\")"+"/Android/android-sdk/platform-tools/";

    //public static AndroidDriver androidDriver;

    public static boolean isElementVisible(WebElement element)
    {
        try{
            if(element.isDisplayed())
                return true;
            return false;
        }
        catch (org.openqa.selenium.NoSuchElementException e)
        {
            return false;
        }

    }


    public static void waitForElementToBeInvisible(WebElement element)
    {
        WebDriverWait wwait = new WebDriverWait(driver,wait);
        wwait.until(invisibilityOfWebElementLocated(element));
    }

    public static void waitUntilElementIsVisible(WebElement element){
        WebDriverWait wwait = new WebDriverWait(driver, wait);
        wwait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitUntilElementHasText(WebElement element, String text)
    {
        WebDriverWait wwait = new WebDriverWait(driver, wait);
        wwait.until(ExpectedConditions.textToBePresentInElement(element,text));
    }

    private static ExpectedCondition<Boolean> invisibilityOfWebElementLocated(final WebElement element)
    {
        return new ExpectedCondition<Boolean>() {
            //@Override
            public Boolean apply(WebDriver driver) {
                try
                {
                    if (element.isDisplayed())
                        return false;
                    return true;
                }
                catch (Exception e)
                {
                    return true;
                }
            }
        };
    }

    public static void waitUntilElementsAttributeHasChanged(WebElement element, String attribute, String initialValue){
        WebDriverWait wwait = new WebDriverWait(driver, wait);
        wwait.until(attributeValueOfElementChanged(element,attribute,initialValue));
    }

    private static ExpectedCondition<Boolean> attributeValueOfElementChanged(final WebElement element, final String attribute, final String initialValue){
        return new ExpectedCondition<Boolean>() {
            //@Override
            public Boolean apply(WebDriver driver) {
                try
                {
                    if (element.getAttribute(attribute).equalsIgnoreCase(initialValue))
                        return false;
                    return true;
                }
                catch (Exception e)
                {
                    return true;
                }
            }
        };

    }

    public static boolean isAndroid(){
        HashMap<String,String> getProperties = PropertyReader.getPropValues("config.properties");
        String runOn = getProperties.get("RUN_ON");
        if(runOn.equalsIgnoreCase("ANDROID_APP"))
            return true;
        return false;
    }

    public static void navigateBack(){
        driver.navigate().back();
    }

    public static void hideKeyboard(){
        try{
            driver.hideKeyboard();
        }
        catch (Exception e){
            System.out.println("Keyboard Already Closed");
        }
    }

    public static void  scrollUp(WebElement element, int stepSize){
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.width *0.4);
        int startPoint = (int) (size.height * 0.80);
        int endPoint = (int) (size.height * 0.40);
        new TouchAction(driver)
                .press(PointOption.point(anchor, startPoint))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(stepSize)))
                .moveTo(PointOption.point(anchor, endPoint))
                .release().perform();
    }
    public static void scrollDown(WebElement element, int stepSize){
        int counter = 0;
        Dimension size = element.getSize();
        int x = element.getLocation().getX();
        int y = element.getLocation().getY();
        System.out.println(size);
        int starty = (int) ((size.height*0.80)+y);
        int endy = (int) ((size.height*0.20)+y);
        int startx = size.width/2;
        new TouchAction(driver)
                .press(PointOption.point(startx,endy))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(stepSize)))
                .moveTo(PointOption.point(startx, starty))
                .release().perform();
        //driver.swipe(startx, endy, startx, starty, stepSize);
    }


//    public static void scrollUp(WebElement element, int stepSize){
//        int counter = 0;
//        Dimension size = element.getSize();
//        int x = element.getLocation().getX();
//        int y = element.getLocation().getY();
//        System.out.println(size);
//        int starty = (int) ((size.height*0.80)+y);
//        int endy = (int) ((size.height*0.20)+y);
//        int startx = size.width/2;
//
//        driver.swipe(startx, starty, startx, endy, stepSize);
//    }
//
//    public static void scrollDown(WebElement element, int stepSize){
//        int counter = 0;
//        Dimension size = element.getSize();
//        int x = element.getLocation().getX();
//        int y = element.getLocation().getY();
//        System.out.println(size);
//        int starty = (int) ((size.height*0.80)+y);
//        int endy = (int) ((size.height*0.20)+y);
//        int startx = size.width/2;
//
//
//        driver.swipe(startx, endy, startx, starty, stepSize);
//    }

    public static void scrollUpWindow(int stepSize){
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.width *0.4);
        int startPoint = (int) (size.height * 0.80);
        int endPoint = (int) (size.height * 0.40);
        new TouchAction(driver)
                .press(PointOption.point(anchor, startPoint))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(stepSize)))
                .moveTo(PointOption.point(anchor, endPoint))
                .release().perform();
    }

//    public static void scrollDownWindow(int stepSize){
//        int counter = 0;
//        Dimension size = driver.manage().window().getSize();
//        int starty = (int) (size.height * 0.80);
//        int endy = (int) (size.height * 0.20);
//        int startx = size.width / 2;
//
//        //Swipe from Top to Bottom.
//        driver.swipe(startx, endy, startx, starty, stepSize);
//    }

//    public static void scrollLeftWindow(int stepSize){
//        Dimension size = driver.manage().window().getSize();
//        System.out.println(size);
//        int startx = (int) (size.width * 0.70);
//        int endx = (int) (size.width * 0.30);
//        int starty = size.height / 2;
//
//        driver.swipe(startx, starty, endx, starty, stepSize);
//    }
//
//    public static void scrollRightWindow(int stepSize){
//        Dimension size = driver.manage().window().getSize();
//        System.out.println(size);
//        int startx = (int) (size.width * 0.70);
//        int endx = (int) (size.width * 0.30);
//        int starty = size.height / 2;
//
//        driver.swipe(endx, starty, startx, starty, stepSize);
//    }

    /**
     * Helper method to scroll up or down to an element by name, with a particular step size.
     *
     * @param element The name of the element.
     * @param up True if movement of 'finger' is upwards.
     * @param stepSize Distance from top and bottom of screen where the gesture will begin/end (percentage of screen)
     * @return True if found.
     */
    public static WebElement loopScrollToElementHavingText(WebElement scrollObjectName,WebElement element, String text, boolean up, int stepSize) {
        int counter = 0;
        String pageSource = "";
        //WebElement element = null;
        while (counter < 50) {
            try {
                System.out.println("Text: "+element.getText());
                if(isElementVisible(element) && element.getText().equalsIgnoreCase(text))
                {
                    return element;
                }
                else
                {
                    System.out.println("Element Not Found while scrolling");
                }
                //int startY = up ? topY : bottomY;
                //int endY = up ? bottomY : topY;

                if (up)
                {
                    scrollUp(scrollObjectName,stepSize);
                }
                else
                {
                    scrollDown(scrollObjectName,stepSize);
                }
                Thread.sleep(1000);
                if (pageSource.equals(driver.getPageSource())) {
                    return element;
                }
                pageSource = driver.getPageSource();
            } catch (Exception e) {
                System.out.println(e);
                System.out.println(counter + " time trying to scroll " + (up ? "up" : "down") + " to " + element.getText());
            }
            counter++;
        }

        return element;
    }

    /**
     * Helper method to scroll up or down to an element by name, with a particular step size.
     *
     * @param element The name of the element.
     * @param up True if movement of 'finger' is upwards.
     * @param stepSize Distance from top and bottom of screen where the gesture will begin/end (percentage of screen)
     * @return True if found.
     */
    public static WebElement loopScrollToElement(WebElement scrollObjectName,WebElement element, boolean up, int stepSize) {
        int counter = 0;
        String pageSource = "";
        //WebElement element = null;
        while (counter < 50) {
            try {
                System.out.println("Text: "+element.getText());
                if(isElementVisible(element))
                {
                    return element;
                }
                else
                {
                    System.out.println("Element Not Found while scrolling");
                }
                //int startY = up ? topY : bottomY;
                //int endY = up ? bottomY : topY;

                if (up)
                {
                    scrollUp(scrollObjectName,stepSize);
                }
                else
                {
                    scrollDown(scrollObjectName,stepSize);
                }
                Thread.sleep(1000);
                if (pageSource.equals(driver.getPageSource())) {
                    return element;
                }
                pageSource = driver.getPageSource();
            } catch (Exception e) {
                System.out.println(e);
                System.out.println(counter + " time trying to scroll " + (up ? "up" : "down") + " to " + element.getText());
            }
            counter++;
        }

        return element;
    }

    public static void sleep(long timeout){
        try{
            Thread.sleep(timeout);
        }
        catch (InterruptedException e){

        }
    }

//    public static void slideSeekbar(WebElement seekbar, int position){
//        System.out.println("Sliding");
//        // get start co-ordinate of seekbar
//        int start=seekbar.getLocation().getX();
//        //Get width of seekbar
//        int end=seekbar.getSize().getWidth();
//        //get location of seekbar vertically
//        int y=seekbar.getLocation().getY();
//
//        // Select till which position you want to move the seekbar
//        TouchAction action=new TouchAction(driver);
//
//
//        //Move it x%
//        int moveTo=(int)(end*(position/100));
//        action.press(start,y).moveTo(moveTo,y).release().perform();
//
//    }

    public static void clickSeekbar(WebElement seekbar, int position){
        // get start co-ordinate of seekbar
        int start=seekbar.getLocation().getX();
        //Get width of seekbar
        int end=seekbar.getSize().getWidth();
        //get location of seekbar vertically
        int y=seekbar.getLocation().getY();

        // Select till which position you want to move the seekbar
        TouchAction action=new TouchAction(driver);


        //Move it x%
        int moveTo=(int)(end*(position/100));
        action.press(PointOption.point(moveTo,y)).release().perform();

    }

    public static String generateRandomString(int length){
        String randomString = new String();
        randomString = RandomStringUtils.randomAlphabetic(length);
        return randomString;
    }

    public static String generateAlphaNumericString(int length){
        String randomAlphaNumericString = new String();
        randomAlphaNumericString  = RandomStringUtils.randomAlphanumeric(length);
        return randomAlphaNumericString;
    }

    public static String generateRandomMobileNumber(){
        String mobileNumber = new String();
        mobileNumber = RandomStringUtils.random(1,"789");
        mobileNumber=mobileNumber+RandomStringUtils.randomNumeric(9);
        return mobileNumber;
    }

    public static String generateRandomEmail(){
        String email = RandomStringUtils.randomAlphanumeric(12).toLowerCase();
        return email+"@mailinator.com";
    }

    public static String generateRandomNumber(){
        return RandomStringUtils.randomNumeric(13);
    }

    public static void executeCommand(String[] args) {
        ProcessBuilder pb = new ProcessBuilder(args);
        Process pc;
        try {
            pc = pb.start();
            pc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void turnOffWifi() {
        String path=checkOS();
        try {
            Runtime.getRuntime().exec(path + "adb.exe shell am broadcast -a io.appium.settings.wifi --es setstatus disable");
            sleep(3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void turnOnWifi() {
        String path=checkOS();
        try {
            Runtime.getRuntime().exec(path + "adb.exe shell am broadcast -a io.appium.settings.wifi --es setstatus enable");
            sleep(3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(int timeout){
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public static void minimizeApplicationAndRestore(int time){
//        sleep(3000);
//        driver.runAppInBackground(time);
//        sleep(3000);
//    }

    public static String checkOS(){
        if (SystemUtils.IS_OS_WINDOWS) {
            return windowsPath;
        } else if (SystemUtils.IS_OS_MAC) {
            return null;
        } else if (SystemUtils.IS_OS_LINUX) {
            return linuxPath;
        } else{
            return null;
        }
    }

    public static String getAndroidVersion() {
        String path = checkOS();
        try {
            java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(path + "adb shell getprop ro.build.version.release").getInputStream()).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            return "";
        }

    }

        //.............................
        public Boolean isvisible(WebElement locator, int timeout){
            try{
                new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(locator));
                return locator.isDisplayed();
            }catch(Exception e){
                System.out.println(locator+ " element was not found");
                return false;
            }
        }


        //....
        public static boolean isPresent(By element)
        {
            try{
                if(driver.findElement(element).isDisplayed())
                    return true;
                return false;
            }
            catch (org.openqa.selenium.NoSuchElementException e)
            {
                System.out.println("exception occured");
                return false;
            }

        }

    public Boolean isEmpty(WebElement locator, int timeout){
        try{
            new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(locator));
            return locator.isDisplayed();
        }catch(Exception e){
            System.out.println(locator+ " element was not found");
            return false;
        }
    }



    public static void loopScrollToElementwithLocator(WebElement scrollObjectName, By element, boolean up, int stepSize , int loopCount ) {
        int counter = 0;
        String pageSource = "";
//WebElement element = null;
        while (counter < loopCount) {
            try {
// System.out.println("Text: "+element.getText());
                if(isPresent(element))
                {
                    return ;
                }
                else
                {
                    System.out.println("Element Not Found while scrolling");
                }
//int startY = up ? topY : bottomY;
//int endY = up ? bottomY : topY;
                if (up)
                {
                    scrollUp(scrollObjectName,stepSize);
                }
                else
                {
                    scrollDown(scrollObjectName,stepSize);
                }
                Thread.sleep(1000);
                if (pageSource.equals(driver.getPageSource())) {
                    return ;
                }
                pageSource = driver.getPageSource();
            } catch (Exception e) {
                System.out.println(e);
// System.out.println(counter + " time trying to scroll " + (up ? "up" : "down") + " to " + element.getText());
            }
            counter++;
            System.out.println(counter);
        }

        return ;
    }


}
