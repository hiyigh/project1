package main.service.method;

import main.dto.PostDto;
import main.model.Post;

import java.util.List;

public interface PostMethod {
    void add(PostDto postDto, int userId);

    void delete(int postId);

    void update(PostDto updatePostDto);

    Post getPostById(Long postId);

    List<Post> getPostByKeywordOrNull(String keyword);

    List<Post> getPostByCategory(int categoryId);

    List<Post> getAllPosts();
}
