package com.gihan.port;

import com.gihan.model.Product;
import com.gihan.model.Store;

public interface StorePreferencePort {

    Store getPreferredStoreForProduct(Product product);
}
