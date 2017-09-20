package com.gihan.service;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.gihan.model.Product;
import com.gihan.model.ShoppingList;
import com.gihan.model.Store;

public class ShoppingListPrinterTest extends GroceryTestBase {

    private ShoppingListPrinter printer = new ShoppingListPrinter();
    private ShoppingList shoppingList;

    @Test
    public void shouldPrintStoreDisplayNameAsFirstLine() throws Exception {
        List<Product> products = singletonList(GREEN_GROCER_1);

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
    public void shoppingListItemsShouldInclude_variant_ifThereIsOne() throws Exception {
        List<Product> products = singletonList(new Product("chick peas", 2, "(canned)"));

        shoppingList = new ShoppingList(Store.ALDI, products);
        String[] lines = printer.print(shoppingList).split("\\n");

        assertThat(lines[1], is("chick peas (canned) x 2"));
    }

    @Test
    public void printedShoppingListShouldEndWithANewline() throws Exception {
        List<Product> products = singletonList(ALDI_1);

        shoppingList = new ShoppingList(Store.ALDI, products);

        String output = printer.print(shoppingList);
        assertThat(output.endsWith("\n"), is(true));
    }
}