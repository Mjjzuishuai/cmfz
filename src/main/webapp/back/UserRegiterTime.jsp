<%@page pageEncoding="utf-8" isELIgnored="false" %>

<head>
    <script type="text/javascript"></script>
</head>

<body>
<%--以下是Echatrs图表信息--%>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据

    //页面加载向controller发请求查询最近一天，一周，一个月，一年注册的用户
    $(function () {
        $.ajax({
            url: "${pageContext.request.contextPath}/user/selectByRegisterTime",
            type: "post",
            datatype: "json",
            success: function (data) {
                myChart.setOption({
                    title: {
                        text: '持明法州用户注册趋势图'
                    },
                    tooltip: {},
                    legend: {
                        data: ['男', '女']
                    },
                    xAxis: {
                        data: ["今日注册数量", "一周注册数量", "一个月注册数量", "一年注册数量"]
                    },
                    yAxis: {},
                    series: [{
                        name: '男',
                        type: 'bar',
                        data: data.man
                    },
                        {
                            name: '女',
                            type: 'bar',
                            data: data.woman
                        }
                    ]
                });
            }
        })
    });

    //初始化GoEasy对象
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BS-c52a928c57644f919a7df4258ff6ec3b", //替换为您的应用appkey
    });
    //接收消息
    goEasy.subscribe({
        channel: "cmfz", //替换为您自己的channel
        onMessage: function (message) {
           var data = JSON.parse(message.content);
            myChart.setOption({
                title: {
                    text: '持明法州用户注册趋势图'
                },
                tooltip: {},
                legend: {
                    data: ['男', '女']
                },
                xAxis: {
                    data: ["今日注册数量", "一周注册数量", "一个月注册数量", "一年注册数量"]
                },
                yAxis: {},
                series: [{
                    name: '男',
                    type: 'bar',
                    data: data.man
                },
                    {
                        name: '女',
                        type: 'bar',
                        data: data.woman
                    }
                ]
            });
        }
    });
</script>
</body>
