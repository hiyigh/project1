package main.service.method.chat;

import main.dto.chat.ChatMessageDto;

import java.util.List;

public interface ChatMessageMethod {
    void save(ChatMessageDto chatMessageDto);
    List<ChatMessageDto> findMessageByRoomNumber(int roomNumber);
}
