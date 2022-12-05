<#import "blocks/template.ftlh" as t>
<@t.template customer>
    `<h1 style="text-align: center">Admin Panel</h1><hr>
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
</@t.template>
