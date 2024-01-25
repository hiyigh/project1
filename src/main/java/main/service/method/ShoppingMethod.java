package main.service.method;

import main.model.Item;

import java.util.List;

public interface ShoppingMethod {
    Item getItem(int itemId);
    List<Item> getRecentItems();
    List<Item> getCheapestItems();
    List<Item> getExpensiveItems();
    List<Item> getFamousItems();
    List<Item> getItemsByName(String name);
    List<Item> getItemsByDetail(String detail);
    List<Item> getItemsByAsc();
    List<Item> getItemsByDesc();
    List<Item> getItemsByKeywordOrNull(String keyword);
}
