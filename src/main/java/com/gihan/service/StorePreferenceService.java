package com.gihan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gihan.model.Product;
import com.gihan.model.Store;
import com.gihan.port.StorePreferencePort;
import com.gihan.service.productsorter.AldiProductSorter;
import com.gihan.service.productsorter.GreenGrocerProductSorter;

@Service
public class StorePreferenceService implements StorePreferencePort {

    @Value("#{'${aldi.preferred.products}'.split(',')}")
    private List<String> productsPreferablyBoughtAt_Aldi;

    @Value("#{'${udaya.preferred.products}'.split(',')}")
    private List<String> productsPreferablyBoughtAt_Udaya;

    @Autowired
    private AldiProductSorter aldiProductSorter;

    @Autowired
    private GreenGrocerProductSorter greenGrocerProductSorter;

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

        switch(store) {
            case ALDI:
                unsortedProducts.sort(aldiProductSorter);
                break;
            case GREEN_GROCER:
                unsortedProducts.sort(greenGrocerProductSorter);
                break;
        }
//        List<Product> sortedProducts = Collections.emptyList();

        return unsortedProducts;
//        return aldiProductSorter.sortProductsInShoppingOrder(store, unsortedProducts);
    }

    private boolean isProductInList(Product product, List<String> preferredProductsForStore) {
        return preferredProductsForStore.stream()
                .anyMatch(name -> name.equalsIgnoreCase(product.getName()));
    }
}
