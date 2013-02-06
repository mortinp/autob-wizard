@echo off
rem for /f %%i in ("%0") do set script_dir=%%~dpi
set script_dir=%~dp0

set script_dir=%script_dir%##
set script_dir=%script_dir:\##=##%
set script_dir=%script_dir:##=%

for /F "usebackq tokens=1* delims==" %%G in ("%script_dir%/autob-config.properties") do (
    if "%%G"=="projects.names" set projects_names_desc=%%H
    if "%%G"=="projects.alias" set projects_alias_desc=%%H
)
for /F "usebackq tokens=1* delims==" %%G in ("%script_dir%/conf/env-config.properties") do (
    if "%%G"=="autobuild.env.path" set auto_build_env_path=%%H
    if "%%G"=="system.java.home" set system_java_home_path=%%H
)
for /F "usebackq tokens=1* delims==" %%G in ("%script_dir%/svn/svn.properties") do (
    if "%%G"=="svn.export.path" set projects_path=%%H
)

set auto_build_env_path=%auto_build_env_path:\=%
set projects_path=%projects_path:\=%
set system_java_home_path=%system_java_home_path:\=%
set resources_path=%auto_build_env_path%/resources

set ANT_HOME=%resources_path%/3p/ant
set JAVA_HOME=%system_java_home_path%
set PATH=%PATH%;%ANT_HOME%/bin;%JAVA_HOME%/bin

set ant_lib_extras=%resources_path%/3p/ant/lib/extras

call ant -lib "%ant_lib_extras%" -buildfile "%resources_path%/svn/svn-export.xml"

cd /d "%resources_path%/custom"

setlocal
call :parse "%projects_names_desc%" "%projects_alias_desc%"
goto :eos

:parse
set listNames=%1
set listNames=%listNames:"=%
set listAlias=%2
set listAlias=%listAlias:"=%
FOR /f "tokens=1* delims=;" %%a IN ("%listNames%") DO (
    set current_project_name=%%a
    set next_project_name=%%b
)
FOR /f "tokens=1* delims=;" %%a IN ("%listAlias%") DO (
    set current_project_alias=%%a
    set next_project_alias=%%b
)

if not "%current_project_name%" == "" call :sub %current_project_name% %current_project_alias%
if not "%next_project_name%" == "" call :parse "%next_project_name%" "%next_project_alias%"
goto :eos

:sub
set project_name=%1
set project_alias=%2

copy "%resources_path%/custom/%project_alias%-build-export.properties" "%projects_path%/%project_name%/build-export.properties"
copy "%resources_path%/custom/postgres.properties" "%projects_path%/%project_name%/postgres.properties"
copy "%resources_path%/custom/nbbuild.properties" "%projects_path%/%project_name%"
copy "%resources_path%/custom/project-custom-build.xml" "%projects_path%/%project_name%"
call ant -lib "%ant_lib_extras%" -buildfile "%projects_path%/%project_name%/project-custom-build.xml" 
goto :eos

:eos
endlocal
