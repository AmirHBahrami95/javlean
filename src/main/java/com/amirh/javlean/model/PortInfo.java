package com.amirh.javlean.model;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlRootElement;

/**
	simple Bean class for saving informations about the ports
	and what each of them corresponds to

	TODO make this xml-parsable and read/write to it
*/
@XmlRootElement(name="Port")
public final class PortInfo{
	
	@XmlElement(name="title")
	private String title; // http, ftp, etc.

	@XmlElement(name="number")
	private int portNo;

	public PortInfo(int portNo,String title){
		this.portNo=portNo;
		this.title=title;
	}
	
	public PortInfo(String title,int portNo){
		this(portNo,title);
	}
	
	@XmlElement(name="shorthand")
	@Override
	public String toString(){return title+" ("+portNo+")";}
	
	public String getTitle(){return this.title;}
	public void setTitle(String title){this.title=title;}
	public int getPortNo(){return this.portNo;}
	public void setPortNo(int portNo){this.portNo=portNo;}
}
