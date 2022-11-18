package controllers;

import models.Chat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class testChatControllerSendMessage {
    private ChatController sut;
    private ArrayList<Chat> chatList;
    private Chat mockedChat;
    private String message;

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
        sut.sendMessage(message);

        //Assert
        Mockito.verify(mockedChat).sendMessage(Mockito.anyString());
    }
}
