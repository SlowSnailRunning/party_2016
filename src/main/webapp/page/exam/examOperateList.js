layui.use(['form', 'layer', 'laydate', 'upload', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //考试列表
    var tableIns = table.render({
        elem: '#examList',
        url: '/exam/queryAllExamList.do',
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
            {field: 'examTime', title: '考试时间', width: 86, align: "center"},
            {field: 'createTime', title: '考试创建时间', width: 160, align: "center"},
            {field: 'examStartTime', title: '考试开始时间', width: 160, align: "center"},
            {field: 'examEndTime', title: '考试结束时间', width: 160, align: "center"},
            {field: 'radioNum', title: '单选题数量', width: 86, align: 'center'},
            {field: 'radioScore', title: '单选题分数', width: 86, align: 'center'},
            {field: 'checkNum', title: '多选题数量', width: 86, align: 'center'},
            {field: 'checkScore', title: '多选题分数', width: 86, align: 'center'},
            {field: 'judgeNum', title: '判断题数量', width: 86, align: 'center'},
            {field: 'judgeScore', title: '判断题分数', width: 86, align: 'center'},
            {field: 'fillNum', title: '填空题数量', width: 86, align: 'center'},
            {field: 'fillScore', title: '填空题分数', width: 86, align: 'center'},
            {field: 'saqNum', title: '简答题数量', width: 86, align: 'center'},
            {field: 'saqScore', title: '简答题分数', width: 86, align: 'center'},
            {field: 'passScore', title: '及格分数', width: 86, align: 'center'},
            {field: 'isMakeup', title: '是否允许补考', width: 86, align: 'center'},
            {title: '操作', width: 120, templet: '#examListBar', fixed: "right", align: "center"}
        ]]
    });

    //是否置顶
    form.on('switch(newsTop)', function (data) {
        var index = layer.msg('修改中，请稍候', {icon: 16, time: false, shade: 0.8});
        setTimeout(function () {
            layer.close(index);
            if (data.elem.checked) {
                layer.msg("置顶成功！");
            } else {
                layer.msg("取消置顶成功！");
            }
        }, 500);
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
                    value: value  //搜索的关键字
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

    //本模块暂时不需要这个上传功能
/*    $(".addNewsList_btn").click(function () {
        var upload = layui.upload; //得到 upload 对象

        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        //var curWwwPath = window.document.location.href;

        //var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        // var localhostPaht = curWwwPath.substring(0, pos);
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        //获取带"/"的项目名，如：/uimcardprj
        var projectName = pathName.substring(0, pathName.substr(1).indexOf("/page") + 1);

        //创建一个上传组件
        upload.render({
            elem: '#uploadDiv'
            , url: projectName + '/user/upload.do'
            , accept: 'file'
            , exts: 'xls|xlsx'
            , done: function (res, index, upload) { //上传后的回调
                layer.msg(res['msg']); //show the message from the backend.
                if (res['code'] === 0)
                    tableIns.reload();  //if import succeeded,reload this table.
            }
            , error: function () {
            }
        })
    });*/  //

    $(".addNews_btn").click(function () {
        addNews();
    });

    //添加和修改考试信息
    function addNews(original_data) {
        var index = layui.layer.open({
            title: "添加考试",
            type: 2,
            content: "examAdd.html",
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (original_data) {
                    body.find(".id").val(original_data.id);
                    body.find(".examName").val(original_data.examName);
                    body.find(".examTime").val(original_data.examTime);
                    body.find(".examStartTime").val(original_data.examStartTime);
                    body.find(".radioNum").val(original_data.radioNum);
                    body.find(".radioScore").val(original_data.radioScore);
                    body.find(".checkNum").val(original_data.checkNum);
                    body.find(".checkScore").val(original_data.checkScore);
                    body.find(".judgeNum").val(original_data.judgeNum);
                    body.find(".judgeScore").val(original_data.judgeScore);
                    body.find(".fillNum").val(original_data.fillNum);
                    body.find(".fillScore").val(original_data.fillScore);
                    body.find(".passScore").val(original_data.passScore);
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
        } else if (layEvent === 'look') { //预览
            layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问")
        }
    });

});