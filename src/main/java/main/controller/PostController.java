package main.controller;

import lombok.RequiredArgsConstructor;
import main.dto.PostDto;
import main.model.*;
import main.service.CategoryService;
import main.service.CommentService;
import main.service.LayoutService;
import main.service.PostService;
import main.service.method.CategoryMethod;
import main.service.paging.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor

@RequestMapping("/post")
public class PostController {

    private final PostService postMethod;
    private final CommentService commentMethod;
    private final LayoutService layoutService;
    @GetMapping("/write" )
    public String writePost(Model model){
        layoutService.addLayout(model);
        return "/post/postWrite";
    }
    @PostMapping("/write")
    public String writePost(@RequestBody PostDto postDto, Model model) {
        layoutService.addLayout(model);
        postMethod.add(postDto);
        return "redirect:/post/postWrite";
    }
    @GetMapping("/edit")
    public String editPost(@PathVariable Long postId, Model model) {
        Post post = postMethod.getPostById(postId);
        layoutService.addLayout(model);
        model.addAttribute("post",post);
        return "/post/postEdit";
    }
    @PostMapping("/edit")
    public String editPost(@RequestBody PostDto postDto, Model model){
        layoutService.addLayout(model);
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
    public String getPostList(Model model , @PathVariable Long categoryId) {
        layoutService.addLayout(model);
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
