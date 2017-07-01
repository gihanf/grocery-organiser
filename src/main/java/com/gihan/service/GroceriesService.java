package com.gihan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    @Autowired
    private ProductParser productParser;

    @Override
    public List<ShoppingList> generateShoppingList(Set<Product> groceries) {
        ArrayList<Product> products = new ArrayList<>(groceries);
        Map<Store, List<Product>> productsByStore = products.stream()
                .collect(Collectors.groupingBy(this::findPreferredStore));

        Set<Store> stores = productsByStore.keySet();
        List<ShoppingList> shoppingLists = stores.stream()
                .map(store -> new ShoppingList(store, getListOfProductsSortedByShoppingOrder(productsByStore, store)))
                .collect(Collectors.toList());
        return shoppingLists;
    }

    @Override
    public List<Product> createListOfProducts(List<String> groceryListItems) {
        return groceryListItems.stream().map(productParser::convertToProduct).collect(Collectors.toList());
    }

    private List<Product> getListOfProductsSortedByShoppingOrder(Map<Store, List<Product>> productsByStore, Store store) {
        return storePort.sortProductsInShoppingOrderForStore(productsByStore.get(store), store);
    }

    private Store findPreferredStore(Product product) {
        return storePort.getPreferredStoreForProduct(product);
    }
}
