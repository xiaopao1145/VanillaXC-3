@rem --------------------------------------------------------------------------
@rem Gradle startup script for Windows
@rem --------------------------------------------------------------------------

@echo off
setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_HOME=%DIRNAME%

@rem Find the Java command
if defined JAVA_HOME goto findJavaFromHome
set JAVA_CMD=java
goto checkJava

:findJavaFromHome
set JAVA_CMD=%JAVA_HOME%\bin\java.exe

:checkJava
"%JAVA_CMD%" -version >nul 2>&1
if errorlevel 1 goto javaNotFound

goto execute

:javaNotFound
echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the location of your Java installation.
goto end

:execute
@rem Set the classpath to include the wrapper JAR
set APP_CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

@rem Execute the Java command to run the Gradle Wrapper
"%JAVA_CMD%" %GRADLE_OPTS% -classpath "%APP_CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

:end
endlocal