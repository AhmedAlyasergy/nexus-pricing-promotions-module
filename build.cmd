@echo off
cd /d %~dp0
if exist apache-maven-3.9.9\bin\mvn.cmd (
  call apache-maven-3.9.9\bin\mvn.cmd clean package -DskipTests
) else (
  echo Local Maven distribution not found. Please download apache-maven-3.9.9 into the project root.
)
