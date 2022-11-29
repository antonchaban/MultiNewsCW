<!DOCTYPE html>
<html>
<head>
    <title>MultiNews</title>
</head>
<body>
<h4>Sign Up</h4>
<form action="/signup" method="post">
    Username: <input type="text" name="userName"><br><br>
    Пароль: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Вход"/>
</form>

</body>
</html>