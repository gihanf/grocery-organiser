package com.gihan.port;

import java.util.List;

import com.gihan.model.Product;
import com.gihan.model.Store;

public interface StorePort {

    Store getPreferredStoreForProduct(Product product);

    List<Product> sortProductsInShoppingOrder(Store store, List<Product> unsortedProducts);
}
