package com.gihan;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
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
import com.gihan.port.StorePort;
import com.gihan.service.GroceriesService;
import com.gihan.service.GroceryTestBase;

@RunWith(MockitoJUnitRunner.class)
public class GroceriesPortFunctionalTest extends GroceryTestBase {

    @InjectMocks
    private GroceriesService groceriesService;

    @Mock
    private StorePort storePort;

    @Test
    public void shouldReturn_Single_ShoppingList_WhenAllProductsBelongToOneSupermarket() throws Exception {
        HashSet<Product> aldiOnlyGroceries = new HashSet<>(Arrays.asList(ALDI_2, ALDI_1));
        List<Product> expectedAldiProductsInOrder = Arrays.asList(ALDI_1, ALDI_2);
        when(storePort.getPreferredStoreForProduct(ALDI_1)).thenReturn(Store.ALDI);
        when(storePort.getPreferredStoreForProduct(ALDI_2)).thenReturn(Store.ALDI);
        when(storePort.sortProductsInShoppingOrderForStore(any(), any())).thenReturn(Arrays.asList(ALDI_1, ALDI_2));

        List<ShoppingList> shoppingLists = groceriesService.generateShoppingList(aldiOnlyGroceries);
        assertThat(shoppingLists.size(), is(1));
        ShoppingList shoppingList = shoppingLists.get(0);
        assertThat(shoppingList.getStore(), is(Store.ALDI));
        assertThat(shoppingList.getItems().equals(expectedAldiProductsInOrder), is(true));
    }

    @Test
    public void shouldReturn_Two_ShoppingLists_WhenProductsBelongToDifferentSupermarkets() {
        HashSet<Product> mixedGroceries = new HashSet<>(Arrays.asList(GREEN_GROCER_1, ALDI_1, ALDI_2));
        when(storePort.getPreferredStoreForProduct(GREEN_GROCER_1)).thenReturn(Store.GREEN_GROCER);
        when(storePort.getPreferredStoreForProduct(ALDI_1)).thenReturn(Store.ALDI);
        when(storePort.getPreferredStoreForProduct(ALDI_2)).thenReturn(Store.ALDI);
        when(storePort.sortProductsInShoppingOrderForStore(any(), eq(Store.ALDI))).thenReturn(Arrays.asList(ALDI_1, ALDI_2));
        when(storePort.sortProductsInShoppingOrderForStore(any(), eq(Store.GREEN_GROCER))).thenReturn(Collections.singletonList(GREEN_GROCER_1));

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
        when(storePort.getPreferredStoreForProduct(ALDI_1)).thenReturn(Store.ALDI);
        when(storePort.getPreferredStoreForProduct(ALDI_2)).thenReturn(Store.ALDI);
        when(storePort.getPreferredStoreForProduct(UNKNOWN_1)).thenReturn(Store.UNKNOWN);
        when(storePort.sortProductsInShoppingOrderForStore(any(), eq(Store.ALDI))).thenReturn(Arrays.asList(ALDI_1, ALDI_2));
        when(storePort.sortProductsInShoppingOrderForStore(any(), eq(Store.UNKNOWN))).thenReturn(Collections.singletonList(UNKNOWN_1));

        List<ShoppingList> shoppingLists = groceriesService.generateShoppingList(mixedGroceries);
        assertThat(shoppingLists.size(), is(2));
        ShoppingList unknownList = shoppingLists.stream().filter(list -> list.getStore().equals(Store.UNKNOWN)).findFirst().get();
        assertThat(unknownList.getItems(), is(singletonList(UNKNOWN_1)));
    }

}
