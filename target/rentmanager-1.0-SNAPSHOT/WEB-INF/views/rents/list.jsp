<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>List of Reservations</title>
    <%@include file="/WEB-INF/views/common/head.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                Reservations
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/rents/create">Ajouter</a>
            </h1>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body no-padding">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th style="width: 10px">#</th>
                                    <th>Voiture</th>
                                    <th>Client</th>
                                    <th>Debut</th>
                                    <th>Fin</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${reservations}" var="reservation">
                                    <tr>
                                        <td>${reservation.id}</td>
                                        <c:choose>
                                            <c:when test="${not empty reservation.vehicle}">
                                                <td>${reservation.vehicle.constructeur} ${reservation.vehicle.modele}</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>No Vehicle</td>
                                            </c:otherwise>
                                        </c:choose>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty reservation.client}">
                                                    ${reservation.client.prenom} ${reservation.client.nom}
                                                </c:when>
                                                <c:otherwise>
                                                    No Client
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${reservation.debut}</td>
                                        <td>${reservation.fin}</td>
                                        <td>
                                            <a class="btn btn-primary disabled" href="${pageContext.request.contextPath}/cars?id=${reservation.vehicle.id}">
                                                <i class="fa fa-play"></i>
                                            </a>
                                            <a class="btn btn-success disabled" href="#">
                                                <i class="fa fa-edit"></i>
                                            </a>
                                            <a class="btn btn-danger" href="${pageContext.request.contextPath}/rents/delete?id=${reservation.id}">
                                                <i class="fa fa-trash"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
