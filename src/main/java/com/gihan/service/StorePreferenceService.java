package com.gihan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gihan.model.Product;
import com.gihan.model.Store;
import com.gihan.port.StorePreferencePort;

@Service
public class StorePreferenceService implements StorePreferencePort {

    @Value("#{'${aldi.preferred.products}'.split(',')}")
    private List<String> productsPreferablyBoughtAt_Aldi;

    @Value("#{'${udaya.preferred.products}'.split(',')}")
    private List<String> productsPreferablyBoughtAt_Udaya;

    @Autowired
    private AldiProductSorter aldiProductSorter;

    @Override
    public Store getPreferredStoreForProduct(Product product) {
        if (isProductInList(product, productsPreferablyBoughtAt_Aldi)) {
            return Store.ALDI;
        }
        if (isProductInList(product, productsPreferablyBoughtAt_Udaya)) {
            return Store.UDAYA;
        }

        return Store.UNKNOWN;
    }

    @Override
    public List<Product> sortProductsInShoppingOrder(Store store, List<Product> unsortedProducts) {

        //TODO: Think about using a comparator to implement different sorting mechanisms
//        switch(store) {
//            case ALDI:
//                allStoreProductNames = new ArrayList<>(productsPreferablyBoughtAt_Aldi);
//                sortedProducts = allStoreProductNames.stream()
//                        .filter(a -> unsortedProducts.stream()
//                                .anyMatch(u -> u.getName().equals(a)))
//                        .collect(Collectors.toList());
//        }
//        List<Product> sortedProducts = Collections.emptyList();
        unsortedProducts.sort(aldiProductSorter);
        return unsortedProducts;
//        return aldiProductSorter.sortProductsInShoppingOrder(store, unsortedProducts);
    }

    private boolean isProductInList(Product product, List<String> preferredProductsForStore) {
        return preferredProductsForStore.stream()
                .anyMatch(name -> name.equalsIgnoreCase(product.getName()));
    }
}
