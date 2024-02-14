package main.repository.method;

import main.dto.ItemDto;
import main.model.Item;

import java.util.List;

public interface ShoppingRepoMethod {
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

    void deleteItem(int itemId);
    void addItem(ItemDto itemDto);

    void addHit(Item item);
}
