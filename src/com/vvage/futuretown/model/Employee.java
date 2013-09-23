package com.vvage.futuretown.model;

public class Employee {
	private String name,phone;

	public Employee(String name, String phone) {
		super();
		this.name = name;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Emploee [name=" + name + ", phone=" + phone + "]";
	}
	

}
