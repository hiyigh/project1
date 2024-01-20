package main.service.method;

import main.dto.CommentDto;
import main.model.Comment;

import java.util.List;

public interface CommentMethod {
    void add(CommentDto commentDto);
    void delete(Long commentId);
    void update(CommentDto commentDto);
    Comment getCommentById(Long commentId);
    List<Comment> getCommentByPostId(Long postId);
    List<Comment> getAllComments();
}
