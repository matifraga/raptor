<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<c:set var="pageTitle" value="Feed" scope="request"/>
<jsp:include page="fragments/head.jsp"/>
<body>
<jsp:include page="fragments/navbar.jsp" />
<div class="container">
    <div class="row">
        <div class="section-timeline">
            <div class="col-md-8 col-md-offset-2 col-sm-9">
                <div class="visible-xs visible-sm">
                    <jsp:include page="fragments/tweetBox.jsp"></jsp:include>
                </div>
                <jsp:include page="fragments/tweetList.jsp"/>
            </div>
        </div>
        <div class="section-trends">
            <div class="col-md-2 col-sm-3">
                <jsp:include page="fragments/trendingBox.jsp"/>
            </div>
        </div>
    </div>
    <jsp:include page="fragments/footer.jsp"></jsp:include>
</div>
</body>
</html>