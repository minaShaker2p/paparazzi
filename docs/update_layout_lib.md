Updating LayoutLib version
==========================

## To update layout lib version requires the following:
1. Look through [android code search](https://cs.android.com) to find the commit before Android Gradle Plugin version release you are targeting.
2. Find commit hash and update inside the root [build.gradle].
3. Run `./gradlew check` to make sure the build passes existing tests.
4. Create snapshot to use against tests outside of this repo to verify any breaking changes.
5. Update any references in [Readme.md] about gradle plugin version support.
6. Create PR to master with new build sha.