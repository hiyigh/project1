package main.dto.chat;

import lombok.Getter;
import lombok.Setter;
import main.model.chat.ChatRoom;

@Getter
@Setter
public class ChatRoomDto {
    private int roomNumber;
    private String userName;
    private String msg;
    private String msgCreatedTime;
    public ChatRoomDto(ChatRoom chatRoom, String userName, String msg, String msgCreatedTime) {
        this.roomNumber = chatRoom.getRoomNumber();
        this.userName = userName;
        this.msg = msg;
        this.msgCreatedTime = msgCreatedTime;
    }
}
