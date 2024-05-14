package com.amirh.javlean.model;

import java.util.List;

/**
	Interface for scanning a range of Ip's 
	@author Amir
*/
public interface IPScanner{
	public List<String> getSubIps();
	public String getIp();
	public void print();
	public void scan();
	public void setLowerBound(int bound);
	public void setHigherBound(int bound);
}
