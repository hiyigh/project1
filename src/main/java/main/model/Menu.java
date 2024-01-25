package main.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Menu {
    private Long id;
    private String link;
    private String text;
    private List<Menu> subMenu;
}
