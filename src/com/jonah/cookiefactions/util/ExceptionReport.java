package com.jonah.cookiefactions.util;

public class ExceptionReport {

	public static void report(Throwable e) {
		System.out.println(ConsoleColors.ANSI_GREEN + "---------------------------------------" + ConsoleColors.ANSI_RESET);
		System.out.println(ConsoleColors.ANSI_GREEN + "Error Caught: Please report to Jonah / Sphro" + ConsoleColors.ANSI_RESET);
		e.printStackTrace();
		System.out.println(ConsoleColors.ANSI_GREEN + "---------------------------------------" + ConsoleColors.ANSI_RESET);
	}
	
}
