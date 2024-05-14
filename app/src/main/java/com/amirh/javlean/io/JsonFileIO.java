package com.amirh.javlean.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFileIO{
	
	public static void writeTo(String path,Object o) throws IOException{
		Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.create();
		FileWriter writer = new FileWriter(new File(path));
		gson.toJson(o, writer);
		writer.close();
	}

}
