@echo off
rem for /f %%i in ("%0") do set script_dir=%%~dpi
set script_dir=%~dp0

set script_dir=%script_dir%##
set script_dir=%script_dir:\##=##%
set script_dir=%script_dir:##=%

rem default value for build interval
set build_interval=10
for /F "usebackq tokens=1* delims==" %%G in ("%script_dir%/conf/env-config.properties") do (
    if "%%G"=="autobuild.env.path" set auto_build_env_path=%%H
    if "%%G"=="system.java.home" set system_java_home_path=%%H
    if "%%G"=="build.interval" set build_interval=%%H
)

set auto_build_env_path=%auto_build_env_path:\=%
set system_java_home_path=%system_java_home_path:\=%

set JAVA_HOME=%system_java_home_path%
set PATH=%PATH%;%JAVA_HOME%/bin

java -jar "%auto_build_env_path%/resources/autob-daemon.jar" "%auto_build_env_path%/resources/launch-autob.bat" %build_interval%