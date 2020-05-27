<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="_script_head.jsp"%>
    <title>Dashboard - 编辑器</title>
</head>

<body class="sticky-header">

<section>
    <%@ include file="_dashboard_navigator.jsp"%>

    <%@ include file="_dashboard_header.jsp"%>

    <!-- main content start-->
    <div class="main-content" >

        <!-- page heading start-->
        <div class="page-heading">
            <h3>编辑器</h3>
            <ul class="breadcrumb">
                <li>
                    <a href="editor.jsp">Forms</a>
                </li>
                <li class="active"> Editors </li>
            </ul>
        </div>
        <!-- page heading end-->

        <!--body wrapper start-->
        <div class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            CKEditor 编辑器
                            <span class="tools pull-right">
                                <a class="fa fa-chevron-down" href="javascript:"></a>
                                <a class="fa fa-times" href="javascript:"></a>
                             </span>
                        </header>
                        <div class="panel-body">
                            <div class="form">
                                <form action="#" class="form-horizontal">
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <textarea class="form-control" name="editor" id="editor" rows="6"></textarea>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
        <!--body wrapper end-->

        <!--footer section start-->
        <jsp:include page="_footer.jsp"/>
        <!--footer section end-->
    </div>
    <!-- main content end-->

</section>
<%@ include file="_script_dashboard.jsp"%>
<script type="text/javascript" src="${contextPath}/lib/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
    $(function () {
        CKEDITOR.replace('editor', {
            language:'zh-cn'
        })
    })
</script>
</body>
</html>
