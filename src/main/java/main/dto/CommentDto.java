package main.dto;

import lombok.Getter;
import main.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentDto {
    private Long commentId;
    private Long mainCommentId;
    private Long postId;
    private String writer;
    private String commentContent;
    private LocalDateTime createdTime;
    private boolean isRoot;
    private boolean isSecret;
}
