package com.gihan;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gihan.application.Application;
import com.gihan.model.Product;
import com.gihan.model.ShoppingList;
import com.gihan.model.Store;
import com.gihan.port.GroceriesPort;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class GroceriesPortFunctionalTest {

    private final static Product ALDI_1 = new Product("Chick Peas", 1);
    private final static Product ALDI_2 = new Product("Tuna Can", 1);
    private final static Product GREEN_GROCER_1 = new Product("Apples", 1);
    private final static Product UNKNOWN_1 = new Product("Beer", 1);

    @Autowired
    private GroceriesPort groceriesPort;

    @Test
    public void shouldReturn_Single_ShoppingList_WhenAllProductsBelongToOneSupermarket() throws Exception {
        HashSet<Product> aldiOnlyGroceries = new HashSet<>(Arrays.asList(ALDI_1, ALDI_2));

        List<ShoppingList> shoppingLists = groceriesPort.generateShoppingList(aldiOnlyGroceries);
        assertThat(shoppingLists.size(), is(1));
        ShoppingList shoppingList = shoppingLists.get(0);
        assertThat(shoppingList.getStore(), is(Store.ALDI));
    }

    @Test
    public void shouldReturn_Two_ShoppingLists_WhenProductsBelongToDifferentSupermarkets() {
        HashSet<Product> mixedGroceries = new HashSet<>(Arrays.asList(GREEN_GROCER_1, ALDI_1, ALDI_2));

        List<ShoppingList> shoppingLists = groceriesPort.generateShoppingList(mixedGroceries);

        assertThat(shoppingLists.size(), is(2));
        ShoppingList aldiList = shoppingLists.stream().filter(list -> list.getStore().equals(Store.ALDI)).findFirst().get();
        assertThat("list did not contain the expected items", aldiList.getItems(), containsInAnyOrder(ALDI_2, ALDI_1));
        ShoppingList greenGrocerList = shoppingLists.stream().filter(list -> list.getStore().equals(Store.GREEN_GROCER)).findFirst().get();
        assertThat("list did not contain the expected items", greenGrocerList.getItems(), containsInAnyOrder(GREEN_GROCER_1));
    }

    @Test
    public void shouldCreateListWithUnknownStore_WhenThereIsNoPreferenceForProduct() {
        HashSet<Product> mixedGroceries = new HashSet<>(Arrays.asList(ALDI_1, ALDI_2, UNKNOWN_1));

        List<ShoppingList> shoppingLists = groceriesPort.generateShoppingList(mixedGroceries);
        assertThat(shoppingLists.size(), is(2));
        ShoppingList unknownList = shoppingLists.stream().filter(list -> list.getStore().equals(Store.UNKNOWN)).findFirst().get();
        assertThat(unknownList.getItems(), is(singletonList(UNKNOWN_1)));
    }

}
