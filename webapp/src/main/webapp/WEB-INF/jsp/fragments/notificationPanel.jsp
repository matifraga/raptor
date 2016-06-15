<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tomi
  Date: 6/11/16
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li class="dropdown">
    <a class="dropdown-toggle glyphicon glyphicon-bell notifications-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="true"><span class="caret"></span></a>
    <div class="dropdown-menu panel-notifications">
        <c:set var="notificationList" value= '${notifications}' scope="request"/>
        <c:forEach items="${notificationList}" var="noti" varStatus="loopStatus">
            <c:choose>
                <c:when test="${loopStatus.last}"><div class="panel panel-rawr panel-notifications no-line"></c:when>
                <c:otherwise><div class="panel panel-rawr panel-notifications"></c:otherwise>
            </c:choose>
                <div class="row" style="padding-bottom: 10px">
                    <div class="col-xs-4 col-sm-2 col-md-2">
                        <img class="profile-picture center-block notifications-pic" src="${noti.from.profilePicture}"/>
                    </div>
                    <div class="col-xs-8 col-sm-10 col-md-10">
                        <div class="rawr-top-bar">
                            <p>${noti.message}</p>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</li>
