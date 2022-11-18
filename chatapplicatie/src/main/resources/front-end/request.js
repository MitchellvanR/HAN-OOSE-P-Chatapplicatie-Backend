const post = document.getElementById('formRequest');

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

function getChatLog(){

    let responseData = {};

    sendHttpRequest('GET', 'http://localhost:8080/chatapplicatie/chats/1/2').then(chatHistory => {
      responseData = chatHistory;
    });

  const incomingMessage = document.getElementById('content');
  const outgoingMessage = document.getElementById('content');

  for (let message of responseData.messages){
    if (message.senderId === "1"){ //@todo het ID van de huidige gebruiker moet hier komen.
      incomingMessage.innerHTML += '' +
          '<div class="outgoing_msg"> ' +
            '<div class="sent_msg"> ' +
              '<p>'+message.message+'</p>' +
              '<span class="time_date"> 11:01 AM | Today</span>' +
            '</div> ' +
          '</div>'
    } else {
      outgoingMessage.innerHTML += '' +
          '<div class="incoming_msg"> ' +
            '<div class="received_msg"> ' +
              '<div class="received_content_msg"> ' +
                '<p>'+message.message+'</p>' +
                '<span class="time_date"> 11:01 AM | Today</span>' +
              '</div> ' +
            '</div>' +
          '</div>'
    }
  }
}

function addMessageToChat(){
//
}

const sendMessage = () => {
  sendHttpRequest('POST', 'http://localhost:8080/chatapplicatie/chats', {
    message:
        {
          "senderId":"1",
          "receiverId":"2",
          "message":document.getElementById('message').value,
        },
    })
    .catch(err => {
      console.log(err);
    });


};

post.addEventListener('click', sendMessage);

