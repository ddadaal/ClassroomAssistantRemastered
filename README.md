# Classroom Assistant Remastered

HCI Project with a JavaFX + TornadoFX + JFoenix Client (for teachers) and Android Client (for students).

# Development

## Prerequisites

- Latest **Intellij IDEA**(not Android Studio!!) with the following plugins enabled
    - Android Support
    - Kotlin
    - TornadoFX
- Android SDK Version 28 **and** 29 installed
- An Android device or Android Emulator with **Android 9.0** or higher

## Develop

- Open the folder with Intellij IDEA
- Import gradle project
    - If an **Import Gradle project** dialog pops up, choose **Use gradle task configuration** instead of the default **Use default gradle wrapper (recommended)**, since Android's gradle plugin needs a newer gradle distribution (>=5.1.1) whereas the IDEA's default is just 4.x.
- Use predefined **Teacher** or **Student** run configuration to launch respective application

## Important Notes

- If any network problem (like downloading Android SDK, gradle distribution) is experienced, try setting up proxy in settings (to 127.0.0.1:1080, if you use shadowsocks) 
- **Aliyun's maven mirror** has been set up for faster Gradle dependency download, so after initial configurations (Android SDK, gradle distro etc.) are completed, proxy may be disabled.
- Android: prefer [AndroidX](https://developer.android.com/jetpack/androidx/) to `android.suppport` ([What is AndroidX](https://stackoverflow.com/questions/51280090/what-is-androidx)). If any codes are copied from Internet, they are likely to be written with `android.support`, so be sure to re-write them  to AndroidX (replace the **packages** and **XML tags** with those of AndroidX) following [this migration guide](https://developer.android.com/jetpack/androidx/migrate).
- Socket communication between **Android app on a real Android device** and **a socket server running on a PC** in **Campus Network (NJU-WLAN)** has been **proven** possible if IP is correct. Remember to add a textfield in student's Android app to input IP address of the server.

