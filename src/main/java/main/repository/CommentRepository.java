package main.repository;

import lombok.RequiredArgsConstructor;
import main.dto.CommentDto;
import main.model.Comment;
import main.repository.method.CommentRepoMethod;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository implements CommentRepoMethod {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public void add(CommentDto commentDto) {
        jdbcTemplate.update("insert into Comments (postId, userId, commentId, mainCommentId, commentContent, isSecret, createdTime)",
                commentDto.getPostId(),
                commentDto.getUserId(),
                commentDto.getMainCommentId(),
                commentDto.getCommentContent(),
                commentDto.isSecret(),
                commentDto.getCreatedTime()
        );
    }

    @Override
    public void delete(Long commentId) {
        jdbcTemplate.update("delete from Comments where commentId = ?", commentId);
    }

    @Override
    public void update(Comment comment) {
        jdbcTemplate.update("update Comments set commentContent=? where commentId = ?",
                comment.getCommentContent(), comment.getCommentId());
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return jdbcTemplate.queryForObject("select * from Comments where commentId = ?", new Object[]{commentId},
                new BeanPropertyRowMapper<>(Comment.class));
    }

    @Override
    public List<Comment> getCommentByPostId(Long postId) {
        return jdbcTemplate.query("select * from Comments where postId = ?", new Object[]{postId},
                new BeanPropertyRowMapper<>(Comment.class));
    }

    @Override
    public List<Comment> getAllComments() {
        return jdbcTemplate.query("select * from Comments", new BeanPropertyRowMapper<>(Comment.class));
    }

    @Override
    public Long getLastCommentNum() {
        return jdbcTemplate.queryForObject("select commentId from Comments order by commentId desc limit 1",Long.class);
    }

    @Override
    public List<Comment> getCommentByParentId(Long parentId) {
        return jdbcTemplate.query("select * from Comments where mainCommentId = ?", new Object[]{parentId},
                new BeanPropertyRowMapper<>(Comment.class));
    }

    @Override
    public void deleteByParentId(Long parentId) {
        jdbcTemplate.update("delete from Comments where mainCommentId = ?", parentId);
    }
}
