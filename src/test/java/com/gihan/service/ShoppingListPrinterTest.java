package com.gihan.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.gihan.model.Product;
import com.gihan.model.ShoppingList;
import com.gihan.model.Store;
import com.gihan.service.product.ShoppingListPrinter;

public class ShoppingListPrinterTest extends GroceryTestBase{

    private ShoppingListPrinter printer = new ShoppingListPrinter();
    private ShoppingList shoppingList;

    @Test
    public void shouldPrintStoreDisplayNameAsFirstLine() throws Exception {
        List<Product> products = Collections.singletonList(GREEN_GROCER_1);

        shoppingList = new ShoppingList(Store.GREEN_GROCER, products);

        String[] lines = printer.print(shoppingList).split("\\n");
        assertThat(lines[0], is("GREEN GROCER"));
    }

    @Test
    public void shoppingListItemsShouldIncludeQuantity_whenQuantityIsGreaterThanOne() throws Exception {
        List<Product> products = Arrays.asList(
                new Product("nut bars", 2, null),
                new Product("cucumbers", 1, null)
        );

        shoppingList = new ShoppingList(Store.ALDI, products);
        String[] lines = printer.print(shoppingList).split("\\n");

        assertThat(lines[1], is("nut bars x 2"));
        assertThat(lines[2], is("cucumbers"));
    }

    @Test
    public void printedShoppingListShouldEndWithANewline() throws Exception {
        List<Product> products = Collections.singletonList(ALDI_1);

        shoppingList = new ShoppingList(Store.ALDI, products);

        String output = printer.print(shoppingList);
        assertThat(output.endsWith("\n"), is(true));
    }
}