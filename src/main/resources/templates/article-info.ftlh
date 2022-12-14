<#import "blocks/template.ftlh" as t>
<@t.template customer language>
    <#if language == "eng">
        <p><h1
            style="text-align: center; display:inline-block; word-wrap:break-word; width:100%">${article.articleTitle}</h1></p>
        <p style="text-align: center; display:inline-block; word-wrap:break-word; width:100%">${article.articleDescription}</p>
        <p style="text-align: center; display:inline-block; word-wrap:break-word; width:100%">Article Source:
            <b>${article.articleSource}</b></p>
        <small class="text-muted">${article.articleDate}</small>

        <br>
        <div style="display: inline-block">
            <#if customer.isAdmin() || customer == article.customer>
                <form action="/edit/articles/${article.articleId}" method="get" style="float:left">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-warning m-3">
                        Edit
                    </button>
                </form>
                <form action="/articles/delete/${article.articleId}" method="post" style="float:right">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-danger m-3">
                        Delete
                    </button>
                </form>
            </#if>
        </div>

        <hr>
        <p style="text-align: center">
            <a href="/customer/${article.customer.customerId}">
                ${article.customer.username}
            </a>
        </p>
    </#if>

    <#if language == "ukr">
        <p><h1 style="text-align: center; display:inline-block; word-wrap:break-word; width:100%">${article.articleTitle}</h1></p>
        <p style="text-align: center; display:inline-block; word-wrap:break-word; width:100%">${article.articleDescription}</p>
        <p style="text-align: center; display:inline-block; word-wrap:break-word; width:100%">Джерело: <b>${article.articleSource}</b></p>
        <small class="text-muted">${article.articleDate}</small>

        <br>
        <div style="display: inline-block">
            <#if customer.isAdmin() || customer == article.customer>
                <form action="/edit/articles/${article.articleId}" method="get" style="float:left">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-warning m-3">
                        Редагувати
                    </button>
                </form>
                <form action="/articles/delete/${article.articleId}" method="post" style="float:right">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-danger m-3">
                        Видалити
                    </button>
                </form>
            </#if>
        </div>

        <hr>
        <p style="text-align: center">
            <a href="/customer/${article.customer.customerId}">
                ${article.customer.username}
            </a>
        </p>
    </#if>


</@t.template>
