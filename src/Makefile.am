SUBDIRS = resources classes

bin_SCRIPTS = $(PACKAGE)
EXTRA_SCRIPTS = sudokuki
sudokuki:
	echo "#!/bin/sh" > $@ ; \
	echo "java -Djava.library.path=\"$(libdir)\" -jar $(datadir)/sudokuki/$(PACKAGE)-$(VERSION).jar" >> $@

CLEANFILES = $(EXTRA_SCRIPTS) 

# gettext.h not needed in this project
# LIBINTL not needed in this project

