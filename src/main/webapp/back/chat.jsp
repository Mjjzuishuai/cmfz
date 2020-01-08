<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <script type="text/javascript">

        //初始化GoEasy对象
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-198a2526cea5481f81c9f8fcdffaafb1", //替换为您的应用appkey
        });

        $(function () {
            //点击发送的click的事件
            $("#sendTextBtn").click(function () {
                //获得input的框的内容
                var msg= $("#text").val();

                //发送消息
                goEasy.publish({
                    channel: "cmfz",
                    message: msg,
                    onSuccess:function(){

                    },
                });

                //接受消息
                goEasy.subscribe({
                    channel: "cmfz",
                    //message包括信息和goeasy中的其他信息，我们只要他的字符串信息，也就是message.content
                    onMessage: function (message) {
                        $("#textShowDiv").append(message.content+"<br/>");
                    }
                });
            })
        });


    </script>



<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">多人聊天室</h3>
    </div>
    <div class="panel-body">
        <div id="textShowDiv">
        </div>
        <div style="margin-top: 500px">
            <div class="col-sm-10">
                <input type="text" name="text" class="form-control" id="text" >
            </div>
            <button class="btn btn-primary" id="sendTextBtn">发送</button>
        </div>
    </div>
</div>
