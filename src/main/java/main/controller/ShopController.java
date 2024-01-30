package main.controller;

import lombok.RequiredArgsConstructor;
import main.dto.ItemDto;
import main.dto.PrincipalDetails;
import main.model.Item;
import main.model.User;
import main.service.LayoutService;
import main.service.method.ShoppingMethod;
import main.service.method.UserMethod;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {
    private final ShoppingMethod shoppingMethod;
    private final LayoutService layoutService;
    private final UserMethod userMethod;
    @GetMapping("/shopList")
    public String shopList(Model model, Authentication authentication) {
        layoutService.addLayout(model, authentication);
        List<Item> items = shoppingMethod.getRecentItems();
        model.addAttribute("items", items);
        return "/shop/shopList";
    }

    @GetMapping("/item")
    public String goToItem(@RequestParam int itemId, Model model,Authentication authentication) {
        Item item = shoppingMethod.getItem(itemId);
        layoutService.addLayout(model, authentication);
        model.addAttribute("item", item);
        return "/shop/itemDetail";
    }
    @GetMapping("/order")
    @ResponseBody
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
    @GetMapping("/basket")
    public String goToBasket(Model model, Authentication authentication) {
        layoutService.addLayout(model, authentication);
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = userMethod.getUserByEmailOrNull(principal.getUsername());

        String itemList;
        List<Item> items = new ArrayList<>();
        int totalPrice = 0;

        if (user.getBasket() == null) {
            itemList = "empty";
        } else {
            itemList = user.getBasket().toString();
        }

        if (itemList.equals("empty")) {
            items = new ArrayList<>();
        } else {
            String[] itemArray = itemList.substring(1, itemList.length()-1).split(",");
            List<Integer> itemListAsInteger = Arrays.stream(itemArray).map(Integer::parseInt).collect(Collectors.toList());

            for (int i = 0; i < itemListAsInteger.size(); ++i) {
                Item item = shoppingMethod.getItem(itemListAsInteger.get(i));
                totalPrice += item.getPrice();
                items.add(item);
            }
        }
        model.addAttribute("items", items);
        model.addAttribute("totalPrice", totalPrice);
        return "/shop/itemBasket";
    }
}
