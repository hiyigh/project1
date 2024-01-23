package main.dto;

import lombok.Getter;
import main.model.Comment;

import java.time.LocalDateTime;
import java.util.List;
@Getter
public class PostDto {
    private Long postId;
    private Long userId;
    private String postTitle;
    private String postContent;
    private Long categoryId;
    private LocalDateTime createdTime;
}
