let sCol;
let sRow;
function createSeatingChart() {
    let seatNum = 0;
    sCol = document.getElementById("seat").value;
    sRow = document.getElementById("seatRow").value;
    let chart = document.getElementById("chart");

    chart.innerHTML = "";
    for (let i = 0; i < sRow; i++) {
        let row = document.createElement("div");
        row.className = "row justify-content-center";
        for (let j = 0; j < sCol; j++) {
            let sBtn = document.createElement("button");
            sBtn.className = "seatBtn";
            sBtn.style.backgroundColor = "#83A783"
            seatNum++;
            sBtn.innerText = seatNum + "";
            row.appendChild(sBtn);
        }
        chart.appendChild(row);
    }

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
}
function saveSeatingChart() {
    const s = document.querySelectorAll(".seatBtn");
    let arr = new Array();
    for (let i = 0; i < s.length; i++) {
        if(s[i].style.backgroundColor === "rgb(131, 167, 131)")
            arr.push(1);
        else
            arr.push(0);
    }
    window.opener.popupSeat(arr, sCol);
    self.close();
}