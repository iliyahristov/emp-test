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
                    <li class="nav-item">
                        <a class="nav-link active" href="${contextPath}/merchant/merchantList">
                            <span data-feather="users"></span>
                            Merchants
                        </a>
                    </li>
                    <li class="nav-item">
                        <form id="logoutForm" method="POST" action="${contextPath}/logout">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                        <a onclick="document.forms['logoutForm'].submit()" href="/login">Sign out</a>
                    </li>
                </ul>
            </div>
        </nav>

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <%--<div class="container">--%>
            <h2 class="form-heading">Merchant List</h2>

            <table class="table table-striped">
                <thead class="thead-light">
                <tr>
                    <th scope="col">Merchant ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">E-mail</th>
                    <th scope="col">Status</th>
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
                        <td>
                        <a th:href="/@{'/edit/' + ${merchant.merchantId}}">Edit</a>
                        &nbsp;&nbsp;&nbsp;
                        <a th:href="/@{'/delete/' + ${merchant.merchantId}}">Delete</a>
                    </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <%--</div>--%>
        </main>
    </div>
</c:if>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
