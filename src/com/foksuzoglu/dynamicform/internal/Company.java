package com.foksuzoglu.dynamicform.internal;

import java.util.List;

import com.foksuzoglu.dynamicform.annotation.Detail;
import com.foksuzoglu.dynamicform.annotation.Row;

public class Company {

	@Row(header = "company.name")
	@Detail(key = "company.name", row = 0, col = 0)
	private String name;

	@Row(header = "company.department")
	@Detail(key = "company.department", row = 1, col = 0)
	private String department;

	@Row(header = "company.title")
	@Detail(key = "company.title", row = 2, col = 0)
	private String title;

	@Row(header = "company.startYear")
	@Detail(key = "company.startYear", row = 3, col = 0)
	private int startYear;

	@Row(header = "company.employeeId")
	@Detail(key = "company.employeeId", row = 4, col = 0)
	private String employeeId;

	@Row(header = "company.level")
	@Detail(key = "company.level", row = 5, col = 0)
	private int level;

	@Row(header = "company.office")
	@Detail(key = "company.office", row = 6, col = 0)
	private String office;

	@Row(header = "company.extension")
	@Detail(key = "company.extension", row = 7, col = 0)
	private int extension;

	@Row(header = "company.active")
	@Detail(key = "company.active", row = 8, col = 0)
	private boolean active;

	@Row(header = "company.bonus")
	@Detail(key = "company.bonus", row = 9, col = 0)
	private double bonus;

	@Row(header = "company.workers")
	@Detail(key = "company.workers", row = 10, col = 0)
	private List<String> workers;

	public Company() {
	}

	@Override
	public String toString() {
		return "Company [name=" + name + ", department=" + department + ", title=" + title + ", startYear=" + startYear
				+ ", employeeId=" + employeeId + ", level=" + level + ", office=" + office + ", extension=" + extension
				+ ", active=" + active + ", bonus=" + bonus + ", \nCompany.workers=" + workers.toString() + "]";
	}

}
