<template>
  <div class="container mt-5">
    <div class="announcements" id="announcements">
      <div class="row mb-2">
        <ul v-for="(announcements, index) in announcements" :key="index">
          <li class="announcement">
            <p>Aankondiging: {{announcements}}</p>
          </li>
        </ul>
      </div>
    </div>
    <div v-if="showAnnouncementMaker()" class="position-relative border1px">
      <form id="getAnnouncementMaker">
        <button type="button" @click.prevent="openForm()" class="btn btn-outline-primary">Nieuwe aankondiging toevoegen</button><br>
      <div class="input_style w-100 form-popup" id="addAnnouncement">
        <button type="button" @click.prevent="closeForm()" class="btn btn-outline-primary">Sluit aankondigingsformulier</button><br>
        <b>Voeg een aankondiging toe</b>
        <form id="announcement-form">
          <label for="announcement" >Aankondiging:</label><br>
          <input type="text" id="announcement" v-model= "announcement" placeholder="Voer hier de aankondiging in..." size="100"/><br>
          <label for="endDate" >Einddatum:</label><br>
          <input type="datetime-local" id="endDate" v-model="endDate"><br>
          <button type="button" @click="saveAnnouncement(announcement, endDate)" class="btn btn-outline-primary">Verzend</button>
        </form>
      </div>
      </form>
    </div>
    <div class="row">
      <p class="display-4">User Menu</p>
      <small><i class="fa fa-exclamation-circle" aria-hidden="true"></i> Note! This is page is used for mocking purposes.</small>
      <hr>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <button class="btn text-info fa-lg float-right">
          <i class="fa fa-info-circle" aria-hidden="true"></i> <small>Need help?</small>
        </button>
      </div>
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
      announcements: [],
      announcement: "",
      endDate:"",

    }
  },
  mounted() {
    this.addToItems()
    this.getAnnouncements();
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
    saveAnnouncement: function (announcement, endDate){
      this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/announcement/' + announcement + '/' + endDate).then(() => {window.location.reload();})
    },
    getAnnouncements: function (){
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/announcement/getAnnouncements').then(responseData => {
        this.announcements.push(...responseData.announcements)
      })
    },
    showAnnouncementMaker: function (){
      return sessionStorage.getItem("userId") === "Admin";
    },
    openForm: function () {
      document.getElementById("addAnnouncement").style.display = "block";
    },
    closeForm: function () {
      document.getElementById("addAnnouncement").style.display = "none";
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
.form-popup {
  display: none;
}
</style>