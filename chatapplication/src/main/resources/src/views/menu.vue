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
                <router-link id="user1" to="/chatlist" custom v-slot="{ navigate }"><button @click="navigate" role="link" class="btn btn-outline-primary" v-on:click="setUserId(1)">Gebruiker #1 (Mitch)</button></router-link>
              </div>
              <div class="row">
                <router-link to="/chat" custom v-slot="{ navigate }"><button @click="navigate" id="user2" role="link" class="btn btn-outline-primary" v-on:click="setUserId(2)">Gebruiker #2 (Jaap)</button></router-link>
              </div>
              <div>

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
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserMenu',
  methods: {
    setUserId: function (userId) {
      sessionStorage.setItem("userId", userId);
    }
  }
}
</script>

<style>
body {
  background: #ebebeb;
}
</style>