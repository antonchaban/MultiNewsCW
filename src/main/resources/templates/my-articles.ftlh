<#import "blocks/template.ftlh" as t>
<@t.template customer language>
    <#if language == "en">
        <h1 style="text-align: center">My Articles</h1>
        <hr>
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 100%"
           data-toggle="collapse" href="#collapseExample" role="button"
           aria-expanded="true" aria-controls="collapseExample">
            Add New Article
        </a>
        <div class="collapse mb-3" id="collapseExample" style="">
            <form action="/articles/create" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="articleTitleEn">Article Title</label>
                    <input type="text" required class="form-control"
                           id="articleTitleEn" name="articleTitleEn">
                </div>
                <div class="form-group">
                    <label for="articleLink">Article Link</label>
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" id="articleLink" name="articleLink">
                    </div>
                </div>
                <div class="form-group">
                    <label for="articleDescriptionEn">Article Description</label>
                    <textarea required class="form-control" id="articleDescriptionEn"
                              name="articleDescriptionEn"></textarea>
                </div>
                <div class="form-group">
                    <label for="articleSource">Article Source</label>
                    <textarea required class="form-control" id="articleSource" name="articleSource"></textarea>
                </div>

                <div class="form-group">
                    <#list categories as category>
                        <div>
                            <label>
                                <input type="radio" name="category" value="${category}">${category}
                            </label>
                        </div>
                    </#list>
                </div>

                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                <button type="submit" style="width: 100%" class="btn btn-dark">Create</button>
            </form>
        </div>
        <div class="card-columns">
            <#list articles as article>
                <a href="/articles/${article.articleId?long?c}" class="article-link">
                    <div class="card text-white bg-dark my-3 product">
                        <div class="card-top">
                            <div style="display: inline-block">
                                <form action="/articles/delete/${article.articleId?long?c}" method="post" style="float:left">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <button type="submit" class="btn btn-danger m-3">
                                        Delete
                                    </button>
                                </form>
                                <form action="/edit/articles/${article.articleId?long?c}" method="get" style="float:right">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <button type="submit" class="btn btn-warning m-3">
                                        Edit
                                    </button>
                                </form>
                            </div>

                        </div>
                        <div class="card-body">
                            <h5 class="card-title">${article.articleTitleEn}</h5>

                            <p class="card-text">${article.articleDescriptionEn}</p>
                        </div>
                        <div class="card-footer">
                            <p class="card-text"><a href="${article.articleLink}">Original Link</a></p>
                            ${article.articleSource}
                        </div>
                    </div>
                </a>
            <#else>
                <h1 style="text-align: center">No Articles</h1>
            </#list>
        </div>
    </#if>

    <#if language == "uk">
        <h1 style="text-align: center">Мої статті</h1>
        <hr>
        <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 100%"
           data-toggle="collapse" href="#collapseExample" role="button"
           aria-expanded="true" aria-controls="collapseExample">
            Створити нову статтю
        </a>
        <div class="collapse mb-3" id="collapseExample" style="">
            <form action="/articles/create" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="articleTitle">Заголовок статті</label>
                    <input type="text" required class="form-control"
                           id="articleTitle" name="articleTitle">
                </div>
                <div class="form-group">
                    <label for="articleLink">Посилання</label>
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" id="articleLink" name="articleLink">
                    </div>
                </div>
                <div class="form-group">
                    <label for="articleDescription">Детальна інформація</label>
                    <textarea required class="form-control" id="articleDescription"
                              name="articleDescription"></textarea>
                </div>
                <div class="form-group">
                    <label for="articleSource">Джерело</label>
                    <textarea required class="form-control" id="articleSource" name="articleSource"></textarea>
                </div>

                <div class="form-group">
                    <#list categories as category>
                        <div>
                            <label>
                                <input type="radio" name="category" value="${category}">${category}
                            </label>
                        </div>
                    </#list>
                </div>

                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                <button type="submit" style="width: 100%" class="btn btn-dark">Створити</button>
            </form>
        </div>
        <div class="card-columns">
            <#list articles as article>
                <a href="/articles/${article.articleId?long?c}" class="article-link">
                    <div class="card text-white bg-dark my-3 product">
                        <div class="card-top">
                            <div style="display: inline-block">
                                <form action="/articles/delete/${article.articleId?long?c}" method="post" style="float:left">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <button type="submit" class="btn btn-danger m-3">
                                        Видалити
                                    </button>
                                </form>
                                <form action="/edit/articles/${article.articleId?long?c}" method="get" style="float:right">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <button type="submit" class="btn btn-warning m-3">
                                        Редагувати
                                    </button>
                                </form>
                            </div>

                        </div>
                        <div class="card-body">
                            <h5 class="card-title">${article.articleTitle}</h5>
                            <p class="card-text">${article.articleDescription}</p>
                        </div>
                        <div class="card-footer">
                            <p class="card-text"><a href="${article.articleLink}">Оригінальне посилання</a></p>
                            ${article.articleSource}
                        </div>
                    </div>
                </a>
            <#else>
                <h1 style="text-align: center">А нема статтей :(</h1>
            </#list>
        </div>
    </#if>

</@t.template>