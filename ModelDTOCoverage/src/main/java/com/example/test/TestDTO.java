package com.example.test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;

public class TestDTO {

	private Integer id;
	
	private String name;
	
	private BigDecimal totalAmount;
	
	private Timestamp timestamp;
	 
	private Date sqlDate;
	
	private String[] stringArray;
	
	private Integer[] integerArray;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Date getSqlDate() {
		return sqlDate;
	}

	public void setSqlDate(Date sqlDate) {
		this.sqlDate = sqlDate;
	}

	public String[] getStringArray() {
		return stringArray;
	}

	public void setStringArray(String[] stringArray) {
		this.stringArray = stringArray;
	}

	public Integer[] getIntegerArray() {
		return integerArray;
	}

	public void setIntegerArray(Integer[] integerArray) {
		this.integerArray = integerArray;
	}

	@Override
	public String toString() {
		return "TestDTO [id=" + id + ", name=" + name + ", totalAmount=" + totalAmount + ", timestamp=" + timestamp
				+ ", sqlDate=" + sqlDate + ", stringArray=" + Arrays.toString(stringArray) + ", integerArray="
				+ Arrays.toString(integerArray) + "]";
	}
}
