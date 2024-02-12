package main.model.chat;

import jakarta.websocket.Session;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.model.Time;
import main.model.User;

import java.util.List;

@Getter
@Setter
public class ChatRoom extends Time {
    private int roomNumber;
    private Long userId;
    private Long otherId;
    public ChatRoom() {
    }
    @Builder
    public ChatRoom(Long userId, Long otherId) {
        this.userId = userId;
        this.otherId = otherId;
    }
}
