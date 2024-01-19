package main.repository;

import main.model.Post;
import main.repository.method.PostRepoMethod;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PostRepository implements PostRepoMethod {
    @Override
    public void add(Post post) {

    }

    @Override
    public void delete(int postId) {

    }

    @Override
    public void update(Post updatePost) {

    }

    @Override
    public Post getPostById(int postId) {
        return null;
    }

    @Override
    public List<Post> getPostByKeywordOrNull(String keyword) {
        return null;
    }

    @Override
    public List<Post> getPostByCategory(int categoryId) {
        return null;
    }

    @Override
    public List<Post> getAllPosts() {
        return null;
    }
    @Override
    public Long getLastPostIdOrNull() {
        return null;
    }
}
