<template>
  <div class="background">
    <div class="container mt-5">
        <p class="display-4">Chatapplicatie Configuratie</p>
        <hr>
        <div class="row">
          <div class="col-6 m-2">
            <div class="row mb-2">
              <b class="text-left">Selecteer een gebruiker:</b>
            </div>
            <form id="user">
              <div class="row mb-2">
                <router-link to="/chat" custom v-slot="{ navigate }"><button @click="navigate" id="user1" role="link" class="btn btn-outline-primary" v-on:click="setUserId(1)">Gebruiker #1 (Mitch)</button></router-link>
              </div>
              <div class="row mb-2">
                <router-link to="/chat" custom v-slot="{ navigate }"><button @click="navigate" id="user2" role="link" class="btn btn-outline-primary" v-on:click="setUserId(2)">Gebruiker #2 (Jaap)</button></router-link>
              </div>
              <div class="row">
                <router-link to="/chat" custom v-slot="{ navigate }"><button @click="navigate" id="user3" role="link" class="btn btn-outline-primary" v-on:click="setUserId(3)">Gebruiker #3 (Helen)</button></router-link>
              </div>
            </form>
          </div>
          <div class="col-4 m-2">
            <div class="row mb-2">
              <b>Log in als administrator:</b>
            </div>
            <div class="row">
              <router-link to="/chat" custom v-slot="{ navigate }"><button @click="navigate" id="administrator" role="link" class="btn btn-primary">Administrator #1</button></router-link>
            </div>
          </div>
        </div>
        <div class="row">
            <div class="row mb-2">
              <b class="text-left">Maak een nieuwe chat</b>
            </div>
            <form id="newChatForm" class="wrap">
              <div class="row mb-2">
                <input type="text" id="userId" placeholder="Enter userId" />
                <button class="btn" type="submit"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
              </div>
            </form>
        </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserMenu',
  mounted() {
    this.createChat();
  },
  methods: {
    setUserId: function (userId) {
      sessionStorage.setItem("userId", userId);
    },
    createChat: function (){
      document.getElementById('newChatForm').onsubmit = data =>
      {
        const input = document.getElementById('userId');
        input.classList.remove("border", "border-danger");

        data.preventDefault();
        if (input.value === ""){
          input.classList.add("border", "border-danger");
        } else {
          //is het een int validatie
          this.addChatToDatabase(input.value);
          input.value = '';
        }
      }
    },
    addChatToDatabase: function (id) {
      sessionStorage.setItem('userId', "1"); // mock
      this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/chats/newChat/' + id).then()
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
    }
  },
}
</script>

<style>
body {
  background: #ebebeb;
}
</style>