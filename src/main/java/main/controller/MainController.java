package main.controller;

import ch.qos.logback.core.Layout;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import main.service.LayoutService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final LayoutService layoutService;
    private final ResourceLoader resourceLoader;

    @GetMapping("/")
    public String home(Model model) {
        layoutService.addLayout(model);
        List<String> imgUrlList;
        int fileCount = getFileCount("classpath:static/img/");
        imgUrlList = getImageUrlList(fileCount);
        model.addAttribute("images", imgUrlList);
        return "/home";
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
    @GetMapping("/aboutMe")
    public String aboutMe(Model model) {
        layoutService.addLayout(model);
        return "/aboutMe";
    }
}
