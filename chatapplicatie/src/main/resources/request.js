const post = document.getElementById('post-btn');

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

function getChatLog(){
    sendHttpRequest('GET', 'https://reqres.in/api/users').then(responseData => {
    // sendHttpRequest('GET', 'http://localhost:63342/getChatLog').then(responseData => {
    //   console.log(responseData);
    });

  let responseData = {
    "messages": [
      {"sender": "1", "receiver": "2", "message": "Hoi Jesse"},
      {"sender": "2", "receiver": "1", "message": "Fakka Thijs"},
      {"sender": "1", "receiver": "2", "message": "Val dood."},
    ]
  }

  const location = document.getElementById('body')
  const p = document.createElement('p')
  location.append(p)

  for (let i = 0; i < responseData.messages.length; i++){


    if (responseData.messages[i].sender === "1)"){
        p.classList.add('ml-5')
    } else {
        p.classList.add('mr-5')
    }
    p.textContent = responseData.messages[i].message

  }
}

const sendMessage = () => {
  sendHttpRequest('POST', 'http://localhost:63342/sendMessage', {
    message:
        [
          {
            "sender":"1",
            "receiver":"2",
            "message":document.getElementById('message').value,
          },
        ],
  })
    .then(responseData => {
      console.log(responseData);
    })
    .catch(err => {
      console.log(err);
    });
};

post.addEventListener('click', sendMessage);
