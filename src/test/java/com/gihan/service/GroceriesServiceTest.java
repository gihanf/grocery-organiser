package com.gihan.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.gihan.model.Product;
import com.gihan.port.ProductPort;
import com.gihan.port.ShoppingListPort;

@RunWith(MockitoJUnitRunner.class)
public class GroceriesServiceTest extends GroceryTestBase {

    @InjectMocks
    private GroceriesService groceriesService;

    @Mock
    private ProductPort productPort;

    @Mock
    private ShoppingListPort shoppingListPort;

    @Test
    public void shouldCallProductPort_andShoppingListPort() throws Exception {
        List<String> groceries = Arrays.asList("nut bars", "wheat bix", "chick peas");
        List<Product> products = Arrays.asList(ALDI_1, ALDI_2, ALDI_3);
        when(productPort.createListOfProducts(groceries)).thenReturn(products);

        groceriesService.generateShoppingLists(groceries);

        verify(shoppingListPort).generateSortedShoppingLists(new HashSet<>(products));
    }
}