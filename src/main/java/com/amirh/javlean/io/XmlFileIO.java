package com.amirh.javlean.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;

import com.amirh.javlean.model.IPv4ClassCScanner;
import com.amirh.javlean.model.SimplePortScanner;

@Deprecated
public final class XmlFileIO{
	
	private static void marshalize(JAXBContext context,Object o,String path) throws JAXBException{
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(o,new File(path));	
	}

	public static void marshal(SimplePortScanner[] spscs,String path) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(SimplePortScanner.class);
		marshalize(context,spscs,path);
	}

	public static void marshal(IPv4ClassCScanner ipsc,String path) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(IPv4ClassCScanner.class);
		marshalize(context,ipsc,path);
	}

	public static void marshal(SimplePortScanner spsc,String path) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(SimplePortScanner.class);
		marshalize(context,spsc,path);
	}
	
	// TODO fix this mofo
	public static Object unmarshal(String path,String className) throws JAXBException,IOException{
		JAXBContext context=null;
		
		if(className.equals("IPv4ClassCScanner"))
			context = JAXBContext.newInstance(IPv4ClassCScanner.class);
		else if(className.equals("SimplePortScanner"))
			context = JAXBContext.newInstance(IPv4ClassCScanner.class);


		if(context==null)
			throw new JAXBException("[XmlFileIO::unmarshal] : no class identified");

		Unmarshaller um = context.createUnmarshaller();
		return um.unmarshal(new FileReader(path));
	}

}
