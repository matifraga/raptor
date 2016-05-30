<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${pageInfo != null}">
    <c:if test="${(pageInfo['hasPrevious'] == true || pageInfo['hasNext'] == true)}">
        <div class="panel panel-rawr no-padding">
            <div class="page-info right-separator">
                <c:if test="${pageInfo['hasPrevious'] == true}">
                    <a href="${pageInfo['previous']['link']}" title="<spring:message code="${pageInfo['previous']['titleCode']}"/>" class="previous-page-button"></a>
                </c:if>
            </div>
            <div class="page-info">
                <c:if test="${pageInfo['hasNext'] == true}">
                    <a href="${pageInfo['next']['link']}" title="<spring:message code="${pageInfo['next']['titleCode']}"/>" class="next-page-button"></a>
                </c:if>
            </div>
        </div>
    </c:if>
</c:if>