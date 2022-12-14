<#import "blocks/template.ftlh" as t>
<@t.template mycustomer language>
    <#if language == "eng">
        <h1 style="text-align: center">Editing ${customer.username}</h1><hr>
        <form action="/admin/customer/edit" method="post">
            <#list roles as role>
                <div>
                    <label><input type="radio" name="role" value="${role}" ${customer.roles?seq_contains(role)?string("checked", "")}>${role}</label>
                </div>
            </#list>
            <input type="hidden" value="${customer.customerId}" name="customerId">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button type="submit" class="btn btn-dark">Save</button>
        </form>
    </#if>
    <#if language == "ukr">
        <h1 style="text-align: center">Редагування користувача ${customer.username}</h1><hr>
        <form action="/admin/customer/edit" method="post">
            <#list roles as role>
                <div>
                    <label><input type="radio" name="role" value="${role}" ${customer.roles?seq_contains(role)?string("checked", "")}>${role}</label>
                </div>
            </#list>
            <input type="hidden" value="${customer.customerId}" name="customerId">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button type="submit" class="btn btn-dark">Зберегти</button>
        </form>
    </#if>


</@t.template>