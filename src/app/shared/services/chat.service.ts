import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Client, Message } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private stompClient: Client;
  private apiUrl = 'http://localhost:3600/chat';
  private connected = false;

  constructor(private http: HttpClient) {
    this.initializeWebSocketConnection();
  }

  private initializeWebSocketConnection(): void {
    const socket = new SockJS('http://localhost:3600/ws');
    this.stompClient = new Client({
      webSocketFactory: () => socket,
      debug: (str) => console.log(str),
      reconnectDelay: 5000,
      onConnect: () => {
        this.connected = true;
        console.log('STOMP client connected');
      },
      onDisconnect: () => {
        this.connected = false;
        console.log('STOMP client disconnected');
      },
      onStompError: (frame) => {
        console.error('STOMP error:', frame);
      }
    });
    this.stompClient.activate();
  }

  // Connect to a specific department
  connect(departementId: number, callback: (message: Message) => void): void {
    if (!this.connected) {
      console.error('STOMP client is not connected.');
      return;
    }
    this.stompClient.onConnect = () => {
      this.stompClient.subscribe(`/topic/departement/${departementId}`, callback);
      console.log(`Subscribed to /topic/departement/${departementId}`);
    };
  }

  // Send a message
  sendMessage(departementId: number, content: string, senderUsername: string): void {
    if (!this.connected) {
      console.error('STOMP client is not connected.');
      return;
    }
    const message = {
      content: content,
      senderUsername: senderUsername,
      departementId: departementId,
      timestamp: new Date().toISOString()
    };

    this.stompClient.publish({
      destination: '/app/chat.sendMessage',
      body: JSON.stringify(message)
    });

    console.log(`Sent message to /app/chat.sendMessage: ${JSON.stringify(message)}`);
  }

  sendMessageHttp(message: any): any {
    return this.http.post(`${this.apiUrl}/send`, message);
  }

  getMessagesByDepartement(departementId: number): any {
    return this.http.get(`${this.apiUrl}/departement/${departementId}`);
  }
}