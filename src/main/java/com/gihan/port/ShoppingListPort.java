package com.gihan.port;

import java.util.List;
import java.util.Set;

import com.gihan.model.Product;
import com.gihan.model.ShoppingList;

public interface ShoppingListPort {

    List<ShoppingList> generateSortedShoppingLists(Set<Product> groceries);
}
