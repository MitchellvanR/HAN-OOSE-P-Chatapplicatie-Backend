<template>
  <div class="background">
    <div class="container mt-5">
      <div class="row">
        <p class="display-4">Chat Menu</p>
        <small><i class="fa fa-exclamation-circle" aria-hidden="true"></i> Note! This is page is used for mocking purposes.</small>
        <hr>
        <div class="position-relative border1px">
          <div class="input_style w-100">
            <form id="secret-form">
              <input type="text" id="secret" v-model= "secret" placeholder="Insert password here..."/>
              <label for="secret"></label>
              <button type="button" @click.prevent="saveSecret(secret)">Submit</button>
            </form>
          </div>
        </div>
      </div>
      <div class="row">
          <div class="col-6 m-2">
            <div class="row mb-2">
              <b class="text-left">Selecteer een gebruiker:</b>
            </div>
            <form id="user">
              <div class="row mb-2">
                <router-link to="/chatlist" custom v-slot="{ navigate }"><button @click="navigate" id="user1" role="link" class="btn btn-outline-primary" v-on:click="setUserId(1)">Gebruiker #1 (Mitch)</button></router-link>
              </div>
              <div class="row">
                <router-link to="/chatlist" custom v-slot="{ navigate }"><button @click="navigate" id="user2" role="link" class="btn btn-outline-primary" v-on:click="setUserId(2)">Gebruiker #2 (Jaap)</button></router-link>
              </div>
              <div class="row">
                <router-link to="/chatlist" custom v-slot="{ navigate }"><button @click="navigate" id="user3" role="link" class="btn btn-outline-primary" v-on:click="setUserId(3)">Gebruiker #3 (Helen)</button></router-link>
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
                <button class="btn" type="submit"><i class="fa fa-check" aria-hidden="true"></i></button>
              </div>
            </form>
        </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {secret: ""};
  },
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
      sessionStorage.setItem('userId', '1'); // mock
      this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/chats/newChat/' + id + '/' + sessionStorage.getItem('userId')).then()
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

    saveSecret: async function (secret) {
      let numberFromString = Number(0);
      for (let i = 0; i < secret.length; i++) {
        numberFromString += secret.charCodeAt(i) * 513;
      }
      let saveSecret = String(numberFromString % 997);
      sessionStorage.setItem("secret", saveSecret);
    },
  }
}
</script>

<style>
body {
  background: #ebebeb;
}
</style>