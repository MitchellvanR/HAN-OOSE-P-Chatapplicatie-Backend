function runWebSocket(){
  const webSocket = new WebSocket('ws://localhost:1234');

  webSocket.addEventListener('message', data => {
    data.data.text().then(incomingMessage);
  });

  document.getElementById('sendMessageForm').onsubmit = data => {
    const input = document.getElementById('message');

    data.preventDefault();
    webSocket.send(input.value);
    outgoingMessage(input.value);
    sendMessage(input.value);
    input.value = '';
  }
}

function setUserId(userId){
  sessionStorage.setItem("userId", userId);
}

function getChatLog(userId){
  runWebSocket()

  if (userId === 1){
    sendHttpRequest('GET', 'http://localhost:8080/chatapplicatie/chats/1/2').then(responseData => {
      for (let message of responseData.messages){
        let cleanMessage = message.content.replace(/['"]+/g, '')
        if (message.senderId === userId){
          this.outgoingMessage(cleanMessage);
        } else {
          this.incomingMessage(cleanMessage);
        }
      }
    });
  }else{
    sendHttpRequest('GET', 'http://localhost:8080/chatapplicatie/chats/2/1').then(responseData => {
      for (let message of responseData.messages){
        let cleanMessage = message.content.replace(/['"]+/g, '')
        if (message.senderId !== userId){
          this.outgoingMessage(cleanMessage);
        } else {
          this.incomingMessage(cleanMessage);
        }
      }
    });
  }
}

function sendMessage() {
  const newMessage = document.getElementById('message').value;

  if (sessionStorage.getItem('userId') === "1"){
    sendHttpRequest('POST', 'http://localhost:8080/chatapplicatie/chats/1/2', newMessage.toString()).then(responseData => {})
  }else{
    sendHttpRequest('POST', 'http://localhost:8080/chatapplicatie/chats/2/1', newMessage.toString()).then(responseData => {})
  }
}

function outgoingMessage(message){
  const outgoingMessage = document.getElementById('content');

  outgoingMessage.innerHTML += '' +
  '<div class="outgoing_msg"> ' +
    '<div class="sent_msg"> ' +
      '<p>'+ message +'</p>' +
      '<span class="time_date"> 00:00 AM/PM | Today</span>' +
    '</div> ' +
  '</div>'
}

function incomingMessage(message){
  const incomingMessage = document.getElementById('content');

  incomingMessage.innerHTML += '' +
  '<div class="incoming_msg"> ' +
    '<div class="received_msg"> ' +
      '<div class="received_content_msg"> ' +
        '<p>'+ message +'</p>' +
        '<span class="time_date"> 00:00 AM/PM | Today</span>' +
      '</div> ' +
    '</div>' +
  '</div>'
}

const sendHttpRequest = (method, url, data) => {
  return new Promise((resolve, reject) => {
    const xhr = new XMLHttpRequest();
    xhr.open(method, url);

    xhr.responseType = 'json';

    if (data) {
      xhr.setRequestHeader('Content-Type', 'application/json');
    }

    xhr.onload = () => {
      if (xhr.status >= 400) {
        reject(xhr.response);
      } else {
        resolve(xhr.response);
      }
    };

    xhr.onerror = () => {
      reject('Something went wrong!');
    };

    xhr.send(JSON.stringify(data));
  });
};