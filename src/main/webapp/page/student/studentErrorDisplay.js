layui.use(['form', 'layer', 'layedit', 'laydate', 'table', 'upload'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        table = layui.table,
        $ = layui.jquery;

    var tableIns = table.render({
        elem: '#wrongQuesDisplayList',
        url: projectName+'/answer/displayError.do',
        cellMinWidth: 80,
        page: true,
        height: "full",
        limit: 20,
        limits: [10, 15, 20, 25],
        id: "studentListTable",
        cols: [[
            {field: 'type', title: '题目类型', width: 86, align: "center"},
            {field: 'questionId', title: '题号', width: 86, align: "center"},
            {field: 'intro', title: '题干', width: 220, align: "center"},
            {field: 'options_content', title: '选项内容', width: 200, align: "center"},
            {field: 'result', title: '正确答案', width: 86, align: "center"},
            {field: 'answer', title: '考生答案', width: 86, align: "center"}
        ]]
    });

    tableIns.reload({
        where: {
            stuNo: $("#studentNo").val()
        }
    });

});