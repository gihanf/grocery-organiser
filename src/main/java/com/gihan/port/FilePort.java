package com.gihan.port;

import java.nio.file.Path;
import java.util.List;

import com.gihan.model.Product;

public interface FilePort {

    List<Product> createListOfProducts(Path listFilePath);
}
