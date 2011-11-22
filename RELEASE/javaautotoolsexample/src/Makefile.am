SUBDIRS = classes

bin_SCRIPTS = $(PACKAGE)
EXTRA_SCRIPTS = javaautotoolsexample
javaautotoolsexample:
	echo "#!/bin/sh" > $@ ; \
	echo "java -Djava.library.path=\"$(libdir)\" -jar $(datadir)/java/$(PACKAGE)-$(VERSION).jar" >> $@

CLEANFILES = $(EXTRA_SCRIPTS) 

HELLO_JNI_DIR=$(top_builddir)/src/classes/net/jankenpoi/autotoolsexample
AM_CPPFLAGS = -I$(HELLO_JNI_DIR) -DLOCALEDIR=\"$(localedir)\" -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/win32 

lib_LTLIBRARIES = libhello_jni.la
libhello_jni_la_SOURCES = $(top_builddir)/src/classes/net/jankenpoi/autotoolsexample/HelloWithJNI.c gettext.h
libhello_jni_la_LDFLAGS = -shared -no-undefined $(LIBINTL) 

