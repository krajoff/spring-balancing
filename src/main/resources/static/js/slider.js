function sendMessage(value) {
    document.getElementById('sliderValue').textContent = value;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/slider/slider", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify({value: value}));
    // Обработка ответа от сервера
    xhr.onreadystatechange = function() {
      if (xhr.readyState == 4 && xhr.status == 200) {
        console.log("Сообщение успешно отправлено на сервер");
      } else {
        console.error("Ошибка при отправке сообщения на сервер");
      }
    };
}

function updateSliderValue(value) {
  document.getElementById('sliderValue').textContent = value;
}
