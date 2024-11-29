import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Message } from '@stomp/stompjs';
import { Employee } from 'src/app/modals/employee.modal';
import { ChatService } from 'src/app/shared/services/chat.service';
import { EmployeeService } from 'src/app/shared/services/employee.service';
import { UserService } from 'src/app/shared/services/user.service';
import {
  FormGroup,
  FormControl,
  FormBuilder,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css'],
})
export class ChatComponent implements OnInit, OnDestroy {
  departementId: number;
  messages: any[] = [];
  newMessage: string = '';
  senderUsername: string;

  employeeId: any;
  employee: Employee = new Employee();

  isAuthorized: boolean = false;
  userType: string;
  loggedInUserId: any;

  private pollingInterval: any;
  private pollingIntervalTime: number = 500;

  constructor(
    private chatService: ChatService,
    private cdr: ChangeDetectorRef,
    private employeeService: EmployeeService,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.userService.getUserProperties().subscribe((res1) => {
      this.userType = res1.authorities[0].authority;
      if (this.userType === 'USER' || this.userType === 'CHEF DEPARTEMENT') {
        this.isAuthorized = true;
      }
      this.senderUsername = res1.username;
      this.userService.getUserInfo(res1.userId).subscribe((res2) => {
        this.loggedInUserId = res2.employeeId;
        this.employeeService.getEmployee(res2.employeeId).subscribe((res3) => {
          this.employee = res3;
          this.departementId = res3.departement.departementId;
          this.chatService
            .getMessagesByDepartement(this.departementId)
            .subscribe(
              (messages: any[]) => {
                this.messages = messages;
                console.log('Fetched messages:', messages);
              },
              (error: any) => {
                console.error('Error fetching messages:', error);
              }
            );
          this.chatService.connect(
            this.departementId,
            this.onMessageReceived.bind(this)
          );
          this.pollingInterval = setInterval(
            () => this.fetchMessages(),
            this.pollingIntervalTime
          );
        });
      });
    });
  }

  ngOnDestroy(): void {
    if (this.pollingInterval) {
      clearInterval(this.pollingInterval);
    }
  }

  onMessageReceived(message: Message): void {
    const receivedMessage = JSON.parse(message.body);
    this.messages.push(receivedMessage);
    this.cdr.detectChanges();
  }

  // Send a new message
  sendMessage(): void {
    if (this.newMessage.trim()) {
      const message = {
        content: this.newMessage,
        senderUsername: this.senderUsername,
        departementId: this.departementId,
        timestamp: new Date().toISOString(),
      };
      console.log(message);
      this.chatService.sendMessageHttp(message).subscribe(
        (response: any) => {
          console.log('Message saved:', response);
          this.chatService.sendMessage(
            this.departementId,
            this.newMessage,
            this.senderUsername
          );
          this.newMessage = '';
        },
        (error: any) => {
          console.error('Error saving message:', error);
        }
      );
    }
  }
  private fetchMessages(): void {
    this.chatService.getMessagesByDepartement(this.departementId).subscribe(
      (messages: any[]) => {
        this.messages = messages;
        this.cdr.detectChanges(); //
      },
      (error: any) => {
        console.error('Error fetching messages:', error);
      }
    );
  }
}
