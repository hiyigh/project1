package main.controller;

import lombok.RequiredArgsConstructor;
import main.dto.PrincipalDetails;
import main.model.*;
import main.model.enumeration.EMenu;
import main.model.enumeration.Role;
import main.service.CategoryService;
import main.service.LayoutService;
import main.service.PostService;
import main.service.ShoppingService;
import main.service.method.UserMethod;
import main.service.paging.Pagination;
import net.minidev.json.JSONUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.PrincipalMethodArgumentResolver;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final LayoutService layoutService;
    private final ShoppingService shoppingService;
    private final ResourceLoader resourceLoader;
    private final CategoryService categoryService;
    private final PostService postService;
    private final UserMethod userMethod;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String home(Model model) {
        layoutService.addLayout(model);
        List<String> imgUrlList;
        int fileCount = getFileCount("classpath:static/img/");
        imgUrlList = getImageUrlList(fileCount);
        model.addAttribute("images", imgUrlList);
        return "/home";
    }
    @GetMapping("/login")
    public String login(Model model) {
        return "/login";
    }
    @GetMapping("/enroll")
    public String goToEnroll() {
        return "/enroll";
    }
    @PostMapping("/enroll")
    public String enroll(@RequestParam String email, @RequestParam String password, @RequestParam String name,
                             @RequestParam boolean gender, Model model) {
        boolean check = duplicatedEmail(email);
        if (check) {
            model.addAttribute("error", "Email is already in use");
            return "redirect:/enroll";
        }
        String bcryptPassword = bCryptPasswordEncoder.encode(password);
        User user = new User(name, email, bcryptPassword, gender, Role.ROLE_USER);
        userMethod.add(user);
        return "redirect:/";
    }
    private boolean duplicatedEmail(String email) {
        User user = userMethod.getUserByEmailOrNull(email);
        if(user == null ) return false;
        return true;
    }

    @GetMapping("/aboutMe")
    public String aboutMe(Model model) {
        layoutService.addLayout(model);
        List<Menu> menus = getMenu(EMenu.ME);
        model.addAttribute("menus", menus);
        return "/aboutMe";
    }
    @GetMapping("/board")
    public String board(Model model) {
        List<Menu> menus = getMenu(EMenu.BOARD);
        layoutService.addLayout(model);
        List<Post> postList = postService.getAllPosts();

        if (postList == null) {
            postList = new ArrayList<>();
            return "post/postList";
        }
        Pagination page = Pagination.paging(1,postList.size());
        model.addAttribute("admitWritePost", "postList");
        model.addAttribute("page", page);
        model.addAttribute("postList", postList);
        model.addAttribute("menus", menus);
        return "/post/postList";
    }
    @GetMapping("/shop")
    public String shop(Model model, Authentication authentication) {
        List<Menu> menus = getMenu(EMenu.SHOP);
        List<Item> items =  shoppingService.getItemsByDesc();
        String userRole = null;

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails || principal instanceof OAuth2User) {
                PrincipalDetails userDetails = (PrincipalDetails) principal;
                userRole = userDetails.getAuthorities().toString();
            }
        }
        for (int i = 0; i< items.size(); ++i) {
            System.out.println("url :" + items.get(i).getImgUrl());
        }

        model.addAttribute("userRole", userRole);
        model.addAttribute("items", items);
        model.addAttribute("menus", menus);
        return "/shop/shopList";
    }
    @PostMapping("/search")
    public String searchKeyword(@RequestParam String keyword, @RequestParam String type, Model model) {
        layoutService.addLayout(model);
        if (type.equals("post")) {
            List<Post> postList = postService.getPostByKeywordOrNull(keyword);
            if (postList == null) {

            }
            model.addAttribute("postList", postList);
            return "redirect:/post/postList";
        } else {
             List<Item> items = shoppingService.getItemsByKeywordOrNull(keyword);
             model.addAttribute(items);
             return "redirect:/shop/shopList";
        }
    }
    private List<String> getImageUrlList(int fileCount) {
        List<String> imgUrlList = new ArrayList<>();
        for (int i = 0; i < fileCount; ++i) {
            imgUrlList.add("/img/images-" + i + ".jpg");
        }
        return imgUrlList;
    }
    private int getFileCount(String directoryPath) {
        try {
            int defaultCount = 1;
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(directoryPath + "*");
            return resources.length - defaultCount;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private List<Menu> getMenu(EMenu menuType) {
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
        Menu menu2 = new Menu();
        menu1.setLink("/shop/basket");
        menu1.setText("장바구니");
        menu2.setLink("/shop/pay");
        menu2.setText("결제");
        menus.add(menu1);
        menus.add(menu2);
    }

    private void setCategoryMenu(List<Menu> menus) {
        List<Category> categoryList = categoryService.getRootCategoryList();

        int length = categoryList.size();
        for (int i =0; i< length; ++i) {
            Menu menu = new Menu();
            menu.setLink("/post/category");
            menu.setId(categoryList.get(i).getCategoryId());
            menu.setText(categoryList.get(i).getCategoryTitle());
            menus.add(menu);
        }
    }

    private void setSubCategoryMenu(List<Menu> menus) {
        for (int i =0; i<menus.size(); ++i) {
            List<Category> categoryList = categoryService.getSubCategoryList(menus.get(i).getId());
            for (int j = 0; j < categoryList.size(); ++j) {
                Menu subMenu = new Menu();
                subMenu.setLink("/post/category");
                subMenu.setId(categoryList.get(j).getCategoryId());
                subMenu.setText(categoryList.get(i).getCategoryTitle());
                menus.get(i).getSubMenu().add(subMenu);
            }
        }
    }

}
