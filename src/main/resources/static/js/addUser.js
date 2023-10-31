let num;
let checkedId;

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
            document.getElementById("addr1").value = data.roadAddress + extraAddr;
            document.getElementById("addr2").focus();
        }
    }).open();
}
function userEnterData() {
    if (document.getElementById("userId").value !== checkedId)
        alert("아이디 중복확인이 필요합니다.");
    else if (document.getElementById("enterNum").value != num)
        alert("이메일 인증이 필요합니다.");
    else if(document.getElementById("role").value === "user" && document.getElementById("birth").value.trim().length === 0)
        alert("생년월일을 입력해주세요.")
    else {
        let phone1 = document.getElementById("phone1").value;
        let phone2 = document.getElementById("phone2").value;
        let phone3 = document.getElementById("phone3").value;
        let phone = document.getElementById("phone");

        phone.value = phone1 + "-" + phone2 + "-" + phone3;
        if(document.getElementById("role").value === "biz")
            document.getElementById("birth").disabled = true;

        if (document.getElementById("addr2").value !== "")
            document.getElementById("address").value = document.getElementById("addr1").value + ', ' + document.getElementById("addr2").value;
        else
            document.getElementById("address").value = document.getElementById("addr1").value;

        document.getElementById("email").value = document.getElementById("email1").value + '@' + document.getElementById("email2").value;

        document.getElementById("joinForm").submit();
    }
}
function checkNumber(id) {
    let id1 = document.getElementById(id);
    const regex = /\D/g;
    id1.value = id1.value.replace(regex, "");
}
function sendEmail() {
    let email = document.getElementById("email1").value + '@' + document.getElementById("email2").value;

    if(document.getElementById("email1").value.trim().length !== 0 && document.getElementById("email2").value.trim().length !== 0) {
        document.getElementById("sendEmailMessage").innerText = "이메일을 전송했습니다.";
        fetch(`/join/email?email=${email}`)
            .then((r) => {
                return r.text();
            }).then((r) => {
                num = r;
                console.log(num);
                document.getElementById("sendEmailMessage").innerText = "";
                $('#enterNumberModal').modal('show');
                document.getElementById("checkResult").innerText = "";
            })
    }
    else
        alert("이메일을 입력해주세요.");
}
function checkAuthNum() {
    let en = document.getElementById("enterNum");
    let re = document.getElementById("checkResult");

    if (num == en.value) {
        document.getElementById("email1").readOnly = true;
        document.getElementById("email2").readOnly = true;
        document.getElementById("check").remove();
        $('#enterNumberModal').modal('hide');

    } else {
        re.innerText = "인증번호가 올바르지 않습니다.";
        en.value = "";
    }
}
function changeText(id) {
    document.getElementById("birth").value = "";
    $("#btnDiv > button").removeClass("activeUserTypeBtn");

    if(id === "user") {
        document.getElementById("user").classList.add("activeUserTypeBtn");
        document.getElementById("n").textContent = "이름";
        document.getElementById("a").textContent = "주소";
        document.getElementById("p").textContent = "휴대폰번호";
        document.getElementById("role").value = "user"
        document.getElementById("birthInput").style.display = "block";
        document.getElementById("userType").innerText = "개인 회원가입";
    }
    else {
        document.getElementById("biz").classList.add("activeUserTypeBtn");
        document.getElementById("n").textContent = "기업(기관)명";
        document.getElementById("a").textContent = "기업(기관)주소";
        document.getElementById("p").textContent = "전화번호";
        document.getElementById("role").value = "biz"
        document.getElementById("birthInput").style.display = "none";
        document.getElementById("userType").innerText = "기업 회원가입";
    }
}

function checkId() {
    const userId = document.getElementById("userId").value;
    document.getElementById("idMessage").innerHTML = "";

    if(userId.trim().length !== 0) {
        fetch(`/join/id?id=${userId}`)
            .then((r) => {
                document.getElementById("userId").classList.remove("errorInput");
                if (r.ok) {
                    document.getElementById("idMessage").innerHTML = "사용 가능한 아이디입니다.<button id='useIdBtn' class='badge bg-light text-dark' onclick='useId()'>사용하기</button>"
                } else {
                    document.getElementById("idMessage").innerText = "사용할 수 없는 아이디 입니다.";
                }
            })
    }
    else
        alert("아이디를 입력해주세요.");
}
function useId() {
    if(document.getElementById("userId").value.trim().length !== 0) {
        checkedId = document.getElementById("userId").value;
        document.getElementById("checkIdBtn").style.display = "none";
        document.getElementById("idDiv").className = "";
        document.getElementById("userId").readOnly = true;
        document.getElementById("idMessage").innerHTML = "";
    }
}
window.addEventListener("load", function () {
    if(role != null) {
        if(role === "user")
            changeText("user")
        else if(role === "biz")
            changeText("biz");
    }
})


