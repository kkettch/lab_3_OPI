function validateForm() {
    function validateForm() {
        // Проверка наличия сообщений об ошибках
        var errorMessage = document.getElementById("Y-value-message").textContent;

        if (errorMessage.trim() !== "") {
            // Есть сообщение об ошибке, форму не отправляем
            return false;
        }

        // Нет сообщений об ошибках, можно отправлять форму
        return true;
    }
}

function check() {

    //получение и валидация X
    const xCheckboxes = document.querySelectorAll('.checkbox[name^="x"]');
    let x = null;
    xCheckboxes.forEach((checkbox) => {
        if (checkbox.checked) {
            x = parseFloat(checkbox.value);
        }
    });
    if (x == null) {
        document.getElementById("error_message").textContent = "Укажите значение X!";
        return;
    }

    //получение и валидация Y
    const yInput = document.querySelector('.textbox').value;
    if (!/^[+-]?\d+(\.\d+)?$/.test(yInput)) {
        document.getElementById("error_message").textContent =
            "Введите числовое значение Y (целое или с плавающей точкой)!";
        return;
    }
    const y = parseFloat(yInput);
    if (isNaN(y)) {
        document.getElementById("error_message").textContent = "Введите значение Y!";
        return;
    } else if (y < -3 || y > 3) {
        document.getElementById("error_message").textContent = "Введите значение в указанном диапазоне!";
        return;
    }

    //получение и валидация R
    const r = parseFloat(PF('mySlider').getValue());
    if (isNaN(r)) {
        document.getElementById("error_message").textContent = "Укажите значение R!";
        return;
    }

    document.getElementById("error_message").textContent = "";

    document.getElementById("myForm:xValue").value = x;
    document.getElementById("myForm:yValue").value = y;
    document.getElementById("myForm:rValue").value = r;

    const pointsObject = {
        x: x,
        y: y,
        r: r
    }
    document.getElementById("myForm:points").value = JSON.stringify(pointsObject);

    PrimeFaces.ab({
        source: 'myForm',
        process: 'myForm:xValue myForm:yValue myForm:rValue myForm:points',
        update: 'myForm:xValue myForm:yValue myForm:rValue myForm:points',
        oncomplete: function() {
            pointBean.add();
        }
    });

    console.log(x, y, r);
}
