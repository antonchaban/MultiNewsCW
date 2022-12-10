<#import "blocks/template.ftlh" as t>
<@t.template customer>
    <p><h1 style="text-align: center">${article.articleTitle}</h1></p>
    <p style="text-align: center">${article.articleDescription}</p>
    <p style="text-align: center">Article Source: <b>${article.articleSource}</b></p>
    <small class="text-muted">${article.articleDate}</small>

    <br>
    <#if customer.isAdmin() || customer == article.customer>
        <form action="/edit/articles/${article.articleId}" method="get">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-warning">
                Edit
            </button>
        </form>
    </#if>

    <hr>
    <p style="text-align: center">
        <a href="/customer/${article.customer.customerId}">
            ${article.customer.username}
        </a>
    </p>

</@t.template>
