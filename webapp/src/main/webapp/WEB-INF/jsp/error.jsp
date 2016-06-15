<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<spring:message var="pageNotFoundTitle" code="pageNotFound.title"/>
<c:set var="pageTitle" value='${pageNotFoundTitle}' scope="request"/>
<jsp:include page="fragments/head.jsp"/>
<body>
<jsp:include page="fragments/navbar.jsp" />
<div class="container">
	<spring:message var="upsMessage" code="error.message"/>
	<div class="section-not-found">
    	<div class="container-fluid">
        	<div class="col-centered col-md-8" style="text-align:center;">
            	<div class="row">
                	<h2 style="margin-bottom: 50px;">${upsMessage}</h2>
                	<img class="raptor-logo" src="resources/img/raptor-sad.png"/>
            	</div>
        	</div>
    	</div>
	</div>
</div>
</body>
</html>