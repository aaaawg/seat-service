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