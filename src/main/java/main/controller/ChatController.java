package main.controller;

import lombok.RequiredArgsConstructor;
import main.dto.PrincipalDetails;
import main.dto.chat.ChatMessageDto;
import main.dto.chat.ChatRoomDto;
import main.dto.chat.ChatRoomWithMessageDto;
import main.model.User;
import main.model.chat.ChatMessage;
import main.model.chat.ChatRoom;
import main.repository.chat.ChatRoomRepository;
import main.service.chat.ChatRoomService;
import main.service.method.UserMethod;
import main.service.method.chat.ChatMessageMethod;
import main.service.method.chat.ChatRoomMethod;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final UserMethod userMethod;
    private final ChatMessageMethod chatMessageMethod;
    private final ChatRoomMethod chatRoomMethod;
    private final ChatRoomRepository chatRoomRepository;
    private static final Long adminId = 1l;

    @GetMapping("/chat")
    public String getStart(Authentication authentication, Model model) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = userMethod.getUserByEmailOrNull(principalDetails.getUsername());

        ChatRoom chatRoom = chatRoomRepository.findByUserAndOtherIdOrNull(user.getUserId(), adminId);
        if (chatRoom == null) {
            chatRoom = chatRoomMethod.createRoom(user.getUserId(), adminId);
        }

        model.addAttribute("user", user);
        model.addAttribute("room", chatRoom);
        return "/chat/chat";
    }
    @PostMapping("/chat/createRoom")
    public String createRoom(Authentication authentication, Model model){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = userMethod.getUserByEmailOrNull(principalDetails.getUsername());
        assert(user != null);
        ChatRoom chatRoom = chatRoomRepository.findByUserAndOtherIdOrNull(user.getUserId(), adminId);
        model.addAttribute("user", user);

        if (chatRoom != null) {
            List<ChatMessageDto> chatMessageDtoList = chatMessageMethod.findMessageByRoomNumber(chatRoom.getRoomNumber());
            ChatRoomWithMessageDto chatRoomWithMessageDto = new ChatRoomWithMessageDto(chatRoom, chatMessageDtoList);
            model.addAttribute("roomInfo", chatRoomWithMessageDto);

            return "/chat/chatRoom";
        } else {
            ChatRoom createNewRoom = chatRoomMethod.createRoom(user.getUserId(), adminId);
            List<ChatMessageDto> chatMessageDtoList = new ArrayList<>();
            ChatRoomWithMessageDto chatRoomWithMessageDto = new ChatRoomWithMessageDto(createNewRoom, chatMessageDtoList);
            model.addAttribute("roomInfo", chatRoomWithMessageDto);
            return "/chat/chatRoom";
        }
    }
    @GetMapping("/chat/getAllList")
    public String getAllList(Model model) {
        List<ChatRoomWithMessageDto> chatRoomWithMessageDtoList = new ArrayList<>();
        List<ChatRoom> chatRoomList = chatRoomMethod.getAllRoomOrNull();
        if (chatRoomList != null) {
            for (ChatRoom chatRoom : chatRoomList) {
                List<ChatMessageDto> chatMessageDtoList = chatMessageMethod.findMessageByRoomNumber(chatRoom.getRoomNumber());
                chatRoomWithMessageDtoList.add(new ChatRoomWithMessageDto(chatRoom, chatMessageDtoList));
            }
            model.addAttribute("roomList", chatRoomWithMessageDtoList);
            return "/chat/chat";
        } else {
            model.addAttribute("roomList", chatRoomWithMessageDtoList);
            return "/chat/chat";
        }
    }
}
