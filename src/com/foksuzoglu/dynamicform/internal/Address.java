package com.foksuzoglu.dynamicform.internal;

import java.util.List;

import com.foksuzoglu.dynamicform.annotation.Detail;
import com.foksuzoglu.dynamicform.annotation.Row;

public class Address {

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