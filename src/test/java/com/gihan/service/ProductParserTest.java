package com.gihan.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import com.gihan.model.Product;

public class ProductParserTest {

    private ProductParser parser = new ProductParser();

    @Test
    public void shouldParse_singleWordedDescription_withoutQuantity_intoProduct_withoutQuantity() throws Exception {
        String two_worded_description = "blueberries";
        Product product = parser.convertToProduct(two_worded_description);
        assertThat(product.getName(), is("blueberries"));
        assertThat(product.getQuantity(), is(1));
    }

    @Test
    public void shouldParse_multiWordedDescription_withoutQuantity_intoProduct_withoutQuantity() throws Exception {
        String two_worded_description = "freezer bags";
        Product product = parser.convertToProduct(two_worded_description);
        assertThat(product.getName(), is("freezer bags"));
        assertThat(product.getQuantity(), is(1));
    }

    @Test
    public void shouldParse_singleWordedDescription_withQuantity_intoProductWithQuantity() throws Exception {
        String description = "apples x 10";

        Product product = parser.convertToProduct(description);
        assertThat(product.getName(), product.getName(), is("apples"));
        assertThat(product.getQuantity(), is(10));
    }

    @Test
    public void shouldParse_multiWordedDescription_withQuantity_intoProductWithQuantity() throws Exception {
        String description = "canned chick peas x 2";

        Product product = parser.convertToProduct(description);
        assertThat(product.getName(), is("canned chick peas"));
        assertThat(product.getQuantity(), is(2));
    }

    @Test
    public void shouldParse_descriptionWithNumbers_intoProduct() throws Exception {
        String description = "milk 2L";

        Product product = parser.convertToProduct(description);
        assertThat(product.getName(), is("milk 2l"));
        assertThat(product.getQuantity(), is(1));
    }

    @Test
    public void shouldParse_descriptionWithExtraWhitespace_intoProductWithName_thatHasWhitespaceTrimmed() throws Exception {
        String description = " oranges ";

        Product product = parser.convertToProduct(description);
        assertThat(product.getName(), is("oranges"));
    }

    @Test
    public void shouldParse_descriptionWithUpperCaseCharacters_intoProductWithName_thatOnlyHasLowerCaseCharacters() throws Exception {
        String description = "OrAngEs";

        Product product = parser.convertToProduct(description);
        assertThat(product.getName(), is("oranges"));
    }

}