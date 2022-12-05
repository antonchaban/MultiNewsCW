<!DOCTYPE html>
<html>
<head>
    <title>MultiNews</title>
</head>
<body>
<h1>MultiNews</h1>
<hr>
<h4>Admin Panel:</h4>
<table border="1">
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
</body>
</html>