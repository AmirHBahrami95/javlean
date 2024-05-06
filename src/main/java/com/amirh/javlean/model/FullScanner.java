package com.amirh.javlean.model;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlRootElement(name="FullScan")
public class FullScanner{
	
	@XmlElement(name="IpScan")
	private IPScanner ips;

	@XmlElement(name="PortScan")
	private PortScanner ps;

	public static FullScanner getFullScanner(String ip,PortInfo[] pinfos){
		return new FullScanner(new IPv4ClassCScanner(ip), new SimplePortScanner(ip,pinfos));
	}

	public FullScanner(IPScanner ips,PortScanner ps){
		this.ips=ips;
		this.ps=ps;
	}

	public IPScanner getIps(){return this.ips;}
	public void setIps(IPScanner ips){this.ips=ips;}
	public PortScanner getPs(){return this.ps;}
	public void setPs(PortScanner ps){this.ps=ps;}
}
