function getChatLog(userId){
    runWebSocket();

    sendHttpRequest('GET', 'http://localhost:8080/chat/1').then(responseData => {
        for (let message of responseData.messages) {
            if (message.senderId === userId) {
                this.outgoingMessage(message.message, message.time);
            } else {
                this.incomingMessage(message.message, message.time);
            }
        }
    });
}

function runWebSocket(){

}

function sendMessage() {
    const newMessage = document.getElementById('message').value;

    if (sessionStorage.getItem('userId') === "1"){
        sendHttpRequest('POST', 'http://localhost:8080/chat1/1', newMessage).then(responseData => {})
    } else{
        sendHttpRequest('POST', 'http://localhost:8080/chat/2/1', newMessage).then(responseData => {})
    }
}

function outgoingMessage(message, time){
    const outgoingMessage = document.getElementById('content');

    outgoingMessage.innerHTML += '' +
        '<div class="outgoing_msg"> ' +
        '<div class="sent_msg"> ' +
        '<p>'+  filterMessage(message) +'</p>' +
        '<span class="time_date float_right">'+ time +'</span>' +
        '</div> ' +
        '</div>'
}

function incomingMessage(message, time = getCurrentTime()){
    const incomingMessage = document.getElementById('content');

    incomingMessage.innerHTML += '' +
        '<div class="received_msg"> ' +
        '<div class="received_content_msg"> ' +
        '<p>'+ filterMessage(message) +'</p>' +
        '<span class="time_date">'+ time +'</span>' +
        '</div> ' +
        '</div>'
}

const sendHttpRequest = (method, url, data) => {
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
};

function setUserId(userId){
    sessionStorage.setItem("userId", userId);
    console.log(userId);
}

function filterMessage(message){
    return message.replace(/<\/?[^>]+>/gi, '')
}

function getCurrentTime() {
    let date = new Date();
    return date.getHours() + ':' + (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();
}
