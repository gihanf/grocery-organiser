package com.gihan.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gihan.model.Product;
import com.gihan.model.ShoppingList;
import com.gihan.port.GroceriesPort;
import com.gihan.port.ProductPort;
import com.gihan.port.ShoppingListPort;

@Component
public class GroceriesService implements GroceriesPort {

    @Autowired
    private ProductPort productPort;

    @Autowired
    private ShoppingListPort shoppingListPort;

    @Override
    public List<ShoppingList> generateShoppingLists(List<String> groceryListItems) {
        List<Product> listOfProducts = productPort.createListOfProducts(groceryListItems);
        return shoppingListPort.generateSortedShoppingLists(new HashSet<>(listOfProducts));
    }
}
