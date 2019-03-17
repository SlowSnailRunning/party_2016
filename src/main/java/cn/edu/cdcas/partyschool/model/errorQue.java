package cn.edu.cdcas.partyschool.model;

import java.io.Serializable;

public class errorQue extends Question implements Serializable{
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
