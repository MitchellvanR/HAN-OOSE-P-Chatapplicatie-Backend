<template>
  <div class="position-relative border1px">
    <div class="input_style w-100">
      <div id="helpline-button">

        <br>
      </div>
      <ul id="chat-list">

      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "openChatlist",

  mounted() {
    this.getUserChatInterface(sessionStorage.getItem('userId'));
  },

  methods: {
    getUserChatInterface: function (userId) {
      this.getUserChats(userId);
      this.makeHelplineButton(userId);
    }
  },

  getUserChats: function(userId) {
    this.sendHttpRequest('GET', 'http://localhost:8080/chatapplicatie/chats/' + userId).then(responseData => {
      let ul = document.getElementById("chat-list");
      for (let chatId of responseData.chatIds) {
        ul.innerHTML +=
            '<form action="http://localhost:63342/prolog-jdi/chatapplicatie/front-end/index.html"> ' +
            '<button type="submit" onclick="setChatId(' + chatId + '); setHelpline(0)">' + 'Chatnummer: ' + chatId + '</button>' +
            '</form>';
      }
    });
  },

  makeHelplineButton: function(userId) {
  this.sendHttpRequest('GET', 'http://localhost:8080/chatapplicatie/chats/helpline/' + userId).then(responseData => {
    let div = document.getElementById("helpline-button");
    console.log(responseData.chatId)
    div.innerHTML +=
        '<form action="http://localhost:63342/prolog-jdi/chatapplicatie/front-end/index.html">' +
        '<button type="submit" onclick="setChatId(' + responseData.chatId + '); setHelpline(1)">Helpline</button>' +
        '</form>';
  });
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

}
</script>

<style scoped>

</style>