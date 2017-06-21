package com.gihan.port;

import java.util.List;
import java.util.Set;

import com.gihan.model.Product;
import com.gihan.model.ShoppingList;

public interface GroceriesPort {

    List<ShoppingList> generateShoppingList(Set<Product> groceries);
}
