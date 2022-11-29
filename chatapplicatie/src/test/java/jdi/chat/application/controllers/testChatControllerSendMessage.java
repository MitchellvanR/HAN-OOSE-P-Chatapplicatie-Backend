package jdi.chat.application.controllers;

import jdi.chat.application.models.Chat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class testChatControllerSendMessage {
    private ChatController sut;
    private ArrayList<Chat> chatList;
    private Chat mockedChat;
    private String message;
    private String senderId = "1";
    private String receiverId = "2";

    @BeforeEach
    public void setup(){
        this.sut = new ChatController();
        this.mockedChat = Mockito.mock(Chat.class);
        this.chatList = new ArrayList<>();
        this.chatList.add(mockedChat);
        sut.setChats(chatList);
    }

    @Test
    public void testSendMessageChatController(){
        //Arrange
        message = "Hello World";
        Mockito.doNothing().when(mockedChat).sendMessage(message);

        //Act
        sut.sendMessage(senderId, receiverId, message);

        //Assert
        Mockito.verify(mockedChat).sendMessage(Mockito.anyString());
    }
}
