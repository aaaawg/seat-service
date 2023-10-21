let addr;
let popupSeatArr;
let popupSeatCol;

window.addEventListener("load", function() {
    showCreatSeatingChart(0);
    enterPlace("오프라인");
});

function placeValue() {
    let detailAddr= document.getElementById("detailAddress").value;
    let val = '';

    val += (detailAddr !== ''? addr + ', ' + detailAddr : addr)

    document.getElementById("place").value = val;
}
function checkNumber(id) {
    let id1 = document.getElementById(id);
    const regex = /\D/g;
    id1.value = id1.value.replace(regex, "");
}
function findAddress() {
    new daum.Postcode({
        oncomplete: function(data) {
            let extraAddr = '';
            addr = '';
            if(data.bname !== '')
                extraAddr += data.bname;
            if(data.buildingName !== '')
                extraAddr += (extraAddr !== ''? ', ' + data.buildingName : data.buildingName)
            if(extraAddr !== '')
                extraAddr = ' (' + extraAddr + ')';
            document.getElementById("address").value = data.roadAddress + extraAddr;
            addr = data.roadAddress + extraAddr;
            placeValue(addr);
            document.getElementById("detailAddress").focus();
        }
    }).open();
}
function addViewing() {
    let viewing = document.getElementById("viewing").value;

    let check = document.createElement("input");
    check.className = "form-check-input"
    check.type = "checkbox";
    check.value = viewing;
    check.name = "viewingDateAndTime"
    check.checked = true;

    let label = document.createElement("label");
    label.className = "form-check-label";
    label.textContent = check.value;

    let viewingList = document.getElementById("viewingList");
    viewingList.appendChild(check);
    viewingList.appendChild(label);
    viewingList.appendChild(document.createElement("br"));
}
function showPlaceInput(type) {
    valueClear();
    document.getElementById("peopleChBox").checked = false;
    peopleNumValue();
    document.getElementById("enterPeopleNum").style.display = "inline";

    if(type) {
        enterPlace("온라인");
        document.getElementById("onlineWay").style.display = "block";

        document.getElementById("offSeatingChart").style.display = "none";
        showCreatSeatingChart(0);
    }
    else {
        enterPlace("오프라인");
        document.getElementById("onlineWay").style.display = "none";

        document.getElementById("nsc").checked = true;
        document.getElementById("offSeatingChart").style.display = "block"
        document.getElementById("csc").style.display = "none";
    }
}
function openPopup() {
    const popup = window.open("/business/program/seat", "좌석배치도", "width=1000px, height=1000px");

    popup.addEventListener("beforeunload", function () {
        const c = document.getElementById("popupChart");
        c.innerHTML = "";
        let num = 1;
        let seatNum = 1;
        let exSeatCount = 0;
        for (let i = 0; i < popupSeatArr.length; i++) {
            if (popupSeatArr[i]) {
                c.innerHTML += "<div class='col seatBtn' style='float: left; background-color: rgb(131, 167, 131);'>" + seatNum + "</div>";
                seatNum++;
            }
            else {
                exSeatCount++;
                c.innerHTML += "<div class='col seatBtn' style='float: left; background-color: rgb(215, 215, 215);'></div>";
            }
            if(num % popupSeatCol === 0 )
                c.innerHTML += "<br>";
            num++;
        }
        let chart = JSON.stringify(popupSeatArr);
        document.getElementById("seatingChart").value = chart;
        document.getElementById("seatCol").value = popupSeatCol;

        document.getElementById("peopleNum").value = popupSeatArr.length - exSeatCount;
        document.getElementById("peopleNum").readOnly = true;

        document.getElementById("enterPeopleNum").style.display = "none";
        document.getElementById("seatLength").innerText = document.getElementById("peopleNum").value;
    });
}
function showCreatSeatingChart(v) {
    valueClear();
    if(v) {
        document.getElementById("csc").style.display = "block";
        document.getElementById("cb").disabled = false;
        document.getElementById("seatingChart").disabled = false;
    } else {
        document.getElementById("popupChart").innerHTML = "";

        document.getElementById("csc").style.display = "none";
        document.getElementById("cb").disabled = true;
        document.getElementById("seatingChart").disabled = true;
        document.getElementById("peopleNum").readOnly = false;
        document.getElementById("enterPeopleNum").style.display = "inline";
    }
}
function popupSeat(arr, col) {
    popupSeatArr = arr;
    popupSeatCol = col;
}
function valueClear() {
    document.getElementById("seatingChart").value = null;
    document.getElementById("seatCol").value = null;
    document.getElementById("peopleNum").value = null;
    document.getElementById("seatLength").innerText = null;
    document.getElementById("way").value = null;
}
function peopleNumValue() {
    const ch = document.getElementById("peopleChBox");
    if(ch.checked) {
        document.getElementById("peopleNum").style.display = "none";
        document.getElementById("peopleNum").disabled = true;
        if(document.getElementById("offline").checked) {
            document.getElementById("offSeatingChart").style.display = "none";
            showCreatSeatingChart(0);
        }
    }
    else {
        document.getElementById("peopleNum").style.display = "inline";
        document.getElementById("peopleNum").disabled = false;

        if(document.getElementById("offline").checked) {
            document.getElementById("offSeatingChart").style.display = "block";
            showCreatSeatingChart(0);
        }
    }
}
function enterPlace(type) {
    const placeDiv = document.getElementById("placeDiv");
    let html;

    if(type === "오프라인") {
        html = "<input type='button' id='searchAddr' value='주소 검색' onclick='findAddress()'>";
        html += "<input type='text' id='address' readonly placeholder='주소'>";
        html += "<input type='text' id='detailAddress' onchange='placeValue()' placeholder='상세주소'>";
        html += "<input type='hidden' id='place' name='place'>";
    }
    else {
        html = "<input type='text' id='place' name='place'>";
    }

    placeDiv.innerHTML = html;
}