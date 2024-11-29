package com.fl2.gd.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl2.gd.DTOs.ChatMessageDTO;
import com.fl2.gd.Services.ChatMessageService;

@RestController
@RequestMapping("/chat")
@CrossOrigin("*")
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;
    
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    
    @PostMapping("/send")
    public ResponseEntity<ChatMessageDTO> sendMessage(@RequestBody ChatMessageDTO chatMessageDTO) {
        ChatMessageDTO savedMessage = chatMessageService.saveMessage(chatMessageDTO);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/departement/{departementId}")
    public ResponseEntity<List<ChatMessageDTO>> getMessagesByDepartement(@PathVariable int departementId) {
        List<ChatMessageDTO> messages = chatMessageService.getMessagesByDepartement(departementId);
        return ResponseEntity.ok(messages);
    }
    
    @MessageMapping("/chat.sendMessage")
    public void sendMessageSecond(@Payload ChatMessageDTO chatMessageDTO) {
        messagingTemplate.convertAndSend(
            "/topic/departement/" + chatMessageDTO.getDepartementId(),
            chatMessageDTO
        );
    }
}
