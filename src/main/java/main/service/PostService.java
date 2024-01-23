package main.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import main.dto.PostDto;
import main.model.Post;
import main.repository.method.PostRepoMethod;
import main.service.method.PostMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Service
public class PostService implements PostMethod {
    private final PostRepoMethod postRepoMethod;
    @Override
    public void add(PostDto postDto) {
        Long lastPostId = postRepoMethod.getLastPostIdOrNull();
        if (lastPostId == null) lastPostId = 1L;

        Post post = new Post();
        post.builder(lastPostId, postDto.getPostTitle(),postDto.getPostContent()
                ,postDto.getCategoryId(),postDto.getUserId());
        postRepoMethod.add(post);
    }
    @Override
    public void delete(Long postId) {
        postRepoMethod.delete(postId);
    }
    @Override
    public void update(PostDto updatePostDto) {
        Post updatePost = postRepoMethod.getPostById(updatePostDto.getPostId());

        updatePost.setPostTitle(updatePostDto.getPostTitle());
        updatePost.setPostContent(updatePostDto.getPostContent());
        updatePost.setCategoryId(updatePost.getCategoryId());

        postRepoMethod.update(updatePost);
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepoMethod.getPostById(postId);
    }
    @Override
    public List<Post> getPostByKeywordOrNull(String keyword) {
        return postRepoMethod.getPostByKeywordOrNull(keyword);
    }
    @Override
    public List<Post> getPostByCategory(Long categoryId) {
        return postRepoMethod.getPostByCategory(categoryId);
    }
    @Override
    public List<Post> getAllPosts() {
        return postRepoMethod.getAllPosts();
    }

    @Override
    public Long getLastPostIdOrNull() {
        return postRepoMethod.getLastPostIdOrNull();
    }
}
