package main.service.chat;

import lombok.RequiredArgsConstructor;
import main.dto.chat.ChatMessageDto;
import main.model.chat.ChatMessage;
import main.repository.chat.ChatMessageRepository;
import main.service.method.chat.ChatMessageMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService implements ChatMessageMethod {
    private final ChatMessageRepository chatMessageRepository;
    @Override
    public void save(ChatMessageDto chatMessageDto) {
        ChatMessage chatMessage = chatMessageDto.toEntity();
        chatMessageRepository.save(chatMessage);
    }

    @Override
    public List<ChatMessageDto> findMessageByRoomNumber(int roomNumber) {
        chatMessageRepository.findMessageByRoomNumber(roomNumber);
        return null;
    }
}
