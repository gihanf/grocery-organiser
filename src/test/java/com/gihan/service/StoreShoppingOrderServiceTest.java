package com.gihan.service;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gihan.application.Application;
import com.gihan.model.Product;
import com.gihan.model.Store;
import com.gihan.port.StorePort;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StoreShoppingOrderServiceTest extends GroceryTestBase {

    @Autowired
    private StorePort storePort;

    @Test
    public void shouldReturn_SameNumberOfProductsWhenListIsSorted() throws Exception {
        List<Product> sortedProducts = storePort.sortProductsInShoppingOrderForStore(asList(ALDI_1, ALDI_2, ALDI_3), Store.ALDI);
        assertThat(sortedProducts.size(), is(3));
    }

    @Test
    public void shouldReturn_UnmodifiedListOfProducts_WhenStoreIsUnknown() throws Exception {
        List<Product> unsortedProducts = asList(UNKNOWN_4, UNKNOWN_1, UNKNOWN_3, UNKNOWN_2);
        List<Product> sortedProducts = storePort.sortProductsInShoppingOrderForStore(unsortedProducts, Store.UNKNOWN);
        assertThat(sortedProducts.equals(unsortedProducts), is(true));
    }

    @Test
    public void shouldReturnProductsSorted_InTheirLogicalPurchaseOrder_for_ALDI() throws Exception {
        List<Product> sortedProducts = storePort.sortProductsInShoppingOrderForStore(asList(ALDI_4, ALDI_1, ALDI_3, ALDI_2), Store.ALDI);

        List<Product> expectedSortedProducts = asList(ALDI_1, ALDI_2, ALDI_3, ALDI_4);
        assertThat("products were not in their expected order", sortedProducts.equals(expectedSortedProducts), is(true));
    }

    @Test
    public void shouldReturnProductsSorted_InTheirLogicalPurchaseOrder_for_GreenGrocer() throws Exception {
        List<Product> sortedProducts = storePort.sortProductsInShoppingOrderForStore(asList(
                GREEN_GROCER_4,
                GREEN_GROCER_1,
                GREEN_GROCER_3,
                GREEN_GROCER_2),
                Store.GREEN_GROCER);

        List<Product> expectedSortedProducts = asList(GREEN_GROCER_1, GREEN_GROCER_2, GREEN_GROCER_3, GREEN_GROCER_4);
        assertThat("products were not in their expected order", sortedProducts.equals(expectedSortedProducts), is(true));
    }
}
