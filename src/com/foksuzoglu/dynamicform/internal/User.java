package com.foksuzoglu.dynamicform.internal;

import com.foksuzoglu.dynamicform.annotation.Detail;

public class User {

	// ---------- ROOT 10 FIELD ----------

	@Detail(key = "user.name", row = 0, col = 0)
	private String name;

	@Detail(key = "user.age", row = 1, col = 0)
	private int age;

	@Detail(key = "user.active", row = 2, col = 0)
	private boolean active;

	@Detail(key = "user.salary", row = 3, col = 0)
	private double salary;

	@Detail(key = "user.email", row = 4, col = 0)
	private String email;

	@Detail(key = "user.phone", row = 5, col = 0)
	private String phone;

	@Detail(key = "user.score", row = 6, col = 0)
	private int score;

	@Detail(key = "user.height", row = 7, col = 0)
	private double height;

	@Detail(key = "user.married", row = 8, col = 0)
	private boolean married;

	@Detail(key = "user.hasJob", row = 9, col = 0)
	private boolean hasJob;

	// ---------- INNER BLOCKS ----------

	@Detail(key = "user.address", row = 10, col = 0)
	private Address address;

	@Detail(key = "user.company", row = 11, col = 0)
	private Company company;

	@Detail(key = "user.bank", row = 12, col = 0)
	private Bank bank;

	@Detail(key = "user.identity", row = 13, col = 0)
	private Identity identity;

	@Detail(key = "user.preferences", row = 14, col = 0)
	private Preferences preferences;

	@Detail(key = "user.security", row = 15, col = 0)
	private Security security;

	@Detail(key = "user.device", row = 16, col = 0)
	private Device device;

	@Detail(key = "user.social", row = 17, col = 0)
	private Social social;

	@Detail(key = "user.statistics", row = 18, col = 0)
	private Statistics statistics;

	public User() {
		// TODO Auto-generated constructor stub
	}

	// ============================================================
	// ===================== INNER CLASSES ========================
	// ============================================================

}

class Address {
	@Detail(key = "address.street", row = 0, col = 0)
	private String street;
	@Detail(key = "address.city", row = 1, col = 0)
	private String city;
	@Detail(key = "address.zip", row = 2, col = 0)
	private String zip;
	@Detail(key = "address.country", row = 3, col = 0)
	private String country;
	@Detail(key = "address.building", row = 4, col = 0)
	private String building;
	@Detail(key = "address.floor", row = 5, col = 0)
	private int floor;
	@Detail(key = "address.apartment", row = 6, col = 0)
	private int apartment;
	@Detail(key = "address.region", row = 7, col = 0)
	private String region;
	@Detail(key = "address.longitude", row = 8, col = 0)
	private double longitude;
	@Detail(key = "address.latitude", row = 9, col = 0)
	private double latitude;

	public Address() {
		// TODO Auto-generated constructor stub
	}

}

class Company {
	@Detail(key = "company.name", row = 0, col = 0)
	private String name;
	@Detail(key = "company.department", row = 1, col = 0)
	private String department;
	@Detail(key = "company.title", row = 2, col = 0)
	private String title;
	@Detail(key = "company.startYear", row = 3, col = 0)
	private int startYear;
	@Detail(key = "company.employeeId", row = 4, col = 0)
	private String employeeId;
	@Detail(key = "company.level", row = 5, col = 0)
	private int level;
	@Detail(key = "company.office", row = 6, col = 0)
	private String office;
	@Detail(key = "company.extension", row = 7, col = 0)
	private int extension;
	@Detail(key = "company.active", row = 8, col = 0)
	private boolean active;
	@Detail(key = "company.bonus", row = 9, col = 0)
	private double bonus;

	public Company() {
		// TODO Auto-generated constructor stub
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
}
