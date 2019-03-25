layui.use(['form', 'layer', 'table'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    //学生成绩表
    var tableIns = table.render({
        elem: '#studentScoreList',
        url: projectName + '/user/all.do',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limit: 20,
        limits: [10, 15, 20, 25],
        id: "studentScoreListTable",
        cols: [[
            {field: 'id', title: 'ID', width: 86, align: "center", hide: true},
            {field: 'idx', title: '序号', width: 86, align: "center"},
            {field: 'name', title: '姓名', width: 100, align: "center"},
            {field: 'studentNo', title: '学号', width: 150, align: 'center'},
            {field: 'examScore', title: '考试成绩', width: 86, align: 'center'},
            {field: 'makeUpScore', title: '补考成绩', width: 86, align: 'center'},
            {field: 'partyNumber', title: '党校号', width: 150, align: 'center'},
            {field: 'examStateStr', title: '考试状态', width: 86, align: 'center'},
            {field: 'major', title: '专业', width: 160, align: "center"},
            {field: 'department', title: '学院', width: 110, align: "center"},
            {field: 'grade', title: '年级', width: 86, align: "center"},
            {title: '操作', width: 160, templet: '#studentScoreListBar', fixed: "right", align: "center"}
        ]]
    });


    $(".search_btn").on("click", function () {
        var field = $(".select-retrieval-column").val(),
            value = $(".searchVal").val();
        if (field !== '' && value !== '') {
            table.reload("studentScoreListTable", {
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
        table.reload("studentScoreListTable", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                field: ''   //set field as '' to avoid error to retrieve data in db.
            }
        })
    });

    //列表操作
    table.on('tool(studentScoreList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;
        if (layEvent === 'edit_score') { //编辑
            layui.layer.open({
                type: 2,
                title: '考生: ' + data.name,
                content: 'studentScoreRevision.html',
                area: ['400px', '225px'],
                success: function (object, index) {
                    var body = layui.layer.getChildFrame('body', index);
                    if (data) {
                        body.find(".id").val(data.id);
                        body.find(".examScore").val(data.examScore);
                        body.find(".makeUpScore").val(data.makeUpScore);
                        form.render();
                    }
                }
            });
        }
        if (layEvent === 'view_error') {
            var index = layui.layer.open({
                type: 2,
                title: data.name + ' ' + data.studentNo + '  错题预览:',
                content: 'studentErrorDisplay.html',
                area: ['820px', '400px'],
                maxmin: true,
                success: function (object, index) {
                    var body = layui.layer.getChildFrame('body', index);
                    if (data) {
                        body.find("#studentNo").val(data.studentNo);
                        form.render();
                    }
                }
            });
            layui.layer.full(index);

        }
    });

});