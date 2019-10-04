<%--
  Created by IntelliJ IDEA.
  User: kevin
  Date: 29.09.2019
  Time: 21:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru-RU" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8">
    <script type="text/javascript" src="jquery-3.4.1.min.js"></script>
    <title>
        ЛР-2 WEB
    </title>
    <link rel="stylesheet" type="text/css" href="main.css">
</head>
<body>
<header>
    <h1 id = "head">Проверка попадания точки<br>в заданную область</h1>
    <h2 id = "name">Беляков Дмитрий P3210<br>
        Варинат 666
    </h2>
</header>
<div class="result_content">
    <div id = "result">
    </div>
</div>
<div class = "content">
    <div class="values">
        <form id="form" name="form">
            <h2 id="step1">
                Шаг 1: введите X от -3 до 3.
            </h2>
            <input name="x" maxlength="15"/>
            <p class = "message" id="messageX"><br></p>
            <h2 id="step2">
                Шаг 2: выберите значение Y.
            </h2>
            <input id="chb-3" name="chb-3" type="checkbox" value="-3"/>
            <label for="chb-3">-3</label>
            <input id="chb-2" name="chb-2" type="checkbox" value="-2"/>
            <label for="chb-2">-2</label>
            <input id="chb-1" name="chb-1" type="checkbox" value="-1"/>
            <label for="chb-1">-1</label>
            <input id="chb0" name="chb0" type="checkbox" value="0"/>
            <label for="chb0">0</label>
            <input id="chb1" name="chb1" type="checkbox" value="1"/>
            <label for="chb1">1</label>
            <input id="chb2" name="chb2" type="checkbox" value="2"/>
            <label for="chb2">2</label>
            <input id="chb3" name="chb3" type="checkbox" value="3"/>
            <label for="chb3">3</label>
            <input id="chb4" name="chb4" type="checkbox" value="4"/>
            <label for="chb4">4</label>
            <input id="chb5" name="chb5" type="checkbox" value="5"/>
            <label for="chb5">5</label>
            <p class = "message" id="messageY"><br></p>
            <h2 id="step3">
                Шаг 3: выберите значение R.
            </h2>
            <input id="rb0" name="rb" type="radio" value="1"/>
            <label for="rb0">1</label>
            <input id="rb1" name="rb" type="radio" value="1.5"/>
            <label for="rb1">1.5</label>
            <input id="rb2" name="rb" type="radio" value="2"/>
            <label for="rb2">2</label>
            <input id="rb3" name="rb" type="radio" value="2.5"/>
            <label for="rb3">2.5</label>
            <input id="rb4" name="rb" type="radio" value="3"/>
            <label for="rb4">3</label>
            <p class = "message" id="messageR"><br></p>
            <p id="buttons">
                <button id="super_button" type="submit">Отправить</button>
            </p>
        </form>
    </div>
    <div class="picture">
        <canvas height='270' width='270' id='areas'></canvas>
    </div>
</div>
</body>
<script>
    let plot = document.getElementById("areas");
    plot.addEventListener("click", clickCanvas, false);

    function validateR(){
        document.getElementById("messageR").innerHTML = "<br>";
        for(let i=0; i<5; i++){
            if(document.getElementById("rb"+i).checked){
                return true;
            }
        }
        document.getElementById("messageR").innerHTML = "Выберите R";
        return false;
    }
    function validateXY() {
        document.getElementById("messageX").innerHTML = "<br>";
        document.getElementById("messageY").innerHTML = "<br>";
        let flag = false;

         let valueX = document.forms['form'].elements['X'].value.trim();
         if(valueX===""){
             document.getElementById("messageX").innerHTML = "Введите X";
             flag = false;
         } else {
             if (!/^(-?\d+)([.,]\d+)?$/
                 .test(valueX)) {
                 document.getElementById("messageX").innerHTML = "X должен быть числом";
                 flag = false;
             } else {
                 if (!/^-?0*[0-2]([.,]\d+)?$/
                     .test(valueX)) {
                     flag = false;
                     document.getElementById("messageX").innerHTML = "X находится вне диапозона";
                 }
             }
         }

        for(let i=-3; i<6; i++){
            if(document.getElementById("chb"+i).checked){
                flag=true;
                break
            }
        }
        if(!flag){
            document.getElementById("messageY").innerHTML = "Введите Y";
        }
        return flag;
    }
    function paint(R){
        let plot = document.getElementById("areas");
        let ctx = plot.getContext('2d');
        ctx.fillStyle = "#fff";
        ctx.fillRect(0,0, 270, 270);

        ctx.fillStyle = "#66C1FF";
        ctx.fillRect(135.5, 135.5-R, R/2, R);
        ctx.beginPath();
        ctx.arc(135.5, 135.5, R/2, 0, Math.PI*0.5, false);
        ctx.lineTo(135.5,135.5);
        ctx.closePath();
        ctx.fill();
        ctx.beginPath();
        ctx.moveTo(135,135);
        ctx.lineTo(135, 135+R);
        ctx.lineTo(135-R, 135);
        ctx.closePath();
        ctx.fill();

        ctx.fillStyle = "#000";
        ctx.beginPath();
        ctx.moveTo(135, 270);
        ctx.lineTo(135, 0);
        ctx.moveTo(130, 8);
        ctx.lineTo(135,0);
        ctx.lineTo(140, 8);

        ctx.moveTo(0, 135);
        ctx.lineTo(270, 135);
        ctx.moveTo(262, 130);
        ctx.lineTo(270, 135);
        ctx.lineTo(262, 140);
        for(let i = 10; i<261; i+=25){
            ctx.moveTo(i, 130);
            ctx.lineTo(i, 140);
            ctx.moveTo(130, i);
            ctx.lineTo(140, i);
        }
        ctx.stroke();
        ctx.font = "bold 12px sans-serif";
        ctx.fillText("x", 260, 145);
        ctx.fillText("y", 145, 10);
    }

    $(function(){
        paint(0);
    });

    $('input[name=rb]').change( function () {
        Radius = $(this).val();
        paint(Radius*25);
    });

    $('#form').submit(function () {
        if(validateXY()&&validateR()) {
            $.post(
                "/controller",
                $('#form').serialize(),
                function (msg) {
                    $('#result').html(msg);
                }
            )
        }
        return false;
    });

    function clickCanvas(e){
        let x;
        let y;
        let r;
        if(validateR()){
            if (e.pageX != undefined && e.pageY != undefined) {
                x = e.pageX;
                y = e.pageY;
            }
            else {
                x = e.clientX + document.body.scrollLeft +
                    document.documentElement.scrollLeft;
                y = e.clientY + document.body.scrollTop +
                    document.documentElement.scrollTop;
            }
            x = (x - plot_canvas.offsetLeft -135)/25;
            y = (y - plot_canvas.offsetTop -135)/25;
            r = $('input[name=rb]:checked').val();
            $.post(
                "/controller",
                {x: x, chb6: y, rb: r},
                function (msg) {
                    $('#result').html(msg);
                }
            )
        }
    }
</script>
</html>