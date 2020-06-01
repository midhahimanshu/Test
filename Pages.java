package com.appiumchrome;


import com.appiumchrome.LoginPage.LoginPage;

import com.appiumchrome.utils.Base;

/**
 * Created by Himanshu on 29/05/20.
 */
public class Pages {

    private static <T extends Base> T getPage(Class<T> pageType)  {
        T page;
        try {
            page = pageType.newInstance();
        } catch (InstantiationException e) { //make sure you use JDK 1.7
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return page;
    }

    public static LoginPage LoginPage(){return getPage(LoginPage.class);}



}
