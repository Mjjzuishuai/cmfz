<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/boot/css/back.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jqgrid/css/jquery-ui.css">
    <script src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="${pageContext.request.contextPath}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/ajaxfileupload.js"></script>
</head>
<script type="text/javascript">
    $(function () {
        $("#ftable").jqGrid(
            {
                url: '${pageContext.request.contextPath}/album/queryByPage',
                datatype: "json",
                height: 500,
                colNames: ['ID', '标题', '评级', '作者', '播放位置', '集数', '描述', '状态', '创建时间','封面'],
                colModel: [
                    {name: 'id',hidden:true},
                    {name: 'title', editable: true},
                    {name: 'score', editable: true},
                    {name: 'author', editable: true},
                    {name: 'broadcast', editable: true},
                    {name: 'count', editable: true},
                    {name: 'des', editable: true},
                    {name: 'status', editable: true},
                    {name: 'createDate'},
                    {name:'cover',formatter: function (cellvalue, options, rowObject) {
                            // 自定义单元格内容
                            return "<img height='100px' width='100px' src='"+cellvalue+"'/>";
                        },
                        editable: true, edittype: "file", editoptions: {"enctype": "multipart/form-data"}
                    }
                ],
                rowNum: 5,//每页显示五行数据
                rowList: [5, 10, 15, 20],//可以选择的每页显示行数
                pager: '#fpage',//分页名
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                mtype: "post",
                multiselect: false,//是否有多选按钮
                autowidth: true,//自适应宽度
                styleUI: "Bootstrap",//不知道
                // 开启子表格支持
                subGrid: true,//有无子表格
                caption: "Grid as Subgrid",
                editurl: "${pageContext.request.contextPath}/album/edit",
                // subgrid_id:父级行的Id  row_id:当前的数据Id
                subGridRowExpanded: function (subgrid_id, row_id) {
                    // 调用生产子表格的方法
                    // 生成表格 | 生产子表格工具栏
                    addSubgrid(subgrid_id, row_id);
                },
                // 删除表格的方法
                subGridRowColapsed: function (subgrid_id, row_id) {
                }
            });
        $("#ftable").jqGrid('navGrid', '#fpage', {
            edit: true,
            add: true,
            del: true
        }, {closeAfterEdit: true,  //修改提交之后发ajax请求，改url
            afterSubmit: function (response, postData) {
                var albumId = response.responseJSON.albumId;
                $.ajaxFileUpload(
                    {
                        url: "${pageContext.request.contextPath}/album/upLoad",
                        type: "post",
                        datatype: "json",
                        data: {albumId: albumId},
                        fileElementId: "cover",
                        success: function (data) {
                            $("#ftable").resetSelection();//取消表格选中记录
                            $("#ftable").trigger("reloadGrid")//刷新整个列表
                        }
                    }
                );
                return postData;
            }}, {closeAfterAdd: true,  //修改提交之后发ajax请求，改url
            afterSubmit: function (response, postData) {
                var albumId = response.responseJSON.albumId;
                $.ajaxFileUpload(
                    {
                        url: "${pageContext.request.contextPath}/album/upLoad",
                        type: "post",
                        datatype: "json",
                        data: {albumId: albumId},
                        fileElementId: "cover",
                        success: function (data) {
                            $("#ftable").resetSelection();//取消表格选中记录
                            $("#ftable").trigger("reloadGrid")//刷新整个列表
                        }
                    }
                );
                return postData;
            }});
    });

    // subgrid_id 父行级id
    function addSubgrid(subgrid_id, row_id) {
        // 声明子表格Id
        var sid = subgrid_id + "table";
        // 声明子表格工具栏id
        var spage = subgrid_id + "page";
        $("#" + subgrid_id).html("<table id='" + sid + "' class='scroll'></table><div id='" + spage + "' style='height: 50px'></div>")
        $("#" + sid).jqGrid(
            {
                // 指定的json文件
                // 指定查询的url 根据专辑id 查询对应章节 row_id: 专辑id
                url: "${pageContext.request.contextPath}/chapter/selectByPage?albumId="+row_id,
                datatype: "json",
                colNames: ['id', '标题', '音频大小', '音频时长', '创建时间', '专辑id','操作' ],
                colModel: [
                    {name: "id", index: "num", width: 80, key: true,hidden:true},
                    {name: "title", index: "item", width: 130, editable: true},
                    {name: "size", index: "item", width: 130},
                    {name: "time", index: "item", width: 130},
                    {name: "createTime", index: "item", width: 130},
                    {name: "albumId", index: "item", width: 130,hidden:true},
                    {
                        name : "url",formatter:function (cellvalue, options, rowObject) {
                            var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"download('"+cellvalue+"')\">下载</button>&nbsp;&nbsp;";
                            //                                                                声明一个onPlay方法 --> 显示模态框 ---> 为audio标签添加src  需要url路径作为参数传递
                            //                                                              'onPlay(参数)' ---> \"onPlay('"+cellvalue+"')\"
                            button+= "<button type=\"button\" class=\"btn btn-danger\" onclick=\"onPlay('"+cellvalue+"')\">在线播放</button>";
                            return button;
                        },editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"}
                    }
                ],
                rowNum: 20,
                pager: spage,
                sortname: 'num',
                sortorder: "asc",
                height: '100%',
                autowidth: true,
                styleUI: "Bootstrap",
                editurl: "${pageContext.request.contextPath}/chapter/edit?albumId="+row_id,
            });
        $("#" + sid).jqGrid('navGrid',
            "#" + spage, {
                edit: true,
                add: true,
                del: true
            }, {
                closeAfterEdit: true,
                //修改提交之后发ajax请求，改url
                afterSubmit: function (response, postData) {
                    var chapterId = response.responseJSON.chapterId;
                    $.ajaxFileUpload(
                        {
                            url: "${pageContext.request.contextPath}/chapter/upLoad",
                            type: "post",
                            datatype: "json",
                            data: {chapterId:chapterId},
                            fileElementId: "url",
                            success: function (data) {
                                $("#" + sid).resetSelection();//取消表格选中记录
                                $("#" + sid).trigger("reloadGrid")//刷新整个列表
                            }
                        }
                    );
                    return postData;
                }
            }, {
                closeAfterAdd: true,
                //修改提交之后发ajax请求，改url
                afterSubmit: function (response, postData) {
                    var chapterId = response.responseJSON.chapterId;
                    $.ajaxFileUpload(
                        {
                            url: "${pageContext.request.contextPath}/chapter/upLoad",
                            type: "post",
                            datatype: "json",
                            data: {chapterId: chapterId},
                            fileElementId: "url",
                            success: function (data) {
                                $("#" + sid).resetSelection();//取消表格选中记录
                                $("#" + sid).trigger("reloadGrid")//刷新整个列表
                            }
                        }
                    );
                    return postData;
                }
            });
    };
    function onPlay(cellValue) {
        $("#music").attr("src",cellValue);
        $("#myModal").modal("show");
    }
    function download(cellValue) {
        location.href = "${pageContext.request.contextPath}/chapter/downloadChapter?url="+cellValue;
    }
</script>
<body>
<table id="ftable"></table>
<div id="fpage" style="height: 50px"></div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <audio id="music" src="" controls="controls">
        </audio>
    </div>
</div>
</body>