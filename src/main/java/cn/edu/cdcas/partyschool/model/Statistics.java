package cn.edu.cdcas.partyschool.model;
/**
 * @Description TODO
 * @Date 2019/1/22 11:05
 * @Created by YR
 */
public class Statistics {
    private Integer  id;
    private String intro;
    private Integer selected;
    private String  correct;
    private String type;
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
