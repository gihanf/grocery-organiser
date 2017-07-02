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
import com.gihan.port.FilePort;
import com.gihan.port.GroceriesPort;
import com.gihan.service.product.ProductParser;

@Component
public class GroceryFileParser implements FilePort {

    private static final Log LOG = LogFactory.getLog(GroceryFileParser.class);

    @Autowired
    private ProductParser productParser;

    @Autowired
    private GroceriesPort groceriesPort;

    @Override
    public List<Product> createListOfProducts(Path listFilePath) {
        try {
            List<String> lines = Files.readAllLines(listFilePath);
            List<Product> products = lines.stream().map(productParser::convertToProduct).collect(Collectors.toList());
            groceriesPort.generateShoppingList(new HashSet<>(products));
            //TODO: Should print to log
            return products;
        } catch (IOException e) {
            LOG.error(String.format("Error occurred while trying to read from file: %s", listFilePath.getFileName()));
        }
        return Lists.emptyList();
    }
}
