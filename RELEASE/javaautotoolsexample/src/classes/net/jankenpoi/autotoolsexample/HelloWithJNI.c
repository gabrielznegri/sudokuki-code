#include <locale.h>
#include "gettext.h"
#define _(string) gettext (string)

#include "HelloWithJNI.h"

JNIEXPORT jstring JNICALL Java_net_jankenpoi_autotoolsexample_HelloWithJNI_helloString(JNIEnv * env, jobject obj) {

    printf("Hello, before setlocale...\n");
    setlocale(LC_ALL, "");
	
    printf("Hello, before bindtextdomain...\n");
    bindtextdomain(PACKAGE, LOCALEDIR);
	
    printf("Hello, before textdomain...\n");
    textdomain(PACKAGE);
    printf("Hello, after textdomain...\n");

    return  (*env)->NewStringUTF(env, _("Hello from JNI"));
}

