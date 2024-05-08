package com.amirh.javlean.model;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.IOException;
import java.io.File;

@XmlRootElement(name="FullScan")
public class FullScanner{
	
	public static FullScanner getFullScanner(String ip,PortInfo[] pinfos){
		return new FullScanner(new IPv4ClassCScanner(ip), new SimplePortScanner(ip,pinfos));
	}

	private IPv4ClassCScanner ips;
	private SimplePortScanner ps;

	public FullScanner(){}

	public FullScanner(IPv4ClassCScanner ips,SimplePortScanner ps){
		this.ips=ips;
		this.ps=ps;
	}

	@XmlElement	public SimplePortScanner getPs(){return this.ps;}
	@XmlElement	public IPv4ClassCScanner getIps(){return this.ips;}

	public void scan(){
		this.ips.scan();
		this.ps.scan();
	}

	public void print(){
		this.ips.print();
		this.ps.print();
	}

	public void marshalTo(String path) throws JAXBException,IOException{
		JAXBContext context=JAXBContext.newInstance(FullScanner.class);
		Marshaller m = context.createMarshaller();
		File f=new File(path);
		if(f.isDirectory()) throw new JAXBException("you don't expect me to write xml to a direcotry do you?!");
		if(!f.exists()) f.createNewFile();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(this,f);
	}
}
