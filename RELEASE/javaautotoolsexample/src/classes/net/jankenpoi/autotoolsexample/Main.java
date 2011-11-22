package net.jankenpoi.autotoolsexample;

import static net.jankenpoi.i18n.I18n._;

public class Main {

	public static void main(String[] args) {

	    System.out.println("Java Autotools Example - "+Version.versionString);
		System.out.println();
		System.out.println(_("This is an example Java program built with GNU Autotools"));
        System.out.println();
		System.out.println("The following line is printed using a JNI call");
		HelloWithJNI helloJNI = new HelloWithJNI();
		helloJNI.printHello();
	}
}
