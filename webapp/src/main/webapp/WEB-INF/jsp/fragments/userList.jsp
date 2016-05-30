<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="panel panel-raptor">
    <div class="panel-heading timeline-heading">
        <c:choose>
            <c:when test="${requestScope.header == null}">
                <a class="active" href="#">${requestScope.userListTitle}</a>
            </c:when>
            <c:otherwise>
                <c:forEach items="${requestScope.header}" var="item">
                    <spring:message code="${item['titleCode']}" var="headerTitle" />
                    <a <c:if test="${item['active']}">class="active"</c:if> href="${item['link']}">
                            ${headerTitle}</a>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
    <c:choose>
        <c:when test="${empty requestScope.userList}">
            <div class="panel panel-rawr" style="padding-top: 15px;">
                <div class="row" style="margin-bottom: 50px;">
                    <spring:message var="searchResultsError" code="userList.noUsers"/>
                    <c:set var="errorMessage" value= '${searchResultsError}' scope="request"/>
                    <jsp:include page="errorPage.jsp"/>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${requestScope.userList}" var="user">
                <div class="panel panel-rawr">
                    <div class="row" style="padding-bottom: 10px">
                        <div class="col-xs-4 col-sm-2 col-md-2">
                            <a href="user/${user.username}"><img class="profile-picture center-block" src="${user.profilePicture}"/></a>
                        </div>
                        <div class="col-xs-8 col-sm-10 col-md-10">
                            <div class="rawr-top-bar">
                                <a href="user/${user.username}">${user.firstName} ${user.lastName}</></a>
                                <a href="user/${user.username}" style="color: #9DAFBB;">@${user.username} <c:if test="${user.verified == true}"><span class="glyphicon glyphicon-ok-sign" style="color:#3C8BED" /></c:if></a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <jsp:include page="pageControls.jsp"></jsp:include>
</div>