package cn.edu.cdcas.partyschool.model;

import java.io.Serializable;

/**
 * party_answer
 *
 * @author Char Jin
 */
public class Answer implements Serializable {
    private Integer id;

    /**
     * 学号
     */
    private String studentNo;

    /**
     * 考试期数
     */
    private Integer examId;

    /**
     * 题目ID
     */
    private Integer questionId;

    /**
     * 学生答案
     */
    private String answer;

    /**
     * 当前题目得分
     */
    private Integer score;

    /**
     * 题目类型
     */
    private Integer questionType;

    /**
     * 正确答案
     */
    private String result;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}