<%--
  Created by IntelliJ IDEA.
  User: Daniel
  Date: 12/5/16
  Time: 2:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Login</title>
</head>
<body>
Please sign in:
<form action="/mvc_cust/checkPassword" method = "post" >
    <input type="text" placeholder="Name">
    <input type="password" placeholder="password" name="password">
    <button type="submit">Submit</button>
</form>
<a href="/mvc_cust/viewAllGuests">Back to Guest View</a>
</body>
</html>
