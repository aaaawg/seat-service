let num;
function findAddress() {
    new daum.Postcode({
        oncomplete: function(data) {
            let extraAddr = '';
            if(data.bname !== '')
                extraAddr += data.bname;
            if(data.buildingName !== '')
                extraAddr += (extraAddr !== ''? ', ' + data.buildingName : data.buildingName)
            if(extraAddr !== '')
                extraAddr = ' (' + extraAddr + ')';
            document.getElementById("address").value = data.roadAddress + extraAddr;
            document.getElementById("detailAddress").focus();
        }
    }).open();
}
function userEnterData() {
    let phone1 = document.getElementById("phone1").value;
    let phone2 = document.getElementById("phone2").value;
    let phone3 = document.getElementById("phone3").value;
    let phone = document.getElementById("phone");

    phone.value = phone1 + "-" + phone2 + "-" + phone3;

    if(document.getElementById("detailAddress").value !== "")
    document.getElementById("fullAddress").value = document.getElementById("address").value + ', ' + document.getElementById("detailAddress").value;
    else
    document.getElementById("fullAddress").value = document.getElementById("address").value;

    document.getElementById("email").value = document.getElementById("email1").value + '@' + document.getElementById("email2").value;
}
function checkNumber(id) {
    let id1 = document.getElementById(id);
    const regex = /\D/g;
    id1.value = id1.value.replace(regex, "");
}
function sendEmail() {
    let email = document.getElementById("email1").value + '@' + document.getElementById("email2").value;
    document.getElementById("sendEmailMessage").innerText = "이메일을 전송했습니다.";

    fetch('/join/email', {
        method: "post",
        headers: {
        "Content-Type": "application/json",
    },
        body: JSON.stringify({
        "userEmail": email
    })
    }).then((r) => {
        return r.json();
    }).then((r) => {
        num = JSON.parse(r);
        document.getElementById("sendEmailMessage").innerText = "";
        $('#enterNumberModal').modal('show');
        document.getElementById("checkResult").innerText = "";
    })
}
function checkAuthNum() {
    let en = document.getElementById("enterNum");
    let re = document.getElementById("checkResult");

    if (num == en.value) {
        document.getElementById("email1").readOnly = true;
        document.getElementById("email2").readOnly = true;
        document.getElementById("check").style.display = "none";
        $('#enterNumberModal').modal('hide');
    } else {
        re.innerText = "인증번호가 올바르지 않습니다.";
        en.value = "";
    }
}
function changeText(id) {
    if(id === "user") {
        document.getElementById("n").textContent = "이름";
        document.getElementById("a").textContent = "주소";
        document.getElementById("p").textContent = "휴대폰번호";
        document.getElementById("role").value = "user"
    }
    else {
        document.getElementById("n").textContent = "기업(기관)명";
        document.getElementById("a").textContent = "기업(기관)주소";
        document.getElementById("p").textContent = "전화번호";
        document.getElementById("role").value = "biz"
    }
}