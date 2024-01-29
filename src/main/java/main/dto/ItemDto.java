package main.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
    private String imgUrl;
    private String itemName;
    private int price;
    private String detail;
    private int inventoryCount;
}
