@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-12
set JBOSS_HOME=c:\tools\wildfly-16.0.0.Final
del /F /Q %JBOSS_HOME%\standalone\log\*
del /F /Q %JBOSS_HOME%\standalone\tmp\*
pushd %cd%
cd ..
call %JBOSS_HOME%\bin\standalone.bat
popd