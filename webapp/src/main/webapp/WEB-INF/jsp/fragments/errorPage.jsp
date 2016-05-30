<%-- Params needed: errorMessage: String --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="section-not-found">
    <div class="container-fluid">
        <div class="col-centered col-md-8" style="text-align:center;">
            <div class="row">
                <h2 style="margin-bottom: 50px;">${requestScope.errorMessage}</h2>
                <img class="raptor-logo" src="resources/img/raptor-angry.png"/>
            </div>
        </div>
    </div>
</div>