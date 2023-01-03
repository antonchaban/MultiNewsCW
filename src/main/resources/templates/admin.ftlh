<#import "blocks/template.ftlh" as t>
<@t.template customer language>
    <#if language == "en">
        <h1 style="text-align: center">Admin Panel</h1><hr>
        <table class="table table-hover table-dark">
            <tr>
                <th>Username</th>
                <th>Role</th>
                <th>Delete</th>
                <th>Edit</th>
                <th>More Info</th>
            </tr>
            <#list customers as customer>
                <tr>
                    <th>${customer.username}</th>
                    <th><#list customer.roles as role>${role}</#list></th>
                    <th>
                        <form action="/admin/customer/delete/${customer.customerId}" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}">
                            <input type="submit" value="Delete">
                        </form>
                    </th>
                    <th><a href="/admin/customer/edit/${customer.customerId}">Edit</a></th>
                    <th><a href="/customer/${customer.customerId}">More Info</a></th>
                </tr>
            <#else>
                <h3>No Customers</h3>
            </#list>
        </table>
    </#if>
    <#if language == "uk">
        <h1 style="text-align: center">Панель Адміністартора</h1><hr>
        <table class="table table-hover table-dark">
            <tr>
                <th>Ім'я користувача</th>
                <th>Роль</th>
                <th>Видалити</th>
                <th>Редагувати</th>
                <th>Більше Інформації</th>
            </tr>
            <#list customers as customer>
                <tr>
                    <th>${customer.username}</th>
                    <th><#list customer.roles as role>${role}</#list></th>
                    <th>
                        <form action="/admin/customer/delete/${customer.customerId}" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}">
                            <input type="submit" value="Delete">
                        </form>
                    </th>
                    <th><a href="/admin/customer/edit/${customer.customerId}">Редагувати</a></th>
                    <th><a href="/customer/${customer.customerId}">Більше Інформації</a></th>
                </tr>
            <#else>
                <h3>А нема користувачів</h3>
            </#list>
        </table>
    </#if>
</@t.template>
