package com.amirh.javlean.model;

import java.io.IOException;
import java.io.File;

public class FullScanManager implements ScanManager{

	private IPScanner ips;
	private PortScanner ports;

	public FullScanManager(){}

	public FullScanManager(IPScanner ips,PortScanner ps){
		this.ips=ips;
		this.ports=ps;
	}

	@Override
	public String getIp(){
		return this.ips.getIp();
	}
	
	@Override
	public void scan(){
		this.ips.scan();
		this.ports.scan();
	}
	
	@Override
	public void scanIps(){
		this.ips.scan();
	}

	@Override
	public void scanPorts(){
		this.ports.scan();
	}
	
	@Override
	public void print(){
		this.ips.print();
		this.ports.print();
	}

	@Override
	public void printIps(){
		this.ips.print();
	}

	@Override 
	public void printPorts(){
		this.ports.print();
	}
	
}
