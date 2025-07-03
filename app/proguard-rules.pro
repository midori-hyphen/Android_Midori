# Midori App ProGuard Configuration

# Keep custom exception classes
-keep class com.example.andoriod_midori.data.ui.MidoriError { *; }

# Hilt
-dontwarn com.google.dagger.hilt.processor.internal.**
-keep class dagger.hilt.android.lifecycle.HiltViewModelMap { *; }

# Timber
-keep class timber.log.** { *; }
-dontwarn timber.log.**

# Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Coil (for SVG support)
-keep class coil.** { *; }
-dontwarn coil.**

# Keep data models
-keep class com.example.andoriod_midori.data.models.** { *; }

# Keep enum classes
-keepclassmembers enum * { *; }

# Keep Parcelable implementations
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}

# Preserve line numbers for debugging
-keepattributes SourceFile,LineNumberTable

# Remove logging in release builds
-assumenosideeffects class timber.log.Timber* {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}