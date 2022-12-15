<template>
  <div id="frame" class="mt-3">
    <div class="content border">
      <div class="contact-profile">
        <div class="row">
          <b>naam reciever komt hier</b>
        </div>
      </div>
      <div class="messages" id="test">
        <ul id="content">
        </ul>
      </div>
      <div class="message-input border-top border-dark p-2">
        <form id="sendMessageForm" class="wrap">
          <input type="text" id="message" placeholder="Write your message..." />
          <button class="btn" type="submit"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
          <button class="btn" type="button"><i class="fa fa-paperclip" aria-hidden="true"></i></button>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'OpenChat',
  mounted() {
    this.getChatLog(sessionStorage.getItem('userId'));
  },
  destroyed() {
    if (this.webSocket.readyState === WebSocket.OPEN) {
      this.webSocket.close();
      this.webSocket = null;
    }
  },
  methods: {
    getChatLog: function (userId) {
      this.runWebSocket();
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/1').then(responseData => {
        for (let message of responseData.messages) {
          if (message.senderId === userId) {
            this.outgoingMessage(message.message, message.time);
          } else {
            this.incomingMessage(message.message, message.time);
          }
        }
      });
    },

    runWebSocket: function () {
      this.webSocket = new WebSocket('ws://localhost:443');

      this.webSocket.addEventListener('message', data => {
        data.data.text().then(this.incomingMessage);
      });

      document.getElementById('sendMessageForm').onsubmit = data => {
        const input = document.getElementById('message');
        input.classList.remove("border", "border-danger");

        data.preventDefault();
        if (input.value === ""){
          input.classList.add("border", "border-danger");
        } else {
          this.webSocket.send(input.value);
          this.outgoingMessage(input.value, this.getCurrentTime());
          this.sendMessage(input.value);
          input.value = '';
        }
      }
    },
    sendMessage: function () {
      const newMessage = document.getElementById('message').value;

      if (sessionStorage.getItem('userId') === "1"){
        this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/chats/1/1', newMessage).then()
      } else{
        this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/chats/2/1', newMessage).then()
      }
    },
    outgoingMessage: function (message, time) {
      const outgoingMessage = document.getElementById('content');

      outgoingMessage.innerHTML += '' +
          '<li class="replies mb-3">' +
            '<small class="float-right margin-right-5px">'+ time +'</small>' +
            '<br>' +
            '<p> '+ this.filterMessage(message) +' </p>' +
          '</li>'
    },
    incomingMessage: function (message, time = this.getCurrentTime()) {
      const incomingMessage = document.getElementById('content');
      incomingMessage.innerHTML += '' +
          '<li class="sent mb-3">' +
          '<small class="margin-left-5px">'+ time +'</small>' +
          '<br>' +
          '<p> '+ this.filterMessage(message) +' </p>' +
          '</li>'

    },
    sendHttpRequest: function (method, url, data) {
      return new Promise((resolve, reject) => {
        const XmlHttpRequest = new XMLHttpRequest();
        XmlHttpRequest.open(method, url);
        XmlHttpRequest.responseType = 'json';

        XmlHttpRequest.setRequestHeader('Content-Type', 'application/json');

        XmlHttpRequest.onload = () => {
          if (XmlHttpRequest.status >= 400) {
            reject(XmlHttpRequest.response);
          } else {
            resolve(XmlHttpRequest.response);
          }
        };

        XmlHttpRequest.send(data);
      });
    },
    filterMessage: function (message) {
      return message.replace(/<\/?[^>]+>/gi, '')
    },
    getCurrentTime: function () {
      let date = new Date();
      return date.getHours() + ':' + (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();    },
  }
}
</script>


<style scoped>
/* ===== Scrollbar CSS ===== */
/* Firefox */
* {
  scrollbar-width: thin;
  scrollbar-color: #000000 #e6eaea;
}

/* Chrome, Edge, and Safari */
*::-webkit-scrollbar {
  width: 16px;
}

*::-webkit-scrollbar-track {
  background: #e6eaea;
}

*::-webkit-scrollbar-thumb {
  background-color: #000000;
  border-radius: 10px;
  border: 3px solid #e6eaea;
}
</style>