layui.use(['layer','element','jquery','form','util', 'laydate'],function () {
    var $,element,form,util,laydate,layer;
    var setInt;
    util = layui.util;
    laydate = layui.laydate;
    form =layui.form;
    layer = layui.layer;
    $=layui.jquery;
    element = layui.element;
    var examId;
    $(function(){
        function loadexamCurrent(){
            $.ajax({
                type:"POST",
                url:projectName+'/exam/queryCurrentExamInformation.do',
                data:{},
                dataType:'json',
                contentType: "application/json;charset=utf-8;",
                success:function(res){
                    // console.log(res.data);
                    // console.log(res.data.length);
                    if(res.data.length!=0){
                        examId=res.data[0]['id'];
                    }
                    if(res && res.code===0){
                        var menuHtml="";
                        var obj = eval("("+JSON.stringify(res.data)+")");
                      /*  console.log("类型："+typeof obj[0] +"  value:" + obj[0]);*/
                        $('.examName').css("width",$('.examTime').css('width'));
                        $('.passScore').css("width",$('.examTime').css('width'));

                        $('.layui-input').css({"font-size":"17px"})
                        $('#peopel').css({"font-size":"18px","font-weight":"120","color":"#FF2200","line-height":"36px"})
                        $('#isMakeup').css({"font-size":"18px","font-weight":"120","color":"#FF2200","line-height":"36px"})
                        $('#remainTime').css({"font-size":"18px","font-weight":"120","color":"#FF2200","line-height":"36px"});
                        $('#examStatus').css({"font-size":"18px","font-weight":"120","color":"#FF2200","line-height":"36px"});

                        if(typeof (obj[0]) == "undefined" ){
                            $('#examStatus').html(" 暂 无 考 试 ");
                            clearInterval(setInt);
                            $("#btnExam").empty();
                            form.render();
                        }
                        else{
                            $("#btnExam").append(" <center>\n" +
                                "            <button class=\"layui-btn layui-btn-radius layui-btn-danger\" id=\"endExam\">立即结束本次考试</button>\n" +
                                "        </center>");

                            setInt=setInterval(function () {
                                $.ajax({
                                    url : projectName+"/exam/studentSize.do",
                                    type : "get",
                                    success : function(data){
                                        $("#peopel").text("      "+data+" 人 ");
                                    }
                                });
                            },5000);
                            $('#examStatus').html("  正 在 考 试 . . . ing");
                            $('.examName').val(obj[0].examName);
                            $('.examTime').val(obj[0].examTime+"分钟");
                            $('.passScore').val(obj[0].passScore+"分");

                            $('.residualTime').val(layui.util.toDateString(obj[0].examEndTime, 'yyyy-MM-dd HH:mm:ss'));
                            /*0：闭卷 ； 1：开卷*/
                            if(obj[0].openOrClose === 0){
                                $('.openOrClose').val('闭卷');
                            }
                            if(obj[0].openOrClose === 1){
                                $('.openOrClose').val("开卷");
                            }
                            if(obj[0].isMakeup === 0){
                                $('#isMakeup').html("       不允许补考");
                            }
                            if(obj[0].isMakeup === 1){
                                $('#isMakeup').html("       允许补考");
                            }


                            $('#currentQuestion').val("单选："+obj[0].radioScore+"(分/道) * "+obj[0].radioNum+"道    ||    "+
                                " 多选："+obj[0].checkScore+"(分/道) * "+obj[0].checkNum+"道    ||    "+
                                " 判断："+obj[0].judgeScore+"(分/道) * "+obj[0].judgeNum+"道    ||    "+
                                " 填空："+obj[0].fillScore+"(分/道) * "+obj[0].fillNum+"道")

                            /*-----------倒计时----------------*/
                            var SysSecond;
                            var InterValObj;
                            $(document).ready(function() {
                                SysSecond = parseInt((obj[0].examEndTime - new Date())/1000); //这里获取倒计时的起始时间
                                InterValObj = window.setInterval(SetRemainTime, 1000); //间隔函数，1秒执行
                            });
                            //将时间减去1秒，计算天、时、分、秒
                            function SetRemainTime() {
                                if (SysSecond > 0) {
                                    SysSecond = SysSecond - 1;
                                    var second = Math.floor(SysSecond % 60);            // 计算秒
                                    var minite = Math.floor((SysSecond / 60) % 60);      //计算分
                                    var hour = Math.floor((SysSecond / 3600) % 24);      //计算小时
                                    var day = Math.floor((SysSecond / 3600) / 24);       //计算天
                                    var hourDiv = "<span id='hourSpan'>"+ hour + "小时"+"</span>";
                                    var dayDiv = "<span id='daySpan'>"+ day + "天"+"</span>";
                                    $("#remainTime").html(dayDiv + hourDiv + minite + "分" + second + "秒");
                                    if(hour === 0) {//当不足1小时时隐藏小时
                                        $('#hourSpan').css('display','none');
                                    }
                                    if(day === 0) {//当不足1天时隐藏天
                                        $('#daySpan').css('display','none');
                                    }
                                } else {//剩余时间小于或等于0的时候，就停止间隔函数
                                    window.clearInterval(InterValObj);
                                    //这里可以添加倒计时时间为0后需要执行的事件
                                    layer.alert("考试时间到！");
                                }
                            }
                            form.render();
                        }
                    }
                }
            })
        };
        loadexamCurrent();

    })
    $(document).on('click','#endExam',function () {
        layer.confirm('确定立即关闭这考试？', {
            btn: ['确定','我再想想'] //按钮
        }, function(){
            $.post(projectName+'/exam/updateEndTime.do',
                {"id":examId},
                function (data) {
                    console.log(data["code"]);
                    if(data["code"]==0){
                        clearInterval(setInt);
                        layer.msg("关闭成功!",{icon:1},function () {
                            location.reload();
                        });
                    }else {
                        layer.msg("关闭失败，请联系开发人员！",{icon:2},function () {
                            location.reload();
                        });
                    }
                },
                "json"
            );
        }, function(){
        });
    })

});
