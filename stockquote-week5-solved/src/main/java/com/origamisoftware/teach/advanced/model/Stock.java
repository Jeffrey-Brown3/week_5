package com.origamisoftware.teach.advanced.model;

import com.origamisoftware.teach.advanced.model.database.DatabasesAccessObject;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Models the Stock table
 */
@Entity
public class Stock implements DatabasesAccessObject {

    private int id;
    private String name;

    /**
     * Primary Key - Unique ID for a particular row in the stock table.
     *
     * @return an integer value
     */
    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    /**
     * Set the unique ID for a particular row in the stock table.
     * This method should not be called by client code. The value is managed in internally.
     *
     * @param id a unique value.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name column as a String
     */
    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 256)
    public String getName() {
        return name;
    }

    /**
     * Specify the person's name
     *
     * @param name a String value
     */
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        if (id != stock.id) return false;
        if (name != null ? !name.equals(stock.name) : stock.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", name='" + name +  +
                '}';
    }
}
