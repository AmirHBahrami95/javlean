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

/**
	Class-C IPv4 Scanner!
	@author Amir
*/
@XmlRootElement(name="IPv4_C")
@XmlType(propOrder={"ip","sub_ips"})
public class IPv4ClassCScanner implements IPScanner{ // XXX convert each IPScanner instance to IPv4... for xml (un)marshallizaiton
	
	@XmlElement(name="ip")
	private String ip;
	
	@XmlElementWrapper(name="sub_ips")
	@XmlElement(name="ipv4")
	private List<String> subIps;
	
	@XmlTransient private int lowerBound;
	@XmlTransient private int upperBound;
	@XmlTransient private boolean done;
	@XmlTransient	private int timeout;
	
	/**
		Take String fullIp (including the last octet), and apply 255.255.255.0
		netmask to it, to make it suitable for a ipv4 scan
		@param fullIp String
		@return IPv4ClassCScanner
	*/
	public static IPv4ClassCScanner ofHost(String host) throws UnknownHostException {
		
		String ip=InetAddress.getByName(host).getHostAddress();
		String[] sections=ip.split("\\.");

		// System.out.println("InetAdress:"+ip);

		return new IPv4ClassCScanner(sections[0]+"."+sections[1]+"."+sections[2]);
	}



	public IPv4ClassCScanner(String ip,int timeout,int lower,int upper){
		// TODO check if ip is c-class
		this.ip=ip;
		this.lowerBound=lower;
		this.upperBound=upper;
		this.done=false;
		this.timeout=timeout;
		this.subIps=new ArrayList<String>();
		System.out.println(this.ip);
	}

	public IPv4ClassCScanner(String ip){
		this(ip,2000,1,255);
	}

	public IPv4ClassCScanner(String ip,int timeout){
		this(ip);
	}

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
				// System.out.print(ip+"."+i);
				iadr=InetAddress.getByName(ip+"."+i);
				if(iadr.isReachable(this.timeout)){ // ping
					this.subIps.add(ip+"."+i);
					System.out.println("[x] "+ip+"."+i);
				}
				else
					System.out.println("[ ] "+ip+"."+i);
			}catch(IOException ioe){
				System.out.println("[ ] "+ip+"."+i);
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
	
}
