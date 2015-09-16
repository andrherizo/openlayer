<%-- 
    Document   : geometry
    Created on : 14 sept. 2015, 17:45:51
    Author     : USER
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gis</title>
    </head>
    <body>
        <h1>Hello GIS!</h1>
         <c:forEach var="g" items="${geom}">
            <div><c:out value="${g.name}|${g.geom}|${g.geomJson}" /></div>
        </c:forEach>
    </body>
</html>
