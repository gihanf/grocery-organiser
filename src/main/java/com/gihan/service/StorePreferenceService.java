package com.gihan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gihan.model.Product;
import com.gihan.model.Store;

@Service
public class StorePreferenceService {

    @Value("#{'${aldi.preferred.products}'.split(',')}")
    private List<String> productsPreferablyBoughtAt_Aldi;

    @Value("#{'${udaya.preferred.products}'.split(',')}")
    private List<String> productsPreferablyBoughtAt_Udaya;

    public Store determinePreferredStoreForProduct(Product product) {
        if (isProductInList(product, productsPreferablyBoughtAt_Aldi)) {
            return Store.ALDI;
        }
        if (isProductInList(product, productsPreferablyBoughtAt_Udaya)) {
            return Store.UDAYA;
        }

        return Store.UNKNOWN;
    }

    private boolean isProductInList(Product product, List<String> preferredProductsForStore) {
        return preferredProductsForStore.stream()
                .anyMatch(name -> name.equalsIgnoreCase(product.getName()));
    }
}
