let addr;
let row;
let num;
function placeValue() {
    let detailAddr= document.getElementById("detailAddress").value;
    let val = '';

    val += (detailAddr !== ''? addr + ', ' + detailAddr : addr)

    document.getElementById("offPlace").value = val;
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
    if(type) {
        document.getElementById("off").style.display = "none";
        document.getElementById("on").style.display = "block";
        document.getElementById("onPlace").disabled = false;
        document.getElementById("offPlace").disabled = true;
    }
    else {
        document.getElementById("off").style.display = "block";
        document.getElementById("on").style.display = "none";
        document.getElementById("onPlace").disabled = true;
        document.getElementById("offPlace").disabled = false;
    }
}
function showArea() {
    let getAddr = document.getElementById("address").value;
    let drop = document.getElementById("drop");
    let addr = '';
    let addr1 = '';
    let addr2 = '';
    let targetDrop = document.getElementById("targetDrop");

    targetDrop.innerHTML = '';
    targetDrop.style.display = "none";

    if(getAddr !== '') {
        if (drop.options[drop.selectedIndex].value !== "제한없음") {
            targetDrop.style.display = "block";

            let areaDrop = document.createElement("select");
            areaDrop.id = "areaDrop";
            targetDrop.appendChild(areaDrop);

            addr = getAddr.split(' ', 2);
            addr1 = addr[0];
            addr2 = addr[1];

            let op1 = document.createElement("option");
            op1.value = addr1;
            op1.text = addr1;
            op1.name = "target";
            areaDrop.appendChild(op1);

            if(addr2 !== '') {
                let op2 = document.createElement("option");
                op2.value = addr1 + addr2;
                op2.text = addr2;
                op2.name = "target";
                areaDrop.options.add(op2);
            }
        }
    }
    else {
        alert("주소를 입력해주세요");
        drop.options[0].selected = true;
    }
}
function createSeatingChart() {
    let seatNum = 0;
    let seat = document.getElementById("seat").value;
    let sRow = document.getElementById("seatRow").value;
    let chart = document.getElementById("chart");

    chart.innerHTML = "";
    for (let i = 0; i < sRow; i++) {
        let row = document.createElement("div");
        row.className = "row justify-content-center";
        for (let j = 0; j < seat; j++) {
            let sBtn = document.createElement("a");
            sBtn.className = "seatBtn";
            seatNum++;
            sBtn.innerText = seatNum + "";

            sBtn.addEventListener("click", function() { removedSeat();})
            row.appendChild(sBtn);
        }
        chart.appendChild(row);
    }
}
function removedSeat() {
    let remove = document.getElementById("removedSeat");
    let n = document.createElement("a")
    n.innerText = row + "열 " + seatNum + "번";
    remove.appendChild(n)
}

function openPopup() {
    window.open("/business/program/seat", "좌석배치도", "width=1000px, height=1000px");
}