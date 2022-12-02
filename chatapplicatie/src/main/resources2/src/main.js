import Vue from 'vue'
import App from './App.vue'
import router from './router'

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  template: <App/>,
  components: { App },

  methods:{
    getUnits: function() {getChatLog()}
  },
  created: function(){
    this.getUnits()
  },
  render: h => h(App),
}).$mount('#app')


// js code from develop

function runWebSocket(){
  const webSocket = new WebSocket('ws://localhost:443');

  webSocket.addEventListener('message', data => {
    data.data.text().then(incomingMessage);
  });

  document.getElementById('sendMessageForm').onsubmit = data => {
    const input = document.getElementById('message');

    data.preventDefault();
    if (input.value === ""){
      alert("Please enter a new value");
    } else {
      webSocket.send(input.value);
      outgoingMessage(input.value);
      sendMessage(input.value);
      input.value = '';
    }
  }
}

function getChatLog(userId){
  runWebSocket();

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
  } else{
    sendHttpRequest('GET', 'http://localhost:8080/chatapplicatie/chats/2/1').then(responseData => {
      for (let message of responseData.messages){
        let cleanMessage = message.content.replace(/['"]+/g, '')
        if (message.senderId === userId){
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
    sendHttpRequest('POST', 'http://localhost:8080/chatapplicatie/chats/1/2', newMessage.toString()).then()
  } else{
    sendHttpRequest('POST', 'http://localhost:8080/chatapplicatie/chats/2/1', newMessage.toString()).then()
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
    const XmlHttpRequest = new XMLHttpRequest();
    XmlHttpRequest.open(method, url);
    XmlHttpRequest.responseType = 'json';

    if (data) {
      XmlHttpRequest.setRequestHeader('Content-Type', 'application/json');
    }

    XmlHttpRequest.onload = () => {
      if (XmlHttpRequest.status >= 400) {
        reject(XmlHttpRequest.response);
      } else {
        resolve(XmlHttpRequest.response);
      }
    };

    XmlHttpRequest.send(JSON.stringify(data));
  });
};

