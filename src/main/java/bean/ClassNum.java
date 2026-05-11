package bean;

import java.io.Serializable;

public class ClassNum implements Serializable {
	private String classNum;
	private School school;

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
}