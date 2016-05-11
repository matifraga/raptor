<%--
  Created by IntelliJ IDEA.
  User: Tomi
  Date: 5/10/16
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionUser != null}">
    <div class="visible-xs visible-sm">
        <div class="panel panel-raptor panel-trends">
            <a href="logout">Log out</a>
        </div>
    </div>
</c:if>
