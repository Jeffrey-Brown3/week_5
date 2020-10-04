package com.origamisoftware.teach.advanced.services;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Calendar;

import static org.junit.Assert.assertNotNull;
/**
 * This class tests the simple stock service.
 */
public class SimpleStockServiceTest {
    StockService service;
    Timestamp timestamp;


    @Before
    public void setUp(){
        service = new SimpleStockService();
        timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    @Test
    public void testGetQuote() throws StockServiceException {
        assertNotNull("test", service.getQuote("APPL"));
    }
}
