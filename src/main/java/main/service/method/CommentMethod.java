package main.service.method;

import main.dto.CommentDto;
import main.model.Comment;

import java.util.List;

public interface CommentMethod {
    void add(CommentDto commentDto);
    void delete(Long commentId);
    void deleteByParentId(Long parentId);
    void update(Comment comment);
    Comment getCommentById(Long commentId);
    List<Comment> getCommentByPostId(Long postId);
    List<Comment> getAllComments();
    Long getLastCommentNum();

    List<Comment> getCommentByParentId(Long parentId);
}
