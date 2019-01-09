package cn.edu.cdcas.partyschool.model;

import java.io.Serializable;
import java.util.Date;

/**
 * party_user
 *
 * @author Char Jin
 */
public class User implements Serializable {
    private Integer id;

    /**
     * 党校学生序号
     */
    private Integer idx;

    /**
     * 党校考试期数
     */
    private Integer examId;

    /**
     * 学号
     */
    private String studentNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 班级
     */
    private String clazz;

    /**
     * 学院
     */
    private String department;

    /**
     * 年级
     */
    private String grade;

    /**
     * 专业
     */
    private String major;

    /**
     * 性别
     */
    private String sex;

    /**
     * 是否毕业
     */
    private String graduated;

    /**
     * 是否优秀
     */
    private String excellent;

    /**
     * 考生考试状态(0：未考，1：首次考试通过，2：未补考，3：补考未过 4：补考通过)
     */
    private Integer examState;

    /**
     * 学生开始考试时间
     */
    private Date examStart;

    /**
     * 学生交卷时间
     */
    private Date examEnd;

    /**
     * 考试成绩
     */
    private Integer examScore;

    /**
     * 学生开始补考时间
     */
    private Date makeUpStart;

    /**
     * 学生补考交卷时间
     */
    private Date makeUpEnd;

    /**
     * 补考分数
     */
    private Integer makeUpScore;

    /**
     * 党校号
     */
    private String partyNumber;

    /**
     * 标识是考生(student)还是管理员(manager)
     */
    private String type;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClass(String clazz) {
        this.clazz = clazz;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGraduated() {
        return graduated;
    }

    public void setGraduated(String graduated) {
        this.graduated = graduated;
    }

    public String getExcellent() {
        return excellent;
    }

    public void setExcellent(String excellent) {
        this.excellent = excellent;
    }

    public Integer getExamState() {
        return examState;
    }

    public void setExamState(Integer examState) {
        this.examState = examState;
    }

    public Date getExamStart() {
        return examStart;
    }

    public void setExamStart(Date examStart) {
        this.examStart = examStart;
    }

    public Date getExamEnd() {
        return examEnd;
    }

    public void setExamEnd(Date examEnd) {
        this.examEnd = examEnd;
    }

    public Integer getExamScore() {
        return examScore;
    }

    public void setExamScore(Integer examScore) {
        this.examScore = examScore;
    }

    public Date getMakeUpStart() {
        return makeUpStart;
    }

    public void setMakeUpStart(Date makeUpStart) {
        this.makeUpStart = makeUpStart;
    }

    public Date getMakeUpEnd() {
        return makeUpEnd;
    }

    public void setMakeUpEnd(Date makeUpEnd) {
        this.makeUpEnd = makeUpEnd;
    }

    public Integer getMakeUpScore() {
        return makeUpScore;
    }

    public void setMakeUpScore(Integer makeUpScore) {
        this.makeUpScore = makeUpScore;
    }

    public String getPartyNumber() {
        return partyNumber;
    }

    public void setPartyNumber(String partyNumber) {
        this.partyNumber = partyNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}