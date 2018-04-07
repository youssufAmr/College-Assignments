<html>
    <head>
        <title>Sign Up</title>
        <script type="text/javascript" src="Validation.js"></script>
        <link rel="stylesheet" href="style.css" type="text/css">
    </head>
    <body>
        <div class="signupBox">            
            <img src="images\signup.png" class="welcome">
            <h2>Sign Up</h2>
            <form name="signupform" onsubmit="return signupvalid()" action="signup_ok.php" method="post">
                <p>Name</p>
                <input name="username" type="text" placeholder="user name">
                <div id="validname" class="valid"><?php echo $user;?></div>
                <p>Email</p>
                <input name="email" type="text" placeholder="avatar@example.com">
                <div id="validemail" class="valid"></div>
                <p>Phone Number</p>
                <input name="phonenumber" type="text" placeholder="01xxxxxxxxx">
                <div id="validphone" class="valid"></div>
                <p>Password</p>
                <input name="password" type="password" placeholder="*********">
                <div id="validpassword" class="valid"></div>
                <p>Account Type: </p>
                <div name="select">
                <select name="type">
                <option value="admin">Admin</option>
                <option value="user">User</option>
                </select>
                </div>
                <input type="Submit" value="Sign Up"> 
            </form>
        </div>
    </body>
</html>