function clock(){
    let date = new Date();
    let options = {
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

window.onload = clock(), getCourses();
window.onbeforeunload = logout();
function openModal() {
    document.getElementById("openModal").classList.remove("hidden");
    document.getElementsByTagName("body").item(0).style.overflow = "hidden";
    clearForms();
    openLoginForm();
}

function closeModal() {
    document.getElementById("openModal").classList.add("hidden");
    document.getElementsByTagName("body").item(0).style.overflow = "visible";
    clearForms();
}

function openRegistrationForm() {
    document.getElementById("loginWindow").classList.add("hidden");
    document.getElementById("registrWindow").classList.remove("hidden");
}

function openLoginForm() {
    document.getElementById("registrWindow").classList.add("hidden");
    document.getElementById("loginWindow").classList.remove("hidden");
}

function clearForms(){

    document.forms.loginForm.elements.login.value = "";
    document.forms.loginForm.elements.password.value = "";
    document.forms.registrForm.elements.login.value = "";
    document.forms.registrForm.elements.password1.value = "";
    document.forms.registrForm.elements.password2.value = "";

}

let user = null;

init();

function init() {
    document.forms.loginForm.onsubmit = (e) => {
        e.preventDefault();
        if (checkloginForm()) {
            login();
        }
    };

    document.forms.registrForm.onsubmit = (e) => {
        e.preventDefault();
        if (checkSignupForm()) {
            signup();
        }
    };
}

function login() {

    document.getElementById("loginFormMessage").innerHTML = "";

    let form = document.forms.loginForm;
    let login =  form.elements.login.value;
    let password = form.elements.password.value;

    let httpRequest = new XMLHttpRequest();
    let siteUrl = new URL(document.URL);
    let url = new URL("/api/", siteUrl.origin);
    url.searchParams.set('action', "login");
    url.searchParams.set('login', login);
    url.searchParams.set('password', password);
    httpRequest.open("POST", url);
    httpRequest.responseType = "json";
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    let json = JSON.stringify({
        action: "login",
        login: login,
        password: password
    });

    httpRequest.send(json);

    httpRequest.onload = () => {
        if (httpRequest.status === 200) {
            if( httpRequest.response.error != null){
                let loginFormMessage = document.getElementById("loginFormMessage");
                loginFormMessage.innerText = httpRequest.response.error;
            }else{
                user = {
                    id : httpRequest.response.id,
                    login : httpRequest.response.login,
                    name : httpRequest.response.name,
                    role : httpRequest.response.role
                };
                document.getElementById('login').classList.add("hidden");
                document.getElementById('logout').classList.remove("hidden");
                document.getElementById('user_name').innerText = user.name;

                closeModal();

            }
        }
    }
}

function checkloginForm() {
    let form = document.forms.loginForm;
    let loginFormMessage = document.getElementById("loginFormMessage");


    if (form.login.value.includes(" ")) {
        loginFormMessage.innerText = "Пожалуйста, введите Логин без пробелов";
        return false;
    }

    if (form.password.value.includes(" ")) {
        loginFormMessage.innerText = "Пожалуйста, введите Пароль без пробелов";
        return false;
    }
    return true;
}

function logout() {

    let httpRequest = new XMLHttpRequest();
    let siteUrl = new URL(document.URL);
    let url = new URL("/api/", siteUrl.origin);
    url.searchParams.set('action', "logout");
    url.searchParams.set('login', login);
    httpRequest.open("POST", url);
    httpRequest.responseType = "json";
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    let json = JSON.stringify({
        action: "logout",
        login: login,
    });

    httpRequest.send(json);

    httpRequest.onload = () => {
        if (httpRequest.status === 200) {

            user = null;
            document.getElementById('login').classList.remove("hidden");
            document.getElementById('logout').classList.add("hidden");
            document.getElementById('user_name').innerText = "Гость";
        }
    }

}

function signup() {

    document.getElementById("registrFormMessage").innerHTML = "";

    let form = document.forms.registrForm;
    let username =  form.elements.username.value;
    let login =  form.elements.login.value;
    let role =  form.elements.role.value;
    let password = form.elements.password.value;

    let httpRequest = new XMLHttpRequest();
    let siteUrl = new URL(document.URL);
    let url = new URL("/api/", siteUrl.origin);
    url.searchParams.set('action', "signup");
    url.searchParams.set('login', login);
    url.searchParams.set('password', password);
    url.searchParams.set('role', role);
    url.searchParams.set('username', username);
    httpRequest.open("POST", url);
    httpRequest.responseType = "json";
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    let json = JSON.stringify({
        action: "signup",
        role: role,
        login: login,
        username: username,
        password: password
    });

    httpRequest.send(json);

    httpRequest.onload = () => {
        if (httpRequest.status === 200) {
            if( httpRequest.response.error != null){
                let registrFormMessage = document.getElementById("registrFormMessage");
                registrFormMessage.innerText = httpRequest.response.error;
            }else{
                user = {
                    id : httpRequest.response.id,
                    login : httpRequest.response.login,
                    name : httpRequest.response.name,
                    role : httpRequest.response.role
                };
                document.getElementById('login').classList.add("hidden");
                document.getElementById('logout').classList.remove("hidden");
                document.getElementById('user_name').innerText = user.name;

                closeModal();

            }
        }
    }

}

function checkSignupForm() {
    let form = document.forms.registrForm;
    let registrFormMessage = document.getElementById("registrFormMessage");

    if (form.username.value.trim() === ""){
        registrFormMessage.innerText = "Пожалуйста, введите ФИО без пробела в начале(конце)";
        return false;
    }

    if (!/яА-ЯёЁ]{2,100}$/.test(form.username.value)) {
        registrFormMessage.innerText = "Поле Фамилия должно содержать только русские буквы";
        return false;
    }


    if (form.login.value.includes(" ")) {
        registrFormMessage.innerText = "Пожалуйста, введите Логин без пробелов";
        return false;
    }

    if (form.password1.value.includes(" ")) {
        registrFormMessage.innerText = "Пожалуйста, введите Пароль без пробелов";
        return false;
    }

    if (form.password1.value !== form.password2.value) {
        registrFormMessage.innerText = "Значения паролей не совпадают";
        return false;
    }

    return true;
}

function getCourses() {

    let httpRequest = new XMLHttpRequest();
    let siteUrl = new URL(document.URL);
    let url = new URL("/api/", siteUrl.origin);
    url.searchParams.set('action', "getcourses");
    httpRequest.open("POST", url);
    httpRequest.responseType = "json";
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    let json = JSON.stringify({
        action: "getcourses",
    });

    httpRequest.send(json);

    httpRequest.onload = () => {
        if (httpRequest.status === 200) {
            getOpenCourses(httpRequest.response);
        }
    }
}

function getOpenCourses(response) {

    response.forEach((course) => {
        let table = document.getElementById('courses');
        let row = table.insertRow(table.rows.length);
        let cell1 = row.insertCell(0);
        let cell2 = row.insertCell(1);
        let cell3 = row.insertCell(2);
        let n = course.courseId;

        if (course.courseEnd === false){
            cell1.appendChild(document.createTextNode(course.courseName));
            cell2.appendChild(document.createTextNode(course.teacherName));
            let button = document.createElement("button");
            button.setAttribute("type", "button")
            button.setAttribute("onclick", "entryToCourse(" + course.courseId + ")");
            button.innerText = "Записаться";
            cell3.appendChild(button);
        }
    });

    document.getElementById('courses').classList.remove("hidden");

}


function entryToCourse(id) {

    if (user == null){
        alert("Войдите или зарегистрируйтесь");
    }
    else if(user.role == "s") {
        let httpRequest = new XMLHttpRequest();
        let siteUrl = new URL(document.URL);
        let url = new URL("/api/", siteUrl.origin);
        url.searchParams.set('action', "entrytocourse");
        url.searchParams.set('courseid', id);
        httpRequest.open("POST", url);
        httpRequest.responseType = "json";
        httpRequest.setRequestHeader('Content-Type', 'application/json');
        let json = JSON.stringify({
            action: "entrytocourse",
            courseid: id
        });

        httpRequest.send(json);

        httpRequest.onload = () => {
            if (httpRequest.status === 200) {
                if (httpRequest.response.error != null) {
                    alert(httpRequest.response.error);
                }
            }else if (httpRequest.status === 201){
                alert("Запись успешна");
            }
        }

    }
    else if(user.role == "t") {
        alert("Ты препоооод!");
    }

}