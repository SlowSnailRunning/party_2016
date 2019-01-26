package cn.edu.cdcas.partyschool.util;

import java.util.List;

/**
 * @author Char Jin
 * @date 2019-01-24
 */
public class JSONTableResult {

    private int code;
    private String msg;
    private int count;
    private int status;
    private List data;

    public JSONTableResult() {
    }

    public JSONTableResult(int code, String msg, int count, int status, List data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.status = status;
        this.data = data;
    }

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

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
