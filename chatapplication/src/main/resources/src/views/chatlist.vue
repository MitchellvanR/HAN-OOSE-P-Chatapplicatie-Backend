<template>
  <div class="position-relative border1px">
    <div class="input_style w-100">
      <div id="helpline-button">

        <br>
      </div>
      <button @click="println()">get id</button>

      <ul id="chat-list">

      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "openChatlist",
  mounted() {
    this.getChatlist(sessionStorage.getItem('userId'));
  },
  methods: {
    getChatlist: function(userId) {
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/user/' + userId).then(responseData => {
        let ul = document.getElementById("chat-list");
        for (let chatId of responseData.chatIds) {
          ul.innerHTML +=
            // '<form action="http://localhost:63342/prolog-jdi/chatapplicatie/front-end/index.html"> ' +
            '<button v-on:click="' + sessionStorage.setItem("chatId", chatId) + '">' + 'Chatnummer: ' + chatId + '</button>'

          // '</form>';
        }
      });
    },
    println: function() {
      console.log(sessionStorage.getItem("chatId"))
    },
    sendHttpRequest: function(method, url, data) {
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
    }
  },
}
</script>

<style scoped>

</style>