package com.gihan.port;

import com.gihan.model.Product;
import com.gihan.model.ShoppingList;

import java.util.List;
import java.util.Set;

public interface GroceriesPort {

    List<ShoppingList> generateShoppingList(Set<Product> groceries);
}
