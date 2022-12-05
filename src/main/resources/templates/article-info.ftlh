<#import "blocks/template.ftlh" as t>
<@t.template customer>
    <p><h1 style="text-align: center">${article.articleTitle}</h1></p>
    <p style="text-align: center">${article.articleDescription}</p>
    <p style="text-align: center">Article Source: <b>${article.articleSource}</b></p>
    <small class="text-muted">${article.articleDate}</small>

    <hr>
    <p style="text-align: center">
        <a href="/customer/${article.customer.customerId}">
            ${article.customer.username}
        </a>
    </p>

</@t.template>
