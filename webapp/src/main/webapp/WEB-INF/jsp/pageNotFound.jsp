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
	<spring:message var="pageNotFoundMessage" code="pageNotFound.message"/>
	<c:set var="errorMessage" value= '${pageNotFoundMessage}' scope="request"/>
	<jsp:include page="fragments/errorPage.jsp"/>
</div>
</body>
</html>