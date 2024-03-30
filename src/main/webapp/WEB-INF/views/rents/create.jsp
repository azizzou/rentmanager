<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/head.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>Create Reservation</h1>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/rents/create">
                            <div class="box-body">
                                <!-- Client Dropdown -->
                                <div class="form-group">
                                    <label for="client" class="col-sm-2 control-label">Client</label>
                                    <div class="col-sm-10">
                                        <select class="form-control" id="client" name="client">
                                            <!-- Add options dynamically based on available clients -->
                                            <c:forEach items="${clients}" var="client">
                                                <option value="${client.id}">${client.nom} ${client.prenom}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <!-- Car Dropdown -->
                                <div class="form-group">
                                    <label for="car" class="col-sm-2 control-label">Car</label>
                                    <div class="col-sm-10">
                                        <select class="form-control" id="car" name="car">
                                            <!-- Add options dynamically based on available vehicles -->
                                            <c:forEach items="${vehicles}" var="car">
                                                <option value="${car.id}">${car.constructeur} ${car.modele}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <!-- Begin Date Input -->
                                <div class="form-group">
                                    <label for="begin" class="col-sm-2 control-label">Begin Date</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="begin" name="begin" required
                                               data-inputmask="'alias': 'dd/mm/yyyy'" data-mask value="<c:out value="${beginDate}"/>">
                                    </div>
                                </div>
                                <!-- End Date Input -->
                                <div class="form-group">
                                    <label for="end" class="col-sm-2 control-label">End Date</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="end" name="end" required
                                               data-inputmask="'alias': 'dd/mm/yyyy'" data-mask value="<c:out value="${endDate}"/>">
                                    </div>
                                </div>
                            </div>
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Create Reservation</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<script>
    $(function () {
        $('[data-mask]').inputmask()
    });
</script>
</body>
</html>
