let count = 0;

function addContent(){
    const element = document.getElementById('content');
    const newContentDiv = document.createElement('div');
    newContentDiv.setAttribute("id", "plus"+count)
    newContentDiv.style.background = '#fffce2';
    newContentDiv.style.padding = "20px 0 20px 0";

    addButtons(newContentDiv);
    origContent(newContentDiv);
    element.appendChild(newContentDiv);
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
//<label><input type="radio" name="fruit" value="apple"> Apple</label>
    var label = document.createElement('label');
    label.setAttribute("id", "select"+selectValue+name);
    var inputRadio = document.createElement('input');
    inputRadio.setAttribute("type", "radio");
    inputRadio.setAttribute("name", name);
    inputRadio.setAttribute("value", selectValue);
    var inputText = document.createElement('input');
    inputText.setAttribute("type", "text");
    inputText.setAttribute("name", name+"_reason");

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
//<input type="checkbox" name="colors" id="chk4" value="black"><label for="radio4">블랙</label>
    var label = document.createElement('label');
    label.setAttribute("id", "checkBox"+selectValue+name);
    var inputCheckBox = document.createElement('input');
    inputCheckBox.setAttribute("type", "checkbox");
    inputCheckBox.setAttribute("name", name);
    inputCheckBox.setAttribute("value", selectValue);
    var inputText = document.createElement('input');
    inputText.setAttribute("type", "text");
    inputText.setAttribute("name", name+"_reason");

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
        var newBtn = makeButton("btn btn-change", count, "addEditSelect(this.id)");
        var newI = document.createElement('i');
        newI.setAttribute("class","bi bi-pencil-square");

        newBtn.appendChild(newI);
        newContentDiv.appendChild(newBtn);

        var newDelBtn = makeButton("btn btn-delete", count, "deleteSelect(this.id)");
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
    newDiv.setAttribute("class", count)

    var span1 = document.createElement( 'span' );
    var titleText = document.createTextNode( '항목 제목' );
    var input1 = makeTextInput("title"+count, "제목 입력");

    span1.appendChild( titleText );
    newDiv.appendChild(span1);
    newDiv.appendChild(input1);
    origContent.appendChild(newDiv);

    const newDiv2 = document.createElement('div');
    newDiv2.setAttribute("class", count)

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

    count++;
    newContentDiv.appendChild(origContent);
    newContentDiv.appendChild(newDiv3);
}
//편집 셀렉트 생성
function addEditSelect(e){
    titleInputLayoutShow(e);
    const divName = document.getElementById('plus'+e);
    const newDiv = document.createElement('div');
    newDiv.setAttribute("id","select"+e);

    var span = document.createElement('span');
    var TypeText = document.createTextNode('유형');
    span.appendChild(TypeText);
    newDiv.appendChild(span);

    var select = document.createElement( 'select' );
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

    const target = document.getElementById(e);
    if(target.disabled === false){
        target.disabled = true;
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
        changeContent[0].appendChild(changeInputText);
        break;
    case '주관식 서술형':
        var changeInputText = makeTextArea("reply"+divNum, "서술형 답변");
        changeContent[0].appendChild(changeInputText);
        break;
    case '단일 선택형':
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
        break;
    case '복수 선택형':
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
        break;
  }
}

function deleteSelect(e){
    const divName = document.getElementById('plus'+e);
    divName.remove();
}
function complete(e){
    const selectName = document.getElementById('select'+e);
    selectName.remove();
    const buttonName = document.getElementById('complete'+e);
    buttonName.remove();
    const target = document.getElementById(e);
    if(target.disabled === true){
       target.disabled = false;
    }


    titleResult(e);
}

function titleInputLayoutShow(num){
    var layout = document.getElementById('titleConReplyDiv'+num);
    var removeDiv = document.getElementsByClassName(num);
    var length = removeDiv.length;
    if(layout != null){
        layout.style.display='none';
        for(let i=0; i<length; i++)
            removeDiv[i].style.display='block';
    }
}

function titleResult(num){
    const removeDiv = document.getElementsByClassName(num);
    var length = removeDiv.length;
    var title = document.getElementsByClassName("title"+num)[0].value;
    var content = document.getElementsByClassName("content"+num)[0].value;
    for(let i=0; i<length; i++)
        removeDiv[i].style.display='none';

    if(document.getElementById('titleConReplyDiv'+num) === null){
        if(title==='')
            title = '제목을 입력해주세요.';
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
        alert('이미 있는 경우');
        var result = document.getElementById('titleConReplyDiv'+num);
        result.style.display='block';
        var ChangeTitleSpan = document.getElementById("titleSpan"+num);
        ChangeTitleSpan.innerText = title;
    }
}