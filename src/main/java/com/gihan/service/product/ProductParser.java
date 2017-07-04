package com.gihan.service.product;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.gihan.model.Product;

@Component
public class ProductParser {

    /* REGEXP explanation
        ".+?(?= x )" - matches all characters preceding the string " x "
        " x "        - matches the string literally
        "\d*"        - match any digit character multiple times
        "| (.)*"     - alternatively, match any character multiple times (this is valid when we aren't using the quantity specifier)
    */
    private static final String REGEX_PRODUCT = "(.+?(?= x ))( x )(\\d*)|(.)*";
    private static final String QUANTITY_SPECIFIER = " x ";

    public Product convertToProduct(String rawString) {
        String sanitisedString = sanitiseRawString(rawString);
        Pattern p = Pattern.compile(REGEX_PRODUCT);
        Matcher m = p.matcher(sanitisedString);

        m.matches();
        String name;
        int quantity = 1;
        if (m.group(0).contains(QUANTITY_SPECIFIER)) {
            name = m.group(1);
            quantity = new Integer(m.group(3));
        } else {
            name = m.group(0);
        }
        return new Product(name, quantity);
    }

    private String sanitiseRawString(String rawString) {
        rawString = StringUtils.strip(rawString);
        rawString = rawString.toLowerCase();
        return rawString;
    }
}
