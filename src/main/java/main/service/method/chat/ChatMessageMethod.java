package main.service.method.chat;

import main.dto.chat.ChatMessageDto;
import main.model.chat.ChatMessage;

import java.util.List;

public interface ChatMessageMethod {
    void save(ChatMessageDto chatMessageDto);
    List<ChatMessage> findMessageByRoomNumber(int roomNumber);
}
