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
          <input type="text" id="announcement" v-model="announcement" placeholder="Voer hier de aankondiging in..." size="100"/><br>
          <label for="endDate" >Einddatum:</label><br>
          <input type="datetime-local" id="endDate" v-model="endDate"><br>
          <button type="button" @click="saveAnnouncement(announcement, endDate)" class="btn btn-outline-primary">Verzend</button>
        </form>
      </div>
      </form>
    </div>
    <div class="row">
      <p class="display-4">Gebruiker Menu</p>
      <small><i class="fa fa-exclamation-circle" aria-hidden="true"></i> Let op! Dit scherm wordt alleen gebruikt voor testen en het geven van demo's.</small>
      <hr>
    </div>
    <div class="row">
      <div class="col-lg-6">
        <form id="newChatForm">
          <label>Open een nieuwe chat</label>
          <div class="input-group mb-5">
            <div class="input-group-prepend">
              <button type="submit" class="btn btn-outline-dark"><i class="fa fa-user-plus" aria-hidden="true"></i></button>
            </div>
            <input class="form-control" type="text" id="userId" placeholder="Enter userId" />
          </div>
        </form>
      </div>
      <div class="col-lg-6">
        <button class="btn text-info fa-lg float-right">
          <i class="fa fa-info-circle" aria-hidden="true"></i> <small>Hulplijn</small>
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
          </tr>
          </thead>
          <tbody>
          <tr v-for="chatId in items" :key="chatId">
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
    this.savePublicKey();
    this.createChat();
    this.addToItems()
    this.getAnnouncements();
  },
  methods: {
    /* global BigInt */
    savePublicKey: function (){
      let userId = sessionStorage.getItem('userId')
      let secret = sessionStorage.getItem('secret')
      let publicKey = this.formulatePublicKey(secret).toString();
      this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/security/' + userId + '/' + String(publicKey)).then(res => {return res})
    },
    formulatePublicKey: function (secret) {
      return BigInt("2") ** BigInt(secret) % BigInt("32317006071311007300338913926423828248817941241140239112842009751400741706634354222619689417363569347117901737909704191754605873209195028853758986185622153212175412514901774520270235796078236248884246189477587641105928646099411723245426622522193230540919037680524235519125679715870117001058055877651038861847280257976054903569732561526167081339361799541336476559160368317896729073178384589680639671900977202194168647225871031411336429319536193471636533209717077448227988588565369208645296636077250268955505928362751121174096972998068410554359584866583291642136218231078990999448652468262416972035911852507045361090559");
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
    addToItems: function() {
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/user/' + sessionStorage.getItem("userId")).then(responseData => {
        this.items.push(...responseData.chatIds);
      });
    },
    setChatId: function (chatId){
      sessionStorage.setItem('chatId', chatId)
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