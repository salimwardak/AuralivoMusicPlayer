# AURALIVO MUSIC PLAYER — v1.1.0

A polished Android local music player foundation built with Kotlin, Jetpack Compose and Android Media3.

## Included
- Automatic device music discovery via MediaStore
- Real playback using Media3 / ExoPlayer
- MediaSession background playback and lock-screen/media controls
- Play queue with next/previous
- Home, Songs, Albums and Playlists sections
- Search across title, artist and album
- Persistent Favorites
- Album artwork loading
- Full Now Playing screen with seek bar
- Handles audio becoming noisy (e.g. headphone disconnect)
- Dark, premium AURALIVO visual language
- Android 13+ media and notification permission handling

## Build
Open this folder in Android Studio, allow Gradle Sync, then Run on an Android device or emulator.

## Next production upgrades
- Persistent user-created playlists (multiple playlists)
- Sleep timer
- Gapless / crossfade controls
- Audio effects / equalizer UI tied to the active audio session
- Lyrics provider integration (with licensing/terms compliance)
- Android Auto browsing/actions
- Backup/restore settings
- Onboarding and Play Store release assets
- Automated tests and accessibility polish

## Build from a phone
See `BUILD_ON_PHONE.md`. A GitHub Actions workflow is included at `.github/workflows/build-apk.yml` to build the debug APK in the cloud.
