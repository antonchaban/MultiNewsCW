<#import "blocks/template.ftlh" as t>
<@t.template customer language>
    <#if language == "en">
        <h1 style="text-align: center">Login</h1>
        <hr>
        <form action="/login " method="post">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" class="form-control" id="username" name="username">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password" minlength="3">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" style="width: 100%" class="btn btn-dark">Login</button>
        </form>
        <hr>
        <h2>Want to become an editor? <a href="/signup">Sign Up!</a></h2>
    </#if>

    <#if language == "uk">
        <h1 style="text-align: center">Вхід</h1>
        <hr>
        <form action="/login " method="post">
            <div class="form-group">
                <label for="username">Ім'я користувача</label>
                <input type="text" class="form-control" id="username" name="username">
            </div>
            <div class="form-group">
                <label for="password">Пароль</label>
                <input type="password" class="form-control" id="password" name="password" minlength="3">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <button type="submit" style="width: 100%" class="btn btn-dark">Увійти</button>
        </form>
        <hr>
        <h2>Бажаєте стати редактором? <a href="/signup">Зареєструйтесь!</a></h2>
    </#if>
</@t.template>
