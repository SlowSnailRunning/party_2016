layui.use(['form', 'layer', 'laydate', 'upload', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $=layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //考试列表
    var tableIns = table.render({
        elem: '#examList',
        url: '/exam/queryExamList.do',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limit: 20,
        limits: [10, 15, 20, 25],
        id: "examListTable",
        cols: [[
            {type: 'checkbox', fixed: 'left', width: 50},
            {field: 'id', title: 'ID', width: 86, align: "center", hide: true},
            {field: 'examName', title: '考试名称', width: 160, align: "center"},
            {field: 'examTime', title: '考试时长(分)', width: 120, align: "center"},
            {
                field: 'createTime',
                title: '考试创建时间',
                width: 180,
                align: "center",
                templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"
            },
            {
                field: 'examStartTime',
                title: '开考时间',
                width: 180,
                align: "center",
                templet: "<div>{{layui.util.toDateString(d.examStartTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"
            },
            {
                field: 'examEndTime',
                title: '停考时间',
                width: 180,
                align: "center",
                templet: "<div>{{layui.util.toDateString(d.examEndTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"
            },
            {field: 'radioNum', title: '单选题数量', width: 110, align: 'center'},
            {field: 'radioScore', title: '(分/道)', width: 90, align: 'center'},
            {field: 'checkNum', title: '多选题数量', width: 110, align: 'center'},
            {field: 'checkScore', title: '(分/道)', width: 90, align: 'center'},
            {field: 'judgeNum', title: '判断题数量', width: 110, align: 'center'},
            {field: 'judgeScore', title: '(分/道)', width: 90, align: 'center'},
            {field: 'fillNum', title: '填空题数量', width: 110, align: 'center'},
            {field: 'fillScore', title: '(分/道)', width: 90, align: 'center'},
            {field: 'saqNum', title: '简答题数量', width: 110, align: 'center'},
            {field: 'saqScore', title: '(分/道)', width: 90, align: 'center'},
            {field: 'passScore', title: '及格分数', width: 86, align: 'center'},
            {
                field: 'openOrClose', title: '开闭卷', width: 180, align: 'center', templet: function (data) {
                    if (data.openOrClose == 0)
                        return "闭卷";
                    else
                        return "开卷";
                }
            },
            {
                field: 'isMakeup', title: '是否允许补考', width: 180, align: 'center', templet: function (data) {
                    if (data.isMakeup == 0)
                        return "否";
                    else
                        return "是";

                }
            },
            {title: '操作', width: 210, fixed: "right", align: "center", templet: function (data) {
                /*console.log("类型："+typeof data + "value：" + data+"结构："+JSON.stringify(data));*/
                    var startTime = layui.util.toDateString(data.examStartTime, 'yyyy-MM-dd HH:mm:ss');
                    var endTime = layui.util.toDateString(data.examEndTime, 'yyyy-MM-dd HH:mm:ss');
                    if((data.examStartTime<=(new Date()).getTime()) &&((new Date()).getTime()<=data.examEndTime)){
                        return " <a class=\"layui-btn layui-btn-xs layui-btn-danger\" lay-event=\"openOrCloseExam\" style=\"padding:0px !important;border-radius: 12px !important;background-color:#FFFFFF !important;\">\n" +
                            "        <input id=\"openOrCloseExam"+data.id+"\" check=\"true\" checked type=\"checkbox\"  name=\"openOrCloseExam\" lay-skin=\"switch\" lay-filter=\"filter"+data.id+"\" lay-text=\"开启|关闭\">\n" +
                            "        </a>\n" +
                            "        <a class=\"layui-btn layui-btn-xs\" lay-event=\"edit\">编辑</a>\n" +
                            "        <a class=\"layui-btn layui-btn-xs layui-btn-danger\" lay-event=\"del\">删除</a>"
                    }else{
                        return " <a class=\"layui-btn layui-btn-xs layui-btn-danger\" lay-event=\"openOrCloseExam\" style=\"padding:0px !important;border-radius: 12px !important;background-color:#FFFFFF !important;\">\n" +
                            "        <input id=\"openOrCloseExam"+data.id+"\" check=\"false\" type=\"checkbox\"  name=\"openOrCloseExam\" lay-skin=\"switch\" lay-filter=\"filter"+data.id+"\" lay-text=\"开启|关闭\">\n" +
                            "        </a>\n" +
                            "        <a class=\"layui-btn layui-btn-xs\" lay-event=\"edit\">编辑</a>\n" +
                            "        <a class=\"layui-btn layui-btn-xs layui-btn-danger\" lay-event=\"del\">删除</a>"
                    }

                } }
        ]]
    });


    //搜索
    $(".search_btn").on("click", function () {
        var field = $(".select-retrieval-column").val(),
            value = $(".searchVal").val();
        if (field !== '' && value !== '') {
            table.reload("examListTable", {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    field: field, //the name of field searched in database.
                    value: value  //搜索的关键字*/
                }
            })
        } else if (field === '') {
            layer.msg("请选择检索列");
        } else {
            layer.msg("请输入搜索内容");
        }
    });

    $(".remove_retrieval_btn").click(function () {
        $(".searchVal").val('');
        // $(".select-retrieval-column").options[0].selected(true);
        table.reload("examListTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                field: ''   //set field as '' to avoid error to retrieve data in db.
            }
        })
    });

    //本模块暂时不需要上传功能(省去)


    $(".addNews_btn").click(function () {
        addNews();
    });

    //添加和修改考试信息
    function addNews(original_data) {
        var index = layui.layer.open({
            title: "添加考试",
            type: 2,
            area: ['100%', '100%'],
            fixed: false,
            content: "examAdd.html",
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (original_data) {
                    body.find(".id").val(original_data.id);
                    body.find(".examName").val(original_data.examName);
                    body.find(".examTime").val(original_data.examTime);
                    body.find(".examStartTime").val(layui.util.toDateString(original_data.examStartTime, 'yyyy-MM-dd HH:mm:ss'));
                    body.find(".examEndTime").val(layui.util.toDateString(original_data.examEndTime, 'yyyy-MM-dd HH:mm:ss'));
                    body.find(".examTimeRange").val(layui.util.toDateString(original_data.examStartTime, 'yyyy-MM-dd HH:mm:ss') + " - " + layui.util.toDateString(original_data.examEndTime, 'yyyy-MM-dd HH:mm:ss'));
                    body.find(".radioNum").val(original_data.radioNum);
                    body.find(".radioScore").val(original_data.radioScore);
                    body.find(".checkNum").val(original_data.checkNum);
                    body.find(".checkScore").val(original_data.checkScore);
                    body.find(".judgeNum").val(original_data.judgeNum);
                    body.find(".judgeScore").val(original_data.judgeScore);
                    body.find(".fillNum").val(original_data.fillNum);
                    body.find(".fillScore").val(original_data.fillScore);
                    body.find(".passScore").val(original_data.passScore);
                    body.find(".openOrClose").val(original_data.openOrClose);
                    body.find(".isMakeup").val(original_data.isMakeup);

                    form.render();
                }
                setTimeout(function () {
                    layui.layer.tips('点击此处返回考试列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        });
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize", function () {
            layui.layer.full(index);
        })
    }


    //批量删除
    $(".delAll_btn").click(function () {
        var checkStatus = table.checkStatus('examListTable'),
            data = checkStatus.data,
            examId = [];
        if (data.length > 0) {
            for (var i in data) {
                examId.push(data[i].id);
            }
            layer.confirm('确定删除选中的考试吗？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/exam/deleteExam-multiple.do", {
                    examId: examId  //将需要删除的stuNo作为参数传入
                }, function (data) {
                    layer.msg(JSON.parse(data)['msg']);     //"删除成功!" or "清空成功!" from backend.
                    tableIns.reload();
                    layer.close(index);
                });
            });
        } else {
            layer.msg("请选择需要删除的考试");
        }
    });

    //清空所有考试
    $(".clearAllexam_btn").click(function () {
        layer.confirm('确认清空此考试列表吗?', {icon: 3, title: '提示信息'}, function (index) {
            $.post("/exam/clear.do", function (data) {
                layer.msg(JSON.parse(data)['msg']);     //"清空成功!" from backend.
                tableIns.reload();
                layer.close(index);
            });
        });
    });

    //列表操作
    table.on('tool(examList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'edit') { //编辑
            addNews(data);
        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此考试？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/exam/deleteExam.do", {
                    examId: data.id  //将需要删除的考试id作为参数传入
                }, function (data) {
                    tableIns.reload();
                    layer.close(index);
                    layer.msg("删除成功");

                })
            });
        }
        else if (layEvent === 'openOrCloseExam') { //开启考试
                form.on("switch(filter"+data.id+")", function(data4){

                    var check = $("#"+"openOrCloseExam"+data.id).attr("check",data4.elem.checked+"");


                    if(data4.elem.checked){

                        var examStart = layui.util.toDateString(((new Date()).getTime()), 'yyyy-MM-dd HH:mm:ss');
                        var examEnd = layui.util.toDateString((((new Date()).getTime()) + (data.examTime * 60 *1000)), 'yyyy-MM-dd HH:mm:ss');


                        //验证此时加入的时间段是否与数据库中各个考试的时间段冲突，冲突则禁止加入！
                        $.ajax({
                            url : "/exam/queryAppointTimeQuantum.do",
                            type : "post",
                            data:{id:data.id,examStartTime:examStart,examEndTime:examEnd},
                            dataType: "text",
                            success : function(data2){
                                if(parseInt(data2)===0) {
                                    $.ajax({
                                        url: "/exam/updateTimeRangeById.do",
                                        type: "post",
                                        data: {id:data.id, examStartTime: examStart, examEndTime: examEnd},
                                        dataType: "json",
                                        success: function (data3) {
                                            if (data3.status === 200) {
                                                console.log(data4.elem);
                                                layer.alert("考试："+data.examName+"开启成功！",{icon: 6});

                                                table.reload("examListTable", {
                                                    page: {
                                                        curr: 1 //重新从第 1 页开始
                                                    },
                                                    where: {
                                                        field: ''   //set field as '' to avoid error to retrieve data in db.
                                                    }
                                                })


                                            } else if(data3.status === 500){
                                                layer.msg("开启失败！",{icon: 5});
                                                /*$('#openOrCloseExam').attr("checked", false);*/
                                                /*$("#"+"openOrCloseExam"+data.id).removeAttr('checked');*/
                                            }

                                        }
                                    });
                                }
                                else if(parseInt(data2)>0){
                                    console.log(data4.elem);
                                    layer.alert("此时间段与数据库中某个时间段冲突！考试："+data.examName+"开启失败！",{icon: 5});
                                    table.reload("examListTable", {
                                        page: {
                                            curr: 1 //重新从第 1 页开始
                                        },
                                        where: {
                                            field: ''   //set field as '' to avoid error to retrieve data in db.
                                        }
                                    })

                                }
                                else if(parseInt(data2)===-1){

                                    layer.alert("时间段为空！请重新选择",{icon: 5});
                                }
                            },
                            fail:function(data2){
                                layer.msg("失败");

                            },
                            error: function (data2) {
                                layer.msg("错误");
                            }

                        });

                    }else{

                        var examEnd = layui.util.toDateString(((new Date()).getTime()), 'yyyy-MM-dd HH:mm:ss');
                        var examStart = layui.util.toDateString((((new Date()).getTime()) - (data.examTime * 60 *1000)), 'yyyy-MM-dd HH:mm:ss');
                        $.ajax({
                            url: "/exam/updateTimeRangeById.do",
                            type: "post",
                            data: {id:data.id, examStartTime: examStart, examEndTime: examEnd},
                            dataType: "json",
                            success: function (data3) {
                                if (data3.status === 200) {
                                    console.log(data4.elem);
                                    layer.alert("考试："+data.examName+"关闭成功！",{icon: 6});

                                    table.reload("examListTable", {
                                        page: {
                                            curr: 1 //重新从第 1 页开始
                                        },
                                        where: {
                                            field: ''   //set field as '' to avoid error to retrieve data in db.
                                        }
                                    })

                                } else if(data3.status === 500){
                                    layer.msg("关闭失败！",{icon: 5});

                                }

                            }
                        });

                    }


                });





             //========================================




        }





    });

});