<template>
  <div class="position-relative border1px">
    <div class="input_style w-100">
      <form id="user">
        <router-link to="/chat" custom v-slot="{ navigate }"><button @click="navigate" role="link" onclick="setUserId(1)">User 1</button></router-link>
        <router-link to="/chat" custom v-slot="{ navigate }"><button @click="navigate" role="link" onclick="setUserId(2)">User 2</button></router-link>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserMenu'
}
</script>

<style>

</style>