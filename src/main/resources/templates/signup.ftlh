<#import "blocks/template.ftlh" as t>
<@t.template customer language>
    <#if language == "en">
        <h1 style="text-align: center">Sign Up Form</h1>
        <hr>
        <form action="/signup" method="post" onsubmit="return comparePwd()">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" class="form-control" required id="username" name="username">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" required name="password" minlength="3">
            </div>
            <div class="form-group">
                <label for="verifyPassword">Verify Password</label>
                <input type="password" class="form-control" id="verifyPassword" required name="verifyPassword" minlength="3">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" style="width: 100%" class="btn btn-dark">Sign Up</button>
        </form>

        <script>
            function comparePwd() {
                var password = document.getElementById("password").value;
                var verifyPassword = document.getElementById("verifyPassword").value;

                if (password !== verifyPassword) {
                    alert("Passwords do not match!");
                    return false;
                }

                return true;
            }
        </script>
    </#if>

    <#if language == "uk">
        <h1 style="text-align: center">Форма реєстрації</h1>
        <hr>
        <form action="/signup" method="post" onsubmit="return comparePwd()">
            <div class="form-group">
                <label for="username">Ім'я користувача</label>
                <input type="text" class="form-control" required id="username" name="username">
            </div>
            <div class="form-group">
                <label for="password">Пароль</label>
                <input type="password" class="form-control" id="password" required name="password" minlength="3">
            </div>
            <div class="form-group">
                <label for="verifyPassword">Підтвердити пароль</label>
                <input type="password" class="form-control" id="verifyPassword" required name="verifyPassword" minlength="3">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" style="width: 100%" class="btn btn-dark">Зареєструватися</button>
        </form>

        <script>
            function comparePwd() {
                var password = document.getElementById("password").value;
                var verifyPassword = document.getElementById("verifyPassword").value;

                if (password !== verifyPassword) {
                    alert("Паролі не співпадають!");
                    return false;
                }

                return true;
            }
        </script>
    </#if>
</@t.template>
