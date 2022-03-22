package com.jonah.cookiefactions.level.System;

import com.jonah.cookiefactions.util.ExceptionReport;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataStreaming {

	public static Object load(File f) {
		ObjectInputStream op;
		try {
			op = new ObjectInputStream(new FileInputStream(f));
			Object obj = op.readObject();
			op.close();
			return obj;
		} catch (IOException | ClassNotFoundException e) {
			if (!(e instanceof EOFException)) {
				ExceptionReport.report(e);
			}
			return null;
		}
	}
	
	public static void save(Object obj, File f) {
		ObjectOutputStream op;
		try {
			op = new ObjectOutputStream(new FileOutputStream(f));
			op.writeObject(obj);
			op.flush();
			op.close();
		} catch (IOException e) {
			if (!(e instanceof EOFException)) {
				ExceptionReport.report(e);
			}
		}
	}
	
}
