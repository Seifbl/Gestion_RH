package com.fl2.gd.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fl2.gd.DTOs.ChatMessageDTO;
import com.fl2.gd.Entities.AppUser;
import com.fl2.gd.Entities.ChatMessage;
import com.fl2.gd.Entities.Departement;
import com.fl2.gd.Repositories.ChatMessageRepository;
import com.fl2.gd.Repositories.DepartementRepository;
import com.fl2.gd.Repositories.UserRepository;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private DepartementRepository departementRepository;  

    @Autowired
    private UserRepository appUserRepository;  

    public ChatMessageDTO saveMessage(ChatMessageDTO chatMessageDTO) {
        AppUser sender = appUserRepository.findByUsernameNonOptional(chatMessageDTO.getSenderUsername());
        Departement department = departementRepository.findById(chatMessageDTO.getDepartementId()) 
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(chatMessageDTO.getContent());
        chatMessage.setSender(sender);
        chatMessage.setDepartement(department);
        chatMessage.setTimestamp(LocalDateTime.now());

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        return convertToDTO(savedMessage);
    }

    public List<ChatMessageDTO> getMessagesByDepartement(int departementId) {
        List<ChatMessage> messages = chatMessageRepository.findByDepartementId(departementId);

        return messages.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList());
    }

    private ChatMessageDTO convertToDTO(ChatMessage chatMessage) {
        return new ChatMessageDTO(
            chatMessage.getContent(),
            chatMessage.getSender().getUsername(),
            chatMessage.getDepartement().getDepartementId(),
            chatMessage.getTimestamp().toString()
        );
    }
}
