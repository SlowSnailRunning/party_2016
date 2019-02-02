package cn.edu.cdcas.partyschool.model;

import java.io.Serializable;

/**
 * 用户的session
 * @author Snail
 *
 */
public class UserSession implements Serializable {
	private String name;
	private String number;
	private String type;
	private int[] questionIdArray;
	private int studentExamState;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public int[] getQuestionIdArray() {
		return questionIdArray;
	}

	public void setQuestionIdArray(int[] questionIdArray) {
		this.questionIdArray = questionIdArray;
	}

	public int getStudentExamState() {
		return studentExamState;
	}

	public void setStudentExamState(int studentExamState) {
		this.studentExamState = studentExamState;
	}
}
