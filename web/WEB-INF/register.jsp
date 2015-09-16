<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test</title>
    </head>
    <body>
        <h1>Test</h1>
        <form action="langue" method="post">
            <table cellpadding="3pt">
                <tr>
                    <td>Code :</td>
                    <td><input type="text" name="code" size="30" /></td>
                </tr>
                <tr>
                    <td>Libelle :</td>
                    <td><input type="text" name="libelle" size="30" /></td>
                </tr>
                <tr>
                    <td>Type rubrique :</td>
                    <td><input type="text" name="typerubrique" size="30" /></td>
                </tr>
                <tr>
                    <td>Code rubrique 1 :</td>
                    <td><input type="text" name="rubrique1" size="30" /></td>
                </tr>
                <tr>
                    <td>Code rubrique 2 :</td>
                    <td><input type="text" name="rubrique2" size="30" /></td>
                </tr>
            </table>
            <p />
            <input type="submit" value="Save" />
        </form>
    </body>
</html>