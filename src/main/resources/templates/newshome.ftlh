<#import "blocks/template.ftlh" as t>
<@t.template customer>
    <h1 style="text-align: center">Breaking News</h1><hr>
    <form method="get" action="/articles">
        <div class="form-row">
            <div class="form-group col-md-2">
                <label for="searchTopic">Topic</label>
                <select id="searchTopic" class="form-control">
                    <option value="">All Topics</option>
                    <option value="War">War</option>
                    <option value="Sport">Sport</option>
                    <option value="Economy">Economy</option>
                </select>
            </div>
            <div class="form-group col-md-10">
                <label for="searchWord">What are you looking for?</label>
                <input type="text" name="searchWord" <#if searchWord??>value="${searchWord}"</#if> class="form-control"
                       id="searchWord">
            </div>
            <button style="width: 100%" type="submit" class="btn btn-dark">Search</button>
        </div>
    </form>
    <hr>
    <div class="card-group">
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
