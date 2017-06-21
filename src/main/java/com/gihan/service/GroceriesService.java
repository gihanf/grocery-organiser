package com.gihan.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.gihan.model.Product;
import com.gihan.model.ShoppingList;
import com.gihan.model.Store;
import com.gihan.port.GroceriesPort;

@Service
public class GroceriesService implements GroceriesPort{

    @Override
    public List<ShoppingList> generateShoppingList(Set<Product> groceries) {
        ArrayList<Product> products = new ArrayList<>(groceries);
        ShoppingList list = new ShoppingList(Store.ALDI, products);
        return Collections.singletonList(list);
    }
}
