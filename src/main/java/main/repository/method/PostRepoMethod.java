package main.repository.method;

import main.model.Post;

import java.util.List;

public interface PostRepoMethod {

    void add(Post post);

    void delete(Long postId);

    void update(Post updatePost);

    Post getPostById(Long postId);

    List<Post> getPostByKeywordOrNull(String keyword);

    List<Post> getPostByCategory(Long categoryId);

    List<Post> getAllPosts();
    Long getLastPostIdOrNull();
}
