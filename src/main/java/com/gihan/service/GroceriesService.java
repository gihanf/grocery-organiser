package com.gihan.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gihan.model.Product;
import com.gihan.model.ShoppingList;
import com.gihan.model.Store;
import com.gihan.port.GroceriesPort;
import com.gihan.port.StorePort;

@Service
public class GroceriesService implements GroceriesPort {

    @Autowired
    private StorePort storePort;

    @Override
    public List<ShoppingList> generateShoppingList(Set<Product> groceries) {
        ArrayList<Product> products = new ArrayList<>(groceries);
        Map<Store, List<Product>> productsByStore = products.stream()
                .collect(Collectors.groupingBy(this::findPreferredStore));

        Set<Store> stores = productsByStore.keySet();
        List<ShoppingList> shoppingLists = stores.stream()
                .map(store -> new ShoppingList(store, productsByStore.get(store)))
                .collect(Collectors.toList());
        return shoppingLists;
    }

    @Override
    public Store findPreferredStore(Product product) {
        return storePort.getPreferredStoreForProduct(product);
    }
}
