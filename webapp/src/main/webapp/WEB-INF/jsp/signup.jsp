<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
 
<html>
<jsp:include page="fragments/head.jsp" />
<body>
<jsp:include page="fragments/navbar.jsp" />
<div class="container">
    <div class="col-md-6 col-centered">
        <h2>Create your Raptor account</h2>
        <br/>
        <form:form role="form" modelAttribute= "signUpForm" action="/signup" method="post">
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <form:label path="firstName">First name:</form:label>
                        <form:input type="text" path="firstName" placeholder="First name" class="form-control" id="firstName"/>
                    	<form:errors path="firstName" cssClass="error"/>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <form:label path="lastName">Last name:</form:label>
                        <form:input type="text" path="lastName" placeholder="Last name" class="form-control" id="lastName"/>
                    	<form:errors path="lastName" cssClass="error"/>
                    </div>
                </div>
            </div>
            <form:label path="username">Username:</form:label>
            <div class="form-group input-group">
                <strong class="input-group-addon" style="border-bottom-left-radius: 20px; border-top-left-radius: 20px;" id="usern">@</strong>
                <form:input type="text" path="username" class="form-control" placeholder="Username" aria-describedby="usern" id="username"/>
            	<form:errors path="username" cssClass="error"/>
            </div>
            <div class="form-group">
                <form:label path="email">Email address:</form:label>
                <form:input type="text" path="email" placeholder="Email" class="form-control" id="email"/>
            	<form:errors path="email" cssClass="error"/>
            </div>
            <div class="form-group">
                <form:label path="password">Password:</form:label>
                <form:input type="password" path="password" placeholder="Password" class="form-control" id="password"/>
            	<form:errors path="password" cssClass="error"/>
            </div>
            <div class="checkbox">
                <form:label path="terms"><form:checkbox path="terms"/> I accept the terms and conditions</form:label>
            	<form:errors path="terms" cssClass="error"/>
            </div>
            <button type="submit" class="btn btn-raptor pull-right">Sign up</button>
        </form:form>
    </div>
</div>
</body>
</html>