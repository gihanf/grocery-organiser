package com.gihan.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Product {

    private String name;
    private int quantity;
    private String variant;

    public Product(String name, int quantity, String variant) {
        this.name = name;
        this.quantity = quantity;
        this.variant = StringUtils.isNotEmpty(variant) ? variant : "";
    }

    public Product(String name) {
        this(name, 1, "");
    }

    public Product(String name, String variant) {
        this(name, 1, variant);
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getVariant() {
        return variant;
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
