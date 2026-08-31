# Build AURALIVO without a computer

This project includes a GitHub Actions workflow that can build the APK in the cloud.

## Recommended phone-only route
1. Create a GitHub account and a new repository named `AuralivoMusicPlayer`.
2. Upload the project files to the repository, including `.github/workflows/build-apk.yml`.
3. Commit to the `main` branch.
4. Open the repository's **Actions** tab.
5. Select **Build AURALIVO APK** and tap **Run workflow** if it is not already running.
6. When the workflow finishes successfully, open the workflow run and download the artifact named **AURALIVO-debug-apk**.
7. Extract the artifact and install `app-debug.apk` on your Android phone.

The first build can take several minutes because Android dependencies are downloaded in the cloud.
