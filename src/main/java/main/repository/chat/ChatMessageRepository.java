package main.repository.chat;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import lombok.RequiredArgsConstructor;
import main.model.chat.ChatMessage;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {
    private final JdbcTemplate jdbcTemplate;
    public List<ChatMessage> findMessageByRoomNumber(int roomNumber) {
        String sql = "select * from Messages where roomNumber = ?";
        return jdbcTemplate.query(sql, new Object[]{roomNumber}, new BeanPropertyRowMapper<>(ChatMessage.class));
    }
    public ChatMessage findChatMessageByOrderCreatedTimeOrNull(int roomNumber) {
        try{
            String sql = "select * from Messages where roomNumber = ? order by createdTime asc ";
            return jdbcTemplate.queryForObject(sql, new Object[]{roomNumber}, ChatMessage.class);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    public void save(ChatMessage chatMessage) {
        String sql = "insert into Messages (message, userEmail, roomNumber) values(?,?,?)";
        jdbcTemplate.update(sql, chatMessage.getMessage(), chatMessage.getUserEmail(), chatMessage.getRoomNumber());
    }

    public ChatMessage findTopByRoomNumberOrderByCreatedTimeDescOrNull(int roomNumber) {
        String sql = "select * from Messages where roomNumber = ? order by createdTime desc limit 1";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{roomNumber}, ChatMessage.class);
        }catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
}
