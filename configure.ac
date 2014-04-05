AC_INIT([sudokuki],[1.2.2],[sylvain@jankenpoi.net])
AC_CONFIG_SRCDIR([src/classes/net/jankenpoi/i18n/I18n.java])
AC_CONFIG_AUX_DIR([build-aux])
AC_CONFIG_MACRO_DIR([m4])
AM_INIT_AUTOMAKE([1.10.1 tar-pax -Wall -Werror])
LT_INIT([shared], [win32-dll])

AM_GNU_GETTEXT_VERSION([0.17])
AM_GNU_GETTEXT([external])

AC_PROG_LIBTOOL




JAVA_HOME_message_line1="Use the following command if you want to specify a custom JAVA_HOME"
JAVA_HOME_message_line2="  ./configure JAVA_HOME=<path to an installed JRE or JDK top directory...)"
NO_JDK_error_message="No valid JRE or JDK environment found, try passing JAVA_HOME to 'configure'"



AC_MSG_NOTICE([$JAVA_HOME_message_line1])
AC_MSG_NOTICE([$JAVA_HOME_message_line2])

AC_MSG_CHECKING([for JAVA_HOME])
if test "x$JAVA_HOME" != x; then
  AC_MSG_RESULT([$JAVA_HOME])
else
  AC_MSG_RESULT([not set])
fi


if test "x$JAVA_HOME" = x; then
  AC_PATH_PROG(TMP_JAVAC, javac, [])
  if test "x$TMP_JAVAC" != "x"; then
    TMP_NOSYMLINK_JAVAC=$(readlink -f $TMP_JAVAC)
    if test "$TMP_NOSYMLINK_JAVAC" != "$TMP_JAVAC"; then
      AC_MSG_RESULT([checking location... $TMP_NOSYMLINK_JAVAC])
      TMP_JAVAC=$TMP_NOSYMLINK_JAVAC
    fi
    JAVA_HOME=`echo $TMP_JAVAC | sed "s/\/*bin\/*javac*//"`
  fi
  if test "x$JAVA_HOME" = x; then
    AC_PATH_PROG(TMP_JAR, jar, [])
    if test "x$TMP_JAR" != "x"; then
      TMP_NOSYMLINK_JAR=$(readlink -f $TMP_JAR)
      if test "$TMP_NOSYMLINK_JAR" != "$TMP_JAR"; then
        AC_MSG_RESULT([checking location... $TMP_NOSYMLINK_JAR])
        TMP_JAR=$TMP_NOSYMLINK_JAR
      fi
      JAVA_HOME=`echo $TMP_JAR | sed "s/\/*bin\/*jar*//"`
    fi
  fi
fi



if test "x$JAVA_HOME" = x; then
  AC_MSG_ERROR([$NO_JDK_error_message])
fi

#AC_MSG_NOTICE
AC_MSG_NOTICE([Checking whether ${JAVA_HOME} is a valid JAVA_HOME for a JRE or JDK...])

AC_PATH_PROG(JAVAC, javac, [not found], [${JAVA_HOME}/bin])
AC_PATH_PROG(JAVA, java, [not found], [${JAVA_HOME}/bin])
AC_PATH_PROG(JAR, jar, [not found], [${JAVA_HOME}/bin])

found_all_tools=false
test "x$JAVAC" != "xnot found" && test "x$JAVA" != "xnot found" && test "x$JAR" != "xnot found" && found_all_tools=true
if test $found_all_tools != true; then
  AC_MSG_ERROR([$NO_JDK_error_message])
fi
AC_ARG_VAR(JAVA_HOME, [Path to an installed Java Development Kit (JDK)])
AC_SUBST(JAVAC)
AC_SUBST(JAR)
AC_SUBST(JAVA)


AC_PROG_CC
AM_PROG_CC_STDC
AC_CONFIG_FILES([Makefile src/Makefile src/resources/Makefile src/classes/Makefile po/Makefile.in])
AC_CONFIG_COMMANDS([Version.java], [echo package: $pkgname version: $pkgversion], [pkgname=$PACKAGE_NAME, pkgversion=$PACKAGE_VERSION])

AC_OUTPUT

