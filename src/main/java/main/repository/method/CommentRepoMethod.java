package main.repository.method;

import main.dto.CommentDto;
import main.model.Comment;

import java.util.List;

public interface CommentRepoMethod {
    void add(CommentDto commentDto);

    void delete(Long commentId);

    void update(Comment comment);

    Comment getCommentById(Long commentId);

    List<Comment> getCommentByPostId(Long postId);

    List<Comment> getAllComments();

    Long getLastCommentNum();

    List<Comment> getCommentByParentId(Long parentId);

    void deleteByParentId(Long parentId);
}
