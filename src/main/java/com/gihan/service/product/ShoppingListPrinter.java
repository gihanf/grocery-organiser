package com.gihan.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gihan.model.Product;
import com.gihan.model.ShoppingList;

@Component
public class ShoppingListPrinter {

    private static final String QUANTITY_SPECIFIER = " x ";
    private static final String DELIMITER = "\n";

    public String print(ShoppingList shoppingList) {
        List<Product> items = shoppingList.getItems();

        String formattedProducts = items.stream().map(product -> {
            StringBuilder builder = new StringBuilder();
            builder.append(product.getName());
            if (product.getQuantity() > 1) {
                builder.append(QUANTITY_SPECIFIER).append(product.getQuantity());
            }
            builder.append(DELIMITER);
            return builder.toString();
        }).collect(Collectors.joining());

        return shoppingList.getStore().toString() + DELIMITER + formattedProducts;
    }
}
