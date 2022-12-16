<template>
  <div class="position-relative border1px">
    <div class="input_style w-100">
      <ul id="helpline-chat-list">

      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "openHelplinelist",

  mounted() {
    this.getHelplineChats(sessionStorage.getItem('userId'));
  },

  methods: {
    getHelplineChats: function() {
      sendHttpRequest('GET', 'http://localhost:8080/chatapplicatie/chats/helplineList').then(responseData => {
        let ul = document.getElementById("helpline-chat-list");
        for (let helplineChat of responseData.helplineChats) {
          ul.innerHTML +=
              '<form action="http://localhost:63342/prolog-jdi/chatapplicatie/front-end/index.html"> ' +
              '<button type="submit">' + helplineChat.latestMessage + '</button>' +
              '</form>';
        }
      });
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
}
</script>

<style scoped>

</style>