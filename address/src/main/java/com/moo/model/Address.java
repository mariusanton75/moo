package com.moo.model;

import java.util.Objects;

public class Address {
	private Long id;
	private int number;
	private String street;
	private String city;
	private String zip;
	private String country;

	public Address() {
	}

	
	
	
	public Address(Long id, int number, String street, String city, String zip, String country) {
		super();
		this.id = id;
		this.number = number;
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.country = country;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}


	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}




	@Override
	public int hashCode() {
		return Objects.hash(city, country, id, number, street, zip);
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Address)) {
			return false;
		}
		Address other = (Address) obj;
		return Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(id, other.id) && number == other.number && Objects.equals(street, other.street)
				&& Objects.equals(zip, other.zip);
	}




	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Address [id=").append(id).append(", number=").append(number).append(", street=").append(street)
				.append(", city=").append(city).append(", zip=").append(zip).append(", country=").append(country)
				.append("]");
		return builder.toString();
	}



}
