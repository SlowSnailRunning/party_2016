layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //新闻列表
    var tableIns = table.render({
        elem: '#studentList',
        url : '/user/all',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        limits:[10,15,20,25],
        id : "studentListTable",
        cols:[[
            {type: 'checkbox', fixed:'left', width:50},
            {field: 'studentId', title: 'ID', width:60, align:"center"},
            {field: 'studentName', title: '姓名',align:"center"},
            {field: 'studentNumber', title: '学号',width:150,align:'center'},
            {field: 'studentGrade', title: '班级', width:150, align:'center' },
            {field: 'studentSex', title: '性别',align:'center'},
            {field: 'studentPhoneNumber', title:'电话',width:150,align:'center'},
            {field: 'studentAddress', title: '地址', width:150,align:'center'},
            {field: 'studentBirthday', title: '出生日期',width:100,align:'center'},
            {field: 'createTime', title: '添加时间',width:100, align:'center'},
            {field: 'updateTime', title: '更新时间',width:100, align:'center'},
            /*{field: 'newsTop', title: '是否置顶', align:'center', templet:function(d){
                return '<input type="checkbox" name="newsTop" lay-filter="newsTop" lay-skin="switch" lay-text="是|否" '+d.newsTop+'>'
            }},
            {field: 'newsTime', title: '发布时间', align:'center', minWidth:110, templet:function(d){
                return d.newsTime.substring(0,10);
            }},*/
            {title: '操作', width:170, templet:'#newsListBar',fixed:"right",align:"center"}
        ]]
    });

    //是否置顶
    form.on('switch(newsTop)', function(data){
        var index = layer.msg('修改中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.close(index);
            if(data.elem.checked){
                layer.msg("置顶成功！");
            }else{
                layer.msg("取消置顶成功！");
            }
        },500);
    })

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload("newsListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    key: $(".searchVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

    //添加学生
    function addNews(edit){
        var index = layui.layer.open({
            title : "添加学生",
            type : 2,
            content : "studentAdd.htm",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".studentName").val(edit.studentName);
                    body.find(".studentNumber").val(edit.studentNumber);
                    body.find(".studentGrade").val(edit.studentGrade);
                    body.find(".studentSex input[value="+edit.studentSex+"]").prop("checked","checked");
                    body.find(".studentPhoneNumber").val(edit.studentPhoneNumber);
                    body.find(".studentAddress").val(edit.studentAddress);
                    body.find(".studentBirthday").val(edit.studentBirthday);


                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })
    }
    $(".addNews_btn").click(function(){
        addNews();
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('newsListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].newsId);
            }
            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {
                // $.get("删除文章接口",{
                //     newsId : newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                tableIns.reload();
                layer.close(index);
                // })
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    })

    //列表操作
    table.on('tool(studentList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            alert(data.studentId+"  "+data.studentName);
            addNews(data);
            alert(data.studentId+"  "+data.studentName);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此学生？',{icon:3, title:'提示信息'},function(index){
                $.post("/user/delete",{
                     studentId : data.studentId  //将需要删除的newsId作为参数传入
                },function(data){

                    tableIns.reload();
                    layer.close(index);
                   layer.msg("删除成功");

                })
            });
        } else if(layEvent === 'look'){ //预览
            layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问")
        }
    });

})