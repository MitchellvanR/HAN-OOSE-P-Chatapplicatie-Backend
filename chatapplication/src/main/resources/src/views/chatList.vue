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
    <div class="row">
      <div class="col lg-8">
        <p class="display-4">Gebruiker Menu</p>
        <small><i class="fa fa-exclamation-circle" aria-hidden="true"></i> Let op! Dit scherm wordt alleen gebruikt voor testen en het geven van demo's.</small>
      </div>
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
        <form id="chat" action="http://localhost:8081/chat">
          <button class="btn btn-outline-info fa-lg float-right" type="submit" v-on:click="setHelpLineChatType()" role="link">
            <i class="fa fa-info-circle" aria-hidden="true"></i> Hulplijn
          </button>
        </form>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-11">
        <table class="table table-hover m-4 w-100">
          <thead>
          <tr>
            <th>Gebruikers</th>
            <th>Acties</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="chat in chats" :key="chat">
            <td>
              <p>
                <span v-for="user in chat.users" :key="user">
                  {{user}}
                </span>
              </p>
            </td>
            <td>
              <form id="chat" action="http://localhost:8081/chat">
                <button @click="navigate" role="link" class="btn" v-on:click="setChatId(chat.chatId)"><i class="fa fa-sign-in" aria-hidden="true"></i></button>
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
  name: "ChatList",
  data() {
    return {
      chats: [],
      announcements: [],
      announcement: "",
      endDate:"",
      chatId: null,
      userId: sessionStorage.getItem('userId'),
    }
  },
  mounted() {
    this.savePublicKey();
    this.createChat();
    this.getAnnouncements();
    this.getAllChatsFromUser()
    this.setHelplineChat();
  },
  methods: {
    /* global BigInt */
    setHelplineChat: function (){
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/helpline/' + this.userId).then(responseData => {
        if(responseData.chatId) {
          this.setChatId(responseData.chatId);
        } else {
          this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/chats/newHelpLineChat/' + this.userId + '/admin').then()
          this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/helpline/' + this.userId).then(responseData => {
            this.setChatId(responseData.chatId);
          });
        }
      });
    },
    savePublicKey: function (){
      let secret = sessionStorage.getItem('secret')
      let publicKey = this.formulatePublicKey(secret).toString();
      this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/security/' + this.userId + '/' + String(publicKey)).then(responseData => {
        let keysMatch = responseData.keysMatch;
        if (!keysMatch){
          history.back();
        }
      })
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
        if (input.value === "" || isNaN(input.value) || input.value === sessionStorage.getItem("userId")){
          input.classList.add("border", "border-danger");
        } else {
          this.validateUserExists(input, input.value)
          input.value = '';
        }
      }
    },
    validateUserExists: function (input, id){
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/newChat/' + id).then(responseData => {
        if (responseData.result === true){
          this.validateCreatedChatDoesntExist(input, id)
        } else {
          input.classList.add("border", "border-danger");
        }
      });
    },
    validateCreatedChatDoesntExist: function (input, id){
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/newChat/' + id + '/' + sessionStorage.getItem('userId')).then(responseData => {
        if (responseData.result === false){
          this.addChatToDatabase(id);
          this.chats.push({
            chatId: "?",
            users: [id],
          });
        } else {
          input.classList.add("border", "border-danger");
        }
      });
    },
    addChatToDatabase: function (id) {
      this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/chats/newChat/' + id + '/' + this.userId).then()
    },
    getAllChatsFromUser: function() {
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/user/' + this.userId).then(responseData => {
        this.chats.push(...responseData.chats);
      });
    },
    setHelpLineChatType: function (){
      sessionStorage.setItem("isHelpline", "true");
    },
    setChatId: function (chatId){
      sessionStorage.setItem("isHelpline", "false");
      sessionStorage.setItem('chatId', chatId);
    },
    getAnnouncements: function (){
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/announcement/getAnnouncements').then(responseData => {
        this.announcements.push(...responseData.announcements);
      })
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