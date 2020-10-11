package com.origamisoftware.teach.advanced.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StockTest {

    final static String stockName = "GOOG";

    /**
     * Testing helper method for generating stock test data
     *
     * @return a stock object that uses static constants for data.
     */
    public static Stock createStock() {
        Stock stock = new Stock();
        stock.setName(stockName);
        return stock;
    }

    @Test
    public void testStockSettersAndGetters() {
        Stock stock = createStock();
        int id = 10;
        stock.setId(id);
        assertEquals("Name", stockName, stock.getName());
        assertEquals("id", id, stock.getId());

    }

}
