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
function selectPage(pageNumber){
    switch(pageNumber){
        case 1:
            document.getElementById("title").innerText ="Первый подпункт";
            break;
        case 11:
            document.getElementById("title").innerText ="Первый подпункт первого подпункта";
            break;
        case 12:
            document.getElementById("title").innerText ="Второй подпункт первого подпункта";
            break;
        case 13:
            document.getElementById("title").innerText ="Третий подпункт первого подпункта";
            break;
        case 2:
            document.getElementById("title").innerText ="Второй подпункт";
            break;
        case 21:
                document.getElementById("title").innerText ="Первый подпункт второго подпункта";
                break;
        case 22:
                document.getElementById("title").innerText ="Второй подпункт второго подпункта";
                break;
        case 23:
                document.getElementById("title").innerText ="Третий подпункт второго подпункта";
                break;
        case 3:
            document.getElementById("title").innerText ="Третий подпункт";
            break;
    }
}
function loadContent(ref) {
    fetch(ref)
        .then(response => {
           return response.text() 
        })
        .then(data => {
           document.getElementById("body").innerHTML = data;
        });
}




	   