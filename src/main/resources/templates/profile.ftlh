<#import "blocks/template.ftlh" as t>
<@t.template customer language>
    <#if language == "en">
        <h1 style="text-align: center">Your Profile</h1>
        <hr>
        <br>
        <div style="text-align: center">
            <img src="/static/images/avatar.png"
                 style="border:2px solid black; border-radius: 20px" height="120">
            <br>
            <br>
            <h2>${customer.username}</h2>
            <br>
            <br>
            <#if customer.isAdmin()>
                <a class="btn btn-dark mb-3" style="text-align: center; width: 100%"
                   href="/admin">
                    Admin Panel
                </a><br>
            </#if>
            <a class="btn btn-dark mb-3" style="text-align: center; width: 100%"
               href="/my/articles">
                My Articles
            </a>
            <form action="/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" style="text-align: center; width: 100%" class="btn btn-danger mb-2">
                    Logout
                </button>
            </form>
        </div>
    </#if>

    <#if language == "uk">
        <h1 style="text-align: center">Ваш профіль</h1>
        <hr>
        <br>
        <div style="text-align: center">
            <img src="/static/images/avatar.png"
                 style="border:2px solid black; border-radius: 20px" height="120">
            <br>
            <br>
            <h2>${customer.username}</h2>
            <br>
            <br>
            <#if customer.isAdmin()>
                <a class="btn btn-dark mb-3" style="text-align: center; width: 100%"
                   href="/admin">
                    Панель адміністратора
                </a><br>
            </#if>
            <a class="btn btn-dark mb-3" style="text-align: center; width: 100%"
               href="/my/articles">
                Мої статті
            </a>
            <form action="/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" style="text-align: center; width: 100%" class="btn btn-danger mb-2">
                    Вийти
                </button>
            </form>
        </div>
    </#if>

</@t.template>