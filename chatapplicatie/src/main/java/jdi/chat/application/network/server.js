const ws = require('ws');

const wss = new ws.Server({port: 1234});

wss.on('connection', client => {
    client.on('message', (message, isBinary) => {
        [...wss.clients]
            .filter(c => c !== client)
            .forEach(c => c.send(isBinary ? message.toString() : message));
    });
});