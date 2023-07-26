let count = 0;

const newType = {
short: "text",
long: "text",
selectOne: "radio",
selectMore: "checkbox"
};
const getType = (selectType) => {return newType[selectType];};
console.log(getType("short"));

function addContent(){
    const element = document.getElementById('content');
    // new div 생성
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

//편집, 지우기 버튼 생성
function addButtons(newContentDiv){
        //edit button
        var newBtn = makeButton("btn btn-change", count, "addEditSelect(this.id)");

        var newI = document.createElement('i');
        newI.setAttribute("class","bi bi-pencil-square");

        newBtn.appendChild(newI);
        newContentDiv.appendChild(newBtn);

        //delete button
        var newDelBtn = makeButton("btn btn-delete", count, "deleteSelect(this.id)");

        var newDelI = document.createElement('i');
        newDelI.setAttribute("class","bi bi-trash3");

        newDelBtn.appendChild(newDelI);
        newContentDiv.appendChild(newDelBtn);
}

//기본 제목, 설명란, 처음 생성시 짧은 주관식
function origContent(newContentDiv){
    const newDiv = document.createElement('div');
    newDiv.setAttribute("class", count)

    var span1 = document.createElement( 'span' );
    var titleText = document.createTextNode( '항목 제목' );
    var input1 = makeTextInput("title"+count, "제목 입력");

    span1.appendChild( titleText );
    newDiv.appendChild(span1);
    newDiv.appendChild(input1);

    const newDiv2 = document.createElement('div');
    newDiv2.setAttribute("class", count)

    var span2 = document.createElement( 'span' );
    var titleText2 = document.createTextNode( '항목 설명' );
    var input2 = makeTextInput("content"+count, "");

    span2.appendChild( titleText2 );
    newDiv2.appendChild(span2);
    newDiv2.appendChild(input2);

    const newDiv3 = document.createElement('div');
    newDiv3.setAttribute("class", "reDiv"+count);

    var inputOrigShort = makeTextInput("reply"+count, "답변");
    inputOrigShort.setAttribute('disabled','');
    newDiv3.appendChild(inputOrigShort);

    count++;
    newContentDiv.appendChild(newDiv);
    newContentDiv.appendChild(newDiv2);
    newContentDiv.appendChild(newDiv3);
}
//편집 셀렉트 생성
function addEditSelect(e){
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
    default :
        //
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
}