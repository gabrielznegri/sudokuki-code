package net.jankenpoi.autotoolsexample;

class HelloWithJNI {

    static {
	try {
	    System.out.println("BEFORE load library...");
	    System.loadLibrary("hello_jni");
	    System.out.println("...AFTER load library");
		} catch (Throwable t) {
		    t.printStackTrace();
		}
	}

    private native String helloString();
	
	void printHello() {
	    System.out.println("Now were are calling the native method HelloWithJNI.helloString()...");
	    System.out.println(helloString());
	}
	
}
