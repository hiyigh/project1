package main.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import main.dto.PrincipalDetails;
import main.model.Category;
import main.model.Menu;
import main.model.User;
import main.model.enumeration.EMenu;
import main.service.method.CategoryMethod;
import main.service.method.PostMethod;
import main.service.method.UserMethod;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Getter
public class LayoutService {
    private final CategoryMethod categoryMethod;
    private final UserMethod userMethod;
    private User user;
    public void addLayout(Model model, Authentication authentication) {
        List<Category> categoryList = categoryMethod.getAllCategoryList();
        String userRole = "default";
        this.user = new User();
        if (authentication != null) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            user = userMethod.getUserByEmailOrNull(principalDetails.getUsername());

            if (user==null) System.out.println("user is null");

            int roleLen = user.getRole().toString().length();
            userRole = user.getRole().toString();
        }
        model.addAttribute("user", user);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("userRole", userRole);
    }
    public List<Menu> getMenu(EMenu menuType) {
        List<Menu> menus = new ArrayList<>();
        switch (menuType) {
            case BOARD:
                setCategoryMenu(menus);
                for (int i = 0; i<menus.size();++i) {
                    setSubCategoryMenu(menus);
                }
                break;
            case SHOP:
                setShopMenu(menus);
                break;
            case ME:
                setAboutMe(menus);
                break;

        }
        return menus;
    }
    private void setAboutMe(List<Menu> menus) {
        Menu menu1 = new Menu();
        menu1.setLink("/aboutMe/profile");
        menu1.setText("프로필");
        menus.add(menu1);
    }

    private void setShopMenu(List<Menu> menus) {
        Menu menu1 = new Menu();
        menu1.setLink("/shop/basket");
        menu1.setText("장바구니");
        menus.add(menu1);
    }

    private void setCategoryMenu(List<Menu> menus) {
        List<Category> categoryList = categoryMethod.getRootCategoryList();

        int length = categoryList.size();
        for (int i =0; i< length; ++i) {
            Menu menu = new Menu();
            menu.setLink("/post/category/");
            menu.setId(categoryList.get(i).getCategoryId());
            menu.setText(categoryList.get(i).getCategoryTitle());
            menus.add(menu);
        }
    }

    private void setSubCategoryMenu(List<Menu> menus) {
        for (int i =0; i<menus.size(); ++i) {
            List<Category> categoryList = categoryMethod.getSubCategoryList(menus.get(i).getId());
            for (int j = 0; j < categoryList.size(); ++j) {
                Menu subMenu = new Menu();
                subMenu.setLink("/post/category/");
                subMenu.setId(categoryList.get(j).getCategoryId());
                subMenu.setText(categoryList.get(i).getCategoryTitle());
                menus.get(i).getSubMenu().add(subMenu);
            }
        }
    }

}
