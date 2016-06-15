<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="section-navbar">
    <nav id="navbar" class="navbar navbar-raptor navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand logo" href=""></a>
                <div class="visible-xs">
                    <c:choose>
                        <c:when test="${navbarViewUser == null}"><button class="mini-pic-link pull-right navbar-text" data-toggle="collapse" data-target="#loginCollapse"><spring:message code="navbar.login"/><span style="color: #4A4A4A;" class="caret"/></button></c:when>
                        <c:otherwise><a class="mini-pic-link pull-right" href="user/${navbarViewUser.username}" style="margin-right: 15px;"><img src=${navbarViewUser.profilePicture} class="mini-pic"/></a></c:otherwise>
                    </c:choose>
                    <button class="mini-pic-link navbar-text pull-right search-blue" data-toggle="collapse" data-target="#searchCollapse"></button>
                </div>
            </div>
            <div class="collapse " id="loginCollapse">
                <div class="visible-xs">
                <jsp:include page="loginForm.jsp"></jsp:include>
                </div>
            </div>
            <div class="collapse " id="searchCollapse">
                <div class="visible-xs">
                    <form class="navbar-form navbar-left" role="search" action="search">
                        <div class="form-group">
                            <spring:message code="navbar.search" var="search_placeholder" />
                            <input type="text" class="form-control search-input" name="searchText" placeholder='${search_placeholder}...'>
                            <button type="submit" class="btn btn-default btn-raptor" style="height: 34px; width: 34px;">
                                <img src="resources/img/icn-search.png" style="height: 18px; margin-left:-4px;"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="hidden-xs">
                <ul class="nav navbar-nav">
                    <li>
                        <form class="navbar-form navbar-left" role="search" action="search">
                            <div class="form-group">
                            	<spring:message code="navbar.search" var="search_placeholder" />
                                <input type="text" class="form-control search-input" name="searchText" placeholder='${search_placeholder}...'>
                            </div>
                            <button type="submit" class="btn btn-default btn-raptor" style="height: 34px; width: 34px;">
                                <img src="resources/img/icn-search.png" style="height: 18px; margin-left:-4px;"/>
                            </button>
                        </form>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right" style="margin-right: 6px;">
                    <c:choose>
                        <c:when test="${navbarViewUser == null}">
                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="true"><spring:message code="navbar.login"/><span class="caret"></span></a>
                                <div class="dropdown-menu">
                                    <div class="navbar-form" style="margin-bottom: 0px;">
                                        <jsp:include page="loginForm.jsp"></jsp:include>
                                    </div>
                                    <br/>
                                </div>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="hidden-xs hidden-sm">
                                <form class="navbar-form" action="actions/post" method="post">
                                    <div class="form-group">
                                        <spring:message code="tweetBox.placeholder" var="tweet_placeholder"/>
                                        <input autocomplete="off" type="text" class="form-control search-input expand" name="message" placeholder='${tweet_placeholder}'>
                                    </div>
                                    <button type="submit" class="btn btn-default btn-raptor">
                                        Rawr
                                    </button>
                                </form>
                            </li>
                            <jsp:include page="notificationPanel.jsp"></jsp:include>
                            <li class="dropdown"><a href="user/${navbarViewUser.username}" style="color: #4A4A4A;">${navbarViewUser.firstName} <c:if test="${navbarViewUser.verified == true}"><span class="glyphicon glyphicon-ok-sign" style="color:#3C8BED" /></c:if><span style="color: #4A4A4A;" class="caret hidden-sm"/></a>
                                <ul class="dropdown-menu">
                                    <li><a href="logout" style="color: #4A4A4A; text-align: center">Log out</a></li>
                                </ul>
                            </li>
                            <li><a class="mini-pic-link" href="user/${navbarViewUser.username}"><img src=${navbarViewUser.profilePicture} class="mini-pic"/></a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>
</div>
