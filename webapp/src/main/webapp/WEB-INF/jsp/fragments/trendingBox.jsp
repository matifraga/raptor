<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="panel panel-raptor panel-trends">
    <h5><spring:message code="trendingBox.title"/></h5>
    <ul>
        <c:forEach items="${requestScope.trendsList}" var="trend">
            <li><a href="/search?searchText=%23${trend}">#${trend}</a></li>
        </c:forEach>
    </ul>
</div>