package com.cmsonline.frameworkdemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cmsonline.framework.EMVIOUtility;
import com.cmsonline.frameworkdemo.FileOpt;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        FileOpt file= new FileOpt(appContext);
       int res= file.addTransaction(file.A200,"{\"element\":\"input\"}",file.AUTH);

        assertEquals(0,res);
       int sed= file.removeTransaction(file.A200,0,file.AUTH);
        assertEquals(0,sed);
        assertEquals("com.cmsonline.frameworkdemo", appContext.getPackageName());
       String test="{ \"Date\":\"2017\\/12\\/19/   12:57:03\"}";
        JSONObject tj = new JSONObject(test);

    }
    @Test
    public void testingtest() throws Exception{
        String cardbrand= EMVIOUtility.getCardBrandName("340000");
        assertEquals("AmericanExpress",cardbrand);
        cardbrand=EMVIOUtility.getCardBrandName("370000");
        assertEquals("AmericanExpress",cardbrand);

        cardbrand=EMVIOUtility.getCardBrandName("620000");
        assertEquals("CUP",cardbrand);

        cardbrand=EMVIOUtility.getCardBrandName("360000");
        assertEquals("DinerClub",cardbrand);
        cardbrand=EMVIOUtility.getCardBrandName("300100");
        assertEquals("DinerClub",cardbrand);
        cardbrand=EMVIOUtility.getCardBrandName("305010");
        assertEquals("DinerClub",cardbrand);
        cardbrand=EMVIOUtility.getCardBrandName("309500");
        assertEquals("DinerClub",cardbrand);
        cardbrand=EMVIOUtility.getCardBrandName("389500");
        assertEquals("DinerClub",cardbrand);
        cardbrand=EMVIOUtility.getCardBrandName("399500");
        assertEquals("DinerClub",cardbrand);

        cardbrand=EMVIOUtility.getCardBrandName("352800");
        assertEquals("JCB",cardbrand);
        cardbrand=EMVIOUtility.getCardBrandName("358900");
        assertEquals("JCB",cardbrand);


    }
}
