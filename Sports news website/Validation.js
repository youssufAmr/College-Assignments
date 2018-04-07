function signupvalid() {
    
    var username = document.forms["signupform"]["username"].value;
    var email = document.forms["signupform"]["email"].value;
    var phonenumber = document.forms["signupform"]["phonenumber"].value;
    var password = document.forms["signupform"]["password"].value;
    var flag1 = true;
    var flag2 = true;
    var flag3 = true;
    var flag4 = true;
    var filtermail = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    var filterpass = /^([a-zA-Z0-9]){8,32}$/;
    var filterphone = /^\(?([0-9]{3})\)?[-]?([0-9]{4})[-]?([0-9]{4})$/;
    
    if (username == "") {
        document.getElementById('validname').innerText = "*Username require";
        flag1 = false;
    }
    else if (username.length > 20) {
        document.getElementById('validname').innerText = "*Username too long";
        flag1 = false;
    }
    else {
        document.getElementById('validname').innerText = "";
        flag1 = true;
    }

    if(email == ""){
        document.getElementById('validemail').innerText = "*Email require";
        flag2 = false;
    }
    else if (!filtermail.test(email)){
        document.getElementById('validemail').innerText = "*Email not valid";
        flag2 = false;
    }
    else{
        document.getElementById('validemail').innerText = "";
        flag2 = true;
    }

    if (!filterphone.test(phonenumber)) {
        document.getElementById('validphone').innerText = "Invalid Phone number";
        flag3 = false;
    }
    else {
        document.getElementById('validphone').innerText = "";
        flag3 = true;
    }

    if(password == ""){
        document.getElementById('validpassword').innerText = "*Password require";
        flag4 = false;
    }else if(!filterpass.test(password)){
        document.getElementById('validpassword').innerText = "*Password should contain upper case,lower case, number ,[8-32]";
        flag4 = false;
    }
    else if(password.length > 32){
        document.getElementById('validpassword').innerText = "*Password too long";
        flag4 = false;
    }
    else if (password.length < 8) {
        document.getElementById('validpassword').innerText = "*Password too short";
        flag4 = false;
    }
    else{
        document.getElementById('validpassword').innerText = "";
        flag4 = true;
    }
    
    if(flag1 && flag2 && flag3 && flag4)
        return true;
    else{
        return false;
    }
}

function loginvlaid(){
    
}