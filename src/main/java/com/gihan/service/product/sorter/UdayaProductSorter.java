package com.gihan.service.product.sorter;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gihan.model.Product;

@Component
public class UdayaProductSorter implements Comparator<Product> {

    @Value("#{'${udaya.preferred.products}'.split(',')}")
    private List<String> productsPreferablyBoughtAt_Udaya;

    @Override
    public int compare(Product p1, Product p2) {
        String p1Name = p1.getName().toLowerCase();
        String p2Name = p2.getName().toLowerCase();
        int p1Index = productsPreferablyBoughtAt_Udaya.indexOf(p1Name);
        int p2Index = productsPreferablyBoughtAt_Udaya.indexOf(p2Name);
        return p1Index - p2Index;
    }
}