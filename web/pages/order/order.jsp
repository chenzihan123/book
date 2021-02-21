<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的订单</title>

    <%-- 静态包含 base标签、css样式、jQuery文件 --%>
    <%@ include file="/pages/common/head.jsp" %>


    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }
    </style>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif">
    <span class="wel_word">我的订单</span>

    <%--静态包含，登录 成功之后的菜单 --%>
    <%@ include file="/pages/common/login_success_menu.jsp" %>


</div>

<div id="main">

    <table>
        <tr>
            <td>订单号</td>
            <td>日期</td>
            <td>金额</td>
            <td>订单状态</td>
            <td>详情</td>
            <td>发货</td>

        </tr>
        <c:forEach items="${requestScope.myOrders}" var="order">
            <tr>
                    <%--<td>${order.createTime}</td>--%>
                    <%--数据库时间格式化输出--%>
                <td>${order.orderId}</td>
                <td><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:MM:SS"/></td>
                <td>${order.price}</td>
                <c:if test="${order.status == 0}">
                    <td>未发货</td>
                </c:if>
                <c:if test="${order.status == 1}">
                    <td>已发货</td>
                </c:if>
                <c:if test="${order.status == 2}">
                    <td>已收货</td>
                </c:if>

                    <%--                <td>${order.status}</td>--%>
                <td><a href="client/orderServlet?action=showOrderDetail&orderId=${order.orderId}">查看详情</a></td>
                <td><a href="client/orderServlet?action=sendOrder&orderId=${order.orderId}">点击发货</a></td>
            </tr>
        </c:forEach>
    </table>


</div>


<%--静态包含页脚内容--%>
<%@include file="/pages/common/footer.jsp" %>


</body>
</html>