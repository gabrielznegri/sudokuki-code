JAVAROOT = $(top_builddir)/output
RESOURCESDIR = $(top_builddir)/src/resources

JAVA_HOME=@JAVA_HOME@
JAVAC=@JAVAC@
JAVAH=@JAVAH@
JAVA=@JAVA@
JAR=@JAR@

include EXTRA_DIST.include

$(EXTRA_DIST): updateAndCheckExtraDistFilesList

updateAndCheckExtraDistFilesList:
	rm $(RESOURCESDIR)/new_EXTRA_DIST.include; \
	line="EXTRA_DIST = "; \
	for next in `find $(RESOURCESDIR)/files -type f | grep -v ".svn" && echo "THE_END"` ; do \
	  if test "$$next" != "THE_END"; then \
	    echo $$line" \\" >> $(RESOURCESDIR)/new_EXTRA_DIST.include; \
	    line=`echo "$$next" | sed "s/^.*src\/resources\/\(.*\)/\1 /"` ; \
	  else \
	    echo $$line >> $(RESOURCESDIR)/new_EXTRA_DIST.include; \
	  fi; \
	done; \
	if test -f $(RESOURCESDIR)/EXTRA_DIST.include; then \
	cmp -s $(RESOURCESDIR)/new_EXTRA_DIST.include $(RESOURCESDIR)/EXTRA_DIST.include; \
	RETVAL=$$?; \
	if test "$$RETVAL" = "0" ; then \
	  echo ""; \
	  echo "*** EXTRA_DIST.include is up-to-date"; \
	  echo ""; \
	else \
	  echo ""; \
	  echo "*** The control file 'EXTRA_DIST.include' needs to be updated first!"; \
	  echo "*** Replace it with 'new_EXTRA_DIST.include' and try again."; \
	  echo ""; \
	  exit 1; \
	fi; \
	fi

CLEANFILES = new_EXTRA_DIST.include

