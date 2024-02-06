package main.service.method.chat;

import main.dto.chat.ChatMessageDto;
import main.dto.chat.ChatRoomDto;
import main.model.chat.ChatRoom;

import java.util.List;

public interface ChatRoomMethod {
    ChatRoom createRoom(Long userId, Long otherId);
    int deleteRoom(int roomNumber);
    ChatRoom findByRoomNumberOrNull(int roomNumber);
    List<ChatRoom> getAllRoomOrNull();
}
