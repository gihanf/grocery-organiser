package com.gihan;

import static org.hamcrest.MatcherAssert.assertThat;
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

    @Autowired
    private GroceriesPort groceriesPort;

    @Test
    public void shouldReturn_Single_ShoppingList_WhenAllProductsBelongToOneSupermarket() throws Exception {
        HashSet<Product> aldiOnlyGroceries = new HashSet<>(Arrays.asList(new Product("Chick Peas", 1), new Product("Tuna can", 1)));

        List<ShoppingList> shoppingLists = groceriesPort.generateShoppingList(aldiOnlyGroceries);
        assertThat(shoppingLists.size(), is(1));
        ShoppingList shoppingList = shoppingLists.get(0);
        assertThat(shoppingList.getStore(), is(Store.ALDI));
    }

    @Test
    public void shouldReturn_Two_ShoppingLists_WhenProductsBelongToDifferentSupermarkets() {

    }

}
