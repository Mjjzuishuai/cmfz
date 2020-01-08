<%@page pageEncoding="utf-8" isELIgnored="false" %>

<head>
    <script type="text/javascript">
        //页面加载之后
        $(function () {
            //查所有
            $("#userTable").jqGrid({
                url: "${pageContext.request.contextPath}/user/selectByPage",//向控制器发分页查所有
                datatype: "json",//预期结果类型
                autowidth: true, // 自适合宽度
                colNames: ["ID", "联系方式", "密码", "盐", "状态", "头像", "真实姓名", "昵称", "性别", "个性签名", "来自", "注册时间", "最后登陆时间"],
                colModel: [
                    {name: "id", hidden: true},
                    {name: "phone"},
                    {name: "password",hidden:true},
                    {name: "salt", hidden: true},
                    {name: "status", editable: true},
                    {name: "photo",
                        formatter:function (cellvalue,option,rowObject) {
                            return "<img height='50px' width='50px' src='"+cellvalue+"'/>";
                        }
                    },
                    {name: "name"},
                    {name: "nickname"},
                    {name: "sex", editable: true},
                    {name: "sign"},
                    {name: "location",editable: true},
                    {name: "rigestDate"},
                    {name: "lastLogin"}
                    ],
                pager: "#pager",
                viewrecords: true,
                multiselect: true,
                rowNum: 5,
                height: 500,
                rowList: [5, 10, 15],
                page: 1,
                editurl: "${pageContext.request.contextPath}/user/edit"//分页查所有结束
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
<table id="userTable"></table>
<div id="pager" style="height: 50px"></div>

</body>
