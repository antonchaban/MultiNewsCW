<!DOCTYPE html>
<html>
<head>
    <title>MultiNews</title>
</head>
<body>
<h4>Login</h4>

<form action="/login" method="post">
    username: <input type="text" name="username"><br><br>
    password: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Вход"/>
</form>
<a href="/signup">Sign Up</a>
</body>
</html>