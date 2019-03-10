layui.use(['form', 'layer', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;
    //用户列表
    var tableIns = table.render({
        elem: '#userList',
        url: '/user/allManger.do',
        cellMinWidth: 95,
        page: true,
        height: "full-120",
        limits: [15, 20, 30, 50],
        limit: 15,
        id: "userListTable",
        cols: [[
            {type: "numbers", title: '序号', fixed: "center"},
            {field: 'id', align: "center", hide: true},
            {field: 'studentNo', title: '账号', align: "center"},
            {field: 'name', title: '姓名', align: "center"},
            {
                field: 'type', title: '类型', align: "center", templet: function (d) {
                    if (d.type == "ROOT")
                        return "超级管理员";
                    else
                        return "管理员";
                }
            },
            {title: '操作', templet: '#userListBar', align: "center"}
        ]]
    });
    var active = {
        reload: function () {
            var name = $(".searchVal").val();
            //执行重载
            table.reload('userListTable', {
                url: '/user/dimQueryMangerByName.do',
                method: 'get',
                page: {curr: 1}, //重新从第 1 页开始
                where: {name: name}//传入日期参数
            });
        }
    };
    $('.search_btn').on('click', function () {
        var type = $(this).data('type');
        //不能为空验证
        if ($('.searchVal').val() == "") {
            layer.msg("请输入用户姓名！！");
            return false;
        }
        active[type] ? active[type].call(this) : '';
    });

    //添加用户
    function addUser(edit) {
        layui.layer.open({
            type: 2,
            anim: 2,
            isOutAnim: false,
            title: ['添加用户', 'font-size:14px;'],
            id: 'news_add',
            area: ['540px', '318px'],
            fixed: false, //不固定
            maxmin: false,
            shade: 0.4,//不显示遮罩
            content:"./userAdd.html",
            end:function () {
                table.reload('userListTable', {
                    url: '/user/allManger.do',
                    method: 'get',
                    page: {curr: 1}, //重新从第 1 页开始
                });
            }
        });

   /*     var index = layui.layer.open({
            title: "添加用户",
            type: 2,
            content: "userAdd.html",
            success: function (layero, index) {
            }
        })
        layui.layer.full(index);
        window.sessionStorage.setItem("index", index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize", function () {
            layui.layer.full(window.sessionStorage.getItem("index"));
        })*/
    }

    $(".addNews_btn").click(function () {
        addUser();
    })

    //列表操作
    table.on('tool(userList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;
        if (layEvent === 'del') { //删除
            if (data.type == "ROOT") {
                layer.msg("超级管理员不可删除！！！", {time: 1500});
            } else {

                layer.confirm('确定删除此用户？', {icon: 3, title: '提示信息'}, function (index) {
                    $.post("/user/delete-individual.do", {
                        stuId: data.id  //将需要删除的newsId作为参数传入
                    }, function (data) {
                        if (data.code != 0) {

                            layer.close(index);
                            tableIns.reload();
                        } else {
                            layer.msg("删除失败！！", {time: 400});
                        }
                    })
                });
            }

        }
    });

})
