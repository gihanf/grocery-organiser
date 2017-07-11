package com.gihan.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gihan.dto.ShoppingListRequestDTO;
import com.gihan.model.ShoppingList;
import com.gihan.port.GroceriesPort;
import com.gihan.service.product.ShoppingListPrinter;

@Controller
public class GroceriesController {

    @Autowired
    private GroceriesPort groceriesPort;

    @Autowired
    private ShoppingListPrinter shoppingListPrinter;

    @RequestMapping(method = RequestMethod.POST, value = "/createShoppingList")
    @ResponseBody
    public String createShoppingList(@RequestBody ShoppingListRequestDTO dto) {
        List<ShoppingList> shoppingLists = groceriesPort.generateShoppingLists(dto.getGroceries());
        String lists = shoppingLists.stream().map(shoppingListPrinter::print).collect(Collectors.joining());
        return lists;
    }

}
