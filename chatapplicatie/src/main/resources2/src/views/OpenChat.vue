<template>
  <div class="container mt-5 float">
    <div class="overflow-hidden border w-100">
      <h3 class="text-center">Gebruiker #</h3>
      <div class="container">
        <div class="overflow-auto h-500px" id="content"></div>
        <div class="position-relative border1px">
          <div class="input_style w-100">
            <form id="sendMessageForm">
              <input type="text" class="w-75 p-1 mb-2" id="message"/>
              <label for="message"></label>
              <button class="custom-btn" type="submit"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ChatApplication'
}
</script>

<style scoped>
.h-500px { height: 500px;}
.border1px {border-top: 1px solid #c4c4c4;}

.input_style input {
  background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
  border: medium none;
  color: #4c4c4c;
  font-size: 15px;
  min-height: 48px;
}
.custom-btn {
  background: #05728f none repeat scroll 0 0;
  border: medium none;
  border-radius: 50%;
  color: #fff;
  cursor: pointer;
  font-size: 17px;
  height: 33px;
  position: absolute;
  right: 0;
  top: 11px;
  width: 33px;
}

.sent_msg p {
  background: #05728f none repeat scroll 0 0;
  border-radius: 3px;
  font-size: 14px;
  margin: 0; color:#fff;
  padding: 5px 10px 5px 12px;
  width:100%;
}

.received_content_msg p {
  background: #ebebeb none repeat scroll 0 0;
  border-radius: 3px;
  color: #646464;
  font-size: 14px;
  margin: 0;
  padding: 5px 10px 5px 12px;
  width: 100%;
}
</style>