package demo.jyl.com.mydemo;

import android.app.Application;
import android.app.Notification;
import android.test.ApplicationTestCase;

import java.util.Random;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    /*public void testRandom(){
        for(int i = 0; i < 10; i++){
            System.out.println(Math.random() * 9 + 1 + "");
        }
    }*/
}