<template>

  <div class="container background">
    <p class="display-4">Menu</p>
    <small><i class="fa fa-exclamation-circle" aria-hidden="true"></i> Let op! Dit scherm wordt alleen gebruikt voor testen en het geven van demo's.</small>
    <hr class="mb-5">

    <div class="row">
      <div class="col-lg-6">
        <form id="secret-form">
          <h5>Voer identificatie in <i class="fa fa-question-circle text-info" aria-hidden="true" title="Omdat de chats beveiligd zijn met End to End Encryption heeft iedere gebruiker een code nodig. Als je een nieuwe gebruiker bent is jouw eerste ingevoerde code je identificatie."></i></h5>
          <div class="input-group mb-5">
            <div class="input-group-prepend">
              <button type="button" class="btn btn-outline-dark" @click.prevent="saveSecret(secret)"><i class="fa fa-check" aria-hidden="true"></i></button>
            </div>
            <input class="form-control" type="text" id="secret" v-model="secret" placeholder="" />
          </div>
        </form>
        <table class="table table-hover w-100">
          <thead>
          <tr>
            <th>Chat</th>
            <th>Actie</th>
            <th>Tijdelijke oplossing</th>
          </tr>
          </thead>
          <tbody>
<!--          <tr v-for="userId in users" :key="userId">-->
<!--            <td>{{userId}}</td>-->
<!--            <td>-->
<!--              <router-link to="/chat" custom v-slot="{ navigate }">-->
<!--                <button @click="navigate" role="link" class="btn" v-on:click="setUserId(userId)"><i class="fa fa-sign-in" aria-hidden="true"></i></button>-->
<!--              </router-link>-->
<!--            </td>-->
<!--            <td></td>-->
<!--          </tr>-->
          <tr>
            <td>1</td>
            <td></td>
            <td>
              <router-link to="/chatlist" custom v-slot="{ navigate }"><button @click="navigate" id="user1" role="link" class="btn btn-outline-primary" v-on:click="setUserId(1)">Gebruiker #1 (Mitch)</button></router-link>
            </td>
          </tr>
          <tr>
            <td>2</td>
            <td></td>
            <td>
              <router-link to="/chatlist" custom v-slot="{ navigate }"><button @click="navigate" id="user2" role="link" class="btn btn-outline-primary" v-on:click="setUserId(2)">Gebruiker #2 (Jaap)</button></router-link>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="col-lg-6">
        <h4>Administrator scherm</h4>
        <router-link to="/chatlist" custom v-slot="{ navigate }"><button @click="navigate" id="administrator" role="link" class="btn btn-primary" v-on:click="setUserId('Admin')"><i class="fa fa-sign-out" aria-hidden="true"></i>  Administrator</button></router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserMenu',
  data() {
    return {
      users: [],
      secret: ""
    };
  },
  mounted() {
    this.getAllUsers();
  },
  methods: {
    getAllUsers: function(){
      this.sendHttpRequest('GET', 'http://localhost:8080/chatapplication/user/getAllUsers').then(data => {
        this.users.push(...data.list);
      });
    },
    setUserId: function (userId) {
      sessionStorage.setItem("userId", userId);
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