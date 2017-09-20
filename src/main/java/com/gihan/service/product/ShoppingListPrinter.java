package com.gihan.service.product;

import static java.util.stream.Collectors.joining;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gihan.model.Product;
import com.gihan.model.ShoppingList;

@Component
public class ShoppingListPrinter {

    private static final String DELIMITER = "\n";

    public String print(ShoppingList shoppingList) {
        List<Product> items = shoppingList.getItems();

        String formattedProducts = items.stream()
                .map(Product::toString)
                .collect(joining());

        return shoppingList.getStore().getDisplayName() + DELIMITER + formattedProducts;
    }
}
