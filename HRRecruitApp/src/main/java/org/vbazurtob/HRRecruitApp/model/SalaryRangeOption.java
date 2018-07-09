package org.vbazurtob.HRRecruitApp.model;

public class SalaryRangeOption {

	@Override
	public String toString() {
		return "SalaryRangeOption [id=" + id + ", text=" + text + ", salaryEqualsMore=" + salaryEqualsMore
				+ ", salaryLessEquals=" + salaryLessEquals + "]";
	}

	private int id;
	private String text;
	private int salaryEqualsMore;
	private int salaryLessEquals;
	
	public SalaryRangeOption() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	

	

	public int getSalaryEqualsMore() {
		return salaryEqualsMore;
	}

	public void setSalaryEqualsMore(int salaryEqualsMore) {
		this.salaryEqualsMore = salaryEqualsMore;
	}

	public int getSalaryLessEquals() {
		return salaryLessEquals;
	}

	public void setSalaryLessEquals(int salaryLessEquals) {
		this.salaryLessEquals = salaryLessEquals;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + salaryEqualsMore;
		result = prime * result + salaryLessEquals;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalaryRangeOption other = (SalaryRangeOption) obj;
		if (id != other.id)
			return false;
		if (salaryEqualsMore != other.salaryEqualsMore)
			return false;
		if (salaryLessEquals != other.salaryLessEquals)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	public SalaryRangeOption(int id, String text, int salaryEqualsMore, int salaryLessEquals) {
		super();
		this.id = id;
		this.text = text;
		this.salaryEqualsMore = salaryEqualsMore;
		this.salaryLessEquals = salaryLessEquals;
	}
	
	

}
