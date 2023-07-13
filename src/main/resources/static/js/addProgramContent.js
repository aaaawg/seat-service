let count = 0;
function addContent(){
    const element = document.getElementById('content');

    // new div 생성
    const newContentDiv = document.createElement('div');
    newContentDiv.setAttribute("class", "plus"+count)
    newContentDiv.style.background = 'yellow';
    newContentDiv.style.padding = "20px 0 20px 0";

    //button
    var newBtn = document.createElement( 'button' );

    newBtn.setAttribute("type", "button");
    newBtn.setAttribute("class", "btn btn-change");

    var newI = document.createElement('i');
    newI.setAttribute("class","bi bi-pencil-square");

    newBtn.appendChild(newI);

    newContentDiv.appendChild(newBtn);

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
        // set attribute (input)
    	input2.setAttribute("type", "text");
    	input2.setAttribute("class", "sample"+count);

        span2.appendChild( titleText2 );
        newDiv2.appendChild(span2);
        newDiv2.appendChild(input2);

       count++;

       newContentDiv.appendChild(newDiv);
       newContentDiv.appendChild(newDiv2);

       element.appendChild(newContentDiv);
}