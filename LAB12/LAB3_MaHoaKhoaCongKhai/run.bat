@echo off
setlocal enabledelayedexpansion
chcp 65001 >nul

set "TARGET_DIR=%~dp0target"
set "CLASSES_DIR=%TARGET_DIR%\classes"
set "JDBC=%USERPROFILE%\.m2\repository\com\microsoft\sqlserver\mssql-jdbc\12.8.1.jre11\mssql-jdbc-12.8.1.jre11.jar"

if not exist "%JDBC%" (
    where mvn >nul 2>&1
    if not errorlevel 1 (
        echo [INFO] Dang tai thu vien mssql-jdbc qua Maven...
        call mvn dependency:get -Dartifact=com.microsoft.sqlserver:mssql-jdbc:12.8.1.jre11 -q
    )
)

set "CP=%JDBC%"

if not exist "%CLASSES_DIR%" mkdir "%CLASSES_DIR%"

set "SRC_LIST=%TEMP%\lab3_sources_%RANDOM%.txt"
(for /r "%~dp0src\main\java" %%f in (*.java) do @if %%~zf GTR 0 echo %%~f) > "%SRC_LIST%"

for %%A in ("%SRC_LIST%") do if %%~zA GTR 0 (
    javac -encoding UTF-8 --release 17 -cp "%CP%" -d "%CLASSES_DIR%" @"%SRC_LIST%"
    if errorlevel 1 (
        del "%SRC_LIST%" >nul 2>&1
        exit /b 1
    )
)
del "%SRC_LIST%" >nul 2>&1

set "TARGET=%~1"
if "%TARGET%"=="" set "TARGET=frm_Menu"

java -Dfile.encoding=UTF-8 -cp "%CLASSES_DIR%;%CP%" week_03.%TARGET% %2 %3 %4 %5
endlocal
