<template>
  <div class="container mt-5">
    <div class="overflow-hidden border w-100">
      <h3 class="text-center">Gebruiker #</h3>
      <div class="container">
        <div class="overflow-auto h-500px" id="content"></div>
        <div class="position-relative border1px">
          <div class="input_style w-100">
            <form id="sendMessageForm">
              <input type="text" class="w-75 p-1 mb-2" id="message"/>
              <label for="message"></label>
              <button class="custom-btn" type="submit"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'ChatApplication',
  mounted() {
    this.getChatLog(sessionStorage.getItem('userId'))
  },
  methods: {
    getChatLog: function (userId) {
      this.runWebSocket();

      this.sendHttpRequest('GET', 'http://localhost:3000/chat/1').then(responseData => {
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
      const webSocket = new WebSocket('ws://localhost:443');

      webSocket.addEventListener('message', data => {
        data.data.text().then(this.incomingMessage);
      });

      document.getElementById('sendMessageForm').onsubmit = data => {
        const input = document.getElementById('message');
        input.classList.remove("border", "border-danger");

        data.preventDefault();
        if (input.value === ""){
          input.classList.add("border", "border-danger");
        } else {
          webSocket.send(input.value);
          this.outgoingMessage(input.value, this.getCurrentTime());
          this.sendMessage(input.value);
          input.value = '';
        }
      }
    },
    sendMessage: function () {
      const newMessage = document.getElementById('message').value;

      if (sessionStorage.getItem('userId') === "1"){
        this.sendHttpRequest('POST', 'http://localhost:8080/chat/1/1', newMessage).then()
      } else{
        this.sendHttpRequest('POST', 'http://localhost:8080/chat/2/1', newMessage).then()
      }
    },
    outgoingMessage: function (message, time) {
      const outgoingMessage = document.getElementById('content');

      outgoingMessage.innerHTML += '' +
          '<div class="outgoing_msg"> ' +
          '<div class="sent_msg"> ' +
          '<p>'+  this.filterMessage(message) +'</p>' +
          '<span class="time_date float_right">'+ time +'</span>' +
          '</div> ' +
          '</div>'
    },
    incomingMessage: function (message, time = this.getCurrentTime()) {
      const incomingMessage = document.getElementById('content');

      incomingMessage.innerHTML += '' +
          '<div class="received_msg"> ' +
          '<div class="received_content_msg"> ' +
          '<p>'+ this.filterMessage(message) +'</p>' +
          '<span class="time_date">'+ time +'</span>' +
          '</div> ' +
          '</div>'
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

        XmlHttpRequest.send(JSON.stringify(data));
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
.h-500px { height: 500px;}
.border1px {border-top: 1px solid #c4c4c4;}

.input_style input {
  background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
  border: medium none;
  color: #4c4c4c;
  font-size: 15px;
  min-height: 48px;
}
.custom-btn {
  background: #05728f none repeat scroll 0 0;
  border: medium none;
  border-radius: 50%;
  color: #fff;
  cursor: pointer;
  font-size: 17px;
  height: 33px;
  position: absolute;
  right: 0;
  top: 11px;
  width: 33px;
}

.sent_msg p {
  background: #05728f none repeat scroll 0 0;
  border-radius: 3px;
  font-size: 14px;
  margin: 0; color:#fff;
  padding: 5px 10px 5px 12px;
  width:100%;
}

.received_content_msg p {
  background: #ebebeb none repeat scroll 0 0;
  border-radius: 3px;
  color: #646464;
  font-size: 14px;
  margin: 0;
  padding: 5px 10px 5px 12px;
  width: 100%;
}
</style>