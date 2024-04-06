<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>List of Vehicles</title>
    <!-- Add your other head elements here -->
    <%@include file="/WEB-INF/views/common/head.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                Vehicles
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/cars/create">Add</a>
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
                                    <th>Brand</th>
                                    <th>Model</th>
                                    <th>Number of Seats</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${vehicles}" var="vehicle">
                                    <tr>
                                        <td>${vehicle.id}.</td>
                                        <td>${vehicle.constructeur}</td>
                                        <td>${vehicle.modele}</td>
                                        <td>${vehicle.nb_places}</td>
                                        <td>
                                            <a class="btn btn-success" href="${pageContext.request.contextPath}/cars/edit?id=${vehicle.id}">
                                                <i class="fa fa-edit"></i>
                                            </a>
                                            <a class="btn btn-danger" href="${pageContext.request.contextPath}/cars/delete?id=${vehicle.id}">
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
