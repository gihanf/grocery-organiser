package com.gihan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gihan.model.Product;
import com.gihan.model.ShoppingList;
import com.gihan.model.Store;
import com.gihan.port.GroceriesPort;
import com.gihan.port.StorePort;

@Service
public class GroceriesService implements GroceriesPort {

    private static final String REGEX_GROCERY_ITEM = "([\\D]+)(( x )(\\d+))*?";

    @Autowired
    private StorePort storePort;

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
        return groceryListItems.stream().map(this::convertToProduct).collect(Collectors.toList());
    }

    private List<Product> getListOfProductsSortedByShoppingOrder(Map<Store, List<Product>> productsByStore, Store store) {
        return storePort.sortProductsInShoppingOrderForStore(productsByStore.get(store), store);
    }

    private Store findPreferredStore(Product product) {
        return storePort.getPreferredStoreForProduct(product);
    }

    private Product convertToProduct(String grocery) {
        Pattern groceryFormat = Pattern.compile(REGEX_GROCERY_ITEM);
        Matcher m = groceryFormat.matcher(grocery);
        m.matches();
        String productName = m.group(1);
        String quantity = m.group(4);
        if (StringUtils.isNotEmpty(quantity)) {
            return new Product(productName, Integer.valueOf(quantity));
        }
        return new Product(productName);
    }
}
