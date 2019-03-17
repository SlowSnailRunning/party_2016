package cn.edu.cdcas.partyschool.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Description
 * @Date 2019/1/22 11:05
 * @Created by YR
 */
public class Statistics {
    @JSONField(ordinal=1)
    private Integer  id;
    @JSONField(ordinal=2)
    private String intro;
    @JSONField(ordinal=3)
    private String type;
    @JSONField(ordinal=4)
    private Integer selected;
    @JSONField(ordinal=5)
    private String  correct;
    @JSONField(ordinal=6)
    private String optionA;
    @JSONField(ordinal=7)
    private String optionB;
    @JSONField(ordinal=8)
    private String optionC;
    @JSONField(ordinal=9)
    private String optionD;

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
