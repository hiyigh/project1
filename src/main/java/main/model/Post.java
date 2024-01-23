package main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class Post extends Time {
    private Long postId;
    private String postTitle;
    private String postContent;
    private Long userId;
    private Long categoryId;

    public void builder(Long postId, String postTitle, String postContent, Long categoryId, Long userId) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.categoryId = categoryId;
        this.userId = userId;
    }
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
