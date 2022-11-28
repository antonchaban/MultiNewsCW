<!DOCTYPE html>
<hmtl>
    <head>
        <title>MultiNews</title>
    </head>
    <body>
    <#list articles as article>
        <div>
            <p>${article.articleTitle}</p>
            <p>${article.articleLink}</p>
            <p>${article.articleDescription}</p>
            <p>${article.articleDate}</p>
            <p>${article.customer.userName}</p>
            <a href="/articles/${article.articleId}">More Info</a>
            <hr>
        </div>
    </#list>
    <hr>
    <h3>Create New Article</h3>
    <form action="/articles/create" method="post">
        Article Title <input type="text" name="articleTitle"> <br> <br>
        Article Link <input type="text" name="articleLink"> <br> <br>
        Article Description <input type="text" name="articleDescription"> <br> <br>
        Article Source <input type="text" name="articleSource"> <br> <br>
        <input type="submit" value="Create Article">
    </form>
    </body>
</hmtl>