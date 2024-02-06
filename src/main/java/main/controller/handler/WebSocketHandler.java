package main.controller.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.dto.chat.ChatMessageDto;
import main.model.chat.ByteArrayMultipartFile;
import main.model.chat.ChatMessage;
import main.model.chat.ChatRoom;
import main.service.chat.AwsS3Service;
import main.service.method.chat.ChatMessageMethod;
import main.service.method.chat.ChatRoomMethod;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;


@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatRoomMethod chatRoomMethod;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom chatRoom =  chatRoomMethod.findByRoomNumberOrNull(chatMessage.getRoomNumber());

        if (chatMessage.getMessageType().equals(ChatMessage.MessageType.ENTER)) {
            chatMessage.setMessage(chatMessage.getUserEmail() + "님이 입장했습니다.");
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
        }
        else if (chatMessage.getMessageType().equals(ChatMessage.MessageType.QUIT)) {
            chatMessage.setMessage(chatMessage.getUserEmail() + "님이 퇴장했습니다..");
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
        }else {
            session.sendMessage(message);
        }
    }
}
