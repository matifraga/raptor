<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<spring:message var="searchResultsTitle" code="searchResults.title"/>
<c:set var="pageTitle" value='${searchResultsTitle}' scope="request"/>
<jsp:include page="fragments/head.jsp"/>
<body>
<jsp:include page="fragments/navbar.jsp" />
<div class="container">
		<c:choose>
			<c:when test="${searchType=='tweetSearch'}">
				<c:set var="tweetList" value="${resultList}" scope="request"/>
				<spring:message var = "searchResultNumber" code="searchResults.number" arguments="${number};${searchText}" htmlEscape="false" argumentSeparator=";"/>
				<c:set var="tweetListTitle" value='${searchResultNumber}' scope="request"/>
				<jsp:include page="fragments/tweetList.jsp"/>
			</c:when>
			<c:when test="${searchType=='userSearch'}">
				<c:set var="userList" value="${resultList}" scope="request"/>
				<spring:message var = "searchResultNumber" code="searchResults.number" arguments="${number};${searchText}" htmlEscape="false" argumentSeparator=";"/>
				<c:set var="userListTitle" value='${searchResultNumber}' scope="request"/>
				<jsp:include page="fragments/userList.jsp"/>
			</c:when>
			<c:otherwise>
				<spring:message var="searchResultsError" code="searchResults.error"/>
				<c:set var="errorMessage" value= '${searchResultsError}' scope="request"/>
				<jsp:include page="fragments/errorPage.jsp"/>
			</c:otherwise>
		</c:choose>
</div>
</body>
</html>