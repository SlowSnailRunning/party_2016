layui.use('table', function () {
    var table = layui.table;
    var loading = layer.load();
    table.render({
        elem: '#test'
        , url: '/statisticsCorrect.do'
        , toolbar: '#toolbarDemo'
        , title: '考试分析数据'
        , limit: 10
        , loading: true
        , height : "full-25"
        /*,toolbar: 'default'*/
        , defaultToolbar: ['filter', 'print', 'exports']
        , limits: [5,10, 20, 100, 200]
        , cols: [[
            /*{type: 'checkbox', fixed:'left', width:50},*/
            {title: '序号', type: 'numbers'},
            {field: 'id', title: 'ID', width: 60, align: "center", hide: true},
            {field: 'intro', title: '内容', align: "center"},
            {field: 'type', title: '类型', width: 150, align: 'center'},
            {field: 'selected', title: '选中次数', width: 150, align: 'center', sort: true},
            {field: 'correct', title: '正确率', align: 'center', sort: true},
            {title: '操作', width: 170, templet: '#barDemo', fixed: "right", align: "center"}
        ]]
        , page: true
    });
    layer.close(loading);
    table.on('tool(test)', function (obj) {
        var data = obj.data;
        if (data.type == "单选" || data.type == "多选") {
            if (obj.event === 'look') {
                $.post('/getQuestion.do?question_id=' + data.id, {}, function (str) {
                    var obj = JSON.parse(str);
                    var question = "<br><font size='3.5' color='#2F4056'>optionA:&#8195" + obj.optionA + "<br><br>optionB:&#8195" + obj.optionB + "<br><br>optionC:&#8195" + obj.optionC + "<br><br>optionD:&#8195" + obj.optionD + "<font>";
                    layer.open({
                        type: 1,
                        title: "选项信息",
                        /*shade: 0,*/
                        area: ['600px', '300px'],
                        content: question //注意，如果str是object，那么需要字符拼接。
                    });
                });
            }
        } else {
            layer.msg('请看表格', {time: 400});
        }
    });
});