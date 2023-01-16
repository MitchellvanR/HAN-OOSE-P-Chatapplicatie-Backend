// ws staat voor WebSocket, maar ws is geïnstalleerd dus deze naam kan niet aangepast worden
const ws = require('ws');

const WebSocketServer = new ws.Server({port: 443});

WebSocketServer.on('connection', client => {
    client.on('message', (message, isBinary) => {
        [...WebSocketServer.clients]
            .filter(Clients => Clients !== client)
            .forEach(Clients => Clients.send(isBinary ? message.toString() : message));
    });
});

module.exports = WebSocketServer;