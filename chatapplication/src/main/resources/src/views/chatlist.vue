<template>
  <div class="position-relative border1px">
    <div class="input_style w-100">
      <div id="helpline-button">
        <i>Chatlijst</i>
        <br>
      </div>
      <ul id="chat-list">

      </ul>
    </div>
  </div>
</template>

<script>
import reusableFunctions from '../components/reusableFunctions.vue';

export default {
  name: "openChatlist",

  mounted() {
    this.getUserChatInterface(sessionStorage.getItem('userId'));
  },
  Components: {
    reusableFunctions
  },
  methods: {
    getUserChatInterface: function(userId) {
      this.getUserChats(userId);
      this.makeHelplineButton(userId);
    },
    getUserChats: function (userId) {
      reusableFunctions.methods.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/' + userId).then(responseData => {
        let ul = document.getElementById("chat-list");

        for (let chatId of responseData.chatIds) {
          ul.innerHTML +=
              '<button type="submit" class="btn btn-outline-primary" onclick="alert(1)">' + 'ChatNummer: ' + 1 + '</button>'

            // '<form action="chat"> ' +
            //   '<router-linkcustom v-slot="{ navigate }">' +
            //     '<button type="submit" class="btn btn-outline-primary" onclick="'+ this.setChatId(chatId) + '">' + 'ChatNummer: ' + chatId + '</button>'
              // '</router-link>'
            // '</form>';
        }
      });
    },
    setChatId: function (chatId) {
      console.log("sad", chatId)
      sessionStorage.setItem("chatId", chatId);
    },
    makeHelplineButton: function (/*userId*/) {
      // console.log(1234567)
      // reusableFunctions.methods.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/helpline/' + userId).then(responseData => {
      //   let div = document.getElementById("helpline-button");
      //   div.innerHTML +=
      //       '<form action="http://localhost:63342/prolog-jdi/chatapplicatie/front-end/index.html">' +
      //       '<button type="submit" onclick="setChatId(' + responseData.chatId + '); setHelpline(1)">Helpline</button>' +
      //       '</form>';
      // });
    },
  }
}
</script>

<style scoped>

</style>