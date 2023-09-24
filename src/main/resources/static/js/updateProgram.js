function showPlaceInput(type) {
    valueClear();
    /*document.getElementById("peopleChBox").checked = false;
    peopleNumValue();
    document.getElementById("enterPeopleNum").style.display = "inline";*/

    if(type) {
        document.getElementById("off").style.display = "none";
        document.getElementById("on").style.display = "block";
       // document.getElementById("onPlace").disabled = false;
      //  document.getElementById("offPlace").disabled = true;

       // document.getElementById("offSeatingChart").style.display = "none";
       // showCreatSeatingChart(0);
    }
    else {
        document.getElementById("off").style.display = "block";
        document.getElementById("on").style.display = "none";
      //  document.getElementById("onPlace").disabled = true;
      //  document.getElementById("offPlace").disabled = false;

      //  document.getElementById("nsc").checked = true;
      //  document.getElementById("offSeatingChart").style.display = "block"
     //   document.getElementById("csc").style.display = "none";
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

        if(document.getElementById("deleteChart") != null){
           document.getElementById("deleteChart").style.display = "none";
        }
        if(document.getElementById("isSeatColSelect") != null){
            document.getElementById("isSeatColSelect").style.display = "none";
        }else{
            document.getElementById("isNotSeatColSelect").style.display = "none";
        }
    }
    else {
        document.getElementById("peopleNum").style.display = "inline";
        document.getElementById("peopleNum").disabled = false;
        if(document.getElementById("deleteChart") != null){
                   document.getElementById("deleteChart").style.display = "inline";
                }
        if(document.getElementById("isSeatColSelect") != null){
             document.getElementById("isSeatColSelect").style.display = "inline";
        }else{
             document.getElementById("isNotSeatColSelect").style.display = "inline";
        }
    }
}

function deleteSeatChart(){
     document.getElementById("seatingChart").value = '';
     document.getElementById("peopleNum").value = 0;
     document.getElementById("peopleNum").readOnly = false;
     document.getElementById("peopleNum").style.backgroundColor = "white";
     document.getElementById("peopleNum").style.color ="black";
     document.getElementById("seatCol").value = '';
     console.log("del");
}