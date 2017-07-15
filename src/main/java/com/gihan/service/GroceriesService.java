package com.gihan.service;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gihan.model.Product;
import com.gihan.model.ShoppingList;
import com.gihan.port.GroceriesPort;
import com.gihan.port.ProductPort;
import com.gihan.port.ShoppingListPort;

@Component
public class GroceriesService implements GroceriesPort {
    private static final Log LOG = LogFactory.getLog(GroceriesService.class);

    @Autowired
    private ProductPort productPort;

    @Autowired
    private ShoppingListPort shoppingListPort;

    @Override
    public List<ShoppingList> generateShoppingLists(List<String> groceryListItems) {
        List<Product> listOfProducts = productPort.createListOfProducts(groceryListItems);
        List<ShoppingList> shoppingLists = shoppingListPort.generateSortedShoppingLists(new HashSet<>(listOfProducts));

        LOG.info("Created the following shopping lists: ");
        LOG.info(shoppingLists);
        return shoppingLists;
    }
}
