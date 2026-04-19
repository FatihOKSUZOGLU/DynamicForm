package com.foksuzoglu.dynamicform.internal;

import java.util.List;

import com.foksuzoglu.dynamicform.annotation.Detail;
import com.foksuzoglu.dynamicform.annotation.Row;
import com.foksuzoglu.dynamicform.graph.Graph;

public class User {

	// ---------- ROOT 10 FIELD ----------

	@Row(header = "user.name")
	@Detail(key = "user.name", row = 0, col = 0)
	private String name;

	@Graph
	@Row(header = "user.age")
	@Detail(key = "user.age", row = 1, col = 0)
	private int age;

	@Row(header = "user.active")
	@Detail(key = "user.active", row = 2, col = 0)
	private boolean active;
	@Graph
	@Row(header = "user.salary")
	@Detail(key = "user.salary", row = 3, col = 0)
	private double salary;

	@Row(header = "user.email")
	@Detail(key = "user.email", row = 4, col = 0)
	private String email;

	@Row(header = "user.phone")
	@Detail(key = "user.phone", row = 5, col = 0)
	private String phone;
	@Graph
	@Row(header = "user.score")
	@Detail(key = "user.score", row = 6, col = 0)
	private int score;
	@Graph
	@Row(header = "user.height")
	@Detail(key = "user.height", row = 7, col = 0)
	private double height;

	@Row(header = "user.married")
	@Detail(key = "user.married", row = 8, col = 0)
	private boolean married;

	@Row(header = "user.hasJob")
	@Detail(key = "user.hasJob", row = 9, col = 0)
	private boolean hasJob;

	@Row(header = "user.company")
	@Detail(key = "user.company", row = 10, col = 0)
	private Company company;

	@Detail(key = "user.names", row = 11, col = 0)
	private List<String> names;

	@Detail(key = "user.addresses", row = 12, col = 0)
	private List<Address> addresses;

	// ---------- INNER BLOCKS ---------
//	private Company company;
//	private Bank bank;
//	private Identity identity;
//	private Preferences preferences;
//	private Security security;
//	private Device device;
//	private Social social;
//	private Statistics statistics;

	public User() {
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", active=" + active + ", \nsalary=" + salary + ", email="
				+ email + ",\n phone=" + phone + ", score=" + score + ", height=" + height + "\n, married=" + married
				+ ", hasJob=" + hasJob + ",\n company=" + company + "\n names=" + names.toString() + ", \naddresses="
				+ addresses.toString() + "]";
	}

}

// ============================================================
// ===================== INNER CLASSES ========================
// ============================================================

class Address {

	@Row(header = "address.street")
	@Detail(key = "address.street", row = 0, col = 0)
	private String street;

	@Row(header = "address.city")
	@Detail(key = "address.city", row = 1, col = 0)
	private String city;

	@Row(header = "address.zip")
	@Detail(key = "address.zip", row = 2, col = 0)
	private String zip;

	@Row(header = "address.country")
	@Detail(key = "address.country", row = 3, col = 0)
	private String country;

	@Row(header = "address.building")
	@Detail(key = "address.building", row = 4, col = 0)
	private String building;

	@Row(header = "address.floor")
	@Detail(key = "address.floor", row = 5, col = 0)
	private int floor;

	@Row(header = "address.apartment")
	@Detail(key = "address.apartment", row = 6, col = 0)
	private int apartment;

	@Row(header = "address.region")
	@Detail(key = "address.region", row = 7, col = 0)
	private String region;

	@Row(header = "address.longitude")
	@Detail(key = "address.longitude", row = 8, col = 0)
	private double longitude;

	@Row(header = "address.latitude")
	@Detail(key = "address.latitude", row = 9, col = 0)
	private double latitude;

	@Row(header = "address.neighbors")
	@Detail(key = "address.neighbors", row = 10, col = 0)
	private List<String> neighbors;

	public Address() {
	}

	@Override
	public String toString() {
		return "\nAddress [street=" + street + ", city=" + city + ", zip=" + zip + ", country=" + country
				+ ", building=" + building + ", floor=" + floor + ", \napartment=" + apartment + ", region=" + region
				+ ", longitude=" + longitude + ", latitude=" + latitude + ", neighbors=" + neighbors.toString() + "]";
	}

}

class Company {

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
				+ ", active=" + active + ", bonus=" + bonus + "]";
	}

}

class Bank {
	@Detail(key = "bank.name", row = 0, col = 0)
	private String name;
	@Detail(key = "bank.iban", row = 1, col = 0)
	private String iban;
	@Detail(key = "bank.swift", row = 2, col = 0)
	private String swift;
	@Detail(key = "bank.accountNo", row = 3, col = 0)
	private String accountNo;
	@Detail(key = "bank.branch", row = 4, col = 0)
	private String branch;
	@Detail(key = "bank.balance", row = 5, col = 0)
	private double balance;
	@Detail(key = "bank.currency", row = 6, col = 0)
	private String currency;
	@Detail(key = "bank.active", row = 7, col = 0)
	private boolean active;
	@Detail(key = "bank.creditScore", row = 8, col = 0)
	private int creditScore;
	@Detail(key = "bank.riskLevel", row = 9, col = 0)
	private int riskLevel;

	public Bank() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Bank [name=" + name + ", iban=" + iban + ", swift=" + swift + ", accountNo=" + accountNo + ", branch="
				+ branch + ", balance=" + balance + ", currency=" + currency + ", active=" + active + ", creditScore="
				+ creditScore + ", riskLevel=" + riskLevel + "]";
	}

}

class Identity {
	@Detail(key = "identity.tc", row = 0, col = 0)
	private String tc;
	@Detail(key = "identity.passport", row = 1, col = 0)
	private String passport;
	@Detail(key = "identity.birthPlace", row = 2, col = 0)
	private String birthPlace;
	@Detail(key = "identity.birthYear", row = 3, col = 0)
	private int birthYear;
	@Detail(key = "identity.motherName", row = 4, col = 0)
	private String motherName;
	@Detail(key = "identity.fatherName", row = 5, col = 0)
	private String fatherName;
	@Detail(key = "identity.gender", row = 6, col = 0)
	private String gender;
	@Detail(key = "identity.serial", row = 7, col = 0)
	private String serial;
	@Detail(key = "identity.issueYear", row = 8, col = 0)
	private int issueYear;
	@Detail(key = "identity.nationality", row = 9, col = 0)
	private String nationality;

	public Identity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Identity [tc=" + tc + ", passport=" + passport + ", birthPlace=" + birthPlace + ", birthYear="
				+ birthYear + ", motherName=" + motherName + ", fatherName=" + fatherName + ", gender=" + gender
				+ ", serial=" + serial + ", issueYear=" + issueYear + ", nationality=" + nationality + "]";
	}

}

class Preferences {
	@Detail(key = "pref.theme", row = 0, col = 0)
	private String theme;
	@Detail(key = "pref.language", row = 1, col = 0)
	private String language;
	@Detail(key = "pref.notifications", row = 2, col = 0)
	private boolean notifications;
	@Detail(key = "pref.darkMode", row = 3, col = 0)
	private boolean darkMode;
	@Detail(key = "pref.autoSave", row = 4, col = 0)
	private boolean autoSave;
	@Detail(key = "pref.fontSize", row = 5, col = 0)
	private int fontSize;
	@Detail(key = "pref.layout", row = 6, col = 0)
	private String layout;
	@Detail(key = "pref.timezone", row = 7, col = 0)
	private String timezone;
	@Detail(key = "pref.volume", row = 8, col = 0)
	private int volume;
	@Detail(key = "pref.privacy", row = 9, col = 0)
	private boolean privacy;

	public Preferences() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Preferences [theme=" + theme + ", language=" + language + ", notifications=" + notifications
				+ ", darkMode=" + darkMode + ", autoSave=" + autoSave + ", fontSize=" + fontSize + ", layout=" + layout
				+ ", timezone=" + timezone + ", volume=" + volume + ", privacy=" + privacy + "]";
	}

}

class Security {
	@Detail(key = "sec.username", row = 0, col = 0)
	private String username;
	@Detail(key = "sec.password", row = 1, col = 0)
	private String password;
	@Detail(key = "sec.twoFactor", row = 2, col = 0)
	private boolean twoFactor;
	@Detail(key = "sec.pin", row = 3, col = 0)
	private int pin;
	@Detail(key = "sec.recoveryEmail", row = 4, col = 0)
	private String recoveryEmail;
	@Detail(key = "sec.locked", row = 5, col = 0)
	private boolean locked;
	@Detail(key = "sec.failedAttempts", row = 6, col = 0)
	private int failedAttempts;
	@Detail(key = "sec.lastLoginYear", row = 7, col = 0)
	private int lastLoginYear;
	@Detail(key = "sec.role", row = 8, col = 0)
	private String role;
	@Detail(key = "sec.enabled", row = 9, col = 0)
	private boolean enabled;

	public Security() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Security [username=" + username + ", password=" + password + ", twoFactor=" + twoFactor + ", pin=" + pin
				+ ", recoveryEmail=" + recoveryEmail + ", locked=" + locked + ", failedAttempts=" + failedAttempts
				+ ", lastLoginYear=" + lastLoginYear + ", role=" + role + ", enabled=" + enabled + "]";
	}

}

class Device {
	@Detail(key = "device.model", row = 0, col = 0)
	private String model;
	@Detail(key = "device.os", row = 1, col = 0)
	private String os;
	@Detail(key = "device.version", row = 2, col = 0)
	private String version;
	@Detail(key = "device.ip", row = 3, col = 0)
	private String ip;
	@Detail(key = "device.mac", row = 4, col = 0)
	private String mac;
	@Detail(key = "device.storage", row = 5, col = 0)
	private int storage;
	@Detail(key = "device.ram", row = 6, col = 0)
	private int ram;
	@Detail(key = "device.cpuCores", row = 7, col = 0)
	private int cpuCores;
	@Detail(key = "device.active", row = 8, col = 0)
	private boolean active;
	@Detail(key = "device.serial", row = 9, col = 0)
	private String serial;

	public Device() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Device [model=" + model + ", os=" + os + ", version=" + version + ", ip=" + ip + ", mac=" + mac
				+ ", storage=" + storage + ", ram=" + ram + ", cpuCores=" + cpuCores + ", active=" + active
				+ ", serial=" + serial + "]";
	}

}

class Social {
	@Detail(key = "social.facebook", row = 0, col = 0)
	private String facebook;
	@Detail(key = "social.twitter", row = 1, col = 0)
	private String twitter;
	@Detail(key = "social.linkedin", row = 2, col = 0)
	private String linkedin;
	@Detail(key = "social.instagram", row = 3, col = 0)
	private String instagram;
	@Detail(key = "social.github", row = 4, col = 0)
	private String github;
	@Detail(key = "social.youtube", row = 5, col = 0)
	private String youtube;
	@Detail(key = "social.followers", row = 6, col = 0)
	private int followers;
	@Detail(key = "social.following", row = 7, col = 0)
	private int following;
	@Detail(key = "social.posts", row = 8, col = 0)
	private int posts;
	@Detail(key = "social.verified", row = 9, col = 0)
	private boolean verified;

	public Social() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Social [facebook=" + facebook + ", twitter=" + twitter + ", linkedin=" + linkedin + ", instagram="
				+ instagram + ", github=" + github + ", youtube=" + youtube + ", followers=" + followers
				+ ", following=" + following + ", posts=" + posts + ", verified=" + verified + "]";
	}

}

class Statistics {
	@Detail(key = "stat.loginCount", row = 0, col = 0)
	private int loginCount;
	@Detail(key = "stat.purchaseCount", row = 1, col = 0)
	private int purchaseCount;
	@Detail(key = "stat.lastYearScore", row = 2, col = 0)
	private double lastYearScore;
	@Detail(key = "stat.currentYearScore", row = 3, col = 0)
	private double currentYearScore;
	@Detail(key = "stat.rank", row = 4, col = 0)
	private int rank;
	@Detail(key = "stat.points", row = 5, col = 0)
	private int points;
	@Detail(key = "stat.reputation", row = 6, col = 0)
	private int reputation;
	@Detail(key = "stat.badges", row = 7, col = 0)
	private int badges;
	@Detail(key = "stat.reviews", row = 8, col = 0)
	private int reviews;
	@Detail(key = "stat.rating", row = 9, col = 0)
	private double rating;

	public Statistics() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Statistics [loginCount=" + loginCount + ", purchaseCount=" + purchaseCount + ", lastYearScore="
				+ lastYearScore + ", currentYearScore=" + currentYearScore + ", rank=" + rank + ", points=" + points
				+ ", reputation=" + reputation + ", badges=" + badges + ", reviews=" + reviews + ", rating=" + rating
				+ "]";
	}

}
