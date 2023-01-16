const ws = require('ws');

const WebSocketServer = new ws.Server({port: 443});

const connections = [];
const chats = [];

WebSocketServer.on('connection', (socket, req) => {
    onConnection(socket, req);
    socket.on('message', body => { onMessage(socket, body, req) });
});

const onConnection = (socket, req) => {
    const reqUrl = new URL('ws://localhost:443' + req.url);
    const id = reqUrl.searchParams.get('id');
    const chatId = reqUrl.searchParams.get('chatId');
    const users = reqUrl.searchParams.get('users');

    const usersInChat = users.split(",");

    connections.push({'id': id, 'socket': socket});
    chats.push({'id': chatId, 'users': usersInChat});
}

const onMessage = (socket, body, req) => {
    const reqUrl = new URL('ws://localhost:443' + req.url);
    const chatId = reqUrl.searchParams.get('chatId');
    getReceivingWebSockets(chatId)
        .filter(clients => clients !== socket)
        .forEach(clients => clients.send(body));
}

const getReceivingWebSockets = chatId => {
    let receivingUsers = []

    for (i = 0; i < chats.length; i++) {
        if (chats[i].id === chatId) {
            receivingUsers = chats[i].users;
        }
    }

    let receivingSockets = [];
    connections.forEach(connection => {
        for (let i = 0; i < receivingUsers.length; i++) {
            if (connection.id === receivingUsers[i]) {
                receivingSockets.push(connection.socket);
            }
        }
    })
    console.log(receivingSockets.length);
    return receivingSockets;
}