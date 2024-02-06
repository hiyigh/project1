package main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Comment extends Time {
    private Long commentId;
    private String commentWriter;
    private Long userId;
    private Long postId;
    private Long mainCommentId;
    private String commentContent;
    private boolean isSecret;

    @Builder
    public void builder(Long commentId, Long userId, Long postId, String commentWriter,String commentContent, Long mainCommentId, boolean isSecret) {
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
        this.commentContent = commentContent;
        this.isSecret = isSecret;
        this.mainCommentId = mainCommentId;
        this.commentWriter = commentWriter;
    }
}
