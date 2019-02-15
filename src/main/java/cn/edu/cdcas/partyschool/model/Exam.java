package cn.edu.cdcas.partyschool.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * party_exam
 *
 * @author Char Jin
 */
public class Exam implements Serializable {
    private Integer id;

    /**
     * 考试名称
     */
    private String examName;

    /**
     * 考试时间
     */
    private Integer examTime;

    /**
     * 考试创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 考试开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date examStartTime;

    /**
     * 考试结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date examEndTime;

    /**
     * 单选题数量
     */
    private Integer radioNum;

    /**
     * 单选题分数
     */
    private Integer radioScore;

    /**
     * 多选题数量
     */
    private Integer checkNum;

    /**
     * 多选题分数
     */
    private Integer checkScore;

    /**
     * 判断题数量
     */
    private Integer judgeNum;

    /**
     * 判断题分数
     */
    private Integer judgeScore;

    /**
     * 填空题数量
     */
    private Integer fillNum;

    /**
     * 填空题分数
     */
    private Integer fillScore;

    /**
     * 简答题数量
     */
    private Integer saqNum;

    /**
     * 简答题分数
     */
    private Integer saqScore;

    /**
     * 及格分数
     */
    private Integer passScore;

    /**
     * 是否允许补考
     */
    private Integer isMakeup;

    private static final long serialVersionUID = 1L;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Integer getExamTime() {
        return examTime;
    }

    public void setExamTime(Integer examTime) {
        this.examTime = examTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExamStartTime() { return examStartTime; }

    public void setExamStartTime(Date examStartTime) { this.examStartTime = examStartTime; }

    public Date getExamEndTime() { return examEndTime; }

    public void setExamEndTime(Date examEndTime) { this.examEndTime = examEndTime; }

    public Integer getRadioNum() {
        return radioNum;
    }

    public void setRadioNum(Integer radioNum) {
        this.radioNum = radioNum;
    }

    public Integer getRadioScore() {
        return radioScore;
    }

    public void setRadioScore(Integer radioScore) {
        this.radioScore = radioScore;
    }

    public Integer getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Integer checkNum) {
        this.checkNum = checkNum;
    }

    public Integer getCheckScore() {
        return checkScore;
    }

    public void setCheckScore(Integer checkScore) {
        this.checkScore = checkScore;
    }

    public Integer getJudgeNum() {
        return judgeNum;
    }

    public void setJudgeNum(Integer judgeNum) {
        this.judgeNum = judgeNum;
    }

    public Integer getJudgeScore() {
        return judgeScore;
    }

    public void setJudgeScore(Integer judgeScore) {
        this.judgeScore = judgeScore;
    }

    public Integer getFillNum() {
        return fillNum;
    }

    public void setFillNum(Integer fillNum) {
        this.fillNum = fillNum;
    }

    public Integer getFillScore() {
        return fillScore;
    }

    public void setFillScore(Integer fillScore) {
        this.fillScore = fillScore;
    }

    public Integer getSaqNum() {
        return saqNum;
    }

    public void setSaqNum(Integer saqNum) {
        this.saqNum = saqNum;
    }

    public Integer getSaqScore() {
        return saqScore;
    }

    public void setSaqScore(Integer saqScore) {
        this.saqScore = saqScore;
    }

    public Integer getPassScore() {
        return passScore;
    }

    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }

    public Integer getIsMakeup() {
        return isMakeup;
    }

    public void setIsMakeup(Integer isMakeup) {
        this.isMakeup = isMakeup;
    }
}