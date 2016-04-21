<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 
<html>
<jsp:include page="fragments/head.jsp" />
<body>
<jsp:include page="fragments/navbar.jsp" />
<div class="container">
    <div class="col-md-6 col-centered">
        <h2><spring:message code="form.title.signup"/></h2>
        <br/>
        <form:form role="form" modelAttribute= "loginForm" action="/login" method="post">
            <form:label path="username">*<spring:message code="form.username"/>:</form:label>
            <div class="form-group">
            	<div class="input-group">
	                <strong class="input-group-addon" style="border-bottom-left-radius: 20px; border-top-left-radius: 20px;" id="usern">@</strong>
	                <spring:message code="form.username" var="username_placeholder" />
	                <form:input type="text" path="username" class="form-control" placeholder='${username_placeholder}' aria-describedby="usern" id="username"/>
            	</div>
                <form:errors path="username" cssClass="error"/>
            </div>
            <div class="form-group">
                <form:label path="password">*<spring:message code="form.password"/>:</form:label>
                <spring:message code="form.password" var="password_placeholder" />
                <form:input type="password" path="password" placeholder='${password_placeholder}' class="form-control" id="password"/>
            	<form:errors path="password" cssClass="error"/>
            </div>
            <button type="submit" class="btn btn-raptor pull-right">Log In</button>
        </form:form>
    </div>
</div>
</body>
</html>