function clock(){
let date = new Date();
var options = {
    era: 'long',
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    weekday: 'long',
    timezone: 'UTC',
    hour: 'numeric',
    minute: 'numeric',
    second: 'numeric',
    caseFirst: 'upper'
};

document.getElementById("lf").innerHTML ="© Башаримов Юрий Сергеевич ИТП-31 ЮКО Inc. Все права защищены <p>&nbsp; &nbsp;" + date.toLocaleString("RU", options) + "</p>";
setTimeout("clock()", 1000);
}
	   