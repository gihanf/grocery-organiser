package com.gihan.dto;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ShoppingListRequestDTO {

    private List<String> groceries;

    public ShoppingListRequestDTO() {
    }

    public ShoppingListRequestDTO(List<String> groceries) {
        this.groceries = groceries;
    }

    public List<String> getGroceries() {
        return groceries;
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
