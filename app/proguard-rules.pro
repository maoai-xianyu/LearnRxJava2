# Add project specific ProGuard rules here.
# By image_default_bg, the flags in this file are appended to flags specified
# in /Users/zhangkun/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizationpasses 10
-dontusemixedcaseclassnames

-dontnote
-dontwarn java.lang.invoke.*
-ignorewarnings
-allowaccessmodification
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.support.v4.app.FragmentActivity
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keepclassmembers class * implements java.io.Serializable { *;}

-keep class com.squareup.** { *; }

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by image_default_bg, so configure it to keep all of it.
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**
# For using GSON @Expose annotati
-keepattributes *Annotation*on
-keep class com.google.gson.stream.** { *; }

#support
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

-keep class dagger.* { *; }

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

#okio
-dontwarn okio.**
-keep class okio.**{*;}

#retrofit
-dontwarn rx.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keep public class *  implements retrofit2.converter.Converter
#otto
-dontwarn com.squareup.otto.**
-keep class com.squareup.otto.** { *; }
-keepattributes *Annotation*
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}

#javax.annotation
-dontwarn javax.annotation.**

#myokhttp
-dontwarn com.tsy.sdk.myokhttp.**
-keep class com.tsy.sdk.myokhttp.**{*;}
