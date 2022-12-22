<template>
  <div id="frame" class="mt-3">
    <div class="content border">
      <div class="contact-profile">
        <div class="row">
          <strong><u>Receiver</u></strong>
        </div>
      </div>
      <div class="messages" id="messages">
        <ul id="content" v-for="message in data" :key="message">
          <li v-if="message.senderId === sessionStorage.getItem('userId')" class="replies mb-3">
            <small class="float-right margin-right-5px">{{message.time}}</small>
            <br>
            <p>{{message.message}}</p>
          </li>
          <li v-else class="sent mb-3">
            <small class="margin-left-5px">{{message.time}}</small>
            <br>
            <p>{{message.message}}</p>
          </li>
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
  data() {
    return {
      data: [],
    }
  },
  mounted() {
    this.getChatLog();
  },
  destroyed() {
    if (this.webSocket.readyState === WebSocket.OPEN) {
      this.webSocket.close();
      this.webSocket = null;
    }
  },
  methods: {
    getChatLog: function () {
      this.runWebSocket();
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/1').then(responseData => {
        this.data.push(...responseData.messages);
        this.scrollToBottom()
      });
    },
    scrollToBottom: function (){
      const element = document.getElementById('messages');
      element.scrollTop = element.scrollHeight;
    },
    runWebSocket: function () {
      this.webSocket = new WebSocket('ws://localhost:443');

      this.webSocket.addEventListener('message', data => {
        console.log(data)
        data.data.text().then(this.incomingMessage);
        // this.data.push(...responseData.messages);

      });

      document.getElementById('sendMessageForm').onsubmit = data => {
        const input = document.getElementById('message');
        input.classList.remove("border", "border-danger");

        data.preventDefault();
        if (input.value === ""){
          input.classList.add("border", "border-danger");
        } else {
          this.webSocket.send(input.value);
          let sad = {
            message: {
              message: input.value,
              time: this.getCurrentTime(),
            }
          }
          this.data.push(...sad);

          this.sendMessage(input.value);
          input.value = '';
        }
      }
    },
    sendMessage: function () {
      const newMessage = document.getElementById('message').value;
      this.scrollToBottom();

      if (sessionStorage.getItem('userId') === "1"){
        this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/chats/1/1', newMessage).then()
      } else{
        this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/chats/2/1', newMessage).then()
      }
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