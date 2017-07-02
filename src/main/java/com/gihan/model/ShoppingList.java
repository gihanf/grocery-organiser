package com.gihan.model;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ShoppingList {

    private Store store;
    private List<Product> items;

    public ShoppingList(Store store, List<Product> items) {
        this.items = items;
        this.store = store;
    }

    public Store getStore() {
        return store;
    }

    public List<Product> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
