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
                                <div class="rawr-action-bar-img img-rerawr img-rerawr-selected" style="float: left;"></div>
                                <span style="padding-left: 5px;"><spring:message code="tweetView.retweetedBy"/> ${tweet.retweetedBy}</span>
                            </div>
                        </div>
                    </c:if>
                    <div class="row" style="padding-bottom: 10px">
                        <div class="col-xs-4 col-sm-2 col-md-2">
                            <img class="profile-picture center-block" src="${tweet.owner.profilePicture}"/>
                        </div>
                        <div class="col-xs-8 col-sm-10 col-md-10">
                            <div class="rawr-top-bar">
                                <a href="/user/${tweet.owner.username}">${tweet.owner.firstName} ${tweet.owner.lastName}</></a>
                                <a href="/user/${tweet.owner.username}" style="color: #9B9B9B;">@${tweet.owner.username} <c:if test="${tweet.owner.verified == true}"><span class="glyphicon glyphicon-ok-sign" style="color:#3C8BED" /></c:if></a>
                                <span style="color: #9B9B9B" class="pull-right hidden-xs">${tweet.timestamp}</span>
                            </div>
                                <span class="message" style="font-size:1.2em;">${tweet.msg}</span>
                                <span style="color: #9B9B9B" class="hidden-sm hidden-md hidden-lg"><br/><br/>${tweet.timestamp}</span>
                            <c:if test="${sessionUser != null}">
                                <div class="rawr-action-bar">
                                    <c:if test="${tweet.owner.id != sessionUser.id}">
                                        <a id="re${tweet.id}" href="#" onclick="return retweet('${tweet.id}')" class="rawr-action-bar-img img-rerawr
                                        <c:if test="${tweet.retweeted}"> img-rerawr-selected</c:if>
                                        " data-toggle="tooltip" data-placement="bottom" title="<spring:message code="tweetView.retweet"/>"></a>
                                    </c:if>
                                    <a id="fav${tweet.id}" href="#" onclick="return favorite('${tweet.id}')" class="rawr-action-bar-img img-fav
                                    <c:if test="${tweet.favorited}">img-fav-selected</c:if>
                                    " data-toggle="tooltip" data-placement="bottom" title="<spring:message code="tweetView.favorite"/>"></a>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
<script>
    $('a').tooltip();
    function favorite(tweetId) {
        var contents = $('#fav' + tweetId);
        contents.tooltip('hide');
        $.post("/actions/favorite",
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
        contents.tooltip('hide');
        $.post("/actions/retweet",
                {'tweetId': tweetId,
                    'retweet': !contents.hasClass('img-rerawr-selected')},
                function (data) {
                    if(data.success == false) toggleClass(contents, 'img-rerawr-selected');
                });
        toggleClass(contents, 'img-rerawr-selected');
        return false;
    }
    function toggleClass(object, className) {
        if(object.hasClass(className)){
            object.removeClass(className);
        } else {
            object.addClass(className);
        }
    }
</script>