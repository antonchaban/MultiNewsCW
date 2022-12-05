<#import "blocks/template.ftlh" as t>
<@t.template mycustomer>
    <h1 style="text-align: center">User Info</h1>
    <hr>
    <div style="text-align: center">
        <img src="/static/images/avatar.png"
             style="border:2px solid black; border-radius: 20px" height="120">
        <br>
        <br>
        <h2>${customer.username}</h2>
    </div>
    <br>
    <hr>
    <h1 style="text-align: center">Articles written by ${customer.username}</h1>
    <br>
    <div class="card-columns">
        <#list articles?sort_by("articleDate")?reverse as article>
            <a href="/articles/${article.articleId}" class="article-link">
                <div class="card text-white bg-dark my-3 article">
                    <div class="card-body">
                        <h5 class="card-title">${article.articleTitle}</h5>
                        <p class="card-text">${article.articleDescription}</p>
                        <p class="card-text">Source: ${article.articleSource}</p>
                    </div>
                    <div class="card-footer">
                        <small class="text-muted">${article.articleDate}</small>
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center">No Articles</h1>
        </#list>
    </div>
</@t.template>