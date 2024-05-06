package com.amirh.javlean.model;

import java.util.List;

/**
	Receives an ip Address, along with optional Headers
	and tries out most famous ports for that ip address
	@author Amir
*/
public interface PortScanner{
	public void scan();
	public List<PortInfo> getPorts();
	public boolean tryPort(int portNo);
	public String getIp();
	public void print();
}
