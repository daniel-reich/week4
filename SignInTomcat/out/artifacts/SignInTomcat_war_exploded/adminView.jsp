<%--
  Created by IntelliJ IDEA.
  User: Daniel
  Date: 12/5/16
  Time: 2:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Guests</title>
</head>
<body>
Admin View <br>
Check in a guest:<br>

<form action="/mvc_cust/addGuestAdmin" method = "post" >
    <input type="text" placeholder="First name" name="firstName" width="200"/>
    <input type="text" placeholder="Last name" name="lastName" width="200"/>
    <input type="text" placeholder="Comments" name="comment" width="1000"/>
    <button type="submit">Check in</button>

</form>



<table>
    <tr>
        <th>Arrival</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Comments</th>
        <th>Status</th>
        <th>Departure</th>
    </tr>
    <c:forEach var="guest" items="${guests}">
        <tr>
            <td><c:out value="${guest.inDate}"/></td>
            <td><c:out value="${guest.firstName}"/></td>
            <td><c:out value="${guest.lastName}"/></td>
            <td><c:out value="${guest.comment}"/></td>

            <td>
                <c:choose>
                    <c:when test="${guest.signedOut}">
                        Checked out
                    </c:when>

                    <c:otherwise>
                        <form action = "/mvc_cust/signOut" method = "post">
                            Checked in
                            <button type="submit">Check out</button>
                            <input type="hidden" name="guestId" value="<c:out value="${guest.guestId}"/>" />
                        </form>
                    </c:otherwise>
                </c:choose>
            </td>
            <td><c:out value="${guest.outDate}"/></td>
        </tr>
    </c:forEach>
</table>

<a href="/mvc_cust/viewAllGuests">Back to Guest View</a>


</body>
</html>
