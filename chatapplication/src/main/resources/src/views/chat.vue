<template>
  <div id="frame" class="mt-3">
    <div class="content border">
      <div class="contact-profile">
        <div class="row">
          <strong><u>Receiver</u></strong>
        </div>
      </div>
      <div class="messages" id="messages">
        <ul v-for="(message, index) in array" :key="index">
          <li v-if="message.senderId === userId" class="replies mb-3">
            <small class="float-right margin-right-5px">{{message.time}}</small>
            <br>
            <p>{{message.message}}</p>
          </li>
          <li v-else class="sent mb-3">
            <small class="margin-left-5px">{{message.time}}</small>
            <br>
            <p>{{message.message}}</p>
          </li>
        </ul>
      </div>
      <div class="message-input border-top border-dark p-2">
        <form id="sendMessageForm" class="wrap">
          <input type="text" id="message" placeholder="Write your message..." />
          <button class="btn" type="submit"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
          <button class="btn" type="button"><i class="fa fa-paperclip" aria-hidden="true"></i></button>
          <button class="btn" type="button" @click="openForm()"><i class="fa fa-plus" aria-hidden="true"></i></button>
          <div class="form-popup" id="addUserToCurrentChat">
            <form id="addUserForm" class="wrap">
              <div class="row mb-2">
                <input type="text" id="userId" placeholder="Enter userId" />
                <button class="btn" type="submit"><i class="fa fa-check" aria-hidden="true"></i></button>
                <button class="btn" type="button" @click="closeForm()"><i class="fa fa-times" aria-hidden="true"></i></button>
              </div>
            </form>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'OpenChat',
  cryptoKey: {},
  data() {
    return {
      array: [],
      userId: sessionStorage.getItem('userId'),
    }
  },
  mounted() {
    this.getChatLog();
    this.savePublicKey(sessionStorage.getItem('userId'), sessionStorage.getItem('secret'));
    this.delay(30);
    sessionStorage.setItem('chatId', '1');
    this.addUserToCurrentChat();
  },
  destroyed() {
    if (this.webSocket.readyState === WebSocket.OPEN) {
      this.webSocket.close();
      this.webSocket = null;
    }
  },
  methods: {
    // Deze comment (global BigInt) is nodig om BigInts in deze file werkend te krijgen. Zonder deze comment werken ze dus niet.
    /* global BigInt */
    formulatePublicKey: function (secret) {
      return BigInt("2") ** BigInt(secret) % BigInt("32317006071311007300338913926423828248817941241140239112842009751400741706634354222619689417363569347117901737909704191754605873209195028853758986185622153212175412514901774520270235796078236248884246189477587641105928646099411723245426622522193230540919037680524235519125679715870117001058055877651038861847280257976054903569732561526167081339361799541336476559160368317896729073178384589680639671900977202194168647225871031411336429319536193471636533209717077448227988588565369208645296636077250268955505928362751121174096972998068410554359584866583291642136218231078990999448652468262416972035911852507045361090559");
    },
    formulatePrivateKey: function (otherPublicKey, secret) {
      return (BigInt(otherPublicKey) ** BigInt(secret)) % BigInt("32317006071311007300338913926423828248817941241140239112842009751400741706634354222619689417363569347117901737909704191754605873209195028853758986185622153212175412514901774520270235796078236248884246189477587641105928646099411723245426622522193230540919037680524235519125679715870117001058055877651038861847280257976054903569732561526167081339361799541336476559160368317896729073178384589680639671900977202194168647225871031411336429319536193471636533209717077448227988588565369208645296636077250268955505928362751121174096972998068410554359584866583291642136218231078990999448652468262416972035911852507045361090559");
    },
    getSecret: function () {
      return BigInt(sessionStorage.getItem("secret"));
    },
    importCryptoKey: async function (value) {
      let key = this.formulatePrivateKey(value, this.getSecret());
      let bufferOne = new TextEncoder().encode(key);
      let bufferTwo = new Uint8Array(32)
      for (let i = 0; i < bufferTwo.length; i++) {
        bufferTwo[i] = bufferOne[i];
      }

      this.cryptoKey = await window.crypto.subtle.importKey(
          "raw",
          bufferTwo,
          "AES-CBC",
          false,
          ["encrypt", "decrypt"]
      );
    },
    generateIv: function () {
      return window.crypto.getRandomValues(new Uint8Array(16));
    },
    encodeMessage: function (message) {
      return new TextEncoder().encode(message);
    },
    decodeMessage: function (message) {
      return new TextDecoder().decode(message);
    },
    encrypt: async function (message) {
      this.getOtherPublicKey(sessionStorage.getItem("userId"), sessionStorage.getItem("chatId"))
      await this.delay(30);
      await this.importCryptoKey(sessionStorage.getItem("otherPublicKey"));
      let iv = this.generateIv();
      sessionStorage.setItem("sendIv", iv.toString());
      let encodedMessage = this.encodeMessage(message);
      return window.crypto.subtle.encrypt(
          {
            name: "AES-CBC",
            iv: iv,
          },
          this.cryptoKey,
          encodedMessage
      );

    },
    cleanForDecrypt: function (message) {
      let cleanMessage = message.replace(/['"]/g, '');
      if (cleanMessage.charCodeAt(0) === 123) {
        cleanMessage = cleanMessage.substring(1, cleanMessage.length - 1);
      }
      let messageArray = cleanMessage.split(",");
      for (let i = 0; i < messageArray.length; i++) {
        messageArray[i] = messageArray[i].replace(String(i) + ":", '');
      }
      let correctArray = new Uint8Array(messageArray.length);
      for (let i = 0; i < correctArray.length; i++) {
        correctArray[i] = messageArray[i];
      }
      return correctArray;
    },
    decrypt: async function (key, message, iv) {
      let messageArray = this.cleanForDecrypt(message);
      let ivArray = this.cleanForDecrypt(iv);

      let encodedMessage = await window.crypto.subtle.decrypt(
          {
            name: "AES-CBC",
            iv: ivArray,
          },
          key,
          messageArray
      ).catch((messy) => {
        console.log("this mess: " + messy);
      });
      return this.decodeMessage(encodedMessage);
    },
    websocketDecrypt: async function (data) {
      let messageAndIvArray = data.split("^");
      let message = messageAndIvArray[0];
      let iv = messageAndIvArray[1];
      let correctData = await this.decrypt(this.cryptoKey, message, iv)
      let dataArray = [correctData];
      return new Blob(dataArray);
    },
    delay: function (milliseconds) {
      return new Promise(resolve => {
        setTimeout(resolve, milliseconds);
      });
    },
    getChatLog: function () {
      this.runWebSocket();
      this.validateSession();
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/chats/' + sessionStorage.getItem('chatId')).then(async responseData => {
        for (let message of responseData.messages) {
          this.getOtherPublicKey(sessionStorage.getItem("userId"), sessionStorage.getItem("chatId"))
          await this.delay(30);
          await this.importCryptoKey(sessionStorage.getItem("otherPublicKey"));
          message.message = await this.decrypt(this.cryptoKey, message.message, message.iv);
        }
        this.array.push(...responseData.messages);
      }).then(() => this.scrollToBottom());
    },
    openForm: function () {
      document.getElementById("addUserToCurrentChat").style.display = "block";
    },
    closeForm: function () {
      document.getElementById("addUserToCurrentChat").style.display = "none";
    },
    runWebSocket: function () {
      this.webSocket = new WebSocket('ws://localhost:443');

      this.webSocket.addEventListener('message', async data => {
        this.getOtherPublicKey(sessionStorage.getItem("userId"), sessionStorage.getItem("chatId"))
        await this.delay(30);
        await this.importCryptoKey(sessionStorage.getItem("otherPublicKey"));
        let dataSet = await this.websocketDecrypt(await data.data.text().then())
        dataSet.text().then(this.showMessage);
      });

      document.getElementById('sendMessageForm').onsubmit = data => {
        const input = document.getElementById('message');
        input.classList.remove("border", "border-danger");
        data.preventDefault();

        if (this.isInputEmpty()) {
          return document.getElementById('message').classList.add("border", "border-danger");
        }
        this.handleMessage(this.webSocket);
      }
    },
    showMessage: function (message) {
        this.array.push({
          message: message,
          time: this.getCurrentTime()
        });
    },
    addUserToCurrentChat: function () {
      document.getElementById('addUserForm').onsubmit = data =>
      {
        const input = document.getElementById('userId');
        input.classList.remove("border", "border-danger");

        data.preventDefault();
        if (input.value === ""){
          input.classList.add("border", "border-danger");
        } else {
          this.addUserToChat(input.value, sessionStorage.getItem("chatId"));
          input.value = '';
        }
      }
    },
    savePublicKey: function (userId, secret){
      let publicKey = this.formulatePublicKey(secret).toString();
      this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/security/' + userId + '/' + String(publicKey)).then(res => {return res})
    },
    getOtherPublicKey: function (userId, chatId){
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/security/' + userId + '/getOtherKey/' + chatId).then(responseData => {
        let publicKey = responseData.publicKey;
        sessionStorage.setItem("otherPublicKey", publicKey);
      });
    },
    addUserToChat: function (userId, chatId){
      this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/chats/' + chatId + '/addUser/' + userId).then(res => {return res})
    },
    isInputEmpty: function() {
      return document.getElementById('message').value === "";
    },
    handleMessage: async function(webSocket) {
      let message = document.getElementById('message').value;
      let encryptedMessageBuffer = await this.encrypt(message);
      let encryptedMessage = new Uint8Array(encryptedMessageBuffer);
      let messageAndIv = encryptedMessage.toString() + "^" + sessionStorage.getItem("sendIv").toString();

      this.array.push({
        message: message,
        senderId: this.userId,
        time: this.getCurrentTime()
      });

      this.sendMessage(messageAndIv);
      webSocket.send(messageAndIv);

      document.getElementById('message').classList.remove("border", "border-danger");
      document.getElementById('message').value = '';
    },
    sendMessage: function (encryptedMessage) {
      this.scrollToBottom();
      this.sendHttpRequest('POST', 'http://localhost:8080/chatapplication/chats/' + sessionStorage.getItem('userId') + '/1', encryptedMessage).then(res => { return res; })
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
    validateSession: function (){
      if (!sessionStorage.getItem("userId")){
        window.location.href = "/";
      }
    },
    getCurrentTime: function () {
      let date = new Date();
      return date.getHours() + ':' + (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();
    },
    scrollToBottom: function (){
      const element = document.getElementById('messages');
      element.scrollTop = element.scrollHeight;
    },
  }
}
</script>

<style scoped>
/* ===== Scrollbar CSS ===== */
/* Firefox */
* {
  scrollbar-width: thin;
  scrollbar-color: #000000 #e6eaea;
}

/* Chrome, Edge, and Safari */
*::-webkit-scrollbar {
  width: 16px;
}

*::-webkit-scrollbar-track {
  background: #e6eaea;
}

*::-webkit-scrollbar-thumb {
  background-color: #000000;
  border-radius: 10px;
  border: 3px solid #e6eaea;
}

.form-popup {
  display: none;
}
</style>