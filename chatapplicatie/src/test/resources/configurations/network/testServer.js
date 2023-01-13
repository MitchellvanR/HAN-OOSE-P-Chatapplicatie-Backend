// let WebSocketServer = require('../../../../main/resources/configuration/network/server')
// WebSocketServer.
//
// const expect = require('chai').expect;
// const WebSocket = require('ws');
//
// describe('Welcome', function () {
//     let socket = null;
//
//     beforeEach(function (done) {
//         WebSocketServer = new WebSocket("ws://localhost:8080")
//         done();
//     });
//
//     afterEach(function (done) {
//         if (socket.readyState === WebSocket.OPEN) {
//             socket.close();
//         } else {
//             console.log('no connection to break');
//         }
//         done();
//     });
//
//     it('Say Welcome to John', function (done) {
//         let message = {
//             message: "Welcome to John"
//         }
//         socket.addEventListener('open', function (event) {
//             socket.send(JSON.stringify(message))
//             socket.addEventListener('message', function (event) {
//                 expect(event.data).equal(JSON.stringify(message))
//                 done();
//             });
//         });
//     });
//
//     it('Say Welcome to nobody', function (done) {
//         socket.addEventListener('open', function (event) {
//             socket.send("")
//             socket.addEventListener('message', function (event) {
//                 expect(event.data).equal("")
//                 done()
//             });
//         });
//     });

    // function initSocketUrl()
    // {
    //     socketUrl = getPublicSocket();
    //     socket = new WebSocket(socketUrl);
    //     return socket;
    // }
// })

// function getPublicSocket()
// {
//     return "wss://ws.postman-echo.com/raw"
// }

let expect = require('chai').expect;
let io = require('socket.io-client');
let app = require('../../../../main/resources/configuration/network/server');
let socketUrl = 'http://localhost:8080';
let options = {
    transports: ['websocket'],
    'force new connection': true
};
let room = 'lobby';

describe('Sockets', function () {
    let client1, client2, client3;

    it('should send and receive a message', function (done))
})