<%@page pageEncoding="utf-8" isELIgnored="false" %>
<head>
    <script type="text/javascript">
        //页面加载之后
        $(function () {
            //查所有
            $("#adminTable").jqGrid({
                url: "${pageContext.request.contextPath}/admin/selectAll",//向控制器发分页查所有
                datatype: "json",//预期结果类型
                autowidth: true, // 自适合宽度
                colNames: ["ID" , "姓名", "名字", "盐值"],
                colModel: [{name: "id",hidden:true},
                    {name: "username", editable: true},
                    {name: "password", editable: true},
                    {name: "salt", editable: true},
                    ],
                pager: "#pager",
                viewrecords: true,
                multiselect:true,
                height:500,
                editurl: "${pageContext.request.contextPath}/admin/edit"
            }).jqGrid("navGrid","#pager",{
                add: true,
                edit: true,
                del: true
            }, {
                //修改之后自动关闭弹出框
                closeAfterEdit: true,
            }, {
                //添加之后自动关闭弹出框
                closeAfterAdd: true,
            });//增删改查
        })//页面加载结束

    </script>
</head>

<body>

<table id="adminTable"></table>
<div id="pager" style="height: 50px"></div>
</body>
