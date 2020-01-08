<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon"/>
    <link href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js">
    </script>
    <script type="text/javascript">
        $(function () {

            $("#change").click(function () {
                $("#code").prop("src","${pageContext.request.contextPath}/admin/createImg?a="+new Date().getTime())
            })

            $("#log").click(function () {
                var username = $("#username").val();
                var password = $("#password").val();
                var clientCode = $("#clientCode").val();
                $.post(
                    "${pageContext.request.contextPath}/admin/login",
                    {
                        "clientCode":clientCode,
                        "username": username,
                        "password": password
                    },
                    function (result) {
                        if(result == "验证码不正确"){
                            $("#msg").empty();
                            $("#msg").html("<font color='red'>" + result + "</font>")
                        } else if (result == "用户名或密码错误") {
                            $("#msg").empty();
                            $("#msg").html("<font color='red'>" + result + "</font>")
                        } else{
                            location.href = "${pageContext.request.contextPath}/back/main.jsp";
                        }
                    },
                    "json"
                )
            })
        });

    </script>
</head>
<body style=" background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>

        <div class="modal-body" id="model-body">
            <div class="form-group">
                <input id="username" type="text" class="form-control" placeholder="用户名" autocomplete="off"
                       name="username">
            </div>
            <div class="form-group">
                <input id="password" type="password" class="form-control" placeholder="密码" autocomplete="off"
                       name="password">
            </div>
            <div class="form-group" >
                <div class="col-xs-4"><input size="1" id="clientCode" type="text" class="form-control" placeholder="验证码" autocomplete="off"
                                             name="clientCode"></div>
                <div class="col-xs-4"><img id="code" src="${pageContext.request.contextPath}/admin/createImg"/></div>
                <div class="col-xs-4"><h3><a href="javascript:void(0)" id="change">看不清,换一张</a></h3></div>
            </div>
            <span id="msg"></span>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="log">登录</button>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default form-control">注册</button>
            </div>

        </div>


    </div>
</div>
</body>
</html>
