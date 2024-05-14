package com.amirh.javlean.concurrent;

import com.amirh.javlean.io.JsonFileIO;

import com.amirh.javlean.model.ScanManager;

import java.io.IOException;

import java.util.Map;

public class FullScanRunnable implements ScanRunnable{

	private ScanManager sm;

	// some flags passed for each ip (later, it'll be eac ip, for now it's all ip's)
	private String path;
	private String mode;
	private Boolean verbose;
	private String host;
	
	public FullScanRunnable(ScanManager sm,Map<String,Object> args){
		this.sm=sm;
		init(args);
	}

	private void init(Map<String,Object> args){
		
		this.path=args.containsKey("path")?(String) args.get("path"):".";
		this.mode=args.containsKey("mode")?(String) args.get("mode"):"f"; // possible values : i(ps), p(orts), f(ull)
		this.verbose=args.containsKey("verbose")?(Boolean) args.get("verbose"):false;
		this.host=args.containsKey("host")?(String) args.get("host"):this.sm.getIp();
	}

	@Override
	public void scan(){
		
		if(verbose) System.out.println("scanning '"+host+"' , will write to '"+this.path+"'");

		switch(this.mode){
			case "i":
				this.sm.scanIps();
				if(this.verbose) this.sm.printIps();
			break;
			case "p":
				this.sm.scanPorts();
				if(this.verbose) this.sm.printPorts();
			break;
			case "f":
				this.sm.scan();
				if(this.verbose) this.sm.print();
			break;
			default:
				System.err.println("[!] failed to scan '"+this.host+"' : mode '"+this.mode+"' does not exist!");
			break;
		}

		try{
			JsonFileIO.writeTo(path,sm);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	@Override 
	public void run(){
		scan();
	}

}
