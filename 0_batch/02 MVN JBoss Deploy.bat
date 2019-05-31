@echo off
:: WildFly must be started before deployment!
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-12
set M2_HOME=c:\tools\apache-maven-3.6.0
pushd %cd%
cd ..
call %M2_HOME%\bin\mvn clean install 
call %M2_HOME%\bin\mvn wildfly:deploy
pause
popd