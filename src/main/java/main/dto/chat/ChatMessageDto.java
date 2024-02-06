package main.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.model.chat.ChatMessage;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDto {
    public enum MessageType {
        ENTER,
        TALK
    }
    private String userEmail;
    private String message;
    private int roomNumber;
    private ChatMessage.MessageType messageType;
    @Builder
    public ChatMessageDto(String userEmail, String message, int roomNumber, ChatMessage.MessageType messageType) {
        this.message = message;
        this.userEmail = userEmail;
        this.roomNumber=roomNumber;
        this.messageType = messageType;
    }
    public ChatMessage toEntity() {
        return ChatMessage.builder()
                .messageType(messageType)
                .message(message)
                .roomNumber(roomNumber)
                .userEmail(userEmail)
                .build();
    }
}
