layui.use(['form','layer','layedit','laydate','upload'],function(){
    var form = layui.form,
    layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;
        $('.submit-btn-submit').css({"left":"-32px","margin-left":"50%"});
        $('.examTimeRange').css("width",$('.passScore').css('width'));
        $('.label-zongfen').css("font-size","17px");
        $('.label-fen').css("font-size","17px");
        $('.examAllScore').css({"color":"blue","font-size":"19px"});
        $('.examAllScore').text("100");
       /* alert($('.examAllScore').text());*/
        //日期时间范围
        laydate.render({
            elem: '#examTimeRangeId'
            ,type: 'datetime'
            ,range: true
            ,done: function(value, date, endDate){
              /*  console.log(value); //得到日期生成的值，如：2017-08-18
                console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。*/
                var str=new String();
                var arr=new Array();

                //可以用字符或字符串分割
                arr=value.split(' - ');

                var laydate = layui.laydate;
                //日期时间选择器
                laydate.render({
                    elem: '#examStartTimeId'
                    , type: 'datetime'
                    ,format: 'yyyy-MM-dd HH:mm:ss'
                    ,value:arr[0]
                });
                //日期时间选择器
                laydate.render({
                    elem: '#examEndTimeId'
                    , type: 'datetime'
                    ,format: 'yyyy-MM-dd HH:mm:ss'
                    ,value:arr[1]
                });
                console.log($('.examStartTime').val());
                console.log($('.examEndTime').val());
            /*    alert($('.examEndTime').val());*/

            }
        });




        //监听提交
        form.on('submit(signAdd)', function (data) {
            // console.log(data.field);
            // return;
            //signAdd为提交按钮的id

            $.post("/exam/add-update.do", data.field,
                function (data) {
                    if (data.status === 200) {
                        layer.msg(data['msg']);
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index); // 关闭layer
                        //使父页面重新刷新
                        parent.location.reload();
                    } else {
                        layer.msg("添加失败");
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index); // 关闭layer
                    }
                }, "json");
            return false;
        });



    //用于同步编辑器内容到textarea
    layedit.sync(editIndex);

    //上传缩略图
    upload.render({
        elem: '.thumbBox',
        url: '../../json/userface.json',
        method : "get",  //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
        done: function(res, index, upload){
            var num = parseInt(4*Math.random());  //生成0-4的随机数，随机显示一个头像信息
            $('.thumbImg').attr('src',res.data[num].src);
            $('.thumbBox').css("background","#fff");
        }
    });

    //格式化时间
    function filterTime(val){
        if(val < 10){
            return "0" + val;
        }else{
            return val;
        }
    }
    //定时发布
    var time = new Date();
    var submitTime = time.getFullYear()+'-'+filterTime(time.getMonth()+1)+'-'+filterTime(time.getDate())+' '+filterTime(time.getHours())+':'+filterTime(time.getMinutes())+':'+filterTime(time.getSeconds());
    laydate.render({
        elem: '#release',
        type: 'datetime',
        trigger : "click",
        done : function(value, date, endDate){
            submitTime = value;
        }
    });
    form.on("radio(release)",function(data){
        if(data.elem.title == "定时发布"){
            $(".releaseDate").removeClass("layui-hide");
            $(".releaseDate #release").attr("lay-verify","required");
        }else{
            $(".releaseDate").addClass("layui-hide");
            $(".releaseDate #release").removeAttr("lay-verify");
            submitTime = time.getFullYear()+'-'+(time.getMonth()+1)+'-'+time.getDate()+' '+time.getHours()+':'+time.getMinutes()+':'+time.getSeconds();
        }
    });

    form.verify({
        newsName : function(val){
            if(val == ''){
                return "文章标题不能为空";
            }
        },
        content : function(val){
            if(val == ''){
                return "文章内容不能为空";
            }
        }
    })
    form.on("submit(addNews)",function(data){
        //截取文章内容中的一部分文字放入文章摘要
        var abstract = layedit.getText(editIndex).substring(0,50);
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});

        setTimeout(function(){
            top.layer.close(index);
            top.layer.msg("文章添加成功！");
            layer.closeAll("iframe");
            //刷新父页面
            parent.location.reload();
        },500);
        return false;
    })

    //预览
    form.on("submit(look)",function(){
        layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问");
        return false;
    })

    //创建一个编辑器
    var editIndex = layedit.build('news_content',{
        height : 535,
        uploadImage : {
            url : "../../json/newsImg.json"
        }
    });

})

