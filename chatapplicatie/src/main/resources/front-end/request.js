const post = document.getElementById('formRequest');

const ws = new WebSocket('ws://localhost:1234');
ws.addEventListener('message', ev => {
  ev.data.text().then(incomingMessage);
});

document.querySelector('form').onsubmit = ev => {
  ev.preventDefault();
  const input = document.querySelector('input');
  ws.send(input.value);
  outgoingMessage(input.value);
  sendMessage(input.value);
  input.value = '';
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

const sendMessage = () => {
  const newMessage = document.getElementById('message').value;

  sendHttpRequest('POST', 'http://localhost:8080/chatapplicatie/chats/1/2', newMessage.toString()
  ).then(responseData => {

  })
};


function getChatLog(){
    sendHttpRequest('GET', 'http://localhost:8080/chatapplicatie/chats/1/2').then(responseData => {
      for (let message of responseData.messages){
        let cleanMessage = message.content.replace(/['"]+/g, '')
        if (message.senderId === "1"){ //@todo het ID van de huidige gebruiker moet hier komen.
          this.outgoingMessage(cleanMessage);
        } else {
          this.incomingMessage(cleanMessage);
        }
      }
    });
}

function addMessageToChat(message){
  document.getElementById('message').value = '';
  this.outgoingMessage(message)
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
