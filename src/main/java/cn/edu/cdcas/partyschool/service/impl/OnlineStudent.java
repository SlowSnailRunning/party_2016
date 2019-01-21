package cn.edu.cdcas.partyschool.service.impl;

import org.springframework.stereotype.Component;

/**
 * @Description 用于统计正在考试的学生人数，可能存在并发问题
 * @Date 2019/1/21 16:47
 * @Created by YR
 */
@Component
public class OnlineStudent {
    private static int onlineStudet = 0;
    //登录成功时触发
    public void increase() {
        --onlineStudet;
    }
    //loginout时触发
    public void reduce() {
        ++onlineStudet;
    }
    public static int getOnlineStudet() {
        return onlineStudet;
    }
}
