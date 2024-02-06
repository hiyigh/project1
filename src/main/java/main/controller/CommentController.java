package main.controller;

import lombok.RequiredArgsConstructor;
import main.dto.CommentDto;
import main.model.Comment;
import main.model.User;
import main.service.UserService;
import main.service.method.CommentMethod;
import main.service.method.UserMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final UserService userMethod;
    private final CommentMethod commentMethod;
    @PostMapping("/addComment")
    @ResponseBody
    public List<Comment> addComment(@RequestParam Long postId, @RequestParam Long commentUserId, String commentContent) {
        CommentDto commentDto = new CommentDto();
        User user = userMethod.getUserById(commentUserId);

        commentDto.setCommentWriter(user.getUserName());
        commentDto.setPostId(postId);
        commentDto.setUserId(commentUserId);
        commentDto.setMainCommentId(null);
        commentDto.setCommentContent(commentContent);
        commentMethod.add(commentDto);

        List<Comment> updateCommentList = commentMethod.getCommentByPostId(postId);
        return updateCommentList;
    }
    @PostMapping("/updateList")
    @ResponseBody
    public List<Comment> updateCommentList(@RequestParam Long postId) {
        List<Comment> updateCommentList = commentMethod.getCommentByPostId(postId);
        return updateCommentList;
    }
    @GetMapping("/addReComment")
    @ResponseBody
    public List<Comment> reCommentList(@RequestParam Long parentId) {
        List<Comment> reCommentList = commentMethod.getCommentByParentId(parentId);
        return reCommentList;
    }
    @PutMapping("/edit")
    public void editComment(@RequestParam Long commentId, @RequestParam String editComment) {
        Comment comment = commentMethod.getCommentById(commentId);
        comment.setCommentContent(editComment);
        commentMethod.update(comment);
    }
    @PutMapping("/delete")
    public void deleteComment(@RequestParam Long commentId) {
        Comment comment = commentMethod.getCommentById(commentId);
        if (comment.getMainCommentId() == null) {
            commentMethod.deleteByParentId(commentId);
        } else {
            commentMethod.delete(commentId);
        }
    }
}
