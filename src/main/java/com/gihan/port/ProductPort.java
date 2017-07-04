package com.gihan.port;

import java.nio.file.Path;
import java.util.List;

import com.gihan.model.Product;

public interface ProductPort {

    List<Product> createListOfProducts(Path listFilePath);

    List<Product> createListOfProducts(List<String> groceryListItems);
}
