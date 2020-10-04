package com.origamisoftware.teach.advanced.services;

import com.origamisoftware.teach.advanced.model.StockQuote;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for the DatabaseStockService
 */
public class DatabaseStockServiceTest {

    @Test
    public void testGetQuote() throws Exception {
        DatabaseStockService databaseStockService = new DatabaseStockService();
        String symbol = "APPL";
        StockQuote stockQuote = databaseStockService.getQuote(symbol);
        assertNotNull("Verify we can get a stock quote from the db", stockQuote);
        assertEquals("Make sure the symbols match", symbol, stockQuote.getSymbol());



    }
    /**
     * Unit tests for the DatabaseStockService list
     */
    @Test
    public void testGetMultipleQuotes() throws Exception{
        DatabaseStockService databaseStockService = new DatabaseStockService();
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy");
        String dateInString = "09/15/2014";
        Date date = sdf.parse(dateInString);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);

        String date2InString = "09/15/2000";
        Date date2 = sdf.parse(date2InString);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        String symbol = "GOOG";
        List<StockQuote> stockQuote2 = databaseStockService.getQuote(symbol, calendar1, calendar2);
        assertNotNull("Verify we can get a stock quote from the db", stockQuote2);
        assertEquals("Make sure the symbols match", symbol, stockQuote2.get(0).getSymbol());
    }
}
