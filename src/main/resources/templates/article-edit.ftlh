<#import "blocks/template.ftlh" as t>
<@t.template customer language>
    <#if language == "eng">
        <h1 style="text-align: center">Editing Article: ${article.articleTitle}</h1>
        <form action="/edit/articles/${article.articleId}" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="articleTitle">Article Title</label>
                <input type="text" required class="form-control"
                       id="articleTitle" name="articleTitle" value="${article.articleTitle}">
            </div>
            <div class="form-group">
                <label for="articleLink">Article Link</label>
                <div class="input-group mb-3">
                    <input type="text" required class="form-control" id="articleLink" name="articleLink" value="${article.articleLink}">
                </div>
            </div>
            <div class="form-group">
                <label for="articleDescription">Article Description</label>
                <textarea required class="form-control" id="articleDescription" name="articleDescription">${article.articleDescription}</textarea>
            </div>
            <div class="form-group">
                <label for="articleSource">Article Source</label>
                <textarea required class="form-control" id="articleSource" name="articleSource">${article.articleSource}</textarea>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <button type="submit" style="width: 100%" class="btn btn-success">Update</button>
        </form>
    </#if>

    <#if language == "ukr">
        <h1 style="text-align: center">Редагування статті: ${article.articleTitle}</h1>
        <form action="/edit/articles/${article.articleId}" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="articleTitle">Заголовок статті</label>
                <input type="text" required class="form-control"
                       id="articleTitle" name="articleTitle" value="${article.articleTitle}">
            </div>
            <div class="form-group">
                <label for="articleLink">Посилання</label>
                <div class="input-group mb-3">
                    <input type="text" required class="form-control" id="articleLink" name="articleLink" value="${article.articleLink}">
                </div>
            </div>
            <div class="form-group">
                <label for="articleDescription">Детальна інформація</label>
                <textarea required class="form-control" id="articleDescription" name="articleDescription">${article.articleDescription}</textarea>
            </div>
            <div class="form-group">
                <label for="articleSource">Джерело статті</label>
                <textarea required class="form-control" id="articleSource" name="articleSource">${article.articleSource}</textarea>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <button type="submit" style="width: 100%" class="btn btn-success">Оновити</button>
        </form>
    </#if>


</@t.template>