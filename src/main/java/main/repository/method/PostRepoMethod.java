package main.repository.method;

import main.model.Post;

import java.util.List;

public interface PostRepoMethod {

    void add(Post post);

    void delete(int postId);

    void update(Post updatePost);

    Post getPostById(int postId);

    List<Post> getPostByKeywordOrNull(String keyword);

    List<Post> getPostByCategory(int categoryId);

    List<Post> getAllPosts();
    Long getLastPostIdOrNull();
}
