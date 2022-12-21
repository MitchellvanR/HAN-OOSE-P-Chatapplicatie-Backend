<template>
  <div class="container mt-4">
    <div class="row">
      <div class="float-right">
        <button class="btn text-info fa-lg">
          <i class="fa fa-info-circle" aria-hidden="true"></i>
        </button>
      </div>
    </div>
    <div class="row">
      <table class="table table-hover">
        <thead>
        <tr>
          <th scope="col">Chat</th>
          <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <tr @click="log(chatId)" v-for="chatId in items" :key="chatId">
          <td>{{chatId}}</td>
          <td>
            <router-link to="/chat" custom v-slot="{ navigate }">
              <button @click="navigate" role="link" class="btn" v-on:click="setChatId(chatId)"><i class="fa fa-sign-in" aria-hidden="true"></i></button>
            </router-link>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: "openChatList",

  data() {
    return {
      items: [],
    }
  },
  mounted() {
    this.addToItems()
  },
  methods: {
    addToItems: function() {
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/user/' + sessionStorage.getItem("userId")).then(responseData => {
        this.items.push(...responseData.chatIds);
      });
    },
    setChatId: function (chatId){
        sessionStorage.setItem("chatId", chatId);
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
}
</script>

<style scoped>

</style>