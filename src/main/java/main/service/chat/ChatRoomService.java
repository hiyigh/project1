package main.service.chat;

import lombok.RequiredArgsConstructor;
import main.dto.chat.ChatRoomDto;
import main.model.User;
import main.model.chat.ChatMessage;
import main.model.chat.ChatRoom;
import main.repository.chat.ChatMessageRepository;
import main.repository.chat.ChatRoomRepository;
import main.repository.method.UserRepoMethod;
import main.service.method.chat.ChatRoomMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService implements ChatRoomMethod {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepoMethod userRepoMethod;
    @Override
    public ChatRoom createRoom(Long userId, Long otherId) {
        return chatRoomRepository.createRoom(userId, otherId);
    }

    @Override
    public int deleteRoom(int roomNumber) {
        int deletedRows = chatRoomRepository.deleteRoom(roomNumber);
        if (deletedRows == 0) {
            throw new IllegalArgumentException("해당 chat room 이 존재하지 않습니다.");
        }
        return deletedRows;
    }

    @Override
    public ChatRoom findByRoomNumberOrNull(int roomNumber) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomNumberOrNull(roomNumber);
        if (chatRoom == null) {
            throw new IllegalArgumentException("해당 chat room 이 존재하지 않습니다 +id: " + roomNumber);
        }
        return chatRoom;
    }

    @Override
    public List<ChatRoom> getAllRoomOrNull() {
        List<ChatRoom> chatRooms = chatRoomRepository.getAllRoomOrNull();
        return chatRooms;
    }
}
