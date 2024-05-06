package com.amirh.javlean.model;

import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

import java.net.Socket;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
	the name suggests this is a simple implementation of a 
	a PortScanner. which means there's no magic,it's a blocking
	single-threaded portlogger which receives an ip and works
	around it. no techniques in terms of logging-prevention (by
	the target system) is used either; simply nothing extra!
	@see PortScanner 
	@author Amir
*/
@XmlRootElement(name="PortScan")
public class SimplePortScanner implements PortScanner{
	
	@XmlElement(name="ip")
	private String ip;
	private boolean done;

	@XmlElementWrapper(name="ports") // what a stupid white-shank1
	@XmlElement(name="port")
	private List<PortInfo> ports;
	private PortInfo[] portInfos;
	
	public SimplePortScanner(String ip,PortInfo[] portInfos){
		this.ip=ip;
		this.portInfos=portInfos;
		this.done=false;
		this.ports=new ArrayList<PortInfo>();
	}
	
	@Override
	public void print(){
		if(!done){
			System.out.println("[NOT_READY]");
			return;
		}

		/*
		Iterator<Map.Entry<PortInfo,Boolean>> it=this.ports.entrySet().iterator();
		Map.Entry<PortInfo,Boolean> entr;
		System.out.println(this.ip);
		while(it.hasNext()){
			entr=it.next();
			if(entr.getValue())
				System.out.print("[x] ");
			else
				System.out.print("[ ] ");
			System.out.println(entr.getKey().toString());
		}
		*/

	}
	
	@Override
	public List<PortInfo> getPorts(){
		this.scan();
		return this.ports;
	}

	@Override
	public boolean tryPort(int portNo){
		try(Socket s=new Socket(this.ip,portNo)){ // implicitly converted to InetAddress(4/6)
			s.setSoTimeout(5000); // 5 seconds
			return s.isConnected();
		}catch(IOException ioe){
			return false;
		}
	}

	@Override
	public String getIp(){return this.ip;}
	
	@Override
	public void scan(){
		if(!this.done){
			for(int i=0;i<portInfos.length;i++)
				if(tryPort(portInfos[i].getPortNo()))
					this.ports.add(portInfos[i]);
		}
		this.done=true; // do only once per instance (for now)
	}

}
