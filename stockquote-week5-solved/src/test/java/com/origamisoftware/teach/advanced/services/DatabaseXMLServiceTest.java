package com.origamisoftware.teach.advanced.services;

import com.origamisoftware.teach.advanced.model.Person;
import com.origamisoftware.teach.advanced.model.PersonTest;
import com.origamisoftware.teach.advanced.model.StockData;
import com.origamisoftware.teach.advanced.model.StockQuote;
import com.origamisoftware.teach.advanced.util.DatabaseInitializationException;
import com.origamisoftware.teach.advanced.util.DatabaseUtils;
import com.origamisoftware.teach.advanced.util.Interval;
import com.origamisoftware.teach.advanced.util.XMLUtils;
import com.origamisoftware.teach.advanced.xml.Stock;
import com.origamisoftware.teach.advanced.xml.Stocks;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class DatabaseXMLServiceTest {

    private DatabaseStockService databaseStockService;


    @Before
    public void setUp() throws DatabaseInitializationException {
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
        databaseStockService = new DatabaseStockService();

    }

    /**
     * Tests passing a stock object pulled from XML doc to database stock service
     *
     */
    @Test
    public void testGetQuoteFromXML() throws Exception {
        String content = Files.readString(Path.of("stockquote-week5-solved/src/main/resources/xml/stock_info.xml"), StandardCharsets.US_ASCII);
        Stocks stocks = XMLUtils.unmarshall(content, Stocks.class);
        Stock stock = stocks.getStock().get(0);
        StockQuote stockQuote = databaseStockService.getQuote(stock);
        assertNotNull("Verify we can get a stock quote from the db", stockQuote);
        assertEquals("Make sure the symbols match", stock.getSymbol(), "VNET");
    }

    @Test
    public void testGetQuoteWithIntervalBasic() throws Exception {
        String content = Files.readString(Path.of("stockquote-week5-solved/src/main/resources/xml/stock_info.xml"), StandardCharsets.US_ASCII);
        Stocks stocks = XMLUtils.unmarshall(content, Stocks.class);
        Stock stock = stocks.getStock().get(0);
        String symbol = stock.getSymbol();
        String fromStringDate = "2000/02/10 00:00:01";
        String untilStringDate = "2015/02/03 00:00:01";

        Calendar fromCalendar = makeCalendarFromString(fromStringDate);
        Calendar untilCalendar = makeCalendarFromString(untilStringDate);

        List<StockQuote> stockQuotes = databaseStockService.getQuote(symbol, fromCalendar, untilCalendar, Interval.DAY);
        //the stock symbol doesnt exist in the list so shouldnt return
        assertTrue("verify stock quotes werent returned", stockQuotes.isEmpty());
    }

    @Test
    public void testGetQuoteWithinRange() throws Exception {

        String fromDateString = "2015/02/10 00:01:01";
        String endDateString = "2015/02/10 00:08:01";
        String content = Files.readString(Path.of("stockquote-week5-solved/src/main/resources/xml/stock_info.xml"), StandardCharsets.US_ASCII);
        Stocks stocks = XMLUtils.unmarshall(content, Stocks.class);
        Stock stock = stocks.getStock().get(0);
        String symbol = stock.getSymbol();

        String sql = "INSERT INTO quotes (symbol,time,price) VALUES ('AMZN','" + fromDateString + "','363.21');";
        DatabaseUtils.executeSQL(sql);

        sql = "INSERT INTO quotes (symbol,time,price) VALUES ('AMZN','2015/02/10 00:04:01','250.21');";
        DatabaseUtils.executeSQL(sql);

        sql = "INSERT INTO quotes (symbol,time,price) VALUES ('AMZN','2015/02/10 00:06:01','251.21');";
        DatabaseUtils.executeSQL(sql);

        sql = "INSERT INTO quotes (symbol,time,price) VALUES ('AMZN','" + endDateString + "','253.21');";
        DatabaseUtils.executeSQL(sql);

        Calendar fromCalendar = makeCalendarFromString(fromDateString);
        Calendar untilCalendar = makeCalendarFromString(endDateString);

        List<StockQuote> stockQuotes = databaseStockService.getQuote(symbol, fromCalendar, untilCalendar, Interval.DAY);
        assertEquals("got back expected number of stockquotes for one day interval", 0, stockQuotes.size());

        stockQuotes = databaseStockService.getQuote(symbol, fromCalendar, untilCalendar, Interval.MINUTE);
        assertEquals("got back expected number of stockquotes for one minute interval", 0, stockQuotes.size());
    }

    /**
     * Handy dandy helper method that converts Strings in the format of   StockData.dateFormat
     * to Calendar instances set to the date expressed in the string.
     *
     * @param dateString a data and time in this format: StockData.dateFormat
     * @return a calendar instance set to the time in the string.
     * @throws ParseException if the string is not in the correct format, we can't tell what
     *                        time it is, and therefore can't make a calender set to that time.
     */
    private Calendar makeCalendarFromString(String dateString) throws ParseException {
        DateFormat format = new SimpleDateFormat(StockData.dateFormat, Locale.ENGLISH);
        Date date = format.parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    @Test
    public void testAddOrUpdateQuote()throws Exception {
        String content = Files.readString(Path.of("stockquote-week5-solved/src/main/resources/xml/stock_info.xml"), StandardCharsets.US_ASCII);
        Stocks stocks = XMLUtils.unmarshall(content, Stocks.class);
        Stock stock = stocks.getStock().get(0);
        DatabaseStockService.addXMLQuote(stock);
        StockQuote stockQuote = databaseStockService.getQuote(stock);
        assertNotNull("Found the quote we added", stockQuote);
    }

}
