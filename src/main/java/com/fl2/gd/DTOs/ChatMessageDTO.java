package com.fl2.gd.DTOs;

public class ChatMessageDTO {
    private String content;
    private String senderUsername; 
    private int departementId;    
    private String timestamp;       

   

    public ChatMessageDTO() {}

    public ChatMessageDTO(String content, String senderUsername, int departementId, String timestamp) {
        this.content = content;
        this.senderUsername = senderUsername;
        this.departementId = departementId;
        this.timestamp = timestamp;
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	public int getDepartementId() {
		return departementId;
	}

	public void setDepartementId(int departementId) {
		this.departementId = departementId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

    
}
