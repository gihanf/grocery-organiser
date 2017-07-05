package com.gihan.integration;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gihan.application.Application;
import com.gihan.model.ShoppingList;
import com.gihan.service.GroceriesService;
import com.gihan.service.GroceryTestBase;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class GroceryIntegrationTest extends GroceryTestBase {

    @Autowired
    private GroceriesService groceriesService;

    @Test
    public void shouldReturnShoppingList_fromListOfGroceries() throws Exception {
        List<String> groceries = Arrays.asList("nut bars", "wheat bix");
        List<ShoppingList> shoppingLists = groceriesService.generateShoppingLists(groceries);

        assertThat(shoppingLists.size(), is(1));
    }

    @Test
    public void shouldReturnTwo_ShoppingLists_whenGroceriesAreFromDifferentStores() throws Exception {
        List<String> groceries = Arrays.asList("nut bars", "wheat bix", "beans");
        List<ShoppingList> shoppingLists = groceriesService.generateShoppingLists(groceries);

        assertThat(shoppingLists.size(), is(2));
    }
}
