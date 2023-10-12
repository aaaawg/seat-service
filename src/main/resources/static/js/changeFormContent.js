function returnResult(){
    const form = document.querySelector('#content');
    //1. input readonly 없애기
    var inputText = form.querySelectorAll("input[type='text'], textarea");
    for(let i=0;i<inputText.length;i++){inputText[i].disabled = false;}
    //2. 편집, 삭제 버튼 없애기
    var btnDelete = form.querySelectorAll("button[class*=btn-change], button[class*=btn-delete]");
    for(let i=0;i<btnDelete.length;i++){btnDelete[i].remove();}
    //3. 히든 삭제
    var inputSelect = form.querySelectorAll('[id^=select]:not([id*=Div]):not([id*=reply])');
    for(let i=0;i<inputSelect.length;i++){inputSelect[i].remove();}

    var hiddenDiv1 = form.querySelectorAll('[class^=classTitle]');
    var hiddenDiv2 = form.querySelectorAll('[class^=classDetail]');
    const length = hiddenDiv1.length;
    for(let i=0;i<length;i++){
        hiddenDiv1[i].remove();
        hiddenDiv2[i].remove();
    }
}

//답변 저장하기
function getResponse(){
    var wholeQuestion = document.querySelectorAll('[id^=plus]');
    var questionNum = wholeQuestion.length;
    var responseJson = {};
    if(questionNum === 0){
        alert("count 0");
        responseJson = null;
    }else{
    for(let i=0; i<questionNum; i++){
    alert("count not 0");
        var re = getType(wholeQuestion[i].querySelector('.reDiv'+i), i);
        if(re === ''){ return 0;}
        responseJson[i] = re;
    }

    }
    return responseJson;
}
//질문 type 선정
function getType(getDiv, i){
    if(getDiv.querySelectorAll("input[type='text']").length !== 0){
        //단답형
        console.log("단답형 접근");
        console.log("1: "+getDiv.querySelector("input[type='text']").value);
        return getDiv.querySelector("input[type='text']").value;
    }else if(getDiv.querySelectorAll("textarea").length !== 0){
        //서술형
        console.log("서술형 접근");
        console.log("2: "+getDiv.querySelector("textarea").value);
        return getDiv.querySelector("textarea").value;
    }else if(getDiv.querySelectorAll("input[type='radio']").length !== 0){
        //radio
        console.log("radio 접근");
        var value = getDiv.querySelector('input[name="reply'+i+'"]:checked');
        if(value === null) return '';
        else{return value.value;}
    }else{
        //checkbox
        console.log("checkbox 접근");
        var value = getDiv.querySelectorAll("input[type='checkbox']:checked");
        const selectedValues = [];
        value.forEach(checkbox => {
            selectedValues.push(checkbox.value);
        });
        console.log("4: "+selectedValues);
        return selectedValues;
    }

}
