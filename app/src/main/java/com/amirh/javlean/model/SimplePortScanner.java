package com.amirh.javlean.model;

import java.util.List;
import java.util.ArrayList;

import java.net.Socket;

import java.io.IOException;
import java.io.File;

/**
	the name suggests this is a simple implementation of a 
	a PortScanner. which means there's no magic,it's a blocking
	single-threaded portlogger which receives an ip and works
	around it. no techniques in terms of logging-prevention (by
	the target system) is used either; simply nothing extra!
	@see PortScanner 
	@author Amir
*/
public class SimplePortScanner implements PortScanner{
	
	private String ip;
	private List<PortInfo> ports;
	private PortInfo[] portInfos;
	private boolean done;

	public SimplePortScanner(){}
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
		
		for(int i=0;i<this.ports.size();i++)
			System.out.println(ip+":"+ports.get(i));
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
