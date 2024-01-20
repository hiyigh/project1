package main.repository;

import lombok.RequiredArgsConstructor;
import main.model.Post;
import main.repository.method.PostRepoMethod;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class PostRepository implements PostRepoMethod {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public void add(Post post) {
        String sql = "insert into Posts(postId, postTitle, postContent, categoryId, postCreatedTime) values (?,?,?,?,?)";
        jdbcTemplate.update(sql, post.getPostId(), post.getPostTitle(),
                post.getPostContent(), post.getCategoryId(), post.getCreatedTime());
    }
    @Override
    public void delete(int postId) {
        String sql = "delete from Posts where postId = ?";
        jdbcTemplate.update(sql, postId);
    }

    @Override
    public void update(Post updatePost) {
        String sql = "update Posts set postTitle = ?, postContent=?, categoryId=? where postId = ?";
        jdbcTemplate.update(sql, updatePost.getPostTitle(), updatePost.getPostContent(),
                updatePost.getCategoryId(), updatePost.getPostId());
    }


    @Override
    public Post getPostById(Long postId) {
        return jdbcTemplate.queryForObject("select * from Posts where postId = ?", new Object[]{postId},
                new BeanPropertyRowMapper<>(Post.class));
    }

    @Override
    public List<Post> getPostByKeywordOrNull(String keyword) {
        return jdbcTemplate.query("select * from Posts where postContent like ?", new Object[]{"%"+ keyword +"%"},
                new BeanPropertyRowMapper<>(Post.class));
    }

    @Override
    public List<Post> getPostByCategory(int categoryId) {
        return jdbcTemplate.query("select * from Posts where categoryId = ?", new Object[]{categoryId},
                new BeanPropertyRowMapper<>(Post.class));
    }

    @Override
    public List<Post> getAllPosts() {
        return jdbcTemplate.query("select * from Posts",new BeanPropertyRowMapper<>(Post.class));
    }
    @Override
    public Long getLastPostIdOrNull() {
        return jdbcTemplate.queryForObject("select postId from Posts order by postId desc limit 1", Long.class);
    }
}
