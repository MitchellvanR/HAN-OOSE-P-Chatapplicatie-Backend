<template>
  <div class="container mt-5">
    <div class="row">
      <p class="display-4">Helpline list</p>
      <small><i class="fa fa-exclamation-circle" aria-hidden="true"></i> Note! This is page is used for mocking purposes.</small>
      <hr>
    </div>
    <div class="row">
      <div class="col-lg-11">
        <table class="table table-hover m-4 w-100">
          <thead>
          <tr>
            <th>Helpline chat</th>
            <th>Latest message</th>
            <th>Action</th>
          </tr>
          </thead>
          <tbody>
          <tr @click="log(chatId)" v-for="(helplineChat, index) in array" :key="index">
            <td>{{helplineChat.chatId}}</td>
            <td>{{helplineChat.latestMessage}}</td>
            <td>
              <router-link to="/chat" custom v-slot="{ navigate }">
                <button @click="navigate" role="link" class="btn" v-on:click="setChatId(helplineChat.chatId)"><i class="fa fa-sign-in" aria-hidden="true"></i></button>
              </router-link>
            </td>
            <td class="w-25"></td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "openHelplinelist",
  data() {
    return {
      array: []
    }
  },
  mounted() {
    this.getHelplineChats();
  },
  methods: {
    getHelplineChats: function() {
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/helplineList').then(responseData => {
        console.log(responseData.helplineChats);
        this.array.push(...responseData.helplineChats);
      });
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
      })
    }
  }
}
</script>

<style scoped>

</style>