<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- Params needed: user: User--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="panel-profile">
    <div class="panel panel-raptor panel-profile-info">
        <ul>
            <li>
                <a href="user/${requestScope.user.username}"
                   <c:if test="${requestScope.following == 2}">data-toggle="tooltip" data-placement="bottom" title="<spring:message code="profileBox.gravatarTip"/>"</c:if>>
                    <img class="profile-picture" src=${user.profilePicture}/></a>
            </li>
            <li style="font-size: 1.2em;"><a href="./user/${requestScope.user.username}">${requestScope.user.firstName} ${requestScope.user.lastName}</a></li>
            <li><a href="./user/${requestScope.user.username}">@${requestScope.user.username} <c:if test="${requestScope.user.verified == true}"><span class="glyphicon glyphicon-ok-sign" /></c:if></a></li>
            <c:if test="${sessionUser != null && sessionUser.username != requestScope.user.username}">
                <c:choose>
                    <c:when test="${requestScope.user.following == false}">
                        <form action="user/${requestScope.user.username}/follow" method="post">
                            <li><button class="btn btn-raptor" style="margin-top: 10px;"><spring:message code="profileBox.follow"/></button></li>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form action="user/${requestScope.user.username}/unfollow" method="post">
                            <li><button class="btn btn-raptor" style="margin-top: 10px;"><spring:message code="profileBox.unfollow"/></button></li>
                        </form>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </ul>
    </div>
    <div class="panel panel-raptor panel-profile-stats">
        <c:if test="${requestScope.user.followersCount != null}">
            <a><span class="number">${requestScope.user.followingCount}</span><br/><spring:message code="profileBox.followers"/></a>
        </c:if>
        <c:if test="${requestScope.user.tweetsCount != null}">
            <a><span class="number">${requestScope.user.tweetsCount}</span><br/><spring:message code="profileBox.tweets"/></a>
        </c:if>
        <c:if test="${requestScope.user.followingCount != null}">
            <a><span class="number">${requestScope.user.followingCount}</span><br/><spring:message code="profileBox.following"/></a>
        </c:if>
    </div>
    <script>$(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })</script>
</div>

