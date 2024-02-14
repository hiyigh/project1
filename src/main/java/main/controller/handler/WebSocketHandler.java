package main.controller.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.dto.chat.ChatMessageDto;
import main.model.chat.ByteArrayMultipartFile;
import main.model.chat.ChatMessage;
import main.model.chat.ChatRoom;
import main.repository.chat.ChatMessageRepository;
import main.service.chat.AwsS3Service;
import main.service.method.chat.ChatMessageMethod;
import main.service.method.chat.ChatRoomMethod;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatRoomMethod chatRoomMethod;
    private final ChatMessageRepository chatMessageRepository;
    private final List<WebSocketSession> chatMemberList = new ArrayList<>();
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        int sessionLenght = 0;
        if (chatMemberList.isEmpty()) {
            chatMemberList.add(session);
        } else {
            sessionLenght = chatMemberList.size();
            boolean check = true;
            for (int i = 0; i < sessionLenght; ++i) {
                if (session.getPrincipal().getName().equals(chatMemberList.get(i).getPrincipal().getName())) {
                    check = false;
                    break;
                }
            }
            if (check) {
                chatMemberList.add(session);
            }
        }
        String payload = message.getPayload();
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);


        if (chatMessage.getMessageType().equals(ChatMessage.MessageType.ENTER)) {
            ChatRoom chatRoom =  chatRoomMethod.findByRoomNumberOrNull(chatMessage.getRoomNumber());
            List<ChatMessage> chatMessageList = new ArrayList<>();
            if (chatRoom != null) {
                chatMessageList = chatMessageRepository.findMessageByRoomNumber(chatRoom.getRoomNumber());
            }
            for (int i = 0; i < sessionLenght; ++i) {
                chatMessage.setMessage(chatMessage.getUserEmail() + "님이 입장했습니다.");
                chatMemberList.get(i).sendMessage(new TextMessage(chatMessage.getUserEmail() + " : " + chatMessage.getMessage()));

                if (!chatMessageList.isEmpty()) {
                    for (int j = 0; j < chatMessageList.size(); ++j) {
                        ChatMessage msg = chatMessageList.get(j);
                        chatMemberList.get(i).sendMessage(new TextMessage(msg.getUserEmail() + " : " + msg.getMessage()));
                    }
                }
            }
        }
        else if (chatMessage.getMessageType().equals(ChatMessage.MessageType.QUIT)) {
            for (int i = 0; i < sessionLenght; ++i) {
                chatMessage.setMessage(chatMessage.getUserEmail() + "님이 퇴장했습니다.");
                chatMemberList.get(i).sendMessage(new TextMessage(chatMessage.getUserEmail() + " : " + chatMessage.getMessage()));
            }
        }else {
            for (int i = 0; i < sessionLenght; ++i) {
                chatMessageRepository.save(chatMessage);
                System.out.println("save chatMessage: " + i + chatMessage.getMessage());
                chatMemberList.get(i).sendMessage(new TextMessage(chatMessage.getUserEmail() + " : " + chatMessage.getMessage()));
            }
        }
    }
}
