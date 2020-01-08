<%@page pageEncoding="utf-8" isELIgnored="false" %>

<head>
    <script type="text/javascript">
        //页面加载之后
        $(function () {
            //查所有
            $("#bannerTable").jqGrid({
                url: "${pageContext.request.contextPath}/banner/queryBypage",//向控制器发分页查所有
                datatype: "json",//预期结果类型
                autowidth: true, // 自适合宽度
                colNames: ["ID", "标题", "路径", "链接地址", "创建时间", "描述", "状态"],
                colModel: [{name: "id"}, {name: "title", editable: true}, {
                    name: "url",
                    formatter: function (cellvalue, options, rowObject) {
                        // 自定义单元格内容
                        return "<img height='100px' width='100px' src='"+cellvalue+"'/>";
                    },
                    editable: true, edittype: "file", editoptions: {"enctype": "multipart/form-data"}
                }, {name: "href", editable: true},
                    {name: "createDate",editable: true,edittype: "date"}, {name: "des", editable: true}, {
                        name: "status",
                        editable: true
                    }],
                pager: "#pager",
                viewrecords: true,
                multiselect:true,
                rowNum: 5,
                height:500,
                rowList: [5, 10, 15],
                page: 1,
                editurl: "${pageContext.request.contextPath}/banner/modify"//分页查所有结束
            }).jqGrid("navGrid", "#pager", {
                add: true,
                edit: true,
                del: true
            }, {
                //修改之后自动关闭弹出框
                closeAfterEdit: true,
                //修改提交之后发ajax请求，改url
                afterSubmit: function (response, postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload(
                        {
                            url: "${pageContext.request.contextPath}/banner/upLoad",
                            type: "post",
                            datatype: "json",
                            data: {bannerId: bannerId},
                            fileElementId: "url",
                            success: function (data) {
                                $("#bannerTable").resetSelection();//取消表格选中记录
                                $("#bannerTable").trigger("reloadGrid")//刷新整个列表
                            }
                        }
                    );
                    return postData;
                }
            }, {
                //添加之后自动关闭弹出框
                closeAfterAdd: true,
                //修改提交之后发ajax请求，改u rl
                afterSubmit: function (response, postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload(
                        {
                            url: "${pageContext.request.contextPath}/banner/upLoad",
                            type: "post",
                             datatype: "json",
                            data: {bannerId: bannerId},
                            fileElementId: "url",
                            success: function (data) {
                                $("#bannerTable").resetSelection();//取消表格选中记录
                                $("#bannerTable").trigger("reloadGrid")//刷新整个列表
                            }
                        }
                    );
                    return postData;
                }
            });//增删改查

        })//页面加载结束
        //点击导入轮播图Excel弹出模态框
        $("#in").click(function () {
            $("#modal1").modal({
                backdrop: false,
                keyboard: false,
                show: true
            });
        });
        //点击弹出模态框按钮向后台发送请求
        $("#sub").click(function () {
            $.ajaxFileUpload({
                url:"${pageContext.request.contextPath}/banner/in",
                type:"post",
                datatype:"json",
                fileElementId:"file",
                success:function () {
                    $("#bannerTable").trigger("reloadGrid")//刷新整个列表
                }
            })
        });
    </script>
</head>

<body>
<ul class="nav nav-tabs">
    <li><a href="${pageContext.request.contextPath}/banner/out" id="out">导出轮播图Excel</a></li>
    <li><a href="javascript:void(0)" id="in">导入轮播图Excel</a></li>
    <li><a href="${pageContext.request.contextPath}/banner/outIn" id="outIn">导出轮播图Excel模版</a></li>
</ul>
<table id="bannerTable"></table>
<div id="pager" style="height: 50px"></div>
<%--添加文章的模态框--%>
<div class="modal fade " id="modal1"   role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <%--头--%>
            <div class="modal-header">
                <%--叉号--%>
                <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                <h4 class="modal-title">添加轮播图信息Excel</h4>
            </div>
            <%--身体--%>
            <div class="modal-body">
                <%--表单--%>
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="file" class="col-sm-2 control-label">选择Excel</label>
                        <div class="col-sm-10">
                            <input type="file" formenctype="multipart/form-data" name="file" class="form-control" id="file">
                        </div>
                    </div>
                </form>
                <%--脚--%>
            </div>
            <div class="modal-footer">
                <button id="sub" type="button" class="btn btn-default" data-dismiss="modal">提交</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
</body>
