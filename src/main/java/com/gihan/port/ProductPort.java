package com.gihan.port;

import java.util.List;

import com.gihan.model.Product;

public interface ProductPort {

    List<Product> createListOfProducts(List<String> groceryListItems);
}
