let addr;
let popupSeatArr;
let popupSeatCol;

window.addEventListener("load", function() {
    showCreatSeatingChart(0);
    showPlaceInput(0);
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
let viewingDateAndTimeList = new Array();
function addViewing() {
    const viewing = document.getElementById("viewing").value;

    if(viewing.trim().length > 0) {
        const html = `<div class="form-check"><input class='form-check-input' type='checkbox' onchange='chBoxChange(this)' name='viewingDateAndTime' value="${viewing}" checked>
                  <label class="form-check-label">${viewing}</label></div>`
        document.getElementById("viewingList").innerHTML += html;
        viewingDateAndTimeList.push(viewing);
    }
    else
        alert("날짜가 선택되지 않았습니다.")
}
function chBoxChange(e) {
    const i = viewingDateAndTimeList.indexOf(e.value);
    if(e.checked)
        viewingDateAndTimeList.push(e.value);
    else
        viewingDateAndTimeList.splice(i, 1);
}
function showPlaceInput(type) {
    valueClear();
    document.getElementById("peopleChBox").checked = false;
    peopleNumValue();
    document.getElementById("enterPeopleNum").style.display = "inline";

    if(type) {
        enterPlace("온라인");
        document.getElementById("onlineWay").style.display = "block";
        document.getElementById("way").disabled = false;

        document.getElementById("offSeatingChart").style.display = "none";
        showCreatSeatingChart(0);
    }
    else {
        enterPlace("오프라인");
        document.getElementById("onlineWay").style.display = "none";
        document.getElementById("way").disabled = true;

        document.getElementById("nsc").checked = true;
        document.getElementById("offSeatingChart").style.display = "block"
        document.getElementById("csc").style.display = "none";
    }
}
function openPopup() {
    const popup = window.open("/business/program/seat", "좌석배치도", "width=500px, height=520px");

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
    document.getElementById("detail2").innerHTML = "";
    let html;

    if (drop.selectedIndex === 1) {
        document.getElementById("detail2").classList.add("col");
        html = "<input type='hidden' id='areaTargetDetail' name='targetDetail'>"
        html += '    <select id="area1" class="form-select" onchange="getAreaDetail(this)">\n' +
            '        <option selected disabled>선택1</option>\n' +
            '        <option>서울</option>\n' +
            '        <option>경기</option>\n' +
            '        <option>인천</option>\n' +
            '        <option>부산</option>\n' +
            '        <option>대구</option>\n' +
            '        <option>광주</option>\n' +
            '        <option>대전</option>\n' +
            '        <option>울산</option>\n' +
            '        <option>세종</option>\n' +
            '        <option>강원</option>\n' +
            '        <option>경남</option>\n' +
            '        <option>경북</option>\n' +
            '        <option>전남</option>\n' +
            '        <option>전북</option>\n' +
            '        <option>충남</option>\n' +
            '        <option>충북</option>\n' +
            '        <option>제주</option>\n' +
            '    </select>';
    } else if (drop.selectedIndex === 2) {
        document.getElementById("detail2").classList.remove("col");
        html = '<input class="form-control" id="etcDetail" type=text name="targetDetail" placeholder="신청대상을 입력해 주세요.">';
    } else
        html = "";

    detailDiv.innerHTML = html;
}
function showAreaDetail(t) {
    let list = t;
    const aDiv = document.getElementById("detail2");

    let html = "<select id='area2' class='form-select'><option selected>선택안함</option>"
    for (let i = 0; i < list.length; i++) {
        html += `<option>${list[i]}</option>`
    }
    html += "</select>"

    aDiv.innerHTML = html;
}

function getAreaDetail(t) {
    const a = t.value;
    const list = [];
    fetch(`/business/program/areaList?area=${a}`)
        .then((r) => {
            return r.json()
        })
        .then((r) => {
            for (let i = 0; i < r.length; i++) {
                list.push(r[i].area2)
            }
            showAreaDetail(list);
        })
}
function checkUserValue() {
    if(document.getElementById("title").value.trim().length === 0) {
        alert("프로그램명을 입력해주세요.");
        return false;
    }
    if(document.getElementById("place").value.trim().length === 0) {
        alert("장소를 입력해주세요.");
        return false;
    }
    if(document.getElementById("online").checked === true) {
        if (document.getElementById("way").value.trim().length === 0) {
            alert("참여방법을 입력해주세요.");
            return false;
        }
    }
    if(document.getElementById("drop").selectedIndex === 2 && document.getElementById("etcDetail").value.trim().length === 0) {
        alert("신청대상을 입력해주세요.");
        return false;
    }
    if(document.getElementById("peopleChBox").checked === false && document.getElementById("peopleNum").value.trim().length === 0) {
        alert("모집인원을 입력해주세요.");
        return false;
    }
    if(document.getElementById("startDate").value.trim().length === 0) {
        alert("신청 시작일을 입력해주세요.");
        return false;
    }
    if(document.getElementById("endDate").value.trim().length === 0) {
        alert("신청 종료일을 입력해주세요.");
        return false;
    }
    if(document.getElementById("startDate").value > document.getElementById("endDate").value) {
        alert("신청 종료일이 신청 시작일보다 이후여야 합니다.");
        return false;
    }
    if(document.getElementById("offline").checked) {
        if (document.getElementById("ysc").checked && document.getElementById("seatingChart").value.trim().length === 0) {
            alert("좌석표를 생성해주세요.");
            return false;
        }
    }
    if(viewingDateAndTimeList.length === 0) {
        alert("진행일시를 추가해주세요.");
        return false;
    }
    return true;
}