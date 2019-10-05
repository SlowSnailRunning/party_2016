package cn.edu.cdcas.party.model;

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
