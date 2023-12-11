const canvasPlot = document.getElementById("canvas");
const ctx = canvasPlot.getContext("2d");

let defaultR = 5;
let currentR;

//ширина и высота канваса
//width = 480, height = 320
const canvasPlotWidth = canvasPlot.clientWidth;
const canvasPlotHeight = canvasPlot.clientHeight;

//отступ между границами сетки
const scaleX = 40;
const scaleY = 40;

//значения рисования координатных осей
//xAxis = 240, yAxis = 160
const xAxis = Math.round(canvasPlotWidth / scaleX / 2) * scaleX;
const yAxis = Math.round(canvasPlotHeight / scaleY / 2) * scaleY;

//коэффициент смещения текста от осей
const shiftNames = 5;
const shiftAxisNames = 20;

//форматирование текста
ctx.textAlign = "left";
ctx.textBaseline = "top";

//вызывается при загрузке html-страницы и потом после передачи ей значений

//!!!при работе с точками вернуть draw(r, points)!!!
function draw(r, points) {
    ctx.clearRect(0, 0, canvasPlotWidth, canvasPlotHeight);
    drawGrid();
    drawAxes();

    currentR = r;
    if (currentR === undefined) {
        drawText(defaultR);
    } else {
        drawText(currentR);
        drawPolygon(currentR);
    }

    // Считывание данных точек из списка и рисование уже имеющихся точек на холсте!!!
    // Вернуть после того как будет понятно как это делать в 3 лабе!!!
    if (points !== undefined) {
        points.forEach(function (point) {
            let x = point.x;
            let y = point.y;
            let color = validatePointForCanvas(x, y, currentR);
            drawPoint(r, x, y, color);
        });
    }


}

//рисование сетки - всегда статично
function drawGrid() {
    ctx.beginPath();
    ctx.strokeStyle = "#ced0ce";

    for (let i = 0; i <= canvasPlotWidth; i = i + scaleX) {
        ctx.moveTo(i, 0);
        ctx.lineTo(i, canvasPlotHeight);

        // ctx.fillText((i - xAxis) / scaleX, i + shiftNames, yAxis + shiftNames);
    }

    for (let i = 0; i <= canvasPlotHeight; i = i + scaleY) {
        ctx.moveTo(0, i);
        ctx.lineTo(canvasPlotWidth, i);

        // ctx.fillText((yAxis - i) / scaleY, xAxis + shiftNames, i + shiftNames);
    }

    ctx.stroke();
    ctx.closePath();
}

//рисование главных осей - всегда статично
function drawAxes() {
    ctx.font = `${Math.round(scaleX / 2)}px Arial`;
    ctx.fillStyle = "black";
    ctx.beginPath();
    ctx.strokeStyle = "#000000";

    ctx.moveTo(xAxis, 0);
    ctx.lineTo(xAxis, canvasPlotHeight);
    ctx.fillText("y", xAxis - shiftAxisNames, 0);

    ctx.moveTo(0, yAxis);
    ctx.lineTo(canvasPlotWidth, yAxis);
    ctx.fillText("x", canvasPlotWidth - shiftAxisNames, yAxis - shiftAxisNames);

    ctx.stroke();
    ctx.closePath();
}

//рисование подписей к главным осям - зависит от R
function drawText(r) {
    ctx.fillStyle = "#4f4f4f";
    ctx.font = `${Math.round((r * 10) / 2)}px Arial`;

    //ось x
    ctx.fillText("-R", xAxis - scaleX * r + shiftNames, yAxis + shiftNames);
    ctx.fillText(
        "-R/2",
        xAxis - (scaleX * r) / 2 + shiftNames,
        yAxis + shiftNames
    );
    ctx.fillText(0, xAxis + shiftNames, yAxis + shiftNames);
    ctx.fillText(
        "R/2",
        xAxis + (scaleX * r) / 2 + shiftNames,
        yAxis + shiftNames
    );
    ctx.fillText("R", xAxis + scaleX * r + shiftNames, yAxis + shiftNames);

    //ось y
    ctx.fillText(
        "R/2",
        xAxis + shiftNames,
        yAxis - (scaleY * r) / 2 + shiftNames
    );
    ctx.fillText(
        "-R/2",
        xAxis + shiftNames,
        yAxis + (scaleY * r) / 2 + shiftNames
    );
}

//рисование всей области - все зависит от R
function drawPolygon(r) {
    drawRect(r);
    drawTriangle(r);
    drawArc(r);
}

//рисование прямоугольника - зависит от R
function drawRect(r) {
    ctx.beginPath();
    ctx.rect(xAxis - scaleX * r, yAxis, scaleX * r, scaleX * (r / 2));
    ctx.closePath();
    ctx.strokeStyle = "#ffba08";
    ctx.fillStyle = "rgba(163, 155, 168, 0.5)";
    ctx.fill();
    ctx.stroke();
}

//рисование круга - зависит от R
function drawArc(r) {
    ctx.beginPath();
    ctx.moveTo(xAxis, yAxis);
    ctx.arc(xAxis, yAxis, scaleX * (r / 2), 0, Math.PI / 2, false);
    ctx.closePath();
    ctx.strokeStyle = "#ffba08";
    ctx.fillStyle = "rgba(163, 155, 168, 0.5)";
    ctx.fill();
    ctx.stroke();
}

//рисование треугольника - зависит от R
function drawTriangle(r) {
    ctx.beginPath();
    ctx.moveTo(xAxis, yAxis);
    ctx.lineTo(xAxis, yAxis - scaleX * (r / 2));
    ctx.lineTo(xAxis - scaleX * r, yAxis);
    ctx.closePath();
    ctx.strokeStyle = "#ffba08";
    ctx.fillStyle = "rgba(163, 155, 168, 0.5)";
    ctx.fill();
    ctx.stroke();
}

//рисование точки при наличии значений x, y
function drawPoint(r, x, y, color) {
    console.log(x, y, r);
    ctx.beginPath();
    const scaledX = xAxis + x * scaleX;
    const scaledY = yAxis - y * scaleY;
    ctx.arc(scaledX, scaledY, 4, 0, 2 * Math.PI);
    ctx.fillStyle = color; // Цвет точки, например, синий
    ctx.fill();
    ctx.closePath();
}

//обработка нажатия на область канваса
canvasPlot.addEventListener("click", function (event) {
    if (currentR === undefined) {
        document.getElementById("error_message").textContent =
            "Выберите значение R!";
    } else {
        document.getElementById("error_message").textContent = "";
        // Получите координаты нажатия мыши относительно canvas
        const x = event.clientX - canvasPlot.getBoundingClientRect().left;
        const y = event.clientY - canvasPlot.getBoundingClientRect().top;

        tableX = (x - xAxis) / scaleX;
        tableY = (yAxis - y) / scaleY;

        if (tableX < -4 || tableX > 2) {
            document.getElementById("error_message").textContent =
                "Значение X не соответствует указанному диапозону!";
        } else {
            if (tableY < -3 || tableY > 3) {
                document.getElementById("error_message").textContent =
                    "Значение Y не соответствует указанному диапозону!";
            } else {
                document.getElementById("error_message").textContent = "";

                //  НЕ рисуем точку в месте нажатия
                // ctx.beginPath();
                // ctx.arc(x, y, 4, 0, 2 * Math.PI);
                // ctx.fillStyle = "#2b2d42"; // Цвет точки
                // ctx.fill();

                sendData(tableX, tableY);
            }
        }
    }
});

//Валидация точки для установки ее цвета
function validatePointForCanvas(x, y, r) {
    if ((y <= x / 2 + r / 2 && y >= 0 && x <= 0) || // Проверка попадания точки в первую область (треугольник)
        (x * x + y * y <= (r / 2) * (r / 2) && x >= 0 && y <= 0) || // Проверка попадания точки во вторую область (окружность)
        (x >= -r && x <= 0 && y >= -r / 2 && y <= 0)) { // Проверка попадания точки в третью область (прямоугольник)
        return "green"; // Если точка попала в одну из областей, возвращаем true
    } else {
        return "red"; // Если точка не попала ни в одну из областей, возвращаем false
    }
}