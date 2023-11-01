let sCol;
let sRow;
function createSeatingChart() {
    let seatNum = 0;
    sCol = document.getElementById("seat").value;
    sRow = document.getElementById("seatRow").value;
    const chart = document.getElementById("chart");

    chart.innerHTML = "";
    let iHtml = "";
    let chColValue = 0;
    let chRowValue = 0;
    for (let i = 0; i < sRow; i++) {
        if(i === 0) {
            iHtml += "<div class='row justify-content-center'>";
            for (let j = 0; j < sCol; j++) {
                chColValue++;
                if(j === 0) {
                    iHtml += "<div class='ch' style='background-color: white'></div>"
                }
                iHtml += `<div class='ch'><input type=checkbox class='form-check-input colCh' style='margin: 2px' value="${chColValue}"></div>`;
            }
            iHtml += "</div>";
        }
        iHtml += "<div class='row justify-content-center'>";
        for (let j = 0; j < sCol; j++) {
            if(j === 0) {
                chRowValue++;
                iHtml += `<div class='ch'><input type=checkbox class='form-check-input rowCh' style='margin: 2px' value='${chRowValue}'></div>`;
            }
            seatNum++;
            iHtml += `<button class='seatBtn' style='background-color: #83A783'>${seatNum}</button>`;
        }
        iHtml += "</div>";
    }
    chart.innerHTML = iHtml;

    let c = document.querySelectorAll(".seatBtn");
    for (let i = 0; i < c.length; i++) {
        c[i].addEventListener("click",function (i) {
            if(c[i].style.backgroundColor === "rgb(131, 167, 131)") {
                c[i].style.backgroundColor = "rgb(215, 215, 215)";
            }
            else {
                c[i].style.backgroundColor = "rgb(131, 167, 131)";
            }
        }.bind(this, i));
    }

    $(".ch > input[type=checkbox]").change(function () {
        if($(this).hasClass('colCh')) {
            let n = parseInt(this.value);
            let cNum = parseInt(sCol);

            let c = document.querySelectorAll(".seatBtn");
            for (let i = 0; i < c.length; i++) {
                if(c[i].innerText == n) {
                    c[i].click();
                    n = n + cNum;
                }
            }
        }
        else {
            $(this).parents().nextAll(".seatBtn").click();
        }
    })
}
function saveSeatingChart() {
    const s = document.querySelectorAll(".seatBtn");
    sCol = document.getElementById("seat").value;
    let arr = new Array();
    for (let i = 0; i < s.length; i++) {
        if(s[i].style.backgroundColor === "rgb(131, 167, 131)"){
            arr.push(1);
        }
        else
            arr.push(0);
    }
    window.opener.popupSeat(arr, sCol);
    window.close();
}