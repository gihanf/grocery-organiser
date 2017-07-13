package com.gihan.service.product.sorter;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gihan.model.Product;

@Component
public class IgaProductSorter implements Comparator<Product> {

    @Value("#{'${iga.preferred.products}'.split(',')}")
    private List<String> productsPreferablyBoughtAt_Iga;

    @Override
    public int compare(Product p1, Product p2) {
        String p1Name = p1.getName().toLowerCase();
        String p2Name = p2.getName().toLowerCase();
        int p1Index = productsPreferablyBoughtAt_Iga.indexOf(p1Name);
        int p2Index = productsPreferablyBoughtAt_Iga.indexOf(p2Name);
        return p1Index - p2Index;
    }
}