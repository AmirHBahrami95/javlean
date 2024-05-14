package com.amirh.javlean;

import com.amirh.javlean.model.*;
import com.amirh.javlean.concurrent.*;

import java.util.Map;
import java.util.HashMap;

import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors;

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

	public static void help(boolean v){
		System.out.println("usage: java -jar javlean.jar <ip/host>+ [-i] [-p] [-f] [-x path_to_ports.xml] [-d sec] [-t tactic]\n");
		if(v){
			System.out.println("\tip/h :target's ip or host only ipv4 is supported as of now");
			System.out.println("\t----------------------------------------------------------------------------------------------------------");
			System.out.println("\t-i  : scan c-class ip range from x.y.z.0 to x.y.z.255 with netmask 255.255.255.0       (default=false)");
			System.out.println("\t-p  : scan famous ports for each ip (if -i given, for each of those ip's)              (default=true)");
			System.out.println("\t-f  : shorthand for -i and -p together                                                 (default=true)");
			System.out.println("\t----------------------------------------------------------------------------------------------------------");
			System.out.println("\t-x  : path to xml file containing information of ports (see ports.xml for the format)  (default=ports.xml)");
			System.out.println("\t-d  : maximum delay in seconds before trying out the openness of next port/ip (505)    (default=2)");
			System.out.println("\t-t  : tactic used to avoid being detected and logged by system (505)                   (default=none)");
			System.out.println("\t-v  : enable verbosity, print each operation as they happen (505)                      (default=false)");
			System.out.println("\t-x  : path to xml file containing information of ports (see ports.xml for the format)  (default=ports.xml)");
			System.out.println("\t----------------------------------------------------------------------------------------------------------");
			System.out.println("\t+   : one or more than one required");
			System.out.println("\t-   : boolean optional flag\n");
			System.out.println("\t505 : not implemented yet");
			System.out.println("--written by Amir Bahrami (https://github.com/AmirHBahrami95)\n");
		}
	}

	public static void main(String[] args){
		
		// these flags aren't used yet
		String flagX="";
		int flagD=3000;
		String flagT="";
		
		// set manually during development
		boolean flagI=true;
		boolean flagP=true;
		boolean flagV=true;

		// TODO extract ip's from cmdline flags
		if(args.length==0){
			help(true);
			return;
		}
		
		PortInfo[] pinfos={
			new PortInfo(80,"http"),
			new PortInfo(443,"https"),
			new PortInfo(23,"telnet"),
			new PortInfo(25,"smtp")
		};
			
		/**/
		ScanRunnable sr;
		ScanManager sm;
		Map<String,Object> currentArgs;
		String host;
		// ExecutorService pool = Executors.newFixedThreadPool(4); // maximum threads allowed is 4
		for (int i=0;i<args.length;i++){
			host=args[i];
			try{
				sm=new FullScanManager(IPv4ClassCScanner.ofHost(host,30,34),new SimplePortScanner(host,pinfos));
			
				// initting args for current scan
				currentArgs=new HashMap<String,Object>();
				currentArgs.put("path",System.getProperty("user.home")+System.getProperty("file.separator")+host+".json");
				currentArgs.put("verbose",true);
				currentArgs.put("mode","f");
				currentArgs.put("host",host);

				// threadizing...
				sr=new FullScanRunnable(sm,currentArgs);
				sr.scan();
			}catch(Exception e){
				System.err.println(e.getMessage()); 
			}
		}

	} // end-of-main

}
