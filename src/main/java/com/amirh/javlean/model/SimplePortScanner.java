package com.amirh.javlean.model;

import java.util.List;
import java.util.ArrayList;

import java.net.Socket;

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

	@XmlElementWrapper(name="ports") // what a stupid white-shank1
	private List<PortInfo> ports;

	@XmlTransient private PortInfo[] portInfos;
	@XmlTransient private boolean done;

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

	@Override
	public void marshalTo(String path) throws JAXBException,IOException{
		JAXBContext context=JAXBContext.newInstance(SimplePortScanner.class);
		Marshaller m = context.createMarshaller();
		File f=new File(path);
		if(f.isDirectory()) throw new JAXBException("you don't expect me to write xml to a direcotry do you?!");
		if(!f.exists()) f.createNewFile();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(this,f);	
	}

}
