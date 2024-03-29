package main.service;

import lombok.RequiredArgsConstructor;
import main.dto.CommentDto;
import main.model.Comment;
import main.repository.method.CommentRepoMethod;
import main.service.method.CommentMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentMethod {
    private final CommentRepoMethod commentRepoMethod;
    @Override
    public void add(CommentDto commentDto) {
        commentRepoMethod.add(commentDto);
    }

    @Override
    public void delete(Long commentId) {
        commentRepoMethod.delete(commentId);
    }

    @Override
    public void update(Comment comment) {
        commentRepoMethod.update(comment);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepoMethod.getCommentById(commentId);
    }

    @Override
    public List<Comment> getCommentByPostId(Long postId) {
        return commentRepoMethod.getCommentByPostId(postId);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepoMethod.getAllComments();
    }
    @Override
    public Long getLastCommentNum() {
        return commentRepoMethod.getLastCommentNum();
    }
    @Override
    public List<Comment> getCommentByParentId(Long parentId) {
        return commentRepoMethod.getCommentByParentId(parentId);
    }
    @Override
    public void deleteByParentId(Long parentId) {
        commentRepoMethod.deleteByParentId(parentId);
    }
}
