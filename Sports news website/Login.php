<html>
    <head>
        <title>Login</title>
        <script type="text/javascript" src="Validation.js"></script>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <div class="loginBox">            
            <img src="images\user.png" class="avatar">
            <h2>Login In</h2>
            <form name="loginform" method="POST" onsubmit="return loginvalid()" action="login_ok.php">
                <p>username</p>
                <input name="username" type="text" placeholder="user name">
                <div id="valid"></div>
                <p>Password</p>
                <input name="password" type="password" placeholder="*******">
                <div id="valid"></div>
                <input type="Submit" value="Sign In">
                <a href="signup.php">Signup</a>
            </form>
        </div>
    </body>
</html>