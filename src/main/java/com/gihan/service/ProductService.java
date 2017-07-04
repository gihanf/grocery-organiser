package com.gihan.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gihan.model.Product;
import com.gihan.port.ProductPort;
import com.gihan.port.ShoppingListPort;
import com.gihan.service.product.ProductParser;

@Component
public class    ProductService implements ProductPort {

    private static final Log LOG = LogFactory.getLog(ProductService.class);

    @Autowired
    private ProductParser productParser;

    @Autowired
    private ShoppingListPort shoppingListPort;

    @Override
    public List<Product> createListOfProducts(Path listFilePath) {
        try {
            List<String> lines = Files.readAllLines(listFilePath);
            List<Product> products = lines.stream().map(productParser::convertToProduct).collect(Collectors.toList());
            shoppingListPort.generateShoppingList(new HashSet<>(products));
            //TODO: Should print to log
            return products;
        } catch (IOException e) {
            LOG.error(String.format("Error occurred while trying to read from file: %s", listFilePath.getFileName()));
        }
        return Lists.emptyList();
    }

    @Override
    public List<Product> createListOfProducts(List<String> groceryListItems) {
        return null;
    }
}
