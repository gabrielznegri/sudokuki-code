SUBDIRS = suexg resources classes

bin_SCRIPTS = $(PACKAGE)
EXTRA_SCRIPTS = sudokuki
sudokuki:
	echo "#!/bin/sh" > $@ ; \
	echo "java -Djava.library.path=\"$(libdir)\" -jar $(datadir)/sudokuki/$(PACKAGE)-$(VERSION).jar" >> $@

CLEANFILES = $(EXTRA_SCRIPTS) 

SUEXG_PROXY_JNI_DIR=$(top_builddir)/src/classes/net/jankenpoi/sudokuki/generator/suexg
AM_CPPFLAGS = -I$(top_builddir)/tmpjar/net/jankenpoi/sudokuki/generator/suexg -Isuexg -DLOCALEDIR=\"$(localedir)\" -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux -I$(JAVA_HOME)/include/win32 

lib_LTLIBRARIES = libsuexg_proxy.la

# gettext.h not needed in this project
libsuexg_proxy_la_SOURCES = $(SUEXG_PROXY_JNI_DIR)/suexg_proxy.c suexg/gene_suexg_v12.c suexg/gene_suexg_v12.h gettext.h

# LIBINTL not needed in this project
libsuexg_proxy_la_LDFLAGS = -shared -no-undefined $(LIBINTL) 

