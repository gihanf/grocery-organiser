package com.gihan.service;

import java.util.*;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import com.gihan.model.Product;
import com.gihan.model.ShoppingList;
import com.gihan.model.Store;
import com.gihan.port.GroceriesPort;

@Service
public class GroceriesService implements GroceriesPort {

    private final static Set<String> ALDI_PRODUCTS = new HashSet<>(Arrays.asList("Chick Peas", "Tuna Can"));

    @Override
    public List<ShoppingList> generateShoppingList(Set<Product> groceries) {
        ArrayList<Product> products = new ArrayList<>(groceries);
        Map<Store, List<Product>> productsByStore = products.stream()
                .collect(Collectors.groupingBy(Product::getPreferredSupermarket));

        Set<Store> stores = productsByStore.keySet();
        List<ShoppingList> shoppingLists = stores.stream()
                .map(store -> new ShoppingList(store, productsByStore.get(store)))
                .collect(Collectors.toList());
//        ShoppingList list = new ShoppingList(Store.ALDI, products);
//        return Collections.singletonList(list);
        return shoppingLists;
    }
}
