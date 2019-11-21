<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="locale.locale" var="locale"/>

    <fmt:message bundle="${locale}" key="locale.customer.label.subject" var="subject"/>
    <fmt:message bundle="${locale}" key="locale.customer.label.transport" var="transport"/>
    <fmt:message bundle="${locale}" key="locale.customer.label.rate" var="rate"/>
    <fmt:message bundle="${locale}" key="locale.user.text.yes" var="yes"/>
    <fmt:message bundle="${locale}" key="locale.user.text.no" var="no"/>
    <fmt:message bundle="${locale}" key="locale.user.text.truck" var="truck"/>
    <fmt:message bundle="${locale}" key="locale.user.text.car" var="car"/>
    <fmt:message bundle="${locale}" key="locale.user.text.withoutTransport" var="withoutTransport"/>
    <fmt:message bundle="${locale}" key="locale.user.label.distance" var="distance"/>
    <fmt:message bundle="${locale}" key="locale.user.label.totalCost" var="totalCost"/>
    <fmt:message bundle="${locale}" key="locale.customer.button.orderTransportation" var="orderTransportation"/>
    <fmt:message bundle="${locale}" key="locale.customer.button.makeChanges" var="makeChanges"/>

    <link rel="stylesheet" href="./css/style.css">
    <title>Confirm order</title>
</head>
<body>
<header>
    <jsp:include page="/jsp/header.jsp"/>
</header>

<main class="main">
    <div>
        <form action="controller" name="newOrder" method="POST">
            <input type="hidden" name="command" value="new_order_command"/>
            <div>
                <br>
                <h2>${newOrder}</h2>
            </div>
            <div class="logIn-form-box-2">
                <span class="form-label">${subject}: </span>
                <input class="login-form-text"
                       name="total"
                       value="${sessionScope.order.subject}"
                       readonly>
                <span class="form-label">${transport}: </span>
                    <c:choose>
                        <c:when test="${sessionScope.order.transport == 'TRUCK'}">
                            <input class="login-form-text"
                                   name="total"
                                   value="${truck}"
                                   readonly>
                        </c:when>
                        <c:when test="${sessionScope.order.transport == 'CAR'}">
                            <input class="login-form-text"
                                   name="total"
                                   value="${car}"
                                   readonly>
                        </c:when>
                        <c:otherwise>
                            <input class="login-form-text"
                                   name="total"
                                   value="${withoutTransport}"
                                   readonly>
                        </c:otherwise>
                    </c:choose>
                    <span class="form-label">${rate}: </span>
                    <c:choose>
                        <c:when test="${sessionScope.order.rate == 'true'}">
                            <input class="login-form-text"
                                   name="total"
                                   value="${yes}"
                                   readonly>
                        </c:when>
                        <c:otherwise>
                            <input class="login-form-text"
                                   name="total"
                                   value="${no}"
                                   readonly>
                        </c:otherwise>
                    </c:choose>
                <span class="form-label">${distance}: </span>
                    <input class="login-form-text"
                           name="total"
                           value="${sessionScope.order.distance}km"
                           readonly>
                <span class="form-label">${totalCost}: </span>
                    <input class="login-form-text"
                           name="total"
                           value="${sessionScope.order.totalPrice} BYN"
                           readonly>
                <input type="submit" value="${orderTransportation}" class="login-form-button">
            </div>
        </form>
        <form action="new-order">
            <input type="submit" value="${makeChanges}" class="common-button">
        </form>
    </div>
</main>

<footer>
    <jsp:include page="/jsp/footer.jsp"/>
</footer>
</body>
</html>