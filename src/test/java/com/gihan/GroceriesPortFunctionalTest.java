package com.gihan;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
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
import com.gihan.model.ShoppingList;
import com.gihan.model.Store;
import com.gihan.port.StorePreferencePort;
import com.gihan.service.GroceriesService;

@RunWith(MockitoJUnitRunner.class)
public class GroceriesPortFunctionalTest {
    private final static Product ALDI_1 = new Product("Chick Peas", 1);
    private final static Product ALDI_2 = new Product("Tuna Can", 1);
    private final static Product GREEN_GROCER_1 = new Product("Apples", 1);
    private final static Product UNKNOWN_1 = new Product("Bath Salt", 1);

    @InjectMocks
    private GroceriesService groceriesService;

    @Mock
    private StorePreferencePort storePreferencePort;

    @Test
    public void shouldReturn_Single_ShoppingList_WhenAllProductsBelongToOneSupermarket() throws Exception {
        HashSet<Product> aldiOnlyGroceries = new HashSet<>(Arrays.asList(ALDI_1, ALDI_2));
        when(storePreferencePort.getPreferredStoreForProduct(ALDI_1)).thenReturn(Store.ALDI);
        when(storePreferencePort.getPreferredStoreForProduct(ALDI_2)).thenReturn(Store.ALDI);

        List<ShoppingList> shoppingLists = groceriesService.generateShoppingList(aldiOnlyGroceries);
        assertThat(shoppingLists.size(), is(1));
        ShoppingList shoppingList = shoppingLists.get(0);
        assertThat(shoppingList.getStore(), is(Store.ALDI));
    }

    @Test
    public void shouldReturn_Two_ShoppingLists_WhenProductsBelongToDifferentSupermarkets() {
        HashSet<Product> mixedGroceries = new HashSet<>(Arrays.asList(GREEN_GROCER_1, ALDI_1, ALDI_2));
        when(storePreferencePort.getPreferredStoreForProduct(GREEN_GROCER_1)).thenReturn(Store.GREEN_GROCER);
        when(storePreferencePort.getPreferredStoreForProduct(ALDI_1)).thenReturn(Store.ALDI);
        when(storePreferencePort.getPreferredStoreForProduct(ALDI_2)).thenReturn(Store.ALDI);

        List<ShoppingList> shoppingLists = groceriesService.generateShoppingList(mixedGroceries);

        assertThat(shoppingLists.size(), is(2));
        ShoppingList aldiList = shoppingLists.stream().filter(list -> list.getStore().equals(Store.ALDI)).findFirst().get();
        assertThat("list did not contain the expected items", aldiList.getItems(), containsInAnyOrder(ALDI_2, ALDI_1));
        ShoppingList greenGrocerList = shoppingLists.stream().filter(list -> list.getStore().equals(Store.GREEN_GROCER)).findFirst().get();
        assertThat("list did not contain the expected items", greenGrocerList.getItems(), containsInAnyOrder(GREEN_GROCER_1));
    }

    @Test
    public void shouldCreateListWithUnknownStore_WhenThereIsNoPreferenceForProduct() {
        HashSet<Product> mixedGroceries = new HashSet<>(Arrays.asList(ALDI_1, ALDI_2, UNKNOWN_1));
        when(storePreferencePort.getPreferredStoreForProduct(ALDI_1)).thenReturn(Store.ALDI);
        when(storePreferencePort.getPreferredStoreForProduct(ALDI_2)).thenReturn(Store.ALDI);
        when(storePreferencePort.getPreferredStoreForProduct(UNKNOWN_1)).thenReturn(Store.UNKNOWN);

        List<ShoppingList> shoppingLists = groceriesService.generateShoppingList(mixedGroceries);
        assertThat(shoppingLists.size(), is(2));
        ShoppingList unknownList = shoppingLists.stream().filter(list -> list.getStore().equals(Store.UNKNOWN)).findFirst().get();
        assertThat(unknownList.getItems(), is(singletonList(UNKNOWN_1)));
    }

}
