package com.gihan.service;

import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.gihan.port.ShoppingListPort;
import com.gihan.service.product.ProductParser;
import com.gihan.service.product.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductParser productParser;

    @Mock
    private ShoppingListPort shoppingListPort;

    @Test
    public void shouldTryToParseEachStringFromList_intoAProduct() throws Exception {
        List<String> groceries = Arrays.asList("freezer bags", "coconut milk x 2", "blueberries");

        productService.createListOfProducts(groceries);
        verify(productParser).convertToProduct("freezer bags");
        verify(productParser).convertToProduct("coconut milk x 2");
        verify(productParser).convertToProduct("blueberries");
    }

    // test to log output of shopping lists
}