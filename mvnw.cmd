@echo off
setlocal
set MAVEN_PROJECTBASEDIR=%~dp0
if defined JAVA_HOME (
  set "JAVA_CMD=%JAVA_HOME%\bin\java"
) else (
  set "JAVA_CMD=java"
)
"%JAVA_CMD%" -cp "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain %*
if errorlevel 1 exit /b %errorlevel%
