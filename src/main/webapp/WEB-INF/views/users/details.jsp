
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/common/head.jsp" %>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="content-wrapper">
        <section class="content">
            <div class="row">
                <div class="col-md-3">
                    <!-- Profile Image -->
                    <div class="box box-primary">
                        <div class="box-body box-profile">
                            <h3 class="profile-username text-center">${client.prenom} ${client.nom} (${client.email})</h3>
                            <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                    <b>Reservation(s)</b> <a class="pull-right">${fn:length(reservations)}</a>
                                </li>
                                <!-- You can add other client-related information here -->
                            </ul>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
                <div class="col-md-9">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#rents" data-toggle="tab">Reservations</a></li>
                            <li><a href="#cars" data-toggle="tab">Voitures</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="active tab-pane" id="rents">
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <thead>
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Voiture</th>
                                            <th>Date de debut</th>
                                            <th>Date de fin</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${reservations}" var="reservation">
                                            <tr>
                                                <td>${reservation.id}.</td>
                                                <td>${reservation.vehicle.modele}</td>
                                                <td>${reservation.debut}</td>
                                                <td>${reservation.fin}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                            <div class="tab-pane" id="cars">
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <thead>
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>constructeur</th>
                                            <th>modele</th>
                                            <th>nombre de place</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${reservations}" var="reservation">
                                            <tr>
                                                <td>${reservation.vehicle.id}.</td>
                                                <td>${reservation.vehicle.constructeur}</td>
                                                <td>${reservation.vehicle.modele}</td>
                                                <td>${reservation.vehicle.nb_places}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                        </div>
                        <!-- /.tab-content -->
                    </div>
                    <!-- /.nav-tabs-custom -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
        </section>
        <!-- /.content -->
    </div>
    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->
<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
