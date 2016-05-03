<%-- Params needed: username: String --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="panel panel-raptor">
    <div class="section-posting">
        <p>${request.contextPath}</p>
        <form class="form-group" action="/tweetAction" method="post">
        	<spring:message code="tweetBox.placeholder" var="tweet_placeholder"/>
            <textarea placeholder='${tweet_placeholder}' name="message" class="form-control" style="margin-bottom:10px;"></textarea>
            <button type="submit" class="btn btn-raptor pull-right">Rawr</button>
            <br/>
        </form>
    </div>
</div>
