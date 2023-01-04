<#import "blocks/template.ftlh" as t>
<@t.template customer language>
    <#if language == "en">
        <h1 style="text-align: center">Editing Article: ${article.articleTitleEn}</h1>
        <form action="/edit/articles/${article.articleId?long?c}" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="articleTitleEn">Article Title</label>
                <input type="text" required class="form-control"
                       id="articleTitleEn" name="articleTitleEn" value="${article.articleTitleEn}">
            </div>
            <div class="form-group">
                <label for="articleLink">Article Link</label>
                <div class="input-group mb-3">
                    <input type="text" required class="form-control" id="articleLink" name="articleLink" value="${article.articleLink}">
                </div>
            </div>
            <div class="form-group">
                <label for="articleDescriptionEn">Article Description</label>
                <textarea required class="form-control" id="articleDescriptionEn" name="articleDescriptionEn">${article.articleDescriptionEn}</textarea>
            </div>
            <div class="form-group">
                <label for="articleSource">Article Source</label>
                <textarea required class="form-control" id="articleSource" name="articleSource">${article.articleSource}</textarea>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <button type="submit" style="width: 100%" class="btn btn-success">Update</button>
        </form>
    </#if>

    <#if language == "uk">
        <h1 style="text-align: center">Редагування статті: ${article.articleTitle}</h1>
        <form action="/edit/articles/${article.articleId?long?c}" method="post" enctype="multipart/form-data">
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