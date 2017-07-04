package com.gihan.port;

import java.util.List;

import com.gihan.model.ShoppingList;

public interface GroceriesPort {

    List<ShoppingList> generateShoppingLists(List<String> groceryListItems);
}
