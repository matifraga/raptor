<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- Params needed: user: User--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="panel-profile">
    <div class="panel panel-raptor panel-profile-info">
        <ul>
            <li>
                <a href="/user/${requestScope.user.username}"><img class="profile-picture" src=${user.profilePicture}/></a>
            </li>
            <li style="font-size: 1.2em;"><a href="../user/${requestScope.user.username}">${requestScope.user.firstName} ${requestScope.user.lastName}</a></li>
            <li><a href="../user/${requestScope.user.username}">@${requestScope.user.username} <c:if test="${requestScope.user.verified == true}"><span class="glyphicon glyphicon-ok-sign" /></c:if></a></li>
            <c:choose>
                <c:when test="${requestScope.following == 0}">
                    <form action="/user/${requestScope.user.username}/follow" method="post">
                        <li><button class="btn btn-raptor" style="margin-top: 10px;"><spring:message code="profileBox.follow"/></button></li>
                    </form>
                </c:when>
                <c:when test="${requestScope.following == 1}">
                    <form action="/user/${requestScope.user.username}/unfollow" method="post">
                        <li><button class="btn btn-raptor" style="margin-top: 10px;"><spring:message code="profileBox.unfollow"/></button></li>
                    </form>
                </c:when>
                <c:otherwise></c:otherwise>
            </c:choose>
        </ul>
    </div>
    <c:if test="${requestScope.userInfo != null}">
    <div class="panel panel-raptor panel-profile-stats">
        <a><span class="number">${requestScope.userInfo["followers_count"]}</span><br/><spring:message code="profileBox.followers"/></a>
        <a><span class="number">${requestScope.userInfo["tweets_count"]}</span><br/><spring:message code="profileBox.tweets"/></a>
        <a><span class="number">${requestScope.userInfo["following_count"]}</span><br/><spring:message code="profileBox.following"/></a>
    </div>
    </c:if>
</div>

