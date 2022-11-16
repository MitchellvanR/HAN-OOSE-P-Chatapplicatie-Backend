const get = document.getElementById('get-btn');
const post = document.getElementById('post-btn');
const message = document.getElementById('message').value

const JSON_String = {
  "messageRequest":
      [
        {
          "sender":"1",
          "status":"online",
          "message":message,
        },
        {
          "sender":"2",
          "status":"online",
        }
      ],
};

const sendHttpRequest = (method, url, data) => {
  return new Promise((resolve, reject) => {
    const xhr = new XMLHttpRequest();
    xhr.open(method, url);

    xhr.responseType = 'json';

    if (data) {
      xhr.setRequestHeader('Content-Type', 'application/json');
    }

    xhr.onload = () => {
      if (xhr.status >= 400) {
        reject(xhr.response);
      } else {
        resolve(xhr.response);
      }
    };

    xhr.onerror = () => {
      reject('Something went wrong!');
    };

    xhr.send(JSON.stringify(data));
  });
};

const getChatLog = () => {
  sendHttpRequest('GET', 'http://localhost:63342/getChatLog').then(responseData => {
    console.log(responseData);
  });
};

const sendMessage = () => {
  sendHttpRequest('POST', 'http://localhost:63342/sendMessage', {
    message: message
  })
    .then(responseData => {
      console.log(responseData);
    })
    .catch(err => {
      console.log(err);
    });
};

get.addEventListener('click', getChatLog);
post.addEventListener('click', sendMessage);
