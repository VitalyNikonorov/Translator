#include <jni.h>

JNIEXPORT jstring JNICALL
Java_nikonorov_net_translator_data_JniManager_getAPIKey(JNIEnv *env, jclass type) {

    static const char key[] = "trnsl.1.1.20170318T213150Z.d83d1acad689334f.b7f777dde4109491a144049dfce47051939c04a2";

    return (*env)->NewStringUTF(env, key);
}