package main.service.method;

import main.dto.PostDto;
import main.model.Post;

import java.util.List;

public interface PostMethod {
    void add(PostDto postDto);

    void delete(Long postId);

    void update(PostDto updatePostDto);

    Post getPostById(Long postId);

    List<Post> getPostByKeywordOrNull(String keyword);

    List<Post> getPostByCategory(Long categoryId);

    List<Post> getAllPosts();

    Long getLastPostIdOrNull();
}
