<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
<c:choose>
    <c:when test="${user != null}">
        <c:set var="pageTitle" value="Hi, ${user.username}" scope="request"/>
    </c:when>
    <c:otherwise>
        <c:set var="pageTitle" value=":(" scope="request"/>
    </c:otherwise>
</c:choose>
<jsp:include page="fragments/head.jsp"/>
<body>
<jsp:include page="fragments/navbar.jsp" />
<div class="section-body">
    <div class="container">
        <c:choose>
        <c:when test="${user != null}">
            <div class="row">
                <div class="col-md-3 col-lg-3">
                    <div class="section-profile">
                        <c:set var="user" value="${user}" scope="request"/>
                        <c:set var="userInfo" value="${userInfo}" scope="request"/>
                        <jsp:include page="fragments/profileBox.jsp"/>
                    </div>
                </div>
                <div class="col-md-7 col-lg-7">
                    <div class="section-timeline">
                        <c:set var="username" value="${user.username}" scope="request"/>
                        <jsp:include page="fragments/tweetBox.jsp"/>
                        <c:set var="tweetList" value="${tweetList}" scope="request"/>
                        <c:set var="tweetListHeader" value="${header}" scope="request"/>
                        <jsp:include page="fragments/tweetList.jsp"/>
                    </div>
                </div>
                <div class="col-md-2 col-lg-2">
                    <div class="section-trends">
                        <c:set var="trendsList" value="${trendsList}" scope="request"/>
                        <jsp:include page="fragments/trendingBox.jsp"/>
                    </div>
                </div>
            </div>
        </c:when>
            <c:otherwise>
                <c:set var="errorMessage" value="Ups! The requested user doesn't exist!" scope="request"/>
                <jsp:include page="fragments/errorPage.jsp"/>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>