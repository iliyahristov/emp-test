<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Transaction List</title>

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
                    <se class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="${contextPath}/welcome">
                                <span data-feather="home"></span>
                                Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="${contextPath}/transaction/transactionList">
                                <span data-feather="file"></span>
                                Transactions
                            </a>
                        </li>
                        <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
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
                <h2 class="form-heading">Transaction List</h2>

                <table class="table table-striped">
                    <thead class="thead-light">
                    <tr>
                        <th scope="col">Transaction ID</th>
                        <th scope="col">Created on</th>
                        <th scope="col">Amount</th>
                        <th scope="col">Status</th>
                        <th scope="col">Customer e-mail</th>
                        <th scope="col">Customer phone</th>
                        <th scope="col">Merchant name</th>
                        <th scope="col">UUID</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="trans" items="${transactionList}">
                        <tr>
                            <td>${trans.transactionId}</td>
                            <td>${trans.createdOn}</td>
                            <td>${trans.amount/100}</td>
                            <td>${trans.status}</td>
                            <td>${trans.customerEmail}</td>
                            <td>${trans.customerPhone}</td>
                            <td>${trans.merchant.name}</td>
                            <td>${trans.uuid}</td>
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
