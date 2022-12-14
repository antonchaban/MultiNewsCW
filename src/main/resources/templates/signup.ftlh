<#import "blocks/template.ftlh" as t>
<@t.template customer language>
    <#if language == "eng">
        <h1 style="text-align: center">Sign Up Form</h1>
        <hr>
        <form action="/signup" method="post">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" class="form-control" required id="username" name="username">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" required name="password">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" style="width: 100%" class="btn btn-dark">Sign Up</button>
        </form>
    </#if>

    <#if language == "ukr">
        <h1 style="text-align: center">Форма реєстрації</h1>
        <hr>
        <form action="/signup" method="post">
            <div class="form-group">
                <label for="username">Ім'я користувача</label>
                <input type="text" class="form-control" required id="username" name="username">
            </div>
            <div class="form-group">
                <label for="password">Пароль</label>
                <input type="password" class="form-control" id="password" required name="password">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" style="width: 100%" class="btn btn-dark">Зареєструватися</button>
        </form>
    </#if>

</@t.template>
