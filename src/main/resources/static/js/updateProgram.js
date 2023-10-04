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

    document.getElementById("peopleNum").readOnly = false;
    document.getElementById("peopleNum").disabled = false;
    document.getElementById("drop").disabled = false;
    document.getElementById("offline").disabled = false;
    document.getElementById("online").disabled = false;

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
        document.getElementById("off").style.display = "inline";
        document.getElementById("on").style.display = "none";
        document.getElementById("address").value = '';
        document.getElementById("searchAddr").style.display = "inline";
        document.getElementById("detailAddress").style.display = "inline";
        document.getElementById("detailAddress").value = '';

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
    document.getElementById("seatingChart").value = null;
    document.getElementById("seatCol").value = null;
    document.getElementById("peopleNum").value = null;
}
function peopleNumValue() {
    const ch = document.getElementById("peopleChBox");
    if(ch.checked) {
        document.getElementById("peopleNum").style.display = "none";
        document.getElementById("peopleNum").disabled = true;
        document.getElementById("peopleNum").value = '';

        document.getElementById("deleteChart").style.display = "none";

        document.getElementById("isSeatColSelect").style.display = "none";
        document.getElementById("isNotSeatColSelect").style.display = "none";
    }
    else {
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
