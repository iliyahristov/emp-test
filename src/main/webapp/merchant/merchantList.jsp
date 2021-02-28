<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Merchant List</title>

    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <link href="${contextPath}/resources/assets/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container-fluid">
    <c:if test="${isAdmin}">
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
                        <c:if test="${isAdmin}">
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
                <c:if test="${message != null}">
                    <div class="alert alert-success" role="alert">
                            ${message}
                    </div>
                </c:if>
                <c:if test="${error != null}">
                    <div class="alert alert-danger" role="alert">
                            ${error}
                    </div>
                </c:if>
                <h2 class="form-heading">Merchant List</h2>

                <table class="table table-striped">
                    <thead class="thead-light">
                    <tr>
                        <th scope="col">Merchant ID</th>
                        <th scope="col">Name</th>
                        <th scope="col">Description</th>
                        <th scope="col">E-mail</th>
                        <th scope="col">Status</th>
                        <th scope="col">Transactions sum</th>
                        <th scope="col">Actions</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="merchant" items="${merchantList}">
                        <tr>
                            <td>${merchant.merchantId}</td>
                            <td>${merchant.name}</td>
                            <td>${merchant.description}</td>
                            <td>${merchant.email}</td>
                            <td>${merchant.status}</td>
                            <td>${merchant.totalTransactionSum}</td>
                            <td>
                                <a href="merchantEdit/${merchant.merchantId}">Edit</a>
                                <c:if test="${merchant.transactionList != null && merchant.transactionList.size() == 0}">
                                    |
                                    <a href="merchantDelete/${merchant.merchantId}">Delete</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </main>
        </div>
    </c:if>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
