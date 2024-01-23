package main.dto;

import lombok.Getter;
import lombok.Setter;
import main.model.Comment;
import main.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CommentDto {
    private Long commentId;
    private Long mainCommentId;
    private Long postId;
    private String commentContent;
    private LocalDateTime createdTime;
    private boolean isSecret;

    private String commentWriter;
    private Long userId;
}
