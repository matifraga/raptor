<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- Params needed: user: User--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel-profile">
    <div class="panel panel-raptor panel-profile-info">
        <ul>
            <li>
                <img class="profile-picture" src="/resources/img/default-pic.png"/>
            </li>
            <li style="font-size: 1.2em;"><a href="../user/${requestScope.user.username}">${requestScope.user.firstName} ${requestScope.user.lastName}</a></li>
            <li><a href="../user/${requestScope.user.username}">@${requestScope.user.username}</a></li>
        </ul>
    </div>
    <c:if test="${requestScope.userInfo != null}">
    <div class="panel panel-raptor panel-profile-stats">
        <a><span class="number">${requestScope.userInfo["followers"]}</span><br/><spring:message code="profileBox.followers"/></a>
        <a><span class="number">${requestScope.userInfo["tweets"]}</span><br/><spring:message code="profileBox.tweets"/></a>
        <a><span class="number">${requestScope.userInfo["following"]}</span><br/><spring:message code="profileBox.following"/></a>
    </div>
    </c:if>
</div>

