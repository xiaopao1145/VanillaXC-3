#!/usr/bin/env sh

# -----------------------------------------------------------------------------
# Gradle startup script for UN*X
# -----------------------------------------------------------------------------

# Set Java command if not already set
if [ -z "$JAVA_CMD" ]; then
    if [ -n "$JAVA_HOME" ]; then
        JAVA_CMD="$JAVA_HOME/bin/java"
    else
        JAVA_CMD="java"
    fi
fi

# Determine the directory of the script
APP_HOME=$(dirname "$0")

# Set the classpath to include the wrapper JAR
APP_CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

# Set Gradle properties (optional)
# GRADLE_OPTS="-Xmx1024m"

# Execute the Java command to run the Gradle Wrapper
exec "$JAVA_CMD" $GRADLE_OPTS -classpath "$APP_CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"