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
    private int userId;
    private int categoryId;
    private List<Integer> commentList;
    @Builder
    public void builder(Long postId, String postTitle, String postContent, int categoryId, int userId) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.categoryId = categoryId;
        this.commentList = null;
        this.userId = userId;
    }
    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }
    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
