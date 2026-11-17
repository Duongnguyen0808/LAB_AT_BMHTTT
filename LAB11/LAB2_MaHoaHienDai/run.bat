@echo off
chcp 65001 >nul
set "JDBC=%USERPROFILE%\.m2\repository\com\microsoft\sqlserver\mssql-jdbc\12.8.1.jre11\mssql-jdbc-12.8.1.jre11.jar"
set "BC=%USERPROFILE%\.m2\repository\org\bouncycastle\bcprov-jdk18on\1.78.1\bcprov-jdk18on-1.78.1.jar"
set "CP=%JDBC%;%BC%"

if not exist "%~dp0target\classes" mkdir "%~dp0target\classes"
(for /r "%~dp0src\main\java" %%f in (*.java) do @echo %%~f) > "%TEMP%\lab2_sources.txt"
javac -encoding UTF-8 --release 17 -cp "%CP%" -d "%~dp0target\classes" @"%TEMP%\lab2_sources.txt"
if errorlevel 1 exit /b 1

set "FORM=%~1"
if "%FORM%"=="" set "FORM=frm_Menu"
java -cp "%~dp0target\classes;%CP%" week_02.%FORM%
