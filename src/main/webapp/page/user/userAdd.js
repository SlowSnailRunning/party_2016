layui.use(['form', 'layer', 'jquery'], function () {
    var form = layui.form
    /*  layer = parent.layer === undefined ? layui.layer : top.layer,*/
    $ = layui.jquery;
    form.on("submit(addUser)", function (data) {
        //弹出loading
        /* var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});*/
        // 实际使用时的提交信息
        $.post("/user/addManger.do", {
            studentNo: data.field.account,
            name: data.field.userName,  //登录名
            type: data.field.type,  //会员等级
            sex: data.field.sex,    //用户简介
        }, function (data) {
            var data = JSON.parse(data);
            if (data.code == 0) {
                layer.msg(data.msg, {time: 400}, function () {
                    $("input[type='text']").val("");
                });
            } else if (data.code == 1) {
                layer.msg(data.msg, {time: 400});
            } else {
                layer.msg(data.msg, {time: 400});
            }
        })
        return false;
    });
    form.verify({account: [/^[\S]{12,15}$/, '密码必须12到15位，且不能出现空格']});
});