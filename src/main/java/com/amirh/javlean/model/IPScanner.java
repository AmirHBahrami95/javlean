package com.amirh.javlean.model;

import java.util.List;

import javax.xml.bind.JAXBException;

/**
	Interface for scanning a range of Ip's 
*/
public interface IPScanner{
	public List<String> getSubIps();
	public void print();
	public void scan();
	public void setLowerBound(int bound);
	public void setHigherBound(int bound);
	public void marshalTo(String path) throws JAXBException;
}
