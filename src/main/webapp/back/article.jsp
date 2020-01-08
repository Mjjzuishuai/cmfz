<%@page pageEncoding="utf-8" isELIgnored="false" %>

<head>
    <script type="text/javascript">
        //页面加载之后
        $(function () {
            //查所有
            $("#articleTable").jqGrid({
                url: "${pageContext.request.contextPath}/article/selectByPage",//向控制器发分页查所有
                datatype: "json",//预期结果类型
                autowidth: true, // 自适合宽度
                colNames: ["ID", "标题", "封面", "内容", "创建时间", "发布时间", "状态", "上师id","操作"],
                colModel: [{name: "id", hidden: true}, {name: "title", editable: true}, {
                    name: "img",
                    formatter: function (cellvalue, options, rowObject) {
                        // 自定义单元格内容
                        return "<img height='100px' width='100px' src='"+cellvalue+"'/>";
                    },
                    editable: true, edittype: "file", editoptions: {"enctype": "multipart/form-data"}
                }, {name: "content", editable: true},
                    {name: "createDate", editable: true, edittype: "date"}, {name: "publishDate", editable: true, edittype: "date"},
                    {
                        name: "status",
                        editable: true
                    },
                    {name: "guruId", editable: true,hidden: true},
                    {name:"option",
                        formatter:function (cellvalue, options, rowObject) {  // 自定义单元格内容
                            var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"update('"+rowObject.id+"')\">修改</button>&nbsp;&nbsp;";
                            button+= "<button type=\"button\" class=\"btn btn-danger\" onclick=\"del('"+rowObject.id+"')\">删除</button>";
                            return button;
                        }
                    }
                ],
                pager: "#pager",
                viewrecords: true,
                multiselect: true,
                rowNum: 5,
                height: 500,
                rowList: [5, 10, 15],
                page: 1,
            }).jqGrid("navGrid", "#pager", {
                add: false,
                edit: false,
                del: false
            });//增删改查

        })//页面加载结束

        /*添加员工的模态框*/
            $("#add").click(function () {
                //弹出模态框之前发ajax查询所有上师在下拉菜单中显示所有上师
                $.ajax({
                    url:"${pageContext.request.contextPath}/guru/queryAll",
                    datatype: "json",
                    type:"post",
                    success:function (guruList) {
                        var option = "<option value='0'>请选择所属上师</option>";
                        guruList.forEach(function (guru) {
                           option+="<option value="+guru.id+">"+guru.name+"</option>";
                        });
                        $("#guru").html(option);
                    }
                });
                $("#modal1").modal({
                    backdrop: false,
                    keyboard: false,
                    show: true
                });
            });
      

        //给修改一个点击事件，1：查所有上师  2：根据id数据回显(注意是根据当前行数据id拿到当前行数据，并不是去数据库里查) 3：从弹出模态框
        function update(id) {
            //1：查所有上师并在下拉框中回显出来
            $.ajax({
                url:"${pageContext.request.contextPath}/guru/queryAll",
                type:"post",
                datatype:"json",
                success:function (guruList) {
                    var option = "<option value='0'>请选择所属上师</option>";
                    guruList.forEach(function (guru) {
                        option+="<option value="+guru.id+">"+guru.name+"</option>";
                    })
                    $("#guru").html(option);
                }
            });
            //2：根据id拿到当前行数据

                // 使用jqGrid("getRowData",id) 目的是屏蔽使用序列化的问题
                // $("#articleTable").jqGrid("getRowData",id); 该方法表示通过Id获取当前行数据
                var data = $("#articleTable").jqGrid("getRowData",id);//data为当前行数据
                $("#id").val(data.id);
                $("#title").val(data.title);
                KindEditor.html("file",data.content)//富文本编辑器要用这个方式赋值
                $("#id").val(data.id);

            //3：弹出模态框
            $("#modal1").modal({
                backdrop: false,
                keyboard: false,
                show: true
            });
        };

        //点击删除发像后台发ajax请求，根据Id删除
        function del(id){
            alert("确定删除吗?");
            $.ajax({
                url:"${pageContext.request.contextPath}/article/delete?id="+id,
                type:"post",
                datatype:"json",
                success:function () {
                    $("#articleTable").trigger("reloadGrid")//刷新整个列表
                }
            })
        }
        
        
        //给模态框中的表单的提交按钮做一个事件，发ajax像controller提交数据
        $("#sub").click(function () {
            $.ajaxFileUpload({
                url:"${pageContext.request.contextPath}/article/edit",
                // ajaxFileUpload 不支持serialize() 格式化形式
                // 只支持{"id":1,XXX:XX}
                // 解决: 1. 手动封装  2. 更改ajaxFileUpload的源码
                // 异步提交时 无法传输修改后的kindeditor内容,需要刷新
                type:"post",
                data:{
                    "id":$("#id").val(),
                    "title":$("#title").val(),
                    "content":$("#content").val(),
                    "guruId":$("#guru").val()
                },
                datatype:"json",
                fileElementId:"file",
                success:function () {
                    $("#articleTable").trigger("reloadGrid")//刷新整个列表
                }
            })
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="javascript:void(0)" id="add">添加文章</a></li>
</ul>
<table id="articleTable"></table>
<div id="pager" style="height: 50px"></div>
</body>
