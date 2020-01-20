let loginRegEx = /^[a-zA-z]{1}[a-zA-Z1-9_]{3,20}$/i;
let passRegEx = /^[a-zA-z]{1}[a-zA-Z0-9_/!/@/#/$/%/&]{3,20}$/i;
let emailRegEx = /^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$/
function loginValidation(login) {
    return loginRegEx.test(login);
}
function passValidation(password) {
    return passRegEx.test(password);
}
function emailValidation(email) {
    return emailRegEx.test(email);
}
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
function openRegistrationForm(){
    document.getElementById("loginFormMessage").innerHTML = "";
    document.getElementById("registrWindowMessage").innerHTML = "";
    document.getElementById("loginWindow").style.display = "none";
    document.getElementById("registrWindow").style.display = "block";
    document.getElementById("openloginWindow").style.display = "block";
    document.forms.loginForm.elements.login.value = "";
    document.forms.loginForm.elements.password.value = "";
}
function openLoginForm(){
    document.getElementById("loginFormMessage").innerHTML = "";
    document.getElementById("registrWindowMessage").innerHTML = "";
    document.getElementById("registrWindow").style.display = "none";
    document.getElementById("loginWindow").style.display = "block";
    document.getElementById("openloginWindow").style.display = "block";
    document.forms.registrForm.elements.login.value = "";
    document.forms.registrForm.elements.email.value = "";
    document.forms.registrForm.elements.password1.value = "";
    document.forms.registrForm.elements.password2.value = "";
}
function logIn(){
    document.getElementById("loginFormMessage").innerHTML = ""
    
    let form = document.forms.loginForm;
    let login = form.elements.login.value;
    let password = form.elements.password.value;

    if(loginValidation(login) && passValidation(password)){
        let httpRequest = new XMLHttpRequest();
        let siteUrl = new URL(document.URL);
        let url = new URL("/authorization", siteUrl.origin);
        url.searchParams.set('login', login);
        url.searchParams.set('password', password);
        httpRequest.open("GET", url);
        httpRequest.responseType = "json";
        httpRequest.setRequestHeader('Content-Type', 'application/json');
        let json = JSON.stringify({
            login: login,
            password: password,
            email: null
        });

        httpRequest.send(json);

        httpRequest.onload = () => {
            if (httpRequest.status == 200) {
                if(httpRequest.response.login[0] != null){
                    document.getElementById("loginName").innerHTML = "Здравствуйте, " + httpRequest.response.login + " !";
                    document.getElementById("openloginWindow").style.display = "none";
                    document.getElementById("login").style.display = "none";
                    document.getElementById("logout").style.display = "block";
                }else{
                    document.getElementById("loginFormMessage").style.color = "red";
                    document.getElementById("loginFormMessage").innerHTML = "Неверный логин, либо пароль";
                }
            }
        }

    }else{
        document.getElementById("loginFormMessage").style.color = "red"
        document.getElementById("loginFormMessage").innerHTML = "Неверный ввод"
    }  
}

function signUp(){
    document.getElementById("registrWindowMessage").innerHTML = ""
    
    let form = document.forms.registrForm;
    let login = form.elements.login.value;
    let password1 = form.elements.password1.value;
    let password2 = form.elements.password1.value;
    let email = form.elements.email.value;


    if(loginValidation(login) && passValidation(password1) && passValidation(password2) && emailValidation(email)){
        if(password1 == password2){


            let httpRequest = new XMLHttpRequest();
            let siteUrl = new URL(document.URL);
            let url = new URL("/authorization", siteUrl.origin);
            url.searchParams.set('login', login);
            url.searchParams.set('email', email);
            url.searchParams.set('password', password1);
            httpRequest.open("POST", url);
            httpRequest.responseType = "json";
            httpRequest.setRequestHeader('Content-Type', 'application/json');
            let json = JSON.stringify({
                login: login,
                password: password1,
                email: email
            });

        httpRequest.send(json);
    
            httpRequest.onload = () => {
                if (httpRequest.status == 200) {
                    if(httpRequest.response.login[0] != null){
                        document.getElementById("loginName").innerHTML = "Здравствуйте, " + httpRequest.response.login + " !";
                        document.getElementById("openloginWindow").style.display = "none";
                        document.getElementById("login").style.display = "none";
                        document.getElementById("logout").style.display = "block";
                    }else{
                        document.getElementById("registrWindowMessage").style.color = "red";
                        document.getElementById("registrWindowMessage").innerHTML = "Пользователь с таким логин/e-mail уже существует ";
                    }
                }
            }


        } else{
           document.getElementById("registrWindowMessage").style.color = "red"
           document.getElementById("registrWindowMessage").innerHTML = "Пароли не совпадают"
        } 
    }else{
        document.getElementById("registrWindowMessage").style.color = "red"
        document.getElementById("registrWindowMessage").innerHTML = "Неверный ввод"
    }  
}

function logOut(){
    document.getElementById("openloginWindow").style.display = "none";
    document.getElementById("loginName").innerHTML = "Гость";
    document.getElementById("logout").style.display = "none";
    document.getElementById("login").style.display = "block";
    document.forms.loginForm.elements.login.value = "";
    document.forms.loginForm.elements.password.value = "";
    document.forms.registrForm.elements.login.value = "";
    document.forms.registrForm.elements.email.value = "";
    document.forms.registrForm.elements.password1.value = "";
    document.forms.registrForm.elements.password2.value = "";
}
function closeForm(){
    document.getElementById("openloginWindow").style.display = "none";
    document.getElementById("message").innerHTML = "";
    document.forms.loginForm.elements.login.value = "";
    document.forms.loginForm.elements.password.value = "";
    document.forms.registrForm.elements.login.value = "";
    document.forms.registrForm.elements.email.value = "";
    document.forms.registrForm.elements.password1.value = "";
    document.forms.registrForm.elements.password2.value = "";
}