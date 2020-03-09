
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

    document.getElementById("date").innerHTML = date.toLocaleString("RU", options) + "</p>";
    setInterval(clock, 1000);
}

document.addEventListener("DOMContentLoaded", () => {
    clock(), getCourses()
});

window.onbeforeunload = logout();


function openModalLogRegForm() {
    document.getElementById("openModalLogRegForm").classList.remove("hidden");
    document.getElementsByTagName("body").item(0).style.overflow = "hidden";
    clearLogRegForms();
    openLoginForm();
}

function closeModalLogRegForm() {
    document.getElementById("openModalLogRegForm").classList.add("hidden");
    document.getElementsByTagName("body").item(0).style.overflow = "visible";
    clearLogRegForms();
}

function openRegistrationForm() {
    document.getElementById("loginWindow").classList.add("hidden");
    document.getElementById("registrWindow").classList.remove("hidden");
}

function openLoginForm() {
    document.getElementById("registrWindow").classList.add("hidden");
    document.getElementById("loginWindow").classList.remove("hidden");
}

function clearLogRegForms(){

    document.forms.loginForm.elements.login.value = "";
    document.forms.loginForm.elements.password.value = "";
    document.forms.registrForm.elements.login.value = "";
    document.forms.registrForm.elements.password1.value = "";
    document.forms.registrForm.elements.password2.value = "";

}

let user = null;
let courses = null;
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

    document.forms.addCourseWindowForm.onsubmit = (e) => {
        e.preventDefault();
            addCourse();
    };

    document.forms.setFeedbackForm.onsubmit = (e) => {
        e.preventDefault();
            setFeedback();
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
                getCourses();
                closeModalLoginForm();

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
        login: login
    });

    httpRequest.send(json);

    httpRequest.onload = () => {
        if (httpRequest.status === 200) {

            user = null;
            document.getElementById('login').classList.remove("hidden");
            document.getElementById('logout').classList.add("hidden");
            document.getElementById('user_name').innerText = "Гость";

            getCourses();
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
                getCourses();
                closeModalLoginForm();

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

/*Guest//Student*/

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

            courses = httpRequest.response;

            if (user == null || user.role[0] === "s" ){
                document.getElementById('courses_for_teachers').classList.add("hidden");
                let table = document.getElementById('courses_for_teachers');
                while (table.rows.length != 1){
                    table.deleteRow(1);
                }
                getOpenCourses( httpRequest.response);
            } else if(user.role[0] === "t") {
                document.getElementById('courses_for_students').classList.add("hidden");
                let table = document.getElementById('courses_for_students');
                while (table.rows.length != 1){
                    table.deleteRow(1);
                }
                getTeacherCourse(httpRequest.response);
            }
        }
    }
}

function getOpenCourses(response) {
    let table = document.getElementById('courses_for_students');
    while (table.rows.length != 1){
        table.deleteRow(1);
    }
    response.forEach((course) => {
        let row = table.insertRow(table.rows.length);
        let cell1 = row.insertCell(0);
        let cell2 = row.insertCell(1);
        let cell3 = row.insertCell(2);

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

    document.getElementById('courses_for_students').classList.remove("hidden");

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

/*teacher*/

function getTeacherCourse(response) {
    let table = document.getElementById('courses_for_teachers');
    while (table.rows.length != 1){
        table.deleteRow(1);
    }
    let html = ``;
    response.forEach((course) => {
        if (course.teacherName === user.name[0]){
            html += `<tr>
                        <th>${course.courseName}</th>`

            if (course.courseEnd === false){
                html += `<th><button onclick="endCourse(${course.courseId})"> Закончить курс </button></th>`
            }else{
                html += `<th>Курс окончен</th>`
            }

            html += `<th><button id="open_student-on-course_${course.courseId}" onclick="openStudentOnCourse(${course.courseId})"> ↓ </button>
                        <button id="close_student-on-course_${course.courseId}" class="hidden" onclick="closeStudentOnCourse(${course.courseId})"> ↑ </button>
                        </th>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <table class="table hidden unvisible" id="students_on_course_${course.courseId}">
                                <tr>
                                    <th>Имя студента</th>
                                    <th colspan="3">Отзыв</th>
                                    <th>Оценка</th>
                                </tr>`

            course.students.forEach((student) => {
                html += `<tr>
                            <th>${student.studentName}</th>`
                if (student.feedback === undefined && student.mark === undefined){
                    html += `<th colspan="3">-</th><th >-</th>`;
                    if (course.courseEnd === false){
                        html += `<th>Курс не окончен</th>`;
                    }else{
                        html += `<th><button onclick="setFeedbackClick(${course.id},${student.id})" > Оставить отзыв </button></th>`;
                    }

                }else{
                    html += `<th colspan="3">${student.feedback}</th><th >${student.mark}</th>`;
                    if (course.courseEnd === false){
                        html += `<th>Курс не окончен</th>`;
                    }else{
                        html += `<th></th>`;
                    }
                }
                 html += `</tr>`;
            });

            html += `</table>
                    </td>
                </tr>`
        }
    });

    document.getElementById('courses_for_teachers').lastChild.insertAdjacentHTML("beforeend", html);
    document.getElementById('courses_for_teachers').classList.remove("hidden");


}

function openStudentOnCourse(id) {
    document.getElementById('open_student-on-course_' + id).classList.add("hidden");
    document.getElementById('close_student-on-course_' + id).classList.remove("hidden");
    document.getElementById('students_on_course_' + id).classList.remove("hidden");
    document.getElementById('students_on_course_' + id).classList.replace("unvisible", "visible");
}

function closeStudentOnCourse(id) {
    document.getElementById('students_on_course_' + id).classList.replace("visible", "unvisible")
    document.getElementById('students_on_course_' + id).classList.add("hidden");
    document.getElementById('open_student-on-course_' + id).classList.remove("hidden");
    document.getElementById('close_student-on-course_' + id).classList.add("hidden");
}

function endCourse(id) {
    if (user == null){
        alert("Войдите или зарегистрируйтесь");
    }
    else if(user.role == "s") {
        alert("Как ты сюда попал, выруби живаааа");
    }
    else if(user.role == "t") {

        let httpRequest = new XMLHttpRequest();
        let siteUrl = new URL(document.URL);
        let url = new URL("/api/", siteUrl.origin);
        url.searchParams.set('action', "finishcourse");
        url.searchParams.set('courseid', id);
        httpRequest.open("POST", url);
        httpRequest.responseType = "json";
        httpRequest.setRequestHeader('Content-Type', 'application/json');
        let json = JSON.stringify({
            action: "finishcourse",
            courseid: id
        });

        httpRequest.send(json);

        httpRequest.onload = () => {
            if (httpRequest.status === 200) {
                if (httpRequest.response.error != null) {
                    alert(httpRequest.response.error);
                }
            }else if (httpRequest.status === 201){
                alert("Успешно");
            }
        }
    }
}

function clearAddCourse_SetFeedbackForms(){

    document.forms.addCourseWindowForm.elements.name.value = "";
    document.forms.setFeedbackForm.elements.feedback.value = "";
    document.forms.setFeedbackForm.elements.mark.value = "";

}

function closeModalAddCourse_SetFeedback() {
    document.getElementById("openModalAddCourse_SetFeedback").classList.add("hidden");
    document.getElementById("addCourseWindow").classList.add("hidden");
    document.getElementById("setFeedbackWindow").classList.add("hidden");
    clearAddCourse_SetFeedbackForms();
}

function addCourseClick() {

   if (user == null){
        alert("Войдите или зарегистрируйтесь");
    }
    else if(user.role === "s") {
        alert("Как ты сюда попал, выруби живаааа");
    }
    else if(user.role === "t") {
       document.getElementById("openModalAddCourse_SetFeedback").classList.remove("hidden");
       document.getElementById("addCourseWindow").classList.remove("hidden");
       clearAddCourse_SetFeedbackForms();
    }
}

function addCourse() {
    document.getElementById("addCourseWindowFormMessage").innerHTML = "";

    let coursename =  document.forms.addCourseWindowForm.elements.name.value;

    let httpRequest = new XMLHttpRequest();
    let siteUrl = new URL(document.URL);
    let url = new URL("/api/", siteUrl.origin);
    url.searchParams.set('action', "addcourse");
    url.searchParams.set('coursename', coursename);
    httpRequest.open("POST", url);
    httpRequest.responseType = "json";
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    let json = JSON.stringify({
        action: "addcourse",
        coursename: coursename
    });

    httpRequest.send(json);

    httpRequest.onload = () => {
        if (httpRequest.status === 200) {
            if( httpRequest.response.error != null){
                let loginFormMessage = document.getElementById("addCourseWindowFormMessage");
                loginFormMessage.innerText = httpRequest.response.error;
            }else{
                alert("Успешно")
                getCourses();
                closeModalAddCourse_SetFeedback();
            }
        }
    }

}

function setFeedbackClick(courseid, studentid) {
    if (user == null){
        alert("Войдите или зарегистрируйтесь");
    }
    else if(user.role === "s") {
        alert("Как ты сюда попал, выруби живаааа");
    }
    else if(user.role === "t") {
        document.getElementById("openModalAddCourse_SetFeedback").classList.remove("hidden");
        document.getElementById("setFeedbackWindow").classList.remove("hidden");
        document.forms.setFeedbackForm.elements.courseid.value = courseid;
        document.forms.setFeedbackForm.elements.studentid.value = studentid;

        clearAddCourse_SetFeedbackForms();
    }
}

function setFeedback() {

    document.getElementById("setFeedbackFormMessage").innerHTML = "";

    let courseid =  document.forms.setFeedbackForm.elements.courseid.value;
    let studentid =  document.forms.setFeedbackForm.elements.studentid.value;
    let feedback =  document.forms.setFeedbackForm.elements.feedback.value;
    let mark =  document.forms.setFeedbackForm.elements.mark.value;

    let httpRequest = new XMLHttpRequest();
    let siteUrl = new URL(document.URL);
    let url = new URL("/api/", siteUrl.origin);
    url.searchParams.set('action', "setfeedback");
    url.searchParams.set('courseid', courseid);
    url.searchParams.set('studentid', studentid);
    url.searchParams.set('feedback', feedback);
    url.searchParams.set('mark', mark);
    httpRequest.open("POST", url);
    httpRequest.responseType = "json";
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    let json = JSON.stringify({
        action: "setfeedback",
        courseid: courseid,
        studentid: studentid,
        feedback: feedback,
        mark: mark
    });

    httpRequest.send(json);

    httpRequest.onload = () => {
        if (httpRequest.status === 200) {
            if( httpRequest.response.error != null){
                let loginFormMessage = document.getElementById("addCourseWindowFormMessage");
                loginFormMessage.innerText = httpRequest.response.error;
            }else{
                alert("Успешно")
                getCourses();
                closeModalAddCourse_SetFeedback();
            }
        }
    }
}

