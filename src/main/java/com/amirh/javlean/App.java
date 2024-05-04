package com.amirh.javlean;

import com.amirh.javlean.model.*;

import java.io.IOException;

/**
 * 	Agenda: I wanna make a request analyzer in java : port/directory and domain scanner
 * 	with patterns that may vary for each request or for a bunch of them to make sure 
 * 	the user isn't logged on target machine

 * 	let's start with simply http(s)ing a list of targets. the "host" provided for 
 * 	http headers must be included, if no host provided, it'll be left out

 */
public class App{

	public static void main(String[] args){
		
		System.out.println("javlean by Amir--\n");
		
		PortInfo[] pinfos={
			new PortInfo(80,"http"),
			new PortInfo(443,"https"),
			new PortInfo(23,"telnet"),
			new PortInfo(25,"smtp")
		};
		
		PortScanner pl;
		for (int i=0;i<args.length;i++){
			pl=new SimplePortScanner(args[i],pinfos);
			pl.scan();
			pl.print();
			System.out.println("-----------------------------------------");
		}

	}

}
