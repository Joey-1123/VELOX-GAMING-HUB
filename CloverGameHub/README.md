# Clover Game Hub

Standalone Android app (Material 3, Jetpack Compose) with optional privileged system app flavor for The Clover Project.

## Build

```bash
./gradlew :app:assembleStandaloneDebug
./gradlew :app:assembleSystemDebug
```

## Install

```bash
adb install -r app/build/outputs/apk/standalone/debug/app-standalone-debug.apk
```

## System flavor (privileged)

- Build `system` flavor and place the APK under `/system/priv-app/CloverGameHub/` with `644` perms.
- Grant privileged permissions via platform `privapp-permissions` XML or whitelist.
- Once privileged, the app can control DND, rotation, brightness, and run an FPS overlay without user-granted special settings.

## Roadmap

- DND toggle with Notification Policy Access fallback
- Rotation lock and orientation quick actions
- Brightness slider and presets
- FPS overlay service
- Curated wallpapers pack