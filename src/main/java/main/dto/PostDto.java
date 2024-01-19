package main.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
public class PostDto {
    private Long postId;
    private String postTitle;
    private String postContent;
    private int categoryId;
    private List<Integer> commentList;
    private LocalDateTime createdTime;
}