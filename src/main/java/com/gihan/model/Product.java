package com.gihan.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Product {

    private final static Set<String> ALDI_PRODUCTS = new HashSet<>(Arrays.asList("Chick Peas", "Tuna Can"));
    private final static Set<String> GREEN_GROCER_PRODUCTS = new HashSet<>(Arrays.asList("Apples", "Bananas"));

    private String name;
    private int quantity;

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Product(String name) {
        this.name = name;
        this.quantity = 1;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Store getPreferredSupermarket() {
        if (ALDI_PRODUCTS.contains(this.getName())) {
            return Store.ALDI;
        }
        if (GREEN_GROCER_PRODUCTS.contains(this.getName())) {
            return Store.GREEN_GROCER;
        }

        return Store.IGA;
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
