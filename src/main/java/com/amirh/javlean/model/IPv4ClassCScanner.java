package com.amirh.javlean.model;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.List;
import java.util.ArrayList;

import java.io.IOException;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;

import java.io.File;

/**
	Class-C IPv4 Scanner!
	@author Amir
*/
@XmlRootElement(name="IPv4_C")
public class IPv4ClassCScanner implements IPScanner{ // XXX convert each IPScanner instance to IPv4... for xml (un)marshallizaiton

	/**
		Take String fullIp (including the last octet), and apply 255.255.255.0
		netmask to it, to make it suitable for a ipv4 scan
		@param fullIp String
		@return IPv4ClassCScanner
	*/
	public static IPv4ClassCScanner ofHost(String host,int lower,int upper) throws UnknownHostException{
		String ip=InetAddress.getByName(host).getHostAddress();
		String[] sections=ip.split("\\.");
		return new IPv4ClassCScanner(sections[0]+"."+sections[1]+"."+sections[2],lower,upper);
	}

	public static IPv4ClassCScanner ofHost(String host) throws UnknownHostException {
		return ofHost(host,0,255);
	}

	// ---------------------------------------------------------------------------------------end_of_static 

	// @XmlElement(name="ip") XXX for now
	private String ip;
	
	private List<String> subIps;
	
	@XmlTransient private int lowerBound;
	@XmlTransient private int upperBound;
	@XmlTransient private boolean done;
	@XmlTransient	private int timeout;
	

	public IPv4ClassCScanner(){}

	public IPv4ClassCScanner(String ip,int lower,int upper,int timeout){
		// TODO check if ip is c-class
		this.ip=ip;
		this.lowerBound=lower;
		this.upperBound=upper;
		this.done=false;
		this.timeout=timeout;
		this.subIps=new ArrayList<String>();
	}

	public IPv4ClassCScanner(String ip,int lower,int upper){
		this(ip,lower,upper,2000);
	}

	public IPv4ClassCScanner(String ip){
		this(ip,1,255);
	}

	@XmlElementWrapper(name="Subnet")
	@XmlElement(name="ip")
	@Override
	public List<String> getSubIps(){
		scan();
		return this.subIps;
	}
	
	@Override
	public void print(){
		for(int i=0;i<subIps.size();i++)
			System.out.println(subIps.get(i));
	}

	@Override
	public void scan(){
		if(this.done) return;
		InetAddress iadr;
		for(int i=this.lowerBound;i<=this.upperBound;i++){
			try{
				iadr=InetAddress.getByName(ip+"."+i);
				if(iadr.isReachable(this.timeout))
					this.subIps.add(ip+"."+i);
			}catch(IOException ioe){
				System.out.println("[error]"+ioe.getMessage());
			}
		}
		this.done=true;
	}
	
	@Override
	public void setLowerBound(int bound) {
		if(bound>this.upperBound) throw new IllegalArgumentException("given 'lowerBound' cannot be set: higherBound<lowerBound");
		this.lowerBound=bound;
	}

	@Override
	public void setHigherBound(int bound){
		if(bound<this.lowerBound)throw new IllegalArgumentException("given 'higherBound' cannot bet set: lowerBound>higherBound");
		this.upperBound=bound;
	}

	@Override
	public void marshalTo(String path) throws JAXBException{
		JAXBContext context=JAXBContext.newInstance(IPv4ClassCScanner.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(this,new File(path));	
	}
	
}
