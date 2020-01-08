<%@page pageEncoding="utf-8" isELIgnored="false" %>

<head>
    <script type="text/javascript">
        //页面加载之后
        $(function () {
            //查所有
            $("#logTable").jqGrid({
                url: "${pageContext.request.contextPath}/Mylog/selectAll",//向控制器发分页查所有
                datatype: "json",//预期结果类型
                autowidth: true, // 自适合宽度
                colNames: [ "管理员", "什么时间", "做了什么", "成功/失败"],
                colModel: [
                    {name: "adminname"},
                    {name: "operatedate", editable: true,edittype: "date"},
                    {name: "thing",editable: true},
                    {name: "status", editable: true}
                    ],
                viewrecords: true,
                multiselect:true,
                height:500,
                editurl: "${pageContext.request.contextPath}/banner/modify"//分页查所有结束
            }).jqGrid("navGrid", "#pager", {
                add: true,
                edit: true,
                del: true
            }, {
                //修改之后自动关闭弹出框
                closeAfterEdit: true
            }, {
                //添加之后自动关闭弹出框
                closeAfterAdd: true
            });//增删改查

        })//页面加载结束
    </script>
</head>

<body>
<table id="logTable"></table>
<div id="pager" style="height: 50px"></div>
</body>
