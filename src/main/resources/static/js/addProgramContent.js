const countSet = document.querySelectorAll('[id^=plus]');
let count = 0;
count = countSet.length;

//데이터 전달
if(document.getElementById('myForm') !== null){
document.getElementById('myForm').addEventListener('submit', function(event) {
   event.preventDefault();
   if (!check()) {
     return; // fetch 실행하지 않음
   }
   if(!checkUserValue()) {
       return;
   }
   // 폼 데이터 생성
    const resultHtml = document.querySelector('#content').outerHTML;
    var additionalData;
    var formData = new FormData(document.getElementById('myForm'));
    /*if(count === 0){
         additionalData = { formHtml: ''};
    }else{
         additionalData = { formHtml: resultHtml};
            for (var key in additionalData) {
                formData.append(key, additionalData[key]);
            }
            var getTitleJson = getTitle();
            formData.append("getTitleJson", JSON.stringify(getTitleJson));
    }*/
    if (count !== 0) {
        additionalData = { formHtml: resultHtml };
        for (var key in additionalData) {
            formData.append(key, additionalData[key]);
        }
        var getTitleJson = getTitle();
        formData.append("getTitleJson", JSON.stringify(getTitleJson));
    }

  // 서버로 폼 데이터 전송
  fetch('/business/program/add', {
    method: 'POST',
    body: formData
   })
  .then(response => response.text())
  .then(data => {
    window.location.href = "http://localhost:8080/business/program";
  })
  .catch(error => {
    console.error(error);
  });
});
}else{
document.getElementById('myFormEdit').addEventListener('submit', function(event) {
   event.preventDefault();
   if (!check()) {return;}
   var additionalData;
    const resultHtml = document.querySelector('#content').outerHTML;
    var formData = new FormData(document.getElementById('myFormEdit'));
    if(count === 0){
         additionalData = { formHtml: ''};
    }else{
        additionalData = { formHtml: resultHtml};
        for (var key in additionalData) {
                    formData.append(key, additionalData[key]);
        }
        var getTitleJson = getTitle();
        formData.append("getTitleJson", JSON.stringify(getTitleJson));
    }

    var currentUrl = window.location.href;
    var urlParams = new URLSearchParams(currentUrl);
    var programNum = urlParams.get("programNum");
    console.log(currentUrl);
    console.log(urlParams);
    console.log(programNum);

  fetch('formEdit', {
    method: 'POST',
    body: formData
   })
  .then(response => response.text())
  .then(data => {
      var currentUrl = window.location.href;
      var urlParts = currentUrl.split("/");
      var programNumIndex = urlParts.indexOf("program");
      var programNum = urlParts[programNumIndex + 1];
      window.location.href = "http://localhost:8080/program/"+programNum;
  })
  .catch(error => {
    console.error(error);
  });
});
};

//유효성 검사 함수
function check(){
    const result = document.querySelectorAll('[id^=plus]');
    if(result.length === 0){ return true; }

    const inputComplete = document.querySelectorAll("[id^=complete]");
    if(inputComplete.length > 0){
        alert("편집을 완료해주세요.");
        return false;
    }
    return true;
}

function getTitle(){
    var titleJson = {};
    var titleText = document.querySelectorAll("[id^=titleSpan]");
    for(let i=0; i< titleText.length; i++){ titleJson[i] = titleText[i].innerText;}
    return titleJson;
}

function addContent(){
    const element = document.getElementById('content');
    const newContentDiv = document.createElement('div');
    newContentDiv.setAttribute("id", "plus"+count)
    newContentDiv.style.background = '#fbfbf3';
    newContentDiv.style.padding = "20px 0 20px 0";
    element.appendChild(newContentDiv);

    addButtons(newContentDiv);
    origContent(newContentDiv);
    element.appendChild(newContentDiv);

    //추가 답변 칸 없애기
        let replyChangeResult = count -1;
        const replyPreviousDiv = document.querySelector(".reDiv"+replyChangeResult);
        const inputElements = replyPreviousDiv.querySelector('input[type="text"], textarea');
        if(inputElements!==null){replyPreviousDiv.style.display = "none";}
}

function makeButton(className, idName, clickName){
    var newBtn = document.createElement('button');
    newBtn.setAttribute("type", "button");
    newBtn.setAttribute("class", className);
    newBtn.setAttribute("id", idName);
    newBtn.setAttribute("onclick", clickName);
    return newBtn;
}

function makeTextInput(className, placeholderName){
    var input = document.createElement('input');
    input.setAttribute("type", "text");
    input.setAttribute("class", className);
    input.setAttribute("placeholder", placeholderName);
    input.setAttribute('maxlength',10);
    return input;
}

function makeTextArea(className, placeholderName){
    var input = document.createElement('textarea');
    input.setAttribute("class", className);
    input.setAttribute("placeholder", placeholderName);
    input.setAttribute('disabled','');
    return input;
}

function makeRadio(name, selectValue){
    var label = document.createElement('label');
    label.setAttribute("id", "select"+selectValue+name);
    var inputRadio = document.createElement('input');
    inputRadio.setAttribute("type", "radio");
    inputRadio.setAttribute("name", name);
    inputRadio.setAttribute("value", selectValue);
    var inputText = document.createElement('input');
    inputText.setAttribute("type", "text");
    inputText.setAttribute("name", name+"_reason"+selectValue);

    var deleteButton = makeButton("select"+selectValue+name, "delete"+selectValue, "clickRadioMinus(this.className)");
    var newI = document.createElement('i');
    newI.setAttribute("class","bi bi-x-square");
    deleteButton.appendChild(newI);

    label.appendChild(inputRadio);
    label.appendChild(inputText);
    label.appendChild(deleteButton);
    return label;
}
function clickRadioPlus(divNum){
    var selectList = document.getElementsByName('reply'+divNum);
    var lastValue = selectList[selectList.length-1].value;
    const changeContent = document.getElementsByClassName('selectDiv'+divNum);
    var label = makeRadio("reply"+divNum, ++lastValue);
    changeContent[0].appendChild(label);
}

function clickRadioMinus(deleteName){
    const changeContent = document.getElementById(deleteName);
    changeContent.remove();
}

function makeCheckBox(name, selectValue){
    var label = document.createElement('label');
    label.setAttribute("id", "checkBox"+selectValue+name);
    var inputCheckBox = document.createElement('input');
    inputCheckBox.setAttribute("type", "checkbox");
    inputCheckBox.setAttribute("name", name);
    inputCheckBox.setAttribute("value", selectValue);
    var inputText = document.createElement('input');
    inputText.setAttribute("type", "text");
    inputText.setAttribute("name", name+"_reason"+selectValue);

    var deleteButton = makeButton("checkBox"+selectValue+name, "deleteCheckBox"+selectValue, "clickRadioMinus(this.className)");
    var newI = document.createElement('i');
    newI.setAttribute("class","bi bi-x-square");
    deleteButton.appendChild(newI);

    label.appendChild(inputCheckBox);
    label.appendChild(inputText);
    label.appendChild(deleteButton);
    return label;
}

function clickCheckPlus(divNum){
    var selectList = document.getElementsByName('replyCh'+divNum);
    var lastValue = selectList[selectList.length-1].value;
    const changeContent = document.getElementsByClassName('checkBoxDiv'+divNum);
    var label = makeCheckBox("replyCh"+divNum, ++lastValue);
    changeContent[0].appendChild(label);
}
//편집, 지우기 버튼 생성
function addButtons(newContentDiv){
        var newBtn = makeButton("btn btn-change"+count, count, "addEditSelect(this.id)");
        var newI = document.createElement('i');
        newI.setAttribute("class","bi bi-pencil-square");
        newBtn.disabled = true;
        newBtn.appendChild(newI);
        newContentDiv.appendChild(newBtn);

        var newDelBtn = makeButton("btn btn-delete"+count, count, "deleteSelect(this.id)");
        var newDelI = document.createElement('i');
        newDelI.setAttribute("class","bi bi-trash3");

        newDelBtn.appendChild(newDelI);
        newContentDiv.appendChild(newDelBtn);
}
//기본 제목, 설명란, 처음 생성시 짧은 주관식
function origContent(newContentDiv){
    const origContent = document.createElement('div');
    origContent.setAttribute("id", "origContent"+count);

    const newDiv = document.createElement('div');
    //!
    newDiv.setAttribute("class", "classTitle"+count)

    var span1 = document.createElement( 'span' );
    var titleText = document.createTextNode( '항목 제목' );
    var input1 = makeTextInput("title"+count, "제목 입력");

    span1.appendChild( titleText );
    newDiv.appendChild(span1);
    newDiv.appendChild(input1);
    origContent.appendChild(newDiv);

    const newDiv2 = document.createElement('div');
    //!
    newDiv2.setAttribute("class", "classDetail"+count);

    var span2 = document.createElement( 'span' );
    var titleText2 = document.createTextNode( '항목 설명' );
    var input2 = makeTextInput("content"+count, "");

    span2.appendChild( titleText2 );
    newDiv2.appendChild(span2);
    newDiv2.appendChild(input2);
    origContent.appendChild(newDiv2);

    const newDiv3 = document.createElement('div');
    newDiv3.setAttribute("class", "reDiv"+count);

    var inputOrigShort = makeTextInput("reply"+count, "답변");
    inputOrigShort.setAttribute('disabled','');
    newDiv3.appendChild(inputOrigShort);

    newContentDiv.appendChild(origContent);
    newContentDiv.appendChild(newDiv3);

    addSelect(count);
    count++;
}
function addSelect(e){
        const divName = document.getElementById('plus'+e);
        const newDiv = document.createElement('div');
        newDiv.setAttribute("id","select"+e);

        var span = document.createElement('span');
        var TypeText = document.createTextNode('유형');
        span.appendChild(TypeText);
        newDiv.appendChild(span);

        var select = document.createElement('select');
        select.setAttribute("name", "content"+e);
        select.setAttribute("onchange", "handleOnChange(this, "+e+")");

        var con = ['주관식 단답형','주관식 서술형', '단일 선택형', '복수 선택형'];

        for(let i=0; i<con.length; i++) {
            var opt = document.createElement( 'option' );
            opt.setAttribute("value", con[i]);
            opt.innerText = con[i];
            select.appendChild(opt);
        }
        newDiv.appendChild(select);
        divName.appendChild(newDiv);
        var newChBtn = makeButton("btn btn-success", "complete"+e, "complete("+e+")");
        newChBtn.innerText = "완료";
        divName.appendChild(newChBtn);
}

function addEditSelect(e){
    titleInputLayoutShow(e);
    var spanTitle = document.getElementById('titleSpan'+e);
    if(spanTitle !== null){ programEditVer(e, spanTitle);}
    const divName = document.getElementById('plus'+e);
    const selectShow = document.getElementById('select'+e);
    selectShow.style.display = "";

//추가 답변 칸 없애기
    const replyPreviousDiv = document.querySelector(".reDiv"+e);
    const inputElements = replyPreviousDiv.querySelector('input[type="text"], textarea');
    if(inputElements!==null){replyPreviousDiv.style.display = "none";}

    var newChBtn = makeButton("btn btn-success", "complete"+e, "complete("+e+")");
    newChBtn.innerText = "완료";
    divName.appendChild(newChBtn);

    var radioContent = reToInput(e, 'radio');
    if(Array.isArray(radioContent) && radioContent.length !== 0){
        const changeContent = document.getElementsByClassName("selectDiv"+e)[0];
        changeContent.innerText = '';
        for(let i=0; i<radioContent.length; i++){
              var label = makeRadio("reply"+e, i);
              changeContent.appendChild(label);
        }
        radioContent.forEach((con, index) => {
              document.getElementsByName("reply"+e+"_reason"+index)[0].value = con;
        });
        const changeSelectDiv = document.getElementsByClassName("changeSelectDiv"+e)[0];
        const selectBtnDiv = document.createElement('div');
        selectBtnDiv.setAttribute("class", "selectBtnDiv"+e);
        var radioPlusBtn = makeButton("radioPlus"+e, "radioP"+e, "clickRadioPlus("+e+")");
        radioPlusBtn.innerText = "옵션 추가";
        selectBtnDiv.appendChild(radioPlusBtn);
        changeSelectDiv.appendChild(selectBtnDiv);

        //css
        const changeCssSelectDiv = document.getElementsByClassName("selectDiv"+e)[0];
        changeCssSelectDiv.style.marginLeft = '6.3rem';
    }
    var checkContent = reToInput(e, 'check');
    if(Array.isArray(checkContent) && checkContent.length !== 0){
        const changeContent = document.getElementsByClassName("checkBoxDiv"+e)[0];
        changeContent.innerText = '';
        for(let i=0; i<checkContent.length; i++){
              var label = makeCheckBox("replyCh"+e, i);
              changeContent.appendChild(label);
        }
        checkContent.forEach((con, index) => {
              document.getElementsByName("replyCh"+e+"_reason"+index)[0].value = con;
        });
        const changeSelectDiv = document.getElementsByClassName("changeCheckDiv"+e)[0];
        const checkBoxBtnDiv = document.createElement('div');
        checkBoxBtnDiv.setAttribute("class", "checkBoxBtnDiv"+e);
        var checkPlusBtn = makeButton("checkPlus"+e, "checkP"+e, "clickCheckPlus("+e+")");
        checkPlusBtn.innerText = "옵션 추가";
        checkBoxBtnDiv.appendChild(checkPlusBtn);
        changeSelectDiv.appendChild(checkBoxBtnDiv);

        //CSS
        const changeCssCheckDiv = document.getElementsByClassName("checkBoxDiv"+e)[0];
        if(changeCssCheckDiv){changeCssCheckDiv.style.marginLeft = '6.3rem';}
    }
    //const target = document.getElementById(e);
    const target = document.getElementsByClassName("btn btn-change"+e)[0];
    const changeColor = document.getElementById('plus'+e);
    //버튼 눌린 상태로 변경
    if(target.disabled === false){
        target.disabled = true;
        changeColor.style.background = '#fbfbf3';
    }
}
//셀렉트 선택시
function handleOnChange(e, divNum) {
  const value = e.value;
  const divName = document.getElementById('plus'+divNum);
  const changeContent = document.getElementsByClassName('reDiv'+divNum);
  changeContent[0].innerText = '';

  switch(e.value){
    case '주관식 단답형':
        var changeInputText = makeTextInput("reply"+divNum, "단답형 답변");
        changeInputText.setAttribute("disabled", "");
        changeContent[0].appendChild(changeInputText);
        break;
    case '주관식 서술형':
        var changeInputText = makeTextArea("reply"+divNum, "서술형 답변");
        changeContent[0].appendChild(changeInputText);
        break;
    case '단일 선택형':
        const store = document.querySelector(".changeSelectDiv"+divNum);
        //다시 보이게 reDiv
        const replyPreviousDiv = document.querySelector(".reDiv"+divNum);
        replyPreviousDiv.style.display = "block";

        if(store === null){
           const wholeSelectDiv = document.createElement('div');
           wholeSelectDiv.setAttribute("class", "wholeSelectDiv"+divNum);
           const selectDiv = document.createElement('div');
           selectDiv.setAttribute("class", "selectDiv"+divNum);
           var label = makeRadio("reply"+divNum, 0);
           selectDiv.appendChild(label);
           wholeSelectDiv.appendChild(selectDiv);
           const selectBtnDiv = document.createElement('div');
           selectBtnDiv.setAttribute("class", "selectBtnDiv"+divNum);
           var radioPlusBtn = makeButton("radioPlus"+divNum, "radioP"+divNum, "clickRadioPlus("+divNum+")");
           radioPlusBtn.innerText = "옵션 추가";
           selectBtnDiv.appendChild(radioPlusBtn);

           wholeSelectDiv.appendChild(selectBtnDiv);
           changeContent[0].appendChild(wholeSelectDiv);
        }
        //css
        const changeCssSelectDiv = document.getElementsByClassName("selectDiv"+divNum)[0];
        changeCssSelectDiv.style.marginLeft = '6.3rem';

        break;
    case '복수 선택형':
        const storeCheck = document.querySelector(".changeCheckDiv"+divNum);
           //다시 보이게 reDiv
            const replyPreviousDiv2 = document.querySelector(".reDiv"+divNum);
            replyPreviousDiv2.style.display = "block";

        if(storeCheck === null){
            const wholeCheckDiv = document.createElement('div');
            wholeCheckDiv.setAttribute("class", "wholeCheckDiv"+divNum);

            const checkBoxDiv = document.createElement('div');
            checkBoxDiv.setAttribute("class", "checkBoxDiv"+divNum);
            var checkBoxLabel = makeCheckBox("replyCh"+divNum, 0);
            checkBoxDiv.appendChild(checkBoxLabel);
            wholeCheckDiv.appendChild(checkBoxDiv);

            const checkBoxBtnDiv = document.createElement('div');
            checkBoxBtnDiv.setAttribute("class", "checkBoxBtnDiv"+divNum);
            var checkPlusBtn = makeButton("checkPlus"+divNum, "checkP"+divNum, "clickCheckPlus("+divNum+")");
            checkPlusBtn.innerText = "옵션 추가";
            checkBoxBtnDiv.appendChild(checkPlusBtn);

            wholeCheckDiv.appendChild(checkBoxBtnDiv);
            changeContent[0].appendChild(wholeCheckDiv);
        }
            //CSS
            const changeCssCheckDiv = document.getElementsByClassName("checkBoxDiv"+divNum)[0];
            if(changeCssCheckDiv){changeCssCheckDiv.style.marginLeft = '6.3rem';}
        break;
  }
}

function deleteSelect(e){
    const divName = document.getElementById('plus'+e);
    divName.remove();
}
function complete(e){
    const selectName = document.getElementById('select'+e);
    selectName.style.display = "none";
    const buttonName = document.getElementById('complete'+e);
    buttonName.remove();
    //const target = document.getElementById(e);
    const target = document.getElementsByClassName("btn btn-change"+e)[0];
    const changeColor = document.getElementById('plus'+e);
    if(target.disabled === true){
       target.disabled = false;
       changeColor.style.background = 'white';
    }
    titleResult(e);

    const radioType = document.getElementsByClassName('wholeSelectDiv'+e);
    const radioAfterType = document.getElementsByClassName('changeSelectDiv'+e);
    if(radioType.length !== 0 || radioAfterType.length !== 0)   RadioResult(e);

    const contentType = document.getElementsByClassName('wholeCheckDiv'+e);
    const checkAfterType = document.getElementsByClassName('changeCheckDiv'+e);
    if(contentType.length !== 0 || checkAfterType.length !== 0){
        checkResult(e);
    }

    //추가 답변 칸 보이게
        const replyPreviousDiv = document.querySelector(".reDiv"+e);
        const inputElements = replyPreviousDiv.querySelector('input[type="text"], textarea');
        if(inputElements!==null){replyPreviousDiv.style.display = "block";}

    //css
        const changeCssSelectDiv = document.getElementsByClassName("selectDiv"+e)[0];
        if(changeCssSelectDiv){changeCssSelectDiv.style.marginLeft = '0';}
        const changeCssCheckDiv = document.getElementsByClassName("checkBoxDiv"+e)[0];
        if(changeCssCheckDiv){changeCssCheckDiv.style.marginLeft = '0';}
}

function titleInputLayoutShow(num){
    var layout = document.getElementById('titleConReplyDiv'+num);
    var removeDiv1 = document.getElementsByClassName("classTitle"+num);
    var removeDiv2 = document.getElementsByClassName("classDetail"+num);
    var length = removeDiv1.length;
    if(layout != null){
        layout.style.display='none';
        for(let i=0; i<length; i++){
            removeDiv1[i].style.display='block';
            removeDiv2[i].style.display='block';
        }
    }
}

function titleResult(num){
//!
    const removeDiv = document.getElementsByClassName("classTitle"+num);
    const removeDiv2 = document.getElementsByClassName("classDetail"+num);
    var length = removeDiv.length;
    var title = document.getElementsByClassName("title"+num)[0].value;
    var content = document.getElementsByClassName("content"+num)[0].value;
    for(let i=0; i<length; i++){
        removeDiv[i].style.display='none';
        removeDiv2[i].style.display='none';
    }

    if(title==='')
       title = '제목을 입력해주세요.';
    if(document.getElementById('titleConReplyDiv'+num) === null){
        const origContent = document.getElementById('origContent'+num);
        const replyDiv = document.createElement('div');
        replyDiv.setAttribute("id", "titleConReplyDiv"+num);

        var spanTitle = document.createElement('span');
        spanTitle.setAttribute("id", "titleSpan"+num);
        var titleText = document.createTextNode(title);
        spanTitle.style.fontSize = "20px";
        var br = document.createElement('br');

        var spanContent = document.createElement('span');
        spanContent.setAttribute("id", "ContentSpan"+num);
        var contentText = document.createTextNode(content);

        spanTitle.appendChild(titleText);
        spanContent.appendChild(contentText);

        replyDiv.appendChild(spanTitle);
        replyDiv.appendChild(br);
        replyDiv.appendChild(spanContent);
        origContent.appendChild(replyDiv);
    }else{
        var result = document.getElementById('titleConReplyDiv'+num);
        result.style.display='block';
        var ChangeTitleSpan = document.getElementById("titleSpan"+num);
        ChangeTitleSpan.innerText = title;
        var ChangeContentSpan = document.getElementById("ContentSpan"+num);
        ChangeContentSpan.innerText = content;
    }
}

function RadioResult(num){
    const labelContents = document.querySelector('.selectDiv'+num);
    saveResult(labelContents);
    var labelContent = document.getElementsByClassName("wholeSelectDiv"+num)[0];
    if(labelContent !== undefined) { labelContent.className = 'changeSelectDiv'+num; }
    const removeBtn = document.getElementsByClassName("selectBtnDiv"+num)[0];
    removeBtn.remove();
}

function saveResult(labelContents){
    const labels = labelContents.querySelectorAll("label");
    labels.forEach((con, index) => {
           if(con !== null){
                const inputRadio = labels[index].querySelectorAll("input");
                inputRadio[0].value = inputRadio[1].value;
                inputRadio[1].remove();
                const inputButton = labels[index].querySelector("button");
                inputButton.remove();
                const radioText = document.createTextNode(inputRadio[0].value);
                labels[index].appendChild(radioText);
           }
    });
}

function checkResult(num){
    const labelContents = document.querySelector('.checkBoxDiv'+num);
    saveResult(labelContents);
    var labelContent = document.getElementsByClassName("wholeCheckDiv"+num)[0];
    if(labelContent !== undefined) { labelContent.className = 'changeCheckDiv'+num; }
    const removeBtn = document.getElementsByClassName("checkBoxBtnDiv"+num)[0];
    removeBtn.remove();
}

function reToInput(num, type){
    var re = [];
    var store;
    if(type === 'radio'){store = document.querySelector(".changeSelectDiv"+num);}
    else if(type === 'check'){store = document.querySelector(".changeCheckDiv"+num);}
    if(store !== null){
        const radioText = store.querySelectorAll("label");
        radioText.forEach((con, index) => {
            re[index] = con.textContent;
        });
    }
    return re;
}

function programEditVer(num, spanTitle){
    var inputTitle = document.getElementsByClassName('title'+num)[0];
    var inputContent = document.getElementsByClassName('content'+num)[0];
    if(inputTitle.value === ''){inputTitle.value = spanTitle.innerText;}
    if(inputContent.value === ''){
        var spanContent = document.getElementById('ContentSpan'+num).innerText;
        inputContent.value = spanContent;
    }
}