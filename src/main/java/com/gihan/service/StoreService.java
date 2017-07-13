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
import com.gihan.service.product.sorter.IgaProductSorter;
import com.gihan.service.product.sorter.UdayaProductSorter;

@Service
public class StoreService implements StorePort {

    @Value("#{'${aldi.preferred.products}'.split(',')}")
    private List<String> productsPreferablyBoughtAt_Aldi;

    @Value("#{'${green.grocer.preferred.products}'.split(',')}")
    private List<String> productsPreferablyBoughtAt_GreenGrocer;

    @Value("#{'${udaya.preferred.products}'.split(',')}")
    private List<String> productsPreferablyBoughtAt_Udaya;

    @Value("#{'${iga.preferred.products}'.split(',')}")
    private List<String> productsPreferablyBoughtAt_Iga;

    @Autowired
    private AldiProductSorter aldiProductSorter;

    @Autowired
    private GreenGrocerProductSorter greenGrocerProductSorter;

    @Autowired
    private UdayaProductSorter udayaProductSorter;

    @Autowired
    private IgaProductSorter igaProductSorter;

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
        if (isProductInList(product, productsPreferablyBoughtAt_Iga)) {
            return Store.IGA;
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
            case UDAYA:
                products.sort(udayaProductSorter);
                break;
            case IGA:
                products.sort(igaProductSorter);
                break;
            default:
        }
        return products;
    }

    private boolean isProductInList(Product product, List<String> preferredProductsForStore) {
        return preferredProductsForStore.stream()
                .anyMatch(storeSpecificProductName -> {
                    if (matchesProductNameIgnoringCase(product, storeSpecificProductName)){
                        return true;
                    }
                    if (matchesPluralFormOfProduct(product, storeSpecificProductName)) {
                        return true;
                    }
                    if (matchesSingularFormOfProduct(product, storeSpecificProductName)) {
                        return true;
                    }
                    return false;
                });
    }

    private boolean matchesPluralFormOfProduct(Product product, String storeSpecificName) {
        return storeSpecificName.concat("s").equalsIgnoreCase(product.getName());
    }

    private boolean matchesSingularFormOfProduct(Product product, String storeSpecificName) {
        if (storeSpecificName.endsWith("s")) {
            String singularForm = storeSpecificName.substring(0, storeSpecificName.length() - 1);
            return singularForm.equalsIgnoreCase(product.getName());
        }
        return false;
    }

    private boolean matchesProductNameIgnoringCase(Product product, String storeSpecificName) {
        return storeSpecificName.equalsIgnoreCase(product.getName());
    }
}
