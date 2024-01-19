package main.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import main.dto.PostDto;
import main.model.Post;
import main.repository.method.PostRepoMethod;
import main.service.method.PostMethod;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class PostService implements PostMethod {
    private final PostRepoMethod postRepoMethod;
    @Override
    public void add(PostDto postDto, int userId) {
        Long lastPostId = postRepoMethod.getLastPostIdOrNull();
        if (lastPostId == null) lastPostId = 1L;

        Post post = new Post();
        post.builder().postId(lastPostId)
                .postTitle(postDto.getPostTitle())
                .postContent(postDto.getPostContent())
                .categoryId(postDto.getCategoryId())
                .userId(userId) // spring security 를 활용해서 login 된 user 데이터를 가져온다.
                .build();
        postRepoMethod.add(post);
    }
    @Override
    public void delete(int postId) {
        postRepoMethod.delete(postId);
    }
    @Override
    public void update(Post updatePost) {
        postRepoMethod.update(updatePost);
    }
    @Override
    public Post getPostById(int postId) {
        return postRepoMethod.getPostById(postId);
    }
    @Override
    public List<Post> getPostByKeywordOrNull(String keyword) {
        return postRepoMethod.getPostByKeywordOrNull(keyword);
    }
    @Override
    public List<Post> getPostByCategory(int categoryId) {
        return postRepoMethod.getPostByCategory(categoryId);
    }
    @Override
    public List<Post> getAllPosts() {
        return postRepoMethod.getAllPosts();
    }

}
