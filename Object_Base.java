package com.appiumchrome.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;


import java.time.Duration;

/**
 * Created by codecraft on 10/11/16.
 */
public class Object_Base {

    public static long wait = 10;

    public Object_Base(AppiumDriver driver){
        FieldDecorator fieldDecorator= new AppiumFieldDecorator(driver, Duration.ofSeconds(wait));
        PageFactory.initElements(fieldDecorator, this);



    }
}
