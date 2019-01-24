layui.use(['form','layer','laydate','upload','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //分析数据列表
    var tableIns = table.render({
        elem: '#statisticsList',
        url : '/statisticsCorrect.do',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        limits:[10,15,20,25],
        id : "statisticsListTable",
        toolbar: 'default',
        defaultToolbar: ['filter', 'print', 'exports'],
        cols:[[
            {type: 'checkbox', fixed:'left', width:50},
            {field: 'id', title: 'ID', width:60, align:"center"},
            {field: 'intro', title: '内容',align:"center"},
            {field: 'type', title: '类型',width:150,align:'center'},
            {field: 'selected', title: '选中次数', width:150, align:'center' },
            {field: 'correct', title: '正确率',align:'center'},
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
            layer.msg("请输入搜索内容");
        }
    });
    $(".addNewsList_btn").click(function(){
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
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

        //创建一个上传组件
        upload.render({
            elem: '#uploadDiv'
            ,url: projectName+'/user/upload.do'
            ,accept: 'file'
            ,exts: 'xls|xlsx'
            ,done: function(res, index, upload){ //上传后的回调
                layer.msg("su");
            }
            ,error: function(){
                layer.msg("error");
            }
        })
    })

    $(".addNews_btn").click(function(){
        addNews();
    })
    //添加学生
    function addNews(edit){
        var index = layui.layer.open({
            title : "添加学生",
            type : 2,
            content : "studentAdd.html",
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