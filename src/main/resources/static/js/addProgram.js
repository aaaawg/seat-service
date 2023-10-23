let addr;
let popupSeatArr;
let popupSeatCol;

window.addEventListener("load", function() {
    showCreatSeatingChart(0);
    enterPlace("오프라인");
});

$(document).ready(function() {
    $('.summernote').summernote({
        tabsize: 2,
        placeholder: '프로그램 상세정보를 입력해 주세요',
        lang: "ko-KR",
        height: 500,
        toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'underline', 'clear']],
            ['fontname', ['fontname']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['table', ['table']],
            ['insert', ['link', 'picture']],
            ['view', ['help']],
        ]
    });

    $("[data-bs-toggle='tooltip']").tooltip({
        trigger: "click hover"
    });
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
    const viewing = document.getElementById("viewing").value;

    const html = `<div class="form-check"><input class='form-check-input' type='checkbox' name='viewingDateAndTime' value="${viewing}" checked>
                  <label class="form-check-label">${viewing}</label></div>`
    document.getElementById("viewingList").innerHTML += html;
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
        c.classList.add("chartPreview");
        c.innerHTML = "<div><i class='bi bi-eye'></i> 좌석표 미리보기</div><hr>";
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
        document.getElementById("csc").style.display = "inline";
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
    document.getElementById("popupChart").classList.remove("chartPreview");
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
        html = "<div class='input-group'><input type='text' class='form-control' id='address' readonly placeholder='주소'>";
        html += "<a class='btn text-white' id='searchAddr' onclick='findAddress()'>검색</a></div>";
        html += "<input type='text' class='form-control' id='detailAddress' onchange='placeValue()' placeholder='상세주소'>";
        html += "<input type='hidden' id='place' name='place'>";
    }
    else {
        html = "<input type='text' class='form-control' id='place' name='place'>";
    }

    placeDiv.innerHTML = html;
}
function showArea() {
    const drop = document.getElementById("drop");
    const detailDiv = document.getElementById("detail");
    let html;

    if (drop.selectedIndex === 1) {
        let addr = userAddr.split(' ', 2);
        let addr1 = addr[0];
        let addr2 = addr[1];

        html = `<select class="form-select" name="targetDetail"><option value='${addr1}'>${addr1}</option><option value='${addr1} ${addr2}'>${addr2}</option></select>`;
    } else if (drop.selectedIndex === 2) {
        html = '<input class="form-control" type=text name="targetDetail" placeholder="신청대상을 입력해 주세요.">';
    } else
        html = "";

    detailDiv.innerHTML = html;
}