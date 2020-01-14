let result = 0;
let scrinValue = "";

 /*window.addEventListener ("keypress", function (e) {
            if (e.keyCode !== 13) return;
            calculate();
});*/

function numberClick(numb){
    switch(numb) {
        case 0:
            scrinValue = scrinValue + 0;
        break;
        case 1:
            scrinValue = scrinValue + 1;
        break;
        case 2:
            scrinValue = scrinValue + 2;
        break;
        case 3:
            scrinValue = scrinValue + 3;
        break;
        case 4:
            scrinValue = scrinValue + 4;
        break;
        case 5:
            scrinValue = scrinValue + 5;
        break;
        case 6:
            scrinValue = scrinValue + 6;
        break;
        case 7:
            scrinValue = scrinValue + 7;
        break;
        case 8:
            scrinValue = scrinValue + 8;
        break;
        case 9:
            scrinValue = scrinValue + 9;
        break;
    }
    document.getElementById("display").value = scrinValue;
}

function operationClick(op){
    let opValue = "";
    switch(op){
        case '+':
            opValue = " + ";
        break;
        case '-':
            opValue = " - ";
        break;
        case '*':
            opValue = " * ";
        break;
        case '/':
            opValue = " / ";
        break;
    }  
    let value = scrinValue.split(" ");
    if(value[value.length - 1] == ""){
        value[value.length - 2] = opValue.trim();
        scrinValue = value.join(" ");
    } 
    else{
        scrinValue = scrinValue + opValue
    }
    document.getElementById("display").value = scrinValue;
}

function clearClick(){
    scrinValue = "";
    document.getElementById("display").value = scrinValue;
}

function backspaceClick(){
    if(scrinValue.endsWith(" ")){
        scrinValue = scrinValue.slice(-scrinValue.length, -3);
    }  
    else{
        scrinValue = scrinValue.slice(-scrinValue.length, -1);
    }
    document.getElementById("display").value = scrinValue;

}

function plusMinusClick(){
    let value = scrinValue.split(" ");
    if(value[value.length - 1] != ""){
        value[value.length - 1] = (Number(value[value.length - 1]) * -1).toFixed();
        scrinValue = value.join(" ");
        document.getElementById("display").value = scrinValue;
    } 
}

function calculate(){
    if(scrinValue.endsWith(" ")){
        scrinValue = scrinValue.slice(-scrinValue.length, -3);
    }  
    result = eval(scrinValue);
   document.getElementById("display").value = result;
   scrinValue = result.toString();
}


