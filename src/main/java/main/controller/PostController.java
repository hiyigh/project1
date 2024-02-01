package main.controller;

import com.sun.jdi.LongValue;
import lombok.RequiredArgsConstructor;
import main.dto.PostDto;
import main.dto.PrincipalDetails;
import main.model.*;
import main.model.enumeration.HistoryType;
import main.service.CategoryService;
import main.service.CommentService;
import main.service.LayoutService;
import main.service.PostService;
import main.service.method.CategoryMethod;
import main.service.method.UserMethod;
import main.service.paging.Pagination;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor

@RequestMapping("/post")
public class PostController {

    private final PostService postMethod;
    private final CommentService commentMethod;
    private final LayoutService layoutService;
    private final UserMethod userMethod;
    @GetMapping("/write" )
    public String writePost(Model model, Authentication authentication){
        layoutService.addLayout(model, authentication);
        return "/post/postWrite";
    }
    @PostMapping("/write")
    public String writePost(@RequestBody PostDto postDto , Model model, Authentication authentication) {
        layoutService.addLayout(model,authentication);
        User user = new User();
        if(authentication!=null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails || principal instanceof OAuth2User) {
                PrincipalDetails userDetails = (PrincipalDetails) principal;
                String userEmail = userDetails.getUsername();

                user = userMethod.getUserByEmailOrNull(userEmail);
                if(user != null) {
                    Long postId = postMethod.getLastPostIdOrNull() + 1l;

                    userMethod.setUserHistory(user.getUserId(), postId.intValue(), HistoryType.POST);
                    postDto.setPostId(postId);
                    postDto.setUserId(user.getUserId());
                }
            }
        }
        postMethod.add(postDto);
        return "redirect:/post/postWrite";
    }
    @GetMapping("/edit")
    public String editPost(@PathVariable Long postId, Model model, Authentication authentication) {
        Post post = postMethod.getPostById(postId);
        layoutService.addLayout(model,authentication);
        model.addAttribute("post",post);
        return "/post/postEdit";
    }
    @PostMapping("/edit")
    public String editPost(@RequestBody PostDto postDto, Model model, Authentication authentication){
        layoutService.addLayout(model, authentication);
        postMethod.update(postDto);

        return "redirect:/post/view?" + postDto.getPostId();
    }
    @GetMapping("/view/{postId}")
    public String viewPost(@PathVariable Long postId, Model model) {
        Post post = postMethod.getPostById(postId);
        List<Comment> commentList = commentMethod.getCommentByPostId(postId);

        model.addAttribute( "post",post);
        model.addAttribute("commentList", commentList);
        return "/post/postView";
    }
    @GetMapping("/category/{categoryId}")
    public String getPostList(Model model , @PathVariable Long categoryId, Authentication authentication) {
        layoutService.addLayout(model, authentication);
        List<Post> postList = postMethod.getPostByCategory(categoryId);
        Pagination page = Pagination.paging(1,postList.size());

        model.addAttribute("page", page);
        model.addAttribute("postList", postList);
        return "/post/postList";
    }
    @GetMapping("/paging")
    @ResponseBody
    public List<Post> getPaging(@RequestParam Long categoryId, @RequestParam int currentPage) {
        List<Post> postList = postMethod.getPostByCategory(categoryId);
        Pagination page = Pagination.paging(currentPage, postList.size());
        List<Post> pagingList = new ArrayList<>();
        for (int i = page.getListStartNum(); i < page.getListEndNum(); ++i) {
            pagingList.add(postList.get(i));
        }
        return pagingList;
    }

}
