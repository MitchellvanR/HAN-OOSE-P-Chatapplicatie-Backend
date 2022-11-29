package jdi.chat.application.controllers;

import jdi.chat.application.data.dto.MessageDTO;
import jdi.chat.application.models.Chat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatControllerTest {
    private ChatController sut;
    private Chat chatMock;

    @BeforeEach
    public void setup() {
        this.sut = new ChatController();
        this.chatMock = Mockito.mock(Chat.class);
        this.chatMock = new Chat("1","2");
    }

//    @Test
//    public void givenSenderIsTheSameAsChatSender() {
//        Assertions.assertEquals("1", chatMock.getSenderId());
//    }
//
//    @Test
//    public void givenReceiverIsTheSameAsChatReceiver() {
//        Assertions.assertEquals("2", chatMock.getReceiverId());
//    }

    @Test
    public void returnArrayListMessages() throws SQLException {

        ArrayList<MessageDTO> messages = new ArrayList<>();
        messages.add(new MessageDTO("1", "2", "Hello World"));

        Mockito.when(chatMock.getChatHistory()).thenReturn(messages);

        Assertions.assertEquals(messages, chatMock.getChatHistory());

    }
}