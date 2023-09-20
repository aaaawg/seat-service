function showPlaceInput(type) {
    /*valueClear();
    document.getElementById("peopleChBox").checked = false;
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
    document.getElementById("seatLength").innerText = null;
}