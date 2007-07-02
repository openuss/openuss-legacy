@echo off
setlocal
rem ---------------------------------------------------------------------------
rem Optimizeit start script for the CATALINA Server
rem
rem Generated by the Optimizeit wizard on Mon Sep 18 10:46:01 CEST 2006
rem ---------------------------------------------------------------------------


rem If this script fails, uncomment the set line below these commented lines by
rem removing the 'rem'. This will cause Tomcat to start in the current command
rem shell so you can read any error message generated
rem set RUN_IN_CURRENT_SHELL=1


rem Guess CATALINA_HOME if not defined
if not "%CATALINA_HOME%" == "" goto gotHome
set CATALINA_HOME=.
if exist "%CATALINA_HOME%\bin\catalina.bat" goto okHome
set CATALINA_HOME=..
:gotHome
if exist "%CATALINA_HOME%\bin\catalina.bat" goto okHome
echo The CATALINA_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
goto end
:okHome

if not "%JAVA_HOME%" == "" goto javaHomeSet
echo JAVA_HOME is not set, using D:\Development\Borland\JBuilder2006\jdk1.5
set JAVA_HOME=D:\Development\Borland\JBuilder2006\jdk1.5
:javaHomeSet
if exist "%JAVA_HOME%" goto okJDK
echo You need to have JAVA_HOME defined and pointing to your JDK
echo to run this script
goto end
:okJDK

set EXECUTABLE=%CATALINA_HOME%\bin\catalina.bat

set OPTIT_HOME=D:\development\Borland\Optimizeit\OptimizeitEntSuite2006
%OPTIT_HOME%\jre\bin\java.exe -classpath "%OPTIT_HOME%\lib\optit.jar;%OPTIT_HOME%\lib\primetime.jar" intuitive.audit.instr.PreInstrumentation "%OPTIT_HOME%" "%JAVA_HOME%" "set OI_BCP=" > oi_tmp.bat
call oi_tmp.bat
del oi_tmp.bat
set CATALINA_OPTS=-Xrunoii:%CATALINA_HOME%\conf\optimizeit.xml -Xbootclasspath/p:%OI_BCP%;%OPTIT_HOME%\lib\optit.jar  %CATALINA_OPTS%
set PATH=%OPTIT_HOME%\lib;%PATH%

if exist "%EXECUTABLE%" goto okExec
echo Cannot find %EXECUTABLE%
echo This file is needed to run this program
goto end
:okExec

set CMD_LINE_ARGS=
:setArgs
if ""%1""=="""" goto doneSetArgs
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto setArgs
:doneSetArgs

set RUN_OR_START=start
if "%RUN_IN_CURRENT_SHELL%" == "" goto nowStart
set RUN_OR_START=run

:nowStart
call "%EXECUTABLE%" %RUN_OR_START% %CMD_LINE_ARGS%

:end
