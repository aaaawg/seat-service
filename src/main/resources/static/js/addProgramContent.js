let count = 0;
function addContent(){
    const element = document.getElementById('content');
    // new div 생성
    const newContentDiv = document.createElement('div');
    newContentDiv.setAttribute("id", "plus"+count)
    newContentDiv.style.background = 'yellow';
    newContentDiv.style.padding = "20px 0 20px 0";

    addButtons(newContentDiv);
    origContent(newContentDiv);
    element.appendChild(newContentDiv);
}

//편집, 지우기 버튼 생성
function addButtons(newContentDiv){
        //edit button
        var newBtn = document.createElement( 'button' );
        newBtn.setAttribute("type", "button");
        newBtn.setAttribute("class", "btn btn-change");
        newBtn.setAttribute("id", count);
        newBtn.setAttribute("onclick", "addEditSelect(this.id)");

        var newI = document.createElement('i');
        newI.setAttribute("class","bi bi-pencil-square");

        newBtn.appendChild(newI);
        newContentDiv.appendChild(newBtn);

        //delete button
        var newDelBtn = document.createElement( 'button' );
        newDelBtn.setAttribute("type", "button");
        newDelBtn.setAttribute("class", "btn btn-delete");
        newDelBtn.setAttribute("id", count);
        newDelBtn.setAttribute("onclick", "deleteSelect(this.id)");

        var newDelI = document.createElement('i');
        newDelI.setAttribute("class","bi bi-trash3");

        newDelBtn.appendChild(newDelI);
        newContentDiv.appendChild(newDelBtn);
}

//기본 주관식 단답형 생성
function origContent(newContentDiv){
    // new div 생성
    const newDiv = document.createElement('div');
    newDiv.setAttribute("class", count)

    var span1 = document.createElement( 'span' );
    var titleText = document.createTextNode( '항목 제목' );
    var input1 = document.createElement( 'input' );
    // set attribute (input)
	input1.setAttribute("type", "text");
	input1.setAttribute("class", "sample"+count);
	input1.setAttribute("placeholder", "제목 입력");

    span1.appendChild( titleText );
    newDiv.appendChild(span1);
    newDiv.appendChild(input1);

    count++;
    // new div 생성
    const newDiv2 = document.createElement('div');
    newDiv2.setAttribute("class", count)

    var span2 = document.createElement( 'span' );
    var titleText2 = document.createTextNode( '항목 설명' );
    var input2 = document.createElement( 'input' );
    input2.setAttribute("type", "text");
    input2.setAttribute("class", "sample"+count);

    span2.appendChild( titleText2 );
    newDiv2.appendChild(span2);
    newDiv2.appendChild(input2);

    count++;

    newContentDiv.appendChild(newDiv);
    newContentDiv.appendChild(newDiv2);
}
//편집 셀렉트 생성
function addEditSelect(e){
    const divName = document.getElementById('plus'+e);
    const newDiv = document.createElement('div');
    newDiv.setAttribute("id","select"+e);

    var select = document.createElement( 'select' );
    select.setAttribute("name", "content"+e);
    select.setAttribute("onchange", "handleOnChange(this, "+e+")");

    var con = ['주관식 단답형','주관식 서술형', '단일 선택형', '복수 선택형',
     '목록 선택형', '선호도형', '날짜/시간', '연락처', '주소', '숫자', '파일'];

    for(let i=0; i<con.length; i++) {
        var opt = document.createElement( 'option' );
        opt.setAttribute("value", con[i]);
        opt.innerText = con[i];
        select.appendChild(opt);
    }
    newDiv.appendChild(select);
    divName.appendChild(newDiv);

    var newChBtn = document.createElement( 'button' );
    newChBtn.setAttribute("type", "button");
    newChBtn.setAttribute("class", "btn btn-success");
    newChBtn.setAttribute("id", "complete"+e);
    newChBtn.setAttribute("onclick", "complete("+e+")");
    newChBtn.innerText = "완료";
    divName.appendChild(newChBtn);
}
//셀렉트 선택시
function handleOnChange(e, divNum) {
  const value = e.value;

  const divName = document.getElementById('plus'+divNum);
   alert(value+ divNum);
  //divName.textContent = "";

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