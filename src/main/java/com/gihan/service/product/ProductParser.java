package com.gihan.service.product;

import static java.util.regex.Pattern.compile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gihan.model.Product;

@Component
public class ProductParser {

    /* REGEXP explanation
        Product format: <word>[ x <quantity>]
        ".+?(?= x )" - matches all characters preceding the string " x "
        " x "        - matches the string literally
        "\d*"        - match any digit character multiple times
        "| (.)*"     - alternatively, match any character multiple times (this is valid when we aren't using the quantity specifier)
    */
    private static final String REGEX_PRODUCT_WITHOUT_VARIANT = "(.+?(?= x ))( x )(\\d*)|(.)*";
    private static final String REGEX_PRODUCT_WITH_VARIANT = "(.*?)(\\(.*\\))( x )?(\\d+)?";
    private static final String REGEX_VARIANT = ".+\\(.+\\)(.+)?";
    private static final String QUANTITY_SPECIFIER = " x ";

    public Product convertToProduct(String rawString) {
        String sanitisedString = sanitiseRawString(rawString);
        Pattern productWithVariant = compile(REGEX_VARIANT);
        Matcher m = productWithVariant.matcher(sanitisedString);

        if (m.matches()) {
            return convertToProductWithVariant(sanitisedString);
        } else {
            return convertToProductWithoutVariant(sanitisedString);
        }
    }

    private Product convertToProductWithVariant(String rawString) {
        Matcher m = compile(REGEX_PRODUCT_WITH_VARIANT).matcher(rawString);
        m.matches();

        String name = m.group(1).trim();
        Integer quantity = 1;
        if (StringUtils.isNotEmpty(m.group(4))) {
            quantity = Integer.valueOf(m.group(4));
        }
        String variant = m.group(2);
        return new Product(name, quantity, variant);
    }

    private Product convertToProductWithoutVariant(String rawString) {
        Matcher m = compile(REGEX_PRODUCT_WITHOUT_VARIANT).matcher(rawString);
        m.matches();

        String name;
        int quantity = 1;
        if (m.group(0).contains(QUANTITY_SPECIFIER)) {
            name = m.group(1);
            quantity = new Integer(m.group(3));
        } else {
            name = m.group(0);
        }
        return new Product(name, quantity, null);
    }

    private String sanitiseRawString(String rawString) {
        rawString = StringUtils.strip(rawString);
        rawString = rawString.toLowerCase();
        return rawString;
    }
}
