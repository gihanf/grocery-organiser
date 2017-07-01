package com.gihan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gihan.model.Product;
import com.gihan.model.Store;
import com.gihan.port.StorePort;
import com.gihan.service.product.sorter.AldiProductSorter;
import com.gihan.service.product.sorter.GreenGrocerProductSorter;

@Service
public class StoreService implements StorePort {

    @Value("#{'${aldi.preferred.products}'.split(',')}")
    private List<String> productsPreferablyBoughtAt_Aldi;

    @Value("#{'${green.grocer.preferred.products}'.split(',')}")
    private List<String> productsPreferablyBoughtAt_GreenGrocer;

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
        if (isProductInList(product, productsPreferablyBoughtAt_GreenGrocer)) {
            return Store.GREEN_GROCER;
        }
        if (isProductInList(product, productsPreferablyBoughtAt_Udaya)) {
            return Store.UDAYA;
        }

        return Store.UNKNOWN;
    }

    @Override
    public List<Product> sortProductsInShoppingOrderForStore(List<Product> products, Store store) {
        switch (store) {
            case ALDI:
                products.sort(aldiProductSorter);
                break;
            case GREEN_GROCER:
                products.sort(greenGrocerProductSorter);
                break;
            default:
        }
        return products;
    }

    private boolean isProductInList(Product product, List<String> preferredProductsForStore) {
        return preferredProductsForStore.stream()
                .anyMatch(name -> name.equalsIgnoreCase(product.getName()));
    }
}
