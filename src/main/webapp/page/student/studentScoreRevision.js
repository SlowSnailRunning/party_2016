layui.use(['form', 'layer', 'layedit', 'laydate', 'upload'], function () {
    var form = layui.form;
    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;

    //监听提交
    //signAdd为提交按钮的id
    form.on('submit(signAdd)', function (data) {
        $.post(projectName+"/user/add-update.do", data.field,
            function (data) {
                if (data.status === 200) {
                    layer.msg(data['msg']);
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index); // 关闭layer
                    //使父页面重新刷新
                    parent.location.reload();
                } else {
                    layer.msg("成绩修改失败");
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index); // 关闭layer
                }
            }, "json");
        return false;
    });

});