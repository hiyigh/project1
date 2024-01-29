package main.controller;

import lombok.RequiredArgsConstructor;
import main.dto.ItemDto;
import main.model.Item;
import main.service.LayoutService;
import main.service.method.ShoppingMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
    @GetMapping("/order")
    public List<Item> orderItems(@RequestParam int orderId) {

        List<Item> items = new ArrayList<>();
        switch(orderId) {
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
    @GetMapping("/add")
    public String addItem() {
        return"/shop/itemAdd";
    }
    @PostMapping("/add")
    public String addItem(@RequestPart("imgUrl") MultipartFile imgUrl,
                          @RequestPart("itemName") String itemName,
                          @RequestPart("price") String price,
                          @RequestPart("inventoryCont") String inventoryCont,
                          @RequestPart("detail") String detail) throws IOException {
        String uploadDirectory = "src/main/resources/static/img/";
        String srcUrl = "img/";

        String url = imgUrl.getOriginalFilename();
        Path imgPath = Path.of(uploadDirectory, url);

        String loadUrl = srcUrl + url;
        Files.copy(imgUrl.getInputStream(), imgPath, StandardCopyOption.REPLACE_EXISTING);

        ItemDto itemDto = new ItemDto();

        itemDto.setImgUrl(loadUrl);
        itemDto.setItemName(itemName);
        itemDto.setPrice(Integer.parseInt(price));
        itemDto.setDetail(detail);
        itemDto.setInventoryCount(Integer.parseInt(inventoryCont));
        shoppingMethod.addItem(itemDto);
        return "redirect:/shop/shopList";
    }
    @GetMapping("/delete")
    public String deleteItem(@RequestParam int itemId) {
        shoppingMethod.deleteItem(itemId);
        return"redirect:/shop/shopList";
    }
}
