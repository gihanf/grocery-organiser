package com.gihan.service.product.sorter;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gihan.model.Product;

@Component
public class AldiProductSorter implements Comparator<Product> {

    @Value("#{'${aldi.preferred.products}'.split(',')}")
    private List<String> productsPreferablyBoughtAt_Aldi;

    @Override
    public int compare(Product p1, Product p2) {
        String p1Name = p1.getName().toLowerCase();
        String p2Name = p2.getName().toLowerCase();
        int p1Index = getPositionOfProductInList(p1Name);
        int p2Index = getPositionOfProductInList(p2Name);
        return p1Index - p2Index;
    }

    private int getPositionOfProductInList(String productName) {
        int p1Index = productsPreferablyBoughtAt_Aldi.indexOf(productName);
        if (p1Index == -1) {
            p1Index = productsPreferablyBoughtAt_Aldi.indexOf(productName.concat("s"));
        }
        if ((p1Index == -1) && productName.endsWith("s")) {
            p1Index = productsPreferablyBoughtAt_Aldi.indexOf(productName.substring(0, productName.length() - 1));
        }
        return p1Index;
    }
}