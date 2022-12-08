<template>
  <div class="container mt-5">
    <form id="user">
      <router-link to="/chat" custom v-slot="{ navigate }"><button @click="navigate" role="link" class="btn btn-outline-primary m-3" v-on:click="setUserId(1)">User 1</button></router-link>
      <router-link to="/chat" custom v-slot="{ navigate }"><button @click="navigate" role="link" class="btn btn-outline-primary" v-on:click="setUserId(2)">User 2</button></router-link>
    </form>
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

</style>