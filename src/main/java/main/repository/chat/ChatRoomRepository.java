package main.repository.chat;

import lombok.RequiredArgsConstructor;
import main.dto.chat.ChatRoomDto;
import main.model.chat.ChatRoom;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {
    private final JdbcTemplate jdbcTemplate;
    public ChatRoom findByRoomNumberOrNull(int roomNumber) {
        String sql = "select * from Rooms where roomNUmber = ?";
        try{
            return jdbcTemplate.queryForObject(sql, new Object[]{roomNumber}, new BeanPropertyRowMapper<>(ChatRoom.class));
        }catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
    public ChatRoom findByUserAndOtherIdOrNull(Long userId, Long otherId) {
        String sql = "select * from Rooms where userId = ? and otherId = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userId, otherId}, new BeanPropertyRowMapper<>(ChatRoom.class));
        } catch(EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ChatRoom createRoom(Long userId, Long otherId) {
        String sql = "insert into Rooms (userId, otherId) values(?,?)";
        jdbcTemplate.update(sql, userId, otherId);

        ChatRoom chatRoom = findByUserAndOtherIdOrNull(userId, otherId);
        return chatRoom;
    }

    public int deleteRoom(int roomNumber) {
        int deletedRows = jdbcTemplate.update("delete from Rooms where roomNumber = ?", roomNumber);
        return deletedRows;
    }

    public List<ChatRoom> getAllRoomOrNull() {
        String sql = "select * from Rooms";
        try{
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ChatRoom.class));
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
}
