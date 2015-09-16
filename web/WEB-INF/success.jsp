
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Réponse</title>
    </head>
    <body>
        <h1>Langues</h1>
        <c:forEach var="langue" items="${langues}">
            <div><c:out value="${langue.codeLangue}|${langue.libelle}" /></div>
        </c:forEach>
        <hr>
        <h1>Types rubrique</h1>
        <c:forEach var="type" items="${typeRubriques}">
            <div><c:out value="L. ${type.libelle}" /></div>
            <ul>
                <c:forEach var="rubriqueByType" items="${rubriquesPerType[type.idTypeRubriqueInfo]}">
                    <li><c:out value="R. ${rubriqueByType.code}" /></li>
                </c:forEach>
            </ul>
        </c:forEach>
        <hr>
        <h1>Rubriques</h1>
        <c:forEach var="rubrique" items="${rubriques}">
            <div><c:out value="R. ${rubrique.code}" /></div>
        </c:forEach>
    </body>
</html>
