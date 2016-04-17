<%--
  Created by IntelliJ IDEA.
  User: Tomi
  Date: 4/7/16
  Time: 02:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="section-navbar">
    <nav id="navbar" class="navbar navbar-raptor navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbarCollapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand logo" href="/"></a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <ul class="nav navbar-nav">
                    <li>
                        <form class="navbar-form navbar-left" role="search" action="/search">
                            <div class="form-group">
                                <input type="text" class="form-control search-input" name="searchText" placeholder="Search Raptor...">
                            </div>
                            <button type="submit" class="btn btn-default btn-raptor" style="height: 34px; width: 34px;">
                                <img src="/resources/img/icn-search.png" style="height: 18px; margin-left:-4px;"/>
                            </button>
                        </form>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/signup">Sign up</a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
