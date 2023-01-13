<template>
  <div class="container mt-5">
    <div class="row">
      <p class="display-4">Hulplijn</p>
      <small><i class="fa fa-exclamation-circle" aria-hidden="true"></i> Let op! Dit scherm wordt alleen gebruikt voor testen en het geven van demo's.</small>
      <hr>
    </div>
    <div class="row">
      <div v-if="showAnnouncementMaker()" class="position-relative">
        <button type="button" @click="toggleView('addAnnouncement')" class="btn btn-outline-primary float-right">Aankondiging sturen</button>

        <div class="input_style w-100 form-popup mt-2 mb-2" id="addAnnouncement">
          <b>Voeg een aankondiging toe</b>
          <form id="announcement-form">
            <div class="form-group">
              <label for="announcement" >Aankondiging:</label><br>
              <input type="text" id="announcement" v-model="announcement" placeholder="Voer hier de aankondiging in..." class="form-control w-50" /><br>
            </div>
            <div class="form-group">
              <label for="endDate" >Einddatum:</label><br>
              <input type="datetime-local" id="endDate" v-model="endDate" class="form-control w-50"><br>
            </div>
            <button type="button" @click="saveAnnouncement(announcement, endDate)" class="btn btn-outline-dark"><i class="fa fa-bullhorn" aria-hidden="true"></i> Verzend</button>
          </form>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-11">
        <table class="table table-hover m-4 w-100">
          <thead>
          <tr>
            <th>Hulplijn chat</th>
            <th>Actie</th>
          </tr>
          </thead>
          <tbody>
          <tr @click="log(chatId)" v-for="(helplineChat, index) in array" :key="index">
            <td>{{helplineChat.chatId}}</td>
            <td>
              <form id="chat" action="http://localhost:8081/chat">
                <button role="link" class="btn" v-on:click="setChatId(helplineChat.chatId)"><i class="fa fa-sign-in" aria-hidden="true"></i></button>
              </form>
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
  name: "openAdministration",
  data() {
    return {
      array: [],
      announcement: "",
      endDate:"",
    }
  },
  mounted() {
    this.savePublicKey();
    this.getHelplineChats();
  },
  methods: {
    /* global BigInt */
    savePublicKey: function (){
      let secret = sessionStorage.getItem('secret')
      let publicKey = this.formulatePublicKey(secret).toString();
      this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/security/' + sessionStorage.getItem('userId') + '/' + String(publicKey)).then(responseData => {
        let keysMatch = responseData.keysMatch;
        if (!keysMatch || sessionStorage.getItem('userId') !== 'Admin'){
          history.back();
        }
      })
    },
    formulatePublicKey: function (secret) {
      return BigInt("2") ** BigInt(secret) % BigInt("32317006071311007300338913926423828248817941241140239112842009751400741706634354222619689417363569347117901737909704191754605873209195028853758986185622153212175412514901774520270235796078236248884246189477587641105928646099411723245426622522193230540919037680524235519125679715870117001058055877651038861847280257976054903569732561526167081339361799541336476559160368317896729073178384589680639671900977202194168647225871031411336429319536193471636533209717077448227988588565369208645296636077250268955505928362751121174096972998068410554359584866583291642136218231078990999448652468262416972035911852507045361090559");
    },
    getHelplineChats: function() {
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/helplineList').then(responseData => {
        this.array.push(...responseData.helplineChats);
      });
    },
    toggleView: function (id) {
      document.getElementById(id).classList.toggle("form-popup");
    },
    saveAnnouncement: function (announcement, endDate){
      this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/announcement/' + announcement + '/' + endDate).then(() => {window.location.reload();})
    },
    showAnnouncementMaker: function (){
      return sessionStorage.getItem("userId") === "Admin";
    },
    setChatId: function (chatId){
      this.setHelpline();
      sessionStorage.setItem("chatId", chatId);
    },
    setHelpline: function() {
      sessionStorage.setItem("isHelpline", "true");
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