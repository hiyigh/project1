package main.controller;

import lombok.RequiredArgsConstructor;
import main.model.Item;
import main.service.LayoutService;
import main.service.method.ShoppingMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final ShoppingMethod shoppingMethod;
    private final LayoutService layoutService;
    @GetMapping("/shopList")
    public String shopList(Model model) {
        layoutService.addLayout(model);
        List<Item> items = shoppingMethod.getRecentItems();
        model.addAttribute("items", items);
        return "/shop/shopList";
    }

    @GetMapping("/item")
    public String goToItem(@RequestParam int itemId, Model model) {
        Item item = shoppingMethod.getItem(itemId);
        layoutService.addLayout(model);
        model.addAttribute("item", item);
        return "/shop/itemDetail";
    }
    @GetMapping("/order/{type}")
    public List<Item> orderItems(@PathVariable int type) {
        List<Item> items = new ArrayList<>();
        switch(type) {
            case 1:
                items = shoppingMethod.getItemsByAsc();
                break;
            case 2:
                items = shoppingMethod.getItemsByDesc();
                break;
            case 3:
                items = shoppingMethod.getFamousItems();
                break;
            case 4:
                items = shoppingMethod.getCheapestItems();
                break;
            case 5:
                items = shoppingMethod.getExpensiveItems();
                break;

        }
        return items;
    }
}
