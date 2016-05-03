<%--
  Created by IntelliJ IDEA.
  User: Tomi
  Date: 4/7/16
  Time: 02:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="section-navbar">
    <nav id="navbar" class="navbar navbar-raptor navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbarCollapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand logo" href="/"></a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <ul class="nav navbar-nav">
                    <li>
                        <form class="navbar-form navbar-left" role="search" action="/search">
                            <div class="form-group">
                            	<spring:message code="navbar.search" var="search_placeholder" />
                                <input type="text" class="form-control search-input" name="searchText" placeholder='${search_placeholder}...'>
                            </div>
                            <button type="submit" class="btn btn-default btn-raptor" style="height: 34px; width: 34px;">
                                <img src="/resources/img/icn-search.png" style="height: 18px; margin-left:-4px;"/>
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
                                        <form:form role="form" modelAttribute= "loginForm" action="/login" method="post">
                                        <div class="form-group">
                                            <spring:message code="form.username" var="username_placeholder" />
                                            <form:input type="text" path="username" class="form-control search-input" placeholder='${username_placeholder}' aria-describedby="usern" id="username" style="margin-bottom:10px; margin-right: 0px;"/>
                                            <form:errors path="username" cssClass="error"/>
                                        </div>
                                        <div class="form-group">
                                            <spring:message code="form.password" var="password_placeholder" />
                                            <form:input type="password" path="password" placeholder='${password_placeholder}' class="form-control search-input" id="password" style="margin-bottom:10px; margin-right: 0px;"/>
                                            <form:errors path="password" cssClass="error"/>
                                        </div>
                                        <button class="btn btn-raptor pull-right" style="margin-bottom:15px;"><spring:message code="form.login"/></button>
                                        <div class="form-group" style="text-align: center;">
                                            <a href="/signup" style="text-align: center;"><spring:message code="navbar.no_account"/></a>
                                        </div>
                                        </form:form>
                                    </div>
                                    <br/>
                                </div>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="dropdown"><a href="/user/${navbarViewUser.username}" style="color: #4A4A4A;">${navbarViewUser.firstName} <c:if test="${navbarViewUser.verified == true}"><span class="glyphicon glyphicon-ok-sign" style="color:#3C8BED" /></c:if><span style="color: #4A4A4A;" class="caret"/></a>
                                <ul class="dropdown-menu">
                                    <li><a href="/logout" style="color: #4A4A4A; text-align: center">Log out</a></li>
                                </ul>
                            </li>
                            <li><img src=${navbarViewUser.profilePicture} class="mini-pic"/></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>
</div>
