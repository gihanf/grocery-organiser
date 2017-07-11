package com.gihan.controller;

import static java.util.Collections.singletonList;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.gihan.Application;
import com.gihan.config.WebConfig;
import com.gihan.dto.ShoppingListRequestDTO;
import com.gihan.model.Product;
import com.gihan.model.ShoppingList;
import com.gihan.model.Store;
import com.gihan.port.GroceriesPort;
import com.gihan.service.product.ShoppingListPrinter;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebConfig.class, Application.class, GroceriesControllerTest.MockConfig.class})
@AutoConfigureMockMvc
public class GroceriesControllerTest {

    private static final String URL_TO_CREATE_SHOPPING_LISTS = "/createShoppingList";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private GroceriesPort mockGroceriesPort;

    @After
    public void tearDown() throws Exception {
        Mockito.reset(mockGroceriesPort);
    }

    @Test
    public void shouldCallGroceriesPort_toGenerateShoppingLists() throws Exception {
        List<String> groceries = singletonList("nut bars");
        ShoppingListRequestDTO dto = new ShoppingListRequestDTO(groceries);

        mockMvc.perform(post(URL_TO_CREATE_SHOPPING_LISTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(dto)))
                .andExpect(status().isOk());

        Mockito.verify(mockGroceriesPort).generateShoppingLists(groceries);
    }

    @Test
    public void shouldPrintMultipleShoppingLists() throws Exception {
        List<String> groceries = Arrays.asList("nut bars", "beans");
        ShoppingListRequestDTO dto = new ShoppingListRequestDTO(groceries);
        List<Product> aldiProducts = singletonList(new Product("nut bars"));
        List<Product> greenGrocerProducts = singletonList(new Product("beans"));

        List<ShoppingList> shoppingLists = Arrays.asList(
                new ShoppingList(Store.ALDI, aldiProducts),
                new ShoppingList(Store.GREEN_GROCER, greenGrocerProducts));

        Mockito.when(mockGroceriesPort.generateShoppingLists(any()))
                .thenReturn(shoppingLists);

        String expectedList = "ALDI\nnut bars\nGREEN_GROCER\nbeans\n";
        mockMvc.perform(post(URL_TO_CREATE_SHOPPING_LISTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedList));
    }

    public static class MockConfig {

        @Primary
        @Bean
        public GroceriesPort groceriesPort() {
            return Mockito.mock(GroceriesPort.class);
        }

        @Primary
        @Bean
        public ShoppingListPrinter shoppingListPrinter() {
            return new ShoppingListPrinter();
        }
    }

}