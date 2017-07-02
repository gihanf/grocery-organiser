package com.gihan.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.gihan.model.Product;
import com.gihan.port.GroceriesPort;
import com.gihan.service.product.ProductParser;

@RunWith(MockitoJUnitRunner.class)
public class GroceryFileParserTest {
    private static final Path TEST_INPUT_FILE_PATH = Paths.get("./src/test/resources/sample-raw-shopping-list.txt");

    @InjectMocks
    private GroceryFileParser groceryFileParser;

    @Mock
    private ProductParser productParser;

    @Mock
    private GroceriesPort groceriesPort;

    @Test
    public void shouldTryToParseEachStringFromFile_intoAProduct() throws Exception {
        groceryFileParser.createListOfProducts(TEST_INPUT_FILE_PATH);

        verify(productParser).convertToProduct("freezer bags");
        verify(productParser).convertToProduct("coconut milk x 2");
        verify(productParser).convertToProduct("blueberries");
    }

    // test to check a message is logged when there is an IOException

    @Test
    public void shouldTryToGenerateAShoppingList_fromParsedProducts() throws Exception {
        Product freezerBags = new Product("freezer bags");
        Product coconutMilk = new Product("coconut milk", 2);
        Product blueberries = new Product("blueberries");
        when(productParser.convertToProduct("freezer bags")).thenReturn(freezerBags);
        when(productParser.convertToProduct("coconut milk x 2")).thenReturn(coconutMilk);
        when(productParser.convertToProduct("blueberries")).thenReturn(blueberries);

        List<Product> products = Arrays.asList(freezerBags, blueberries, coconutMilk);

        groceryFileParser.createListOfProducts(TEST_INPUT_FILE_PATH);

        HashSet<Product> expectedGroceries = new HashSet<>(products);
        verify(groceriesPort).generateShoppingList(expectedGroceries);
    }

    // test to log output of shopping lists

}