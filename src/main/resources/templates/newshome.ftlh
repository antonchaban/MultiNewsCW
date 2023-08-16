<#import "blocks/template.ftlh" as t>
<@t.template customer language>
<#--    ENG    -->
    <#if language == "en">
        <h1 style="text-align: center">Breaking News</h1>
        <hr>
    </#if>
<#--    -->
    <#if language == "uk">
        <h1 style="text-align: center">Останні Новини</h1>
        <hr>
    </#if>

<#--    ENG    -->
    <#if language == "en">
        <form method="get" action="/articles">
            <div class="form-row">
                <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 100%"
                   data-toggle="collapse" href="#collapseExample" role="button"
                   aria-expanded="true" aria-controls="collapseExample">
                    Search Parameters
                </a>
                <div class="collapse mb-3" id="collapseExample" style="">
                    <label for="searchTopic">Topic</label>
                    <select id="searchTopic" class="form-control">
                        <option value="">All Topics</option>
                        <option value="War">War</option>
                        <option value="Sport">Sport</option>
                        <option value="Economy">Economy</option>
                        <option value="Entertainment">Entertainment</option>
                        <option value="Science">Science</option>
                        <option value="Other">Other</option>
                    </select>
                    <label for="searchSource">Source</label>
                    <select id="searchSource" class="form-control" name="searchSource">
                        <option value="">All Sources</option>
                        <option value="UP">Українська правда</option>
                        <option value="UNIAN">УНІАН</option>
                        <option value="CNN">CNN</option>
                        <option value="FOX">FOX NEWS</option>
                    </select>
                    <div>
                        <label for="newsDate">Date:</label>
                        <input type="date" id="newsDate" name="newsDate" />
                    </div>
                </div>
                <div class="form-group col-md-10">
                    <label for="searchWord">What are you looking for?</label>
                    <input type="text" name="searchWord" <#if searchWord??>value="${searchWord}"</#if>
                           class="form-control"
                           id="searchWord">
                </div>
                <button style="width: 100%" type="submit" class="btn btn-dark">Search</button>
            </div>
        </form>
        <hr>
        <div class="card-group">
            <#list articles?sort_by("articleDate")?reverse as article>
                <a href="/articles/${article.articleId?long?c}" class="article-link">
                    <div class="card text-white bg-dark my-3 article">
                        <div class="card-body">
                            <h5 class="card-title">${article.articleTitleEn}</h5>
                            <p class="card-text">${article.articleDescriptionEn}</p>
                            <p class="card-text">Source: ${article.articleSource}</p>
                        </div>
                        <div class="card-footer">
                            <small class="text-muted">${article.articleDate?datetime}</small>
                        </div>
                    </div>
                </a>
            <#else>
                <h1 style="text-align: center">No Articles</h1>
            </#list>
        </div>
    </#if>

<#--    UKR   -->
    <#if language == "uk">
        <form method="get" action="/articles">
            <div class="form-row">
                <a class="btn btn-dark mt-3 mb-3" style="text-align: center; width: 100%"
                   data-toggle="collapse" href="#collapseExample" role="button"
                   aria-expanded="true" aria-controls="collapseExample">
                    Параметри Пошуку
                </a>
                <div class="collapse mb-3" id="collapseExample" style="">
                    <label for="searchTopic">Тема</label>
                    <select id="searchTopic" class="form-control">
                        <option value="">Всі теми</option>
                        <option value="War">Війна</option>
                        <option value="Sport">Спорт</option>
                        <option value="Economy">Економіка</option>
                        <option value="Entertainment">Розваги</option>
                        <option value="Science">Наука</option>
                        <option value="Other">Інші</option>
                    </select>
                    <label for="searchSource">Source</label>
                    <select id="searchSource" class="form-control" name="searchSource">
                        <option value="">Всі ресурси</option>
                        <option value="UP">Українська правда</option>
                        <option value="UNIAN">УНІАН</option>
                        <option value="CNN">CNN</option>
                        <option value="FOX">FOX NEWS</option>
                    </select>
                    <div>
                        <label for="newsDate">Дата:</label>
                        <input type="date" id="newsDate" name="newsDate" />
                    </div>
                </div>
                <div class="form-group col-md-10">
                    <label for="searchWord">Що ви шукаєте?</label>
                    <input type="text" name="searchWord" <#if searchWord??>value="${searchWord}"</#if>
                           class="form-control"
                           id="searchWord">
                </div>
                <button style="width: 100%" type="submit" class="btn btn-dark">На пошуки!</button>
            </div>
        </form>
        <hr>
        <div class="card-group">
            <#list articles?sort_by("articleDate")?reverse as article>
                <a href="/articles/${article.articleId?long?c}" class="article-link">
                    <div class="card text-white bg-dark my-3 article">
                        <div class="card-body">
                            <h5 class="card-title">${article.articleTitle}</h5>
                            <p class="card-text">${article.articleDescription}</p>
                            <p class="card-text">Джерело: ${article.articleSource}</p>
                        </div>
                        <div class="card-footer">
                            <small class="text-muted">${article.articleDate?datetime}</small>
                        </div>
                    </div>
                </a>
            <#else>
                <h1 style="text-align: center">Нема статей</h1>
            </#list>
        </div>
    </#if>
</@t.template>
