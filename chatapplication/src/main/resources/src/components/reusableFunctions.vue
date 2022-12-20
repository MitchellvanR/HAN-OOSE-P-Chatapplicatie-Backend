<template>
<div>

</div>
</template>

<script>
export default {
  name: "reusableFunctions",

  methods: {
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
    setUserId: function (userId) {
      sessionStorage.setItem("userId", userId);
    },
    setChatId: function (chatId) {
      console.log(chatId)
      sessionStorage.setItem("chatId", chatId);
    },
    setHelpline: function (helpline) {
      sessionStorage.setItem("helpline", helpline);
    }
  }
}
</script>

<style scoped>

</style>