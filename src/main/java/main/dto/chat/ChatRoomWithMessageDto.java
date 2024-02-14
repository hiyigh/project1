package main.dto.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import main.model.chat.ChatMessage;
import main.model.chat.ChatRoom;

import java.util.List;

@Getter
@Setter
public class ChatRoomWithMessageDto {
    private final ChatRoom chatRoom;
    private final List<ChatMessage> chatMessageList;
    public ChatRoomWithMessageDto(ChatRoom chatRoom, List<ChatMessage> chatMessageList){
        this.chatRoom = chatRoom;
        this.chatMessageList = chatMessageList;
    }
}
