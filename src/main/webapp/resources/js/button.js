//button.js проверяет, что чможно выбрать только один чекбокс, при выборе второго галочка с первого снимается

document.addEventListener("DOMContentLoaded", function () {
    const btnXElements = document.querySelectorAll(".btnX");

    //настройка кнопок с координатой X
    btnXElements.forEach((btnX) => {
        btnX.addEventListener("click", function () {
            btnXElements.forEach((btn) => {
                btn.classList.remove("active");
            });
            this.classList.add("active");
        });
    });
});