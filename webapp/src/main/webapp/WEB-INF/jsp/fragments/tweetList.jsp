<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- Params needed: tweetList: List, tweetListTitle: String || header: List<Map<String, Object>> --%>
<div class="panel panel-raptor">
    <div class="panel-heading timeline-heading">
        <c:choose>
            <c:when test="${requestScope.header == null}">
                <a class="active" href="#">${requestScope.tweetListTitle}</a>
            </c:when>
            <c:otherwise>
                <c:forEach items="${requestScope.header}" var="item">
                	<spring:message code="${item['titleCode']}" var="headerTitle" />
                    <a <c:if test="${item['active']}">class="active"</c:if> href="${item['link']}">
                            ${headerTitle}</a>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
    <c:choose>
        <c:when test="${empty requestScope.tweetList}">
            <div class="panel panel-rawr" style="padding-top: 15px;">
                <div class="row">
                    <div class="col-xs-8 col-sm-10 col-md-10">
                        <span><spring:message code="tweetList.noTweets"/></span>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${requestScope.tweetList}" var="tweet">
                <div class="panel panel-rawr">
                    <c:if test="${tweet.retweetedBy != null}">
                        <div class="col-md-offset-2 col-sm-offset-2">
                            <div class="rerawr-info-bar">
                                <div class="rawr-action-bar-img img-rerawr img-rerawr-small"></div>
                                <span><spring:message code="tweetView.retweetedBy"/> ${tweet.retweetedBy}</span>
                            </div>
                        </div>
                    </c:if>
                    <div class="row" style="padding-bottom: 10px">
                        <div class="col-xs-4 col-sm-2 col-md-2">
                           <a href="user/${tweet.owner.username}"><img class="profile-picture center-block" src="${tweet.owner.profilePicture}"/></a>
                        </div>
                        <div class="col-xs-8 col-sm-10 col-md-10">
                            <div class="rawr-top-bar">
                                <a href="user/${tweet.owner.username}">${tweet.owner.firstName} ${tweet.owner.lastName}</></a>
                                <a href="user/${tweet.owner.username}" style="color: #9B9B9B;">@${tweet.owner.username} <c:if test="${tweet.owner.verified == true}"><span class="glyphicon glyphicon-ok-sign" style="color:#3C8BED" /></c:if></a>
                                <span style="color: #9B9B9B" class="pull-right hidden-xs">${tweet.timestamp}</span>
                            </div>
                                <span class="message" style="font-size:1.2em;">${tweet.msg}</span>
                                <span style="color: #9B9B9B" class="hidden-sm hidden-md hidden-lg"><br/><br/>${tweet.timestamp}</span>
                            <c:if test="${sessionUser != null}">
                                <div class="rawr-action-bar">
                                    <c:if test="${tweet.owner.id != sessionUser.id}">
                                        <a id="re${tweet.id}" href="#" onclick="return retweet('${tweet.id}')" class="rawr-action-bar-img img-rerawr
                                        <c:if test="${tweet.retweeted}"> img-rerawr-selected</c:if>
                                        "   <c:if test="${tweet.countRetweets >= 0}"> data-count="${tweet.countRetweets}"</c:if>>
                                            <c:if test="${tweet.countRetweets > 0}">${tweet.countRetweets}</c:if>
                                        </a>
                                    </c:if>
                                    <a id="fav${tweet.id}" href="#" onclick="return favorite('${tweet.id}')" class="rawr-action-bar-img img-fav
                                    <c:if test="${tweet.favorited}">img-fav-selected</c:if>
                                    "   <c:if test="${tweet.countFavorites >= 0}"> data-count="${tweet.countFavorites}"</c:if>>
                                        <c:if test="${tweet.countFavorites > 0}">${tweet.countFavorites}</c:if>
                                    </a>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    <c:if test="${pageInfo != null}">
        <div class="panel panel-rawr no-padding">
            <div class="page-buttons right-separator">
                <c:if test="${pageInfo['hasPrevious'] == true}">
                    <a href="${pageInfo['previous']['link']}"><spring:message code="${pageInfo['previous']['titleCode']}"/></a>
                </c:if>
            </div>
            <div class="page-buttons">
                <c:if test="${pageInfo['hasNext'] == true}">
                    <a href="${pageInfo['next']['link']}"><spring:message code="${pageInfo['next']['titleCode']}"/></a>
                </c:if>
            </div>
        </div>
    </c:if>
</div>
<script>
    function favorite(tweetId) {
        var contents = $('#fav' + tweetId);
        $.post("actions/favorite",
                {'tweetId': tweetId,
                 'favorite': !contents.hasClass('img-fav-selected')},
                function (data) {
                    if(data.success == false) toggleClass(contents, 'img-fav-selected');
                });
        toggleClass(contents, 'img-fav-selected');
        return false;
    }
    function retweet(tweetId) {
        var contents = $('#re' + tweetId);
        $.post("actions/retweet",
                {'tweetId': tweetId,
                    'retweet': !contents.hasClass('img-rerawr-selected')},
                function (data) {
                    if(data.success == false) toggleClass(contents, 'img-rerawr-selected');
                });
        toggleClass(contents, 'img-rerawr-selected');
        return false;
    }
    function toggleClass(object, className) {
        var count = object.data("count");
        console.log(object);
        if(object.hasClass(className)){
            object.removeClass(className);
            object.data("count", count-1);
            if(count-1>0) object.text(count-1);
            else object.text("");
        } else {
            object.addClass(className);
            object.data("count", count+1);
            object.text(count+1);
        }
    }
</script>