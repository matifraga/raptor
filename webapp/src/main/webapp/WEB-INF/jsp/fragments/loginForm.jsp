<%--
  Created by IntelliJ IDEA.
  User: Tomi
  Date: 5/10/16
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="navbar-form" style="margin-bottom: 0px;">
    <form:form role="form" modelAttribute= "loginForm" action="login" method="post">
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
            <a href="signup" style="text-align: center;"><spring:message code="navbar.no_account"/></a>
        </div>
    </form:form>
</div>