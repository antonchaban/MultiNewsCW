<!DOCTYPE html>
<hmtl>
    <head>
        <title>MultiNews</title>
    </head>
    <body>
    <h1>Current News</h1>
    <#if customer.username??>
        <h4>Username: <i>${customer.username}</i></h4>
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Logout"/>
        </form>
    <#else>
        <a href="/login"> Login</a>
    </#if>


    <hr>
    <#list articles as article>
        <div>
            <p>${article.articleTitle}</p>
            <p>${article.articleLink}</p>
            <p>${article.articleDescription}</p>
            <p>${article.articleDate}</p>
            <p>${article.customer.username}</p>
            <a href="/articles/${article.articleId}">More Info</a>
            <hr>
        </div>
    </#list>
    <#if customer.username??>
        <h3>Create New Article</h3>
        <form action="/articles/create" method="post">
            Article Title <input type="text" name="articleTitle"> <br> <br>
            Article Link <input type="text" name="articleLink"> <br> <br>
            Article Description <input type="text" name="articleDescription"> <br> <br>
            Article Source <input type="text" name="articleSource"> <br> <br>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Create Article">
        </form>
    </#if>

    </body>
</hmtl>