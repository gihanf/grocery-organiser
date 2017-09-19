package com.gihan.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gihan.model.Product;
import com.gihan.port.ProductPort;
import com.gihan.service.product.ProductParser;

@Component
public class ProductService implements ProductPort {

    @Autowired
    private ProductParser productParser;

    @Override
    public List<Product> createListOfProducts(List<String> groceryListItems) {
        return groceryListItems.stream()
                .map(productParser::convertToProduct)
                .collect(Collectors.toList());
    }
}
