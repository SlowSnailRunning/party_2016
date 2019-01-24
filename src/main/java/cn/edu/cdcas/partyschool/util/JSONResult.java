package cn.edu.cdcas.partyschool.util;

/**
 * @author Char Jin
 * @date 2019-01-24
 */
public class JSONResult {
    private int code;
    private String msg;
    private int status;

    public JSONResult() {
    }

    public JSONResult(int code, String msg, int status) {
        this.code = code;
        this.msg = msg;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
