package com.origamisoftware.teach.advanced.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * Java class for stocks xml.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "stock"
})
@XmlRootElement(name = "stocks")
public class Stocks implements XMLDomainObject {

    @XmlElement(name = "stock", required = true)
    protected List<Stock> stock;

    /**
     * Gets the value of the stock property.
     *
     * @return
     *     possible object is
     *     {@link Stock }
     *
     */
    public List<Stock> getStock() {
        if (stock == null) {
            stock = new ArrayList<Stock>();
        }
        return this.stock;
    }

    public void setStock(List<Stock> stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Stocks{" +
                "stock=" + stock +
                '}';
    }

}
