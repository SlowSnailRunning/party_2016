package cn.edu.cdcas.partyschool.util.impl;

import cn.edu.cdcas.partyschool.model.Statistics;

import java.util.List;
/**
 * @Description
 * @Date 2019/1/22 17:15
 * @Created by YR
 */
public class StatisticsPage {
    private int code;
    private String msg;
    private int count;
    private int status;
    List<Statistics> data;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Statistics> getData() {
        return data;
    }

    public void setData(List<Statistics> data) {
        this.data = data;
    }
}
