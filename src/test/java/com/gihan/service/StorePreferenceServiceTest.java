package com.gihan.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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
public class StorePreferenceServiceTest extends GroceryTestBase {

    @Autowired
    private StorePort storePort;

    @Test
    public void shouldReturn_Aldi_asPreferredStoreForProduct() {
        Store preferredStore = storePort.getPreferredStoreForProduct(ALDI_3);
        assertThat(preferredStore, is(Store.ALDI));
    }

    @Test
    public void shouldReturn_GreenGrocer_asPreferredStoreForProduct() {
        Store preferredStore = storePort.getPreferredStoreForProduct(GREEN_GROCER_1);
        assertThat(preferredStore, is(Store.GREEN_GROCER));
    }

    @Test
    public void shouldReturn_Udaya_asPreferredStoreForProduct() throws Exception {
        Store preferredStore = storePort.getPreferredStoreForProduct(UDAYA_1);
        assertThat(preferredStore, is(Store.UDAYA));
    }

    @Test
    public void shouldReturn_Unknown_asPreferredStoreForProduct_WhenProductHasNoPreference() throws Exception {
        Store preferredStore = storePort.getPreferredStoreForProduct(UNKNOWN_1);
        assertThat(preferredStore, is(Store.UNKNOWN));
    }

    @Test
    public void shouldReturn_Aldi_whenProductNameCaseDoesNotMatchAldiProductList() throws Exception {
        Store preferredStore = storePort.getPreferredStoreForProduct(new Product(ALDI_1.getName().toUpperCase(), 1));
        assertThat(preferredStore, is(Store.ALDI));
    }

}