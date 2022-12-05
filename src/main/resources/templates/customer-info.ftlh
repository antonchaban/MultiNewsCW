<!DOCTYPE html>
<html>
<head>
    <title>MultiNews</title>
</head>
<body>
<h4>Articles by: ${customer.username}</h4>
<#list articles as article>
    <div>
        <p>${article.articleTitle}</p>
        <p>${article.articleLink}</p>
        <p>${article.articleDescription}</p>
        <p>${article.articleDate}</p>
        <a href="/articles/${article.articleId}">More Info</a>
        <hr>
    </div>
    <#else>
    <h5>No articles</h5>
</#list>
</body>
</html>