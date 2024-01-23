package main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Comment extends Time {
    private Long commentId;
    private Long userId;
    private Long postId;
    private Long mainCommentId;
    private String commentContent;
    private boolean isSecret;

    public Comment(Comment comment) {
        this.commentId = comment.getCommentId();
        this.userId = comment.getUserId();
        this.postId = comment.getMainCommentId();
        this.commentContent = comment.getCommentContent();
        this.isSecret = comment.isSecret();
    }
    @Builder
    public void builder(Long commentId, Long userId, Long postId, String commentContent, Long mainCommentId, boolean isSecret) {
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
        this.commentContent = commentContent;
        this.isSecret = isSecret;
        this.mainCommentId = mainCommentId;
    }

    public void setCommentContent(String editComment) {
        this.commentContent = editComment;
    }
}
