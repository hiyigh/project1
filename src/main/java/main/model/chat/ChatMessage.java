package main.model.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.model.Time;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ChatMessage extends Time {
    public enum MessageType {
        ENTER, TALK,QUIT
    }
    private MessageType messageType; // 메시지 타입
    private int roomNumber; // 방번호
    private String userEmail; // 메시지 보낸사람
    private String message; // 메시지
    public ChatMessage(){}
    @Builder
    public ChatMessage(int roomNumber, String userEmail, String message, MessageType messageType) {
        this.roomNumber =roomNumber;
        this.userEmail = userEmail;
        this.message = message;
        this.messageType = messageType;
    }
}
