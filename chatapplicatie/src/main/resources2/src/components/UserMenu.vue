<template>
  <div class="position-relative border1px">
    <div class="input_style w-100">
      <form action="http://localhost:63342/prolog-jdi/chatapplicatie/front-end/index.html">
        <button class="custom-btn" id="user1" type="submit"><i class="fa fa-paper-plane-o" aria-hidden="true" onclick="setUserId(1)">User 1</i></button>
        <button class="custom-btn" id="user2" type="submit"><i class="fa fa-paper-plane-o" aria-hidden="true" onclick="setUserId(2)">User 2</i></button>
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
  .custom-btn {
    background: green;
  }
</style>