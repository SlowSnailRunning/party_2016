package cn.edu.cdcas.partyschool.model;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户的session
 * @author Snail
 *
 */
public class UserSession implements Serializable {
	private String name;
	private String number;
	private String type;
	private Set<Integer> questionIdArray;
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

	public Set<Integer> getQuestionIdArray() {
		return questionIdArray;
	}

	public void setQuestionIdArray(Set<Integer> questionIdArray) {
		this.questionIdArray = questionIdArray;
	}

	public int getStudentExamState() {
		return studentExamState;
	}

	public void setStudentExamState(int studentExamState) {
		this.studentExamState = studentExamState;
	}
}
