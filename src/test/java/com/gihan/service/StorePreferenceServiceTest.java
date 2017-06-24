package com.gihan.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gihan.application.Application;
import com.gihan.model.Product;
import com.gihan.model.Store;
import com.gihan.port.StorePreferencePort;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class StorePreferenceServiceTest {

    private final static Product ALDI_1 = new Product("nut bars", 1);
    private final static Product ALDI_2 = new Product("wheat bix", 1);
    private final static Product ALDI_3 = new Product("chick peas", 1);
    private final static Product ALDI_4 = new Product("pasta", 1);
    private final static Product GREEN_GROCER_1 = new Product("Apples", 1);
    private final static Product UNKNOWN_1 = new Product("Bath Salt", 1);
    private static final Product UDAYA_1 = new Product("Tapioca Chips", 2);

    @Autowired
    private StorePreferencePort storePreferencePort;

    @Test
    public void shouldReturn_Aldi_asPreferredStoreForProduct() {
        Store preferredStore = storePreferencePort.getPreferredStoreForProduct(ALDI_3);
        assertThat(preferredStore, is(Store.ALDI));
    }

    @Test
    public void shouldReturn_GreenGrocer_asPreferredStoreForProduct() throws Exception {
        Store preferredStore = storePreferencePort.getPreferredStoreForProduct(UDAYA_1);
        assertThat(preferredStore, is(Store.UDAYA));
    }

    @Test
    public void shouldReturn_Unknown_asPreferredStoreForProduct_WhenProductHasNoPreference() throws Exception {
        Store preferredStore = storePreferencePort.getPreferredStoreForProduct(UNKNOWN_1);
        assertThat(preferredStore, is(Store.UNKNOWN));
    }

    @Test
    public void shouldReturn_Aldi_whenProductNameCaseDoesNotMatchAldiProductList() throws Exception {
        Store preferredStore = storePreferencePort.getPreferredStoreForProduct(new Product(ALDI_1.getName().toUpperCase(), 1));
        assertThat(preferredStore, is(Store.ALDI));
    }

    @Test
    public void shouldReturn_SameNumberOfProductsWhenListIsSorted() throws Exception {
        List<Product> sortedProducts = storePreferencePort.sortProductsInShoppingOrder(Store.ALDI, Arrays.asList(ALDI_1, ALDI_2, ALDI_3));
        assertThat(sortedProducts.size(), is(3));
    }

    @Test
    public void shouldReturnProductsSorted_InTheirLogicalPurchaseOrder() throws Exception {
        List<Product> sortedProducts = storePreferencePort.sortProductsInShoppingOrder(Store.ALDI, Arrays.asList(ALDI_4, ALDI_1, ALDI_3, ALDI_2));

        List<Product> expectedSortedProducts = Arrays.asList(ALDI_1, ALDI_2, ALDI_3, ALDI_4);
        assertThat("products were not in their expected order", sortedProducts.equals(expectedSortedProducts), is(true));
    }


}