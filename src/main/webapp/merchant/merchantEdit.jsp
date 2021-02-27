<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Merchant Edit</title>

    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <link href="${contextPath}/resources/assets/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container-fluid">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <div class="row">
            <nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar">
                <div class="position-sticky pt-3">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="${contextPath}/welcome">
                                <span data-feather="home"></span>
                                Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${contextPath}/transaction/transactionList">
                                <span data-feather="file"></span>
                                Transactions
                            </a>
                        </li>
                        <c:if test="${pageContext.request.isUserInRole(MerchantRole.ADMIN)}">
                            <li class="nav-item">
                                <a class="nav-link" href="${contextPath}/merchant/merchantList">
                                    <span data-feather="users"></span>
                                    Merchants
                                </a>
                            </li>
                        </c:if>
                        <li class="nav-item">
                            <form id="logoutForm" method="POST" action="${contextPath}/logout">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                            <a onclick="document.forms['logoutForm'].submit()" class="sign-out">Sign out</a>
                        </li>
                    </ul>
                </div>
            </nav>

            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <h2 class="form-heading">Merchant Edit</h2>

                <form:form method="POST" modelAttribute="merchant" class="form-signin">
                    <spring:bind path="username">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="username" class="form-control" placeholder="Username"
                                        autofocus="true"></form:input>
                            <form:errors path="username"></form:errors>
                        </div>
                    </spring:bind>

                    <spring:bind path="email">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="email" class="form-control" placeholder="Email"
                                        autofocus="true"></form:input>
                            <form:errors path="email"></form:errors>
                        </div>
                    </spring:bind>

                    <spring:bind path="name">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="name" class="form-control" placeholder="Name"></form:input>
                            <form:errors path="name"></form:errors>
                        </div>
                    </spring:bind>

                    <spring:bind path="description">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input type="text" path="description" class="form-control"
                                        placeholder="Description"></form:input>
                            <form:errors path="Description"></form:errors>
                        </div>
                    </spring:bind>

                    <form:select class="form-control" path="role" items="${roleList}" />

                    <form:select class="form-control" path="status" items="${statusList}" />

                    <spring:bind path="password">
                            <form:hidden path="password" ></form:hidden>
                    </spring:bind>
                    <spring:bind path="merchantId">
                            <form:hidden path="merchantId"></form:hidden>
                    </spring:bind>
                    <input class="btn btn-lg btn-primary btn-block" type="submit" name="save" value="Save" />
                    <input class="btn btn-lg btn-default btn-block" type="submit" name="cancel" value="Cancel" />
                </form:form>
            </main>
        </div>
    </c:if>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
