package com.amirh.javlean;

import com.amirh.javlean.model.*;

import java.io.IOException;

import java.net.UnknownHostException;

/**
 * 	Agenda: I wanna make a request analyzer in java : port/directory and domain scanner
 * 	with patterns that may vary for each request or for a bunch of them to make sure 
 * 	the user isn't logged on target machine

 * 	let's start with simply http(s)ing a list of targets. the "host" provided for 
 * 	http headers must be included, if no host provided, it'll be left out

	// TODO add randomized delay between each port/ip scan (not batch, each single one)

	@author Amir
 */
public class App{

	public static void help(){
		System.out.println("usage: java -jar javlean.jar <ip>+ [-i] [-x path_to_ports.xml] [-d sec] [-t tactic]");
		System.out.println("\tip  : only ipv4 is supported as of now");
		System.out.println("\t-i  : scan only ips, not ports for each of those ip's                                 | default=false");
		System.out.println("\t-x  : path to xml file containing information of ports (see ports.xml for the format) | default=ports.xml");
		System.out.println("\t-d  : maximum delay in seconds before trying out the openness of next port/ip         | default=2");
		System.out.println("\t-t  : tactic used to avoid being detected and logged by system (not implemented yet)  | default=NONE");
	}

	public static void main(String[] args){
		
		// these flags aren't used yet
		String flagX="";
		int flagD=3000;
		String flagT="";
		
		// set manually during development
		boolean flagI=true;

		// TODO extract ip's from cmdline flags

		System.out.println("javlean by Amir--\n");
		if(args.length==0){
			help();
			return;
		}
		
		PortInfo[] pinfos={
			new PortInfo(80,"http"),
			new PortInfo(443,"https"),
			new PortInfo(23,"telnet"),
			new PortInfo(25,"smtp")
		};

		IPScanner ips;
		PortScanner pl;
		for (int i=0;i<args.length;i++){
			try{
				ips=IPv4ClassCScanner.ofHost(args[i]);
				ips.scan();
				ips.print();
				/*
				if(!flagI){
					pl=new SimplePortScanner(args[i],pinfos);
					pl.scan();
					pl.print();
				}
				*/
			}catch(UnknownHostException uhe){
				System.err.println(uhe.getMessage());
			}
			System.out.println("-----------------------------------------");
		}

	}

}
