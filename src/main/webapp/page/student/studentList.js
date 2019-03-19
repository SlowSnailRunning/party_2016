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
            {field: 'id', title: 'ID', width: 86, align: "center", hide: true},
            {field: 'idx', title: '序号', width: 86, align: "center"},
            {field: 'grade', title: '年级', width: 86, align: "center"},
            {field: 'department', title: '学院', width: 100, align: "center"},
            {field: 'major', title: '专业', width: 160, align: "center"},
            {field: 'name', title: '姓名', width: 100, align: "center"},
            {field: 'studentNo', title: '学号', width: 150, align: 'center'},
            {field: 'partyNumber', title: '党校号', width: 86, align: 'center'},
            {field: 'examStateStr', title: '考试状态', width: 86, align: 'center'},
            {field: 'examScore', title: '考试成绩', width: 86, align: 'center'},
            {field: 'makeUpScore', title: '补考成绩', width: 86, align: 'center'},
            {title: '操作', width: 190, templet: '#studentListBar', fixed: "right", align: "center"}
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
        var field = $(".select-retrieval-column").val(),
            value = $(".searchVal").val();
        if (field !== '' && value !== '') {
            table.reload("studentListTable", {
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
        table.reload("studentListTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                field: ''   //set field as '' to avoid error to retrieve data in db.
            }
        })
    });

    $(".addNewsList_btn").click(function () {
        // 0:cover,1:append.
        uploadFile(1);

        /*layer.confirm('请选择上传方式:', {btn: ['覆盖', '附加']},
            function (index) {
                uploadFile(0);
                layer.close(index);
            },
            function () {
                uploadFile(1);
            });*/


    });

    function uploadFile(type_upload) {
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
            , url: projectName + '/user/upload.do?type=' + type_upload
            , accept: 'file'
            , exts: 'xls|xlsx'
            , done: function (res, index, upload) { //上传后的回调
                layer.msg(res['msg']); //show the message from the backend.
                if (res['code'] === 0)
                    tableIns.reload();  //if import succeeded,reload this table.
            }
            , error: function () {
            }
        });
    }

    $(".addNews_btn").click(function () {
        addNews();
    });

    //添加和修改考生信息
    function addNews(original_data) {
        var index = layui.layer.open({
            title: "添加学生",
            type: 2,
            content: "studentAdd.html",
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);
                if (original_data) {
                    body.find(".id").val(original_data.id);
                    body.find(".idx").val(original_data.idx);
                    body.find(".grade").val(original_data.grade);
                    body.find(".department").val(original_data.department);
                    body.find(".major").val(original_data.major);
                    body.find(".name").val(original_data.name);
                    body.find(".studentNo").val(original_data.studentNo);
                    body.find(".partyNumber").val(original_data.partyNumber);
                    form.render();
                }
                setTimeout(function () {
                    layui.layer.tips('点击此处返回考生列表', '.layui-layer-setwin .layui-layer-close', {
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
        var checkStatus = table.checkStatus('studentListTable'),
            data = checkStatus.data,
            stuId = [];
        if (data.length > 0) {
            for (var i in data) {
                stuId.push(data[i].id);
            }
            layer.confirm('确定删除选中的学生吗？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/user/delete-multiple.do", {
                    stuId: stuId  //将需要删除的stuNo作为参数传入
                }, function (data) {
                    layer.msg(JSON.parse(data)['msg']);     //"删除成功!" or "清空成功!" from backend.
                    tableIns.reload();
                    layer.close(index);
                });
            });
        } else {
            layer.msg("请选择需要删除的学生");
        }
    });

    //清空所有考生
    $(".clearAllStu_btn").click(function () {
        layer.confirm('确认清空此学生列表吗?', {icon: 3, title: '提示信息'}, function (index) {
            $.post("/user/clear.do", function (data) {
                layer.msg(JSON.parse(data)['msg']);     //"清空成功!" from backend.
                tableIns.reload();
                layer.close(index);
            });
        });
    });

    //列表操作
    table.on('tool(studentList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;
         // if(layEvent=="modify")
         // {
         //     console.log("asdfsaf");
         // }


        if (layEvent === 'edit') { //编辑
            addNews(data);
        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此学生？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/user/delete-individual.do", {
                    stuId: data.id  //将需要删除的学生学号作为参数传入
                }, function (data) {
                    tableIns.reload();
                    layer.close(index);
                    layer.msg("删除成功");

                })
            });
        } else if (layEvent === 'look') { //预览
            layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问")
        } else if (layEvent === "modify") { //预览
              console.log(12312);
            layer.confirm('确认重置考生状态吗？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/user//modify.do", {
                    stu_no: data.studentNo  //将需要删除的学生学号作为参数传入
                }, function (data) {
                    if (data == 1) {
                        layer.msg("重置成功！！！", {time:1000,
                            end: function () {
                                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.layer.close(index); //再执行关闭
                            }
                        });
                    } else {
                        layer.msg("重置失败请重试！！！", {time:2000,
                            end: function () {
                                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.layer.close(index); //再执行关闭
                            }
                        });
                    }
                });
            });
        }

    });

});