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
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    private final PostService postService;
    private final UserMethod userMethod;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        layoutService.addLayout(model, authentication);
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
    public String aboutMe(Model model, Authentication authentication) {
        layoutService.addLayout(model, authentication);
        List<Menu> menus = layoutService.getMenu(EMenu.ME);
        model.addAttribute("menus", menus);
        return "/aboutMe";
    }
    @GetMapping("/board/{curPage}")
    public String board(@PathVariable int curPage, Model model, Authentication authentication) {
        List<Menu> menus = layoutService.getMenu(EMenu.BOARD);
        layoutService.addLayout(model, authentication);
        List<Post> totalList = postService.getAllPosts();

        if (totalList == null) {
            totalList = new ArrayList<>();
            return "post/postList";
        }
        Pagination page = Pagination.paging(curPage, totalList.size());

        List<Post> postList = new ArrayList<>();
        for (int i = page.getListStartNum() ; i < page.getListEndNum(); ++i) {
            postList.add(totalList.get(i - 1));
        }
        model.addAttribute("admitWritePost", "postList");
        model.addAttribute("page", page);
        model.addAttribute("postList", postList);
        model.addAttribute("menus", menus);
        return "/post/postList";
    }
    @GetMapping("/shop")
    public String shop(Model model, Authentication authentication) {
        layoutService.addLayout(model, authentication);
        List<Menu> menus = layoutService.getMenu(EMenu.SHOP);

        List<Item> items =  shoppingService.getItemsByDesc();
        Pagination page = Pagination.paging(1, items.size());

        String userRole = "default";
        model.addAttribute("page", page);
        model.addAttribute("items", items);
        model.addAttribute("menus", menus);
        return "/shop/shopList";
    }
    @GetMapping("/search")
    public String searchKeyword(@RequestParam String keyword, @RequestParam String type, Model model, Authentication authentication) {
        layoutService.addLayout(model, authentication);
        if (type.equals("post")) {
            return "redirect:/post/list?keyword=" + keyword;
        } else {
             return "redirect:/shop/list?keyword=" + keyword + "&curPage=" + 1;
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

}
