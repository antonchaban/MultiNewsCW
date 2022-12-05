<!DOCTYPE html>
<html>
<head>
    <title>MultiNews</title>
</head>
<body>
<h4>About Article</h4>
<b>Article Title </b>${article.articleTitle}<br>
<b>Article Link </b>${article.articleLink}<br>
<b>Article Description </b>${article.articleDescription}<br>
<b>Article Source </b>${article.articleSource}<br>
<b>Author: </b><a href="/customer/${article.customer.customerId}">${article.customer.username}</a><br>
<hr>
<#--<form action="/product/delete/${product.id}" method="post">-->
<#--    <input type="submit" value="Удалить товар"/>-->
<#--</form>-->
</body>
</html>