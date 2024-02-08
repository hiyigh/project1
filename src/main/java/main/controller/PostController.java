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
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
        if(authentication != null) {
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
        return "redirect:/post/write";
    }
    @GetMapping("/edit")
    public String editPost(@RequestParam Long postId, Model model, Authentication authentication) {

        Post post = postMethod.getPostById(postId);
        layoutService.addLayout(model, authentication);
        model.addAttribute("post", post);
        return "/post/postEdit";
    }
    @PostMapping("/edit")
    public String editPost(@RequestBody PostDto postDto, Model model, Authentication authentication){
        layoutService.addLayout(model, authentication);

        postMethod.update(postDto);
        return "redirect:/post/view/" + postDto.getPostId();
    }
    @GetMapping("/delete")
    public String deletePost(@RequestParam Long postId) {
        postMethod.delete(postId);
        return "redirect:/board";
    }
    @GetMapping("/view/{postId}")
    public String viewPost(@PathVariable Long postId, Model model, Authentication authentication) {
        Post post = postMethod.getPostById(postId);
        User postUser = userMethod.getUserById(post.getUserId());
        User loginUser = null;
        if (authentication != null && authentication.isAuthenticated()) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            loginUser = userMethod.getUserByEmailOrNull(principalDetails.getUsername());
        }

        List<Comment> commentList = commentMethod.getCommentByPostId(postId);
        if (commentList == null) {
            commentList = new ArrayList<>();
        }

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("postUser", postUser);
        model.addAttribute( "post",post);
        model.addAttribute("commentList", commentList);
        return "/post/postView";
    }
    @GetMapping("/category/{categoryId}")
    public String getPostList(Model model , @PathVariable Long categoryId, Authentication authentication) {
        layoutService.addLayout(model, authentication);
        List<Post> totalList = postMethod.getPostByCategory(categoryId);
        Pagination page = Pagination.paging(1,totalList.size());

        List<Post> postList = new ArrayList<>();
        for (int i = page.getListStartNum(); i < page.getListEndNum(); ++i) {
            postList.add(totalList.get(i - 1));
        }
        model.addAttribute("page", page);
        model.addAttribute("postList", postList);
        return "/post/postList";
    }
    @GetMapping("/list")
    public String searchPosts(@RequestParam String keyword, Model model, Authentication authentication) {
        layoutService.addLayout(model, authentication);
        List<Post> totalList = postMethod.getPostByKeywordOrNull(keyword);
        Pagination page = Pagination.paging(1, totalList.size());
        List<Post> pagingList = new ArrayList<>();

        for (int i = page.getListStartNum(); i < page.getListEndNum(); ++i) {
            pagingList.add(totalList.get(i - 1));
        }
        model.addAttribute("page", page);
        model.addAttribute("postList", pagingList);
        return "/post/postList";
    }
}
