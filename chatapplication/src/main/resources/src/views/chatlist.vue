<template>
  <div class="container mt-5">
    <div class="row">
      <p class="display-4">User Menu</p>
      <small><i class="fa fa-exclamation-circle" aria-hidden="true"></i> Note! This is page is used for mocking purposes.</small>
      <hr>
    </div>
    <div class="row">
      <form id="newChatForm" class="wrap">
        <div class="col-lg-12">
          <router-link to="/chat" custom v-slot="{ navigate }">
            <button class="btn text-info fa-lg float-right" @click="navigate" role="link" class="btn" v-on:click="setChatId(this.chatId)">
              <i class="fa fa-info-circle" aria-hidden="true"></i> <small>Need help?</small>
            </button>
          </router-link>
        </div>
      </form>
    </div>
    <div class="row">
      <div class="col-lg-11">
        <table class="table table-hover m-4 w-100">
          <thead>
          <tr>
            <th>Chat</th>
            <th>Action</th>
            <th>
              <button class="btn">New chat <i class="fa fa-user-plus"></i></button>
            </th>
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
  name: "openChatList",

  data() {
    return {
      items: [],
      chatId: ''
    }
  },
  mounted() {
    this.addToItems()
    this.createChat()
  },
  methods: {
    addToItems: function() {
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/user/' + sessionStorage.getItem("userId")).then(responseData => {
        this.items.push(...responseData.chatIds);
      });
    },
    createChat: function (){
      document.getElementById('newChatForm').onsubmit = data =>
      {
        this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/helpline/' + sessionStorage.getItem("userId")).then(responseData => {
          if(responseData.chatId) {
            this.chatid = responseData.chatId;
          } else {
            
          }
        });
        const input = document.getElementById('userId');
        input.classList.remove("border", "border-danger");

        data.preventDefault();
        if (input.value === ""){
          input.classList.add("border", "border-danger");
        } else {
          this.addChatToDatabase(input.value);
          input.value = '';
        }
      }
    },
    addChatToDatabase: function (id) {
      sessionStorage.setItem('userId', '1'); // mock
      this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/chats/newChat/' + id + '/' + sessionStorage.getItem('userId')).then()
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