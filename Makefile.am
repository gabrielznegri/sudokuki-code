SUBDIRS = po src
ACLOCAL_AMFLAGS = -I m4

EXTRA_DIST = debian build-aux/config.rpath sudokuki.sh

update-po:
	if [ -e src/resources/files/net/jankenpoi/i18n/* ]; then rm src/resources/files/net/jankenpoi/i18n/*; fi
	@find $(srcdir)/src/ -name "*.java" -exec grep -q "I18n._" {} \; -print | sort > $(srcdir)/po/POTFILES.in.2 ; \
	if diff $(srcdir)/po/POTFILES.in $(srcdir)/po/POTFILES.in.2 >/dev/null 2>&1 ; then \
		rm -f $(srcdir)/po/POTFILES.in.2 ; \
	else \
		mv $(srcdir)/po/POTFILES.in.2 $(srcdir)/po/POTFILES.in ; \
	fi
	cd po && $(MAKE) $(AM_MAKEFLAGS) update-po
	cd ..
	for language in `cat po/LINGUAS`; do \
	  echo $$language; \
	  msgmerge --update --no-fuzzy-matching --backup=off "po/"$$language".po" po/sudokuki.pot; \
	  msgcat "po/"$$language".po" -p -o "po/sudokuki_"$$language".properties"; \
	done
	if [ ! -e src/resources/files/net/jankenpoi/i18n ]; then mkdir -p src/resources/files/net/jankenpoi/i18n; fi
	cp po/*.properties src/resources/files/net/jankenpoi/i18n/

