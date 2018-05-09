package com.transactionplatform.frameworkdemo;

import com.transactionplatform.framework.NexionUtility;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testingtest() throws Exception{
        String cardbrand=NexionUtility.getCardBrandName("340000");
        assertEquals("AmericanExpress",cardbrand);
        cardbrand=NexionUtility.getCardBrandName("370000");
        assertEquals("AmericanExpress",cardbrand);

        cardbrand=NexionUtility.getCardBrandName("620000");
        assertEquals("CUP",cardbrand);

        cardbrand=NexionUtility.getCardBrandName("360000");
        assertEquals("DinerClub",cardbrand);
        cardbrand=NexionUtility.getCardBrandName("300100");
        assertEquals("DinerClub",cardbrand);
        cardbrand=NexionUtility.getCardBrandName("305010");
        assertEquals("DinerClub",cardbrand);
        cardbrand=NexionUtility.getCardBrandName("309500");
        assertEquals("DinerClub",cardbrand);
        cardbrand=NexionUtility.getCardBrandName("389500");
        assertEquals("DinerClub",cardbrand);
        cardbrand=NexionUtility.getCardBrandName("399500");
        assertEquals("DinerClub",cardbrand);

        cardbrand=NexionUtility.getCardBrandName("352800");
        assertEquals("JCB",cardbrand);
        cardbrand=NexionUtility.getCardBrandName("358900");
        assertEquals("JCB",cardbrand);

        cardbrand=NexionUtility.getCardBrandName("222110");
        assertEquals("MASTER",cardbrand);
        cardbrand=NexionUtility.getCardBrandName("272010");
        assertEquals("MASTER",cardbrand);
        cardbrand=NexionUtility.getCardBrandName("510000");
        assertEquals("MASTER",cardbrand);
        cardbrand=NexionUtility.getCardBrandName("550000");
        assertEquals("MASTER",cardbrand);
        cardbrand=NexionUtility.getCardBrandName("400000");
        assertEquals("VISA",cardbrand);
        cardbrand=NexionUtility.getCardBrandName("100021");
        assertEquals("UnknowCard",cardbrand);
        cardbrand=NexionUtility.getCardBrandName("640000");
        assertEquals("Discover",cardbrand);
        cardbrand=NexionUtility.getCardBrandName("650000");
        assertEquals("Discover",cardbrand);
        cardbrand=NexionUtility.getCardBrandName("601100");
        assertEquals("Discover",cardbrand);
    }
}