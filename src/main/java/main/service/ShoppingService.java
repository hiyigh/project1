package main.service;

import lombok.RequiredArgsConstructor;
import main.dto.ItemDto;
import main.model.Item;
import main.repository.method.ShoppingRepoMethod;
import main.service.method.ShoppingMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingService implements ShoppingMethod {
    private final ShoppingRepoMethod shopRepoMethod;

    public List<Item> getItemsByKeywordOrNull(String keyword) {
        return shopRepoMethod.getItemsByKeywordOrNull(keyword);
    }

    public Item getItem(int itemId) {
        return shopRepoMethod.getItem(itemId);
    }

    @Override
    public List<Item> getRecentItems() {
        return shopRepoMethod.getRecentItems();
    }

    @Override
    public List<Item> getCheapestItems() {
        return shopRepoMethod.getCheapestItems();
    }

    @Override
    public List<Item> getExpensiveItems() {
        return shopRepoMethod.getExpensiveItems();
    }

    @Override
    public List<Item> getFamousItems() {
        return shopRepoMethod.getFamousItems();
    }

    @Override
    public List<Item> getItemsByName(String name) {
        return shopRepoMethod.getItemsByName(name);
    }

    @Override
    public List<Item> getItemsByDetail(String detail) {
        return shopRepoMethod.getItemsByDetail(detail);
    }

    @Override
    public List<Item> getItemsByAsc() {
        return shopRepoMethod.getItemsByAsc();
    }

    @Override
    public List<Item> getItemsByDesc() {
        return shopRepoMethod.getItemsByDesc();
    }

    @Override
    public void deleteItem(int itemId) {
        shopRepoMethod.deleteItem(itemId);
    }

    @Override
    public void addItem(ItemDto itemDto) {
        shopRepoMethod.addItem(itemDto);
    }

    @Override
    public void addHit(Item item) {
        shopRepoMethod.addHit(item);
    }
}
