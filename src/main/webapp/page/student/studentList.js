layui.use(['form', 'layer', 'laydate', 'upload', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //新闻列表
    var tableIns = table.render({
        elem: '#studentList',
        url: '/user/all.do',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limit: 20,
        limits: [10, 15, 20, 25],
        id: "studentListTable",
        cols: [[
            {type: 'checkbox', fixed: 'left', width: 50},
            {field: 'idx', title: 'ID', width: 86, align: "center"},
            {field: 'grade', title: '年级', width: 86, align: "center"},
            {field: 'department', title: '学院', width: 100, align: "center"},
            {field: 'major', title: '专业', width: 160, align: "center"},
            {field: 'name', title: '姓名', width: 100, align: "center"},
            {field: 'studentNo', title: '学号', width: 150, align: 'center'},
            {field: 'partyNumber', title: '党校号', width: 86, align: 'center'},
            {field: 'examStateStr', title: '考试状态', width: 86, align: 'center'},
            {field: 'examScore', title: '考试成绩', width: 86, align: 'center'},
            {field: 'makeUpScore', title: '补考成绩', width: 86, align: 'center'},
            {title: '操作', width: 170, templet: '#newsListBar', fixed: "right", align: "center"}
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

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        if ($(".searchVal").val() != '') {
            table.reload("newsListTable", {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    key: $(".searchVal").val()  //搜索的关键字
                }
            })
        } else {
            layer.msg("请输入搜索内容");
        }
    });
    $(".addNewsList_btn").click(function () {
        // layer.msg("dsfdsfsaf");
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
    });

    $(".addNews_btn").click(function () {
        addNews();
    });

    //添加学生
    function addNews(edit) {
        var index = layui.layer.open({
            title: "添加学生",
            type: 2,
            content: "studentAdd.html",
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (edit) {
                    body.find(".studentName").val(edit.studentName);
                    body.find(".studentNumber").val(edit.studentNumber);
                    body.find(".studentGrade").val(edit.studentGrade);
                    body.find(".studentSex input[value=" + edit.studentSex + "]").prop("checked", "checked");
                    body.find(".studentPhoneNumber").val(edit.studentPhoneNumber);
                    body.find(".studentAddress").val(edit.studentAddress);
                    body.find(".studentBirthday").val(edit.studentBirthday);


                    form.render();
                }
                setTimeout(function () {
                    layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
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
        var checkStatus = table.checkStatus('newsListTable'),
            data = checkStatus.data,
            stuNo = [];
        if (data.length > 0) {
            for (var i in data) {
                stuNo.push(data[i].studentNo);
            }
            layer.confirm('确定删除选中的学生吗？', {icon: 3, title: '提示信息'}, function (index) {
                alert("jifdsji");
                $.post("/user/delete1.do", {
                    stuNo: stuNo  //将需要删除的newsId作为参数传入
                }, function (data) {
                    tableIns.reload();
                    layer.close(index);
                });
            })
        } else {
            layer.msg("请选择需要删除的文章");
        }
    });

    //列表操作
    table.on('tool(studentList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'edit') { //编辑
            alert(data.studentNo + "  " + data.name);
            addNews(data);
            alert(data.studentNo + "  " + data.name);
        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此学生？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/user/delete.do", {
                    studentNo: data.studentNo  //将需要删除的学生学号作为参数传入
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