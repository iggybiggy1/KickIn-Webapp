<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1">
    <title>Log In</title>
    <link rel="stylesheet" href="style.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@300&display=swap" rel="stylesheet">
    
    <script
  	src="https://code.jquery.com/jquery-3.4.1.min.js"
  	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
  	crossorigin="anonymous">
    </script>
    <script 
    type="text/javascript"
    src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.min.js">
    </script>
</head>

<body>
<div class="header">
    <h1>Kick-In Document Tool</h1>
</div>
<!--TODO: add action to form -->
<div class="form-div">
    <form action="login" method="post">
        <div class="container">
            <h2 id="login">Log In</h2>
            <label for="uname"><b>Username:</b></label>
                <input type="text" placeholder="Enter Username" name="uname" required>
            <label for="psw" id="psw-label"><b>Password:</b></label>
            <input type="password" placeholder="Enter Password" name="psw" id="password-input" required>
			<br>
			<span style = "color: red;">${message}</span>
			<br><br>
			<div id="passRecovery">
				<a href="passRecovery.html"> Forgot password? </a>
			</div>
            <button type="submit" class="submitbutton" onclick = "message()" >Login</button>
            
        </div>
    </form>
</div>

</body>

<script>


$(document).ready(function() {
    $("#loginForm").validate({
        rules: {
            uname: {
                required: true,
                uname: true
            },

            psw: "required",
        },

        messages: {
            uname: {
                required: "Please enter your username",
                uname: "Please enter a valid username or email address"
            },

            psw: "Please enter your password"
        }
    });

});




</script>

</html>