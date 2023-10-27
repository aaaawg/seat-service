var peopleNum = 0;
var seatingChart;
var seatCol;
var origPlace;
var origAddress;
var origDetailAddress;

window.addEventListener("load", (event) => {
    peopleNum = document.getElementById("peopleNum").value;
    seatingChart = document.getElementById("seatingChart").value;
    seatCol = document.getElementById("seatCol").value;
    origPlace = document.getElementById("place").value;
    origAddress = document.getElementById("address").value;
    origDetailAddress = document.getElementById("detailAddress").value;
    if(peopleNum === '무제한') peopleNum = '';
    init();
});

document.getElementById('updateForm').addEventListener('submit', function(event) {
   event.preventDefault();
   if (!check()) {
   console.log("not");
     return; //실행하지 않음
   }
   var currentUrl = window.location.href;
   var urlParts = currentUrl.split("/");
   var programNumIndex = urlParts.indexOf("update");
   var programNum = urlParts[programNumIndex + 1];
   var formData = new FormData(document.getElementById('updateForm'));
   var url = `business/program/update/${programNum}`;

   fetch(programNum, {
       method: 'POST',
       body: formData
      })
     .then(response => response.text())
     .then(data => {
         window.location.href = "http://localhost:8080/program/"+programNum;
     })
     .catch(error => {
       console.error(error);
     });
});
function check(){
    const place1 = document.getElementById("detailAddress").value;
    const place2 = document.getElementById("address").value;

    if(document.getElementById("offline").checked === true){
        if(place1 === '' || place2 === ''){
            alert("장소를 입력해주세요.");
                    return false;
        }
        if (document.getElementById("peopleNum").value === '0' || document.getElementById("peopleNum").value === '') {
            alert("모집 인원은 0명 이상이어야 합니다.");
            return false;
        }
    }else{
        if (document.getElementById("peopleNum").value === '0') {
            alert("모집 인원은 0명 이상이어야 합니다.");
            return false;
        }
    }

    if(document.getElementById("title").value === ''){
        alert("타이틀을 입력해주세요.");
        return false;
    }

    if(document.getElementById("way").value === ''){
        alert("접속 링크를 입력해주세요.");
        return false;
    }

    document.getElementById("peopleNum").readOnly = false;
    document.getElementById("peopleNum").disabled = false;
    document.getElementById("drop").disabled = false;
    document.getElementById("offline").disabled = false;
    document.getElementById("online").disabled = false;
    return true;
}

function showPlaceInput(type, result) {
    if(result > 0){
        alert("신청자가 존재하므로 변경이 불가능합니다.");
        if(type === 1){
            document.getElementById("offline").checked = true;
        }else{
            document.getElementById("online").checked = true;
        }
    }else{
        valueClear();
    if(type) {
    //온라인
        document.getElementById("off").style.display = "none";
        document.getElementById("on").style.display = "block";
        document.getElementById("way").style.display = "block";
        document.getElementById("peopleNum").readOnly=false;
        document.getElementById("peopleNum").disabled = false;
        document.getElementById("seatingChart").value = '';
        document.getElementById("searchAddr").style.display = "none";
        document.getElementById("searchAddr").readOnly=true;
        document.getElementById("detailAddress").style.display = "none";

        document.getElementById("isSeatColSelect").style.display = "none";
        document.getElementById("isNotSeatColSelect").style.display = "none";
        document.getElementById("deleteChart").style.display = "none";
        document.getElementById("peopleNum").value = '';
    }
    else {
    //오프라인
    if(peopleNum!==0){
        document.getElementById("peopleNum").value = peopleNum;
        document.getElementById("seatingChart").value = seatingChart;
        document.getElementById("seatCol").value = seatCol;
    }
    if(origAddress !== '온라인 관람'){
        document.getElementById("place").readOnly = false;

        document.getElementById("place").value = origPlace;
        document.getElementById("address").value = origAddress;
        document.getElementById("detailAddress").value = origDetailAddress;
        document.getElementById("place").readOnly = true;
    }else{
        document.getElementById("address").value = '';
        document.getElementById("detailAddress").value = '';
    }
        document.getElementById("off").style.display = "block";
        document.getElementById("on").style.display = "none";
        document.getElementById("way").style.display = "none";
        document.getElementById("searchAddr").style.display = "inline";
        document.getElementById("detailAddress").style.display = "inline";

        const checkSeat = document.getElementById("seatingChart");
        console.log(checkSeat.value);
        if(checkSeat.value === ""){
            document.getElementById("isNotSeatColSelect").style.display = "inline";
            document.getElementById("isSeatColSelect").style.display = "none";
            document.getElementById("deleteChart").style.display = "none";
        }else {
            document.getElementById("isSeatColSelect").style.display = "inline";
            document.getElementById("deleteChart").style.display = "inline";
            document.getElementById("isNotSeatColSelect").style.display = "none";
        }

    }
    }
}
function valueClear() {
    document.getElementById("seatingChart").value = '';
    document.getElementById("seatCol").value = '';
    document.getElementById("peopleNum").value = '';
}
function peopleNumValue() {
    const ch = document.getElementById("peopleChBox");

    if(ch.checked) {
        document.getElementById("peopleNum").style.display = "none";
        document.getElementById("peopleNum").disabled = true;
        //document.getElementById("peopleNum").value = '';

        valueClear();

        document.getElementById("deleteChart").style.display = "none";

        document.getElementById("isSeatColSelect").style.display = "none";
        document.getElementById("isNotSeatColSelect").style.display = "none";
    }
    else {
        if(peopleNum !== 0){
            document.getElementById("peopleNum").value = peopleNum;
            document.getElementById("seatingChart").value = seatingChart;
            document.getElementById("seatCol").value = seatCol;
        }
        document.getElementById("peopleNum").style.display = "inline";
        document.getElementById("peopleNum").disabled = false;

        const checkSeat = document.getElementById("seatingChart");
        if(document.getElementById("offline").checked === true){
                if(checkSeat.value === ""){
                    document.getElementById("isNotSeatColSelect").style.display = "inline";
                    document.getElementById("isSeatColSelect").style.display = "none";
                    document.getElementById("deleteChart").style.display = "none";
                }else {
                    document.getElementById("isSeatColSelect").style.display = "inline";
                    document.getElementById("deleteChart").style.display = "inline";
                    document.getElementById("isNotSeatColSelect").style.display = "none";
                }
        }else{
                document.getElementById("isSeatColSelect").style.display = "none";
                document.getElementById("isNotSeatColSelect").style.display = "none";
                document.getElementById("deleteChart").style.display = "none";
                document.getElementById("peopleNum").value = '';
        }
    }
}

function deleteSeatChart(bookingCnt){
    if(bookingCnt > 0){
        alert("신청자가 존재하므로 변경이 불가능합니다.");
    }else{
     document.getElementById("seatingChart").value = '';
     document.getElementById("peopleNum").value = 0;
     document.getElementById("peopleNum").readOnly = false;
     document.getElementById("peopleNum").disabled = false;
     document.getElementById("peopleNum").style.backgroundColor = "white";
     document.getElementById("peopleNum").style.color ="black";
     document.getElementById("seatCol").value = '';
     document.getElementById('deleteChart').style.display = "none";
     }
}

let addr;
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
function placeValue() {
    let detailAddr= document.getElementById("detailAddress").value;
    let val = '';
    val += (detailAddr !== ''? addr + ', ' + detailAddr : addr)
    document.getElementById("place").value = val;
}
function changeTargetSelect(bookingCnt) {
    if(bookingCnt > 0){
        alert("신청자가 존재하므로 변경이 불가능합니다.");
        var selectElement = document.getElementById("drop");
        var selectValue = selectElement.options[selectElement.selectedIndex].value;
        var optionElements = selectElement.getElementsByTagName("option");
        if (selectValue === "제한없음") {
            optionElements[1].selected = true; // "제한없음" 옵션을 선택
        } else if (selectValue === "지역") {
            optionElements[0].selected = true; // "지역" 옵션을 선택
        }
    }
}

function addViewing(){
    let viewing = document.getElementById("viewing").value;

    let check = document.createElement("input");
    let elementsWithInputId = document.querySelectorAll('[id*="button_"]');
    let checkCount = elementsWithInputId.length;
    for(let i=0; i<elementsWithInputId.length; i++){
        if(elementsWithInputId[i].className === checkCount.toString()){
               checkCount++;
               i=0;
        }
    }
    check.id = 'input_'+checkCount;
    check.type = "text";
    check.value = viewing;
    check.name = "viewingDateAndTime"

    let label = document.createElement("button");
    label.type = 'button';
    label.className = checkCount;
    label.id = 'button_'+checkCount;
    label.textContent = "X";
    label.onclick = function() {
        deleteViewing(this);
    };

    let viewingList = document.getElementById("viewDateTime");

    let viewDiv = document.createElement("div");
    viewDiv.className = "viewingList";
    viewDiv.appendChild(check);
    viewDiv.appendChild(label);

    viewingList.appendChild(viewDiv);
}

function deleteViewing(e){
    console.log("test:" + e.id + e.className);
    let num = e.className;
    let delInput = document.getElementById('input_'+num);
    delInput.remove();
    e.remove();
}


let imageIndex = 0;
let position = 0;

let imageIndexOrig = 0;
let positionOrig = 0;

function prev(){
const btnPrevious = document.querySelector(".previous");
const btnNext = document.querySelector(".next");
const images = document.querySelector("#image_container");
const imagesAll = images.querySelectorAll("img");
const imagesLength = imagesAll.length-1;
console.log("pre"+imagesLength);

    if(imageIndex > 0){
        btnNext.removeAttribute("disabled");
        position += imagesAll[imageIndex].width;
        images.style.transform = `translateX(${position}px)`;
        imageIndex = imageIndex -1;
    }
    if(imageIndex==0){
        btnPrevious.setAttribute('disabled','true');
    }
}
function prevOrig(){
const orig = document.querySelector("#origImg");
const btnPrevious = orig.querySelector(".previousOrig");
const btnNext = orig.querySelector(".nextOrig");
const images = document.querySelector("#origImage_container");
const imagesAll = images.querySelectorAll("img");
const imagesLength = imagesAll.length-1;
console.log("pre"+imagesLength);
    if(imageIndexOrig > 0){
        btnNext.removeAttribute("disabled");
        positionOrig += imagesAll[imageIndexOrig].width;
        images.style.transform = `translateX(${positionOrig}px)`;
        imageIndexOrig = imageIndexOrig -1;
    }
    if(imageIndexOrig==0){
        btnPrevious.setAttribute('disabled','true');
    }
}

function next(){
const btnPrevious = document.querySelector(".previous");
const btnNext = document.querySelector(".next");
const images = document.querySelector("#image_container");
const imagesAll = images.querySelectorAll("img");
const imagesLength = imagesAll.length-1;
console.log("n"+imagesLength);

    if(imageIndex < imagesLength){
         btnPrevious.removeAttribute("disabled");
         position -= imagesAll[imageIndex].width;
         images.style.transform = `translateX(${position}px)`;
         imageIndex = imageIndex +1;
     }
     if(imageIndex==imagesLength){
         btnNext.setAttribute('disabled','true');
     }
}
function nextOrig(){
const orig = document.querySelector("#origImg");
const btnPrevious = orig.querySelector(".previousOrig");
const btnNext = orig.querySelector(".nextOrig");
const images = document.querySelector("#origImage_container");
const imagesAll = images.querySelectorAll("img");
const imagesLength = imagesAll.length-1;
console.log("n"+imagesLength);
    if(imageIndexOrig < imagesLength){
         btnPrevious.removeAttribute("disabled");
         positionOrig -= imagesAll[imageIndexOrig].width;
         images.style.transform = `translateX(${positionOrig}px)`;
         imageIndexOrig = imageIndexOrig +1;
     }
     if(imageIndexOrig==imagesLength){
         btnNext.setAttribute('disabled','true');
     }
}

function init(){
    const btnPrevious = document.querySelector(".previous");
    const btnNext = document.querySelector(".next");
    btnPrevious.setAttribute('disabled','true');
    btnPrevious.addEventListener("click", prev);
    btnNext.addEventListener("click", next);
    if(document.querySelector(".previousOrig")!==null){
        const btnPreviousOrig = document.querySelector(".previousOrig");
        const btnNextOrig = document.querySelector(".nextOrig");
        btnPreviousOrig.setAttribute('disabled','true');
            btnPreviousOrig.addEventListener("click", prevOrig);
            btnNextOrig.addEventListener("click", nextOrig);
    }
}