package main.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item extends Time{
    private int itemId;
    private String imgUrl;
    private String itemName;
    private int price;
    private String detail;
    private int hits;
    private int inventoryCount;
    private int soldCount;
}
