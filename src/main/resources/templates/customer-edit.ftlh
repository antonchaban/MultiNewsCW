<!DOCTYPE html>
<html>
<head>
    <title>MultiNews</title>
</head>
<body>
<h1>MultiNews</h1><hr>
<h3>Editing ${customer.username}</h3>
<form action="/admin/customer/edit" method="post">
    <#list roles as role>
        <div>
            <label><input type="radio" name="role" value="${role}" ${customer.roles?seq_contains(role)?string("checked", "")}>${role}</label>
        </div>
    </#list>
    <input type="hidden" value="${customer.customerId}" name="customerId">
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <button type="submit">Save</button>
</form>
</body>
</html>