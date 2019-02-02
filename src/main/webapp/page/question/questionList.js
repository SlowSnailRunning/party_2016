layui.use(['form', 'layer', 'laydate', 'upload', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //题库列表
    var tableIns = table.render({
        elem: '#questionList',
        url: '/question/selectQue.do',
        /* url:'questionList.json',*/
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limit: 20,
        limits: [10, 15, 20, 25],
        id: "questionListTable",
        cols: [[
            {type: 'checkbox', fixed: 'left', width: 50},
            {field: 'id', title: 'ID', width: 86, align: "center", hide: true},
            {title: '序号', type: 'numbers'},
            {field: 'intro', title: '题干', align: "center"},
            {field: 'optionA', title: '选项A', width: 110, align: "center"},
            {field: 'optionB', title: '选项B', width: 110, align: "center"},
            {field: 'optionC', title: '选项C', width: 110, align: "center"},
            {field: 'optionD', title: '选项D', width: 110, align: "center"},
            {field: 'result', title: '答案', width: 80, align: 'center'},
            {field: 'type', title: '试题类型', width: 100, align: 'center', templet: function (d) {
                    /*1：单选，2：多选，3：判断，4：填空，5：解答*/
                    var result = "";
                    switch (d.type) { case 1: result = "单选";  break; case 2: result = "多选";  break;
                        case 3: result = "判断";  break;  case 4: result = "填空";  break;
                        case 5: result = "解答";  break; default:  result = "未知错误";
                    }
                    return result;
                }
            },
            {
                field: 'state', title: '是否启用', width: 86, align: 'center', templet: function (d) {
                    return '<input type="checkbox" name="state" lay-filter="state" lay-skin="switch" lay-text="是|否" ' + d.state + '>'
                }
            },
        ]]
    });

    //是否启动
    var data2;
    table.on('row(questionList)', function (obj) {
        data2 = obj.data;
        /*        layer.alert(JSON.stringify(data2), {
                    title: '当前行数据：'
                });*/
    });
    form.on('switch(state)', function (data) {
        var index = layer.msg('修改中，请稍候', {icon: 16, time: false, shade: 0.8});
        var result = "";
        setTimeout(function () {
            /*            console.log(data2["id"]);
                        console.log(data2["state"]);
                        console.log(data2["state"]=="checked"?"":"checked");*/
            $.ajaxSettings.async = false;
            $.post("/question/modifyState.do", {
                state: data2["state"] == "checked" ? "" : "checked",
                id: data2["id"]
            }, function (res) {
                result = res;
            })
            layer.close(index);
            $.ajaxSettings.async = true;
            if (data.elem.checked) {
                if (result == "true")
                    layer.msg("启用成功！");
                else
                    layer.msg("启用失败！");
            } else {
                if (result == "true")
                    layer.msg("禁用成功！");
                else {
                    layer.msg("禁用失败！")
                }
            }
        }, 500);
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    /* console.log($('#search option:selected').val());*/
    /* var value=$('#search option:selected').val();*/
    var search;
    //默认关键字输入框隐藏
    $(".searchVal").hide();
    form.on('select(search)', function (data) {
        search = data.value;
        if (search != "intro") {
            $(".searchVal").hide();
        } else {
            $(".searchVal").show();
        }
        console.log(search); //得到被选中的值

    });

    $(".search_btn").on("click", function () {
        //按照单选题、多选题、、、、
        if (search != "intro") {
            table.reload("questionListTable", {
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    /* type: search,*/  //按类型搜索
                    /*context:"",*/

                },
                url: '/question/selectQue.do' + '?type=' + search
            })
            console.log("一");
        } else {
            //按关键字搜索
            if ($(".searchVal").val() != '') {
                table.reload("questionListTable", {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    },
                    where: {
                        /*type:"",*/
                        /*context:$(".searchVal").val() */  //按题干搜索
                    },
                    url: '/question/selectQue.do' + '?intro=' + $(".searchVal").val()
                })
                console.log("er");
                return;
            }

            layer.msg("请输入搜索内容");


        }
    });
    $(".addNewsList_btn").click(function () {
        if (true) {
            //tableIns为空
            addQue();
        } else {////tableIns不为空,第一次点击没有反应，第二次直接弹出来
            layer.confirm('发现题库数据不为空！！', {
                btn: ['追加导入', '为我清空题库后导入'] //按钮
            }, function () {
                addQue();
            }, function () {
                $.post("/question/clear.do", function (data) {
                    layer.msg(JSON.parse(data)['msg']);     //"清空成功!" from backend.
                });
                addQue();
                tableIns.reload();
            });
        }
    });

    function addQue() {
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

        //var index = layer.msg('文件上传中，请稍候', {icon: 16, time: false, shade: 0.8});
        //创建一个上传组件
        upload.render({
            elem: '#uploadDiv'
            , url: projectName + '/question/upload.do'
            , accept: 'file'
            , exts: 'xls|xlsx'
            , done: function (res, index, upload) { //上传后的回调
                //layer.close(index);
                layer.msg(res['msg']); //show the message from the backend.
                if (res['code'] === 0)
                    tableIns.reload();  //if import succeeded,reload this table.
            }
            , error: function () {
                //layer.close(index);
                layer.msg("网络故障，稍后再试吧！");
            }
        });
    }

    $(".addNews_btn").click(function () {
        addNews();
    });

    //添加和修改考生信息
    function addNews(original_data) {
        var index = layui.layer.open({
            title: "添加考题",
            type: 2,
            content: "questionAdd.html",
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
    $(".delChecked_btn").click(function () {
        var checkStatus = table.checkStatus('questionListTable'),
            data = checkStatus.data;
        if (data.length <= 0) {
            layer.msg("请选择需要删除的学生");
            return;
        }

        var havaExam = "";
        $.ajaxSettings.async = false;
        $.post("/exam/haveExam.do", function (res) {
            havaExam = res;
        })
        $.ajaxSettings.async = true;
        if (havaExam == "true") {
            //有考试，不允许删除题目
            layer.alert('考试期间，不允许删除题目！你可以禁用题目使其不在考卷中再次出现！', {icon: 4});
            tableIns.reload();
        } else {
            var queId = [];
            for (var i in data) {
                queId.push(data[i].id);
            }
            layer.confirm('确定删除选中的题目吗？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/question/delete-multiple.do", {
                    queId: queId  //将需要删除的stuNo作为参数传入
                }, function (data) {
                    if (data == "true") {
                        layer.msg("删除成功！")
                    } else {
                        layer.msg("删除失败！")
                    }
                    tableIns.reload();
                    layer.close(index);
                });
            });
        }
    });

    //清空所有
    $(".clearAllStu_btn").click(function () {
        $.post("/exam/haveExam.do", function (res) {
            if (res == "true") {
                layer.alert('考试期间，不允许清空题库！', {icon: 4});
                tableIns.reload();
            } else {
                layer.confirm('确认清空题库吗?', {icon: 3, title: '提示信息'}, function (index) {
                    $.post("/question/clear.do", function (data) {
                        if (data == 'true') {
                            layer.msg("清空成功!");     //"清空成功!" from backend.
                        } else {
                            layer.msg("清空失败!");
                        }
                        tableIns.reload();
                    });
                });
            }
        })

    });

    //列表操作
    table.on('tool(studentList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'edit') { //编辑
            addNews(data);
        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此学生？', {icon: 3, title: '提示信息'}, function (index) {
                $.post("/user/delete-individual.do", {
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