@echo off
setlocal enabledelayedexpansion
chcp 65001 >nul

set "TARGET_DIR=%~dp0target"
set "CLASSES_DIR=%TARGET_DIR%\classes"
set "TEST_CLASSES_DIR=%TARGET_DIR%\test-classes"
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
if not exist "%TEST_CLASSES_DIR%" mkdir "%TEST_CLASSES_DIR%"

echo [1/3] Compiling main sources (week_03)...
set "MAIN_SRC_LIST=%TEMP%\lab3_main_sources_%RANDOM%.txt"
(for /r "%~dp0src\main\java" %%f in (*.java) do @if %%~zf GTR 0 echo %%~f) > "%MAIN_SRC_LIST%"
for %%A in ("%MAIN_SRC_LIST%") do if %%~zA GTR 0 (
    javac -encoding UTF-8 --release 17 -cp "%CP%" -d "%CLASSES_DIR%" @"%MAIN_SRC_LIST%"
    if errorlevel 1 (
        echo [ERROR] Main compilation failed!
        del "%MAIN_SRC_LIST%" >nul 2>&1
        exit /b 1
    )
)
del "%MAIN_SRC_LIST%" >nul 2>&1

echo [2/3] Compiling test suite (Lab3TestSuite)...
set "TEST_SRC_LIST=%TEMP%\lab3_test_sources_%RANDOM%.txt"
(for /r "%~dp0src\test\java" %%f in (*.java) do @if %%~zf GTR 0 echo %%~f) > "%TEST_SRC_LIST%"
for %%A in ("%TEST_SRC_LIST%") do if %%~zA GTR 0 (
    javac -encoding UTF-8 --release 17 -cp "%CLASSES_DIR%;%CP%" -d "%TEST_CLASSES_DIR%" @"%TEST_SRC_LIST%"
    if errorlevel 1 (
        echo [ERROR] Test compilation failed!
        del "%TEST_SRC_LIST%" >nul 2>&1
        exit /b 1
    )
)
del "%TEST_SRC_LIST%" >nul 2>&1

echo [3/3] Running Lab3TestSuite...
java -Dfile.encoding=UTF-8 -cp "%CLASSES_DIR%;%TEST_CLASSES_DIR%;%CP%" week_03.Lab3TestSuite %*
if errorlevel 1 exit /b 1
endlocal
