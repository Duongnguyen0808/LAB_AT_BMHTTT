@echo off
setlocal
chcp 65001 >nul
set "JDBC=%USERPROFILE%\.m2\repository\com\microsoft\sqlserver\mssql-jdbc\12.8.1.jre11\mssql-jdbc-12.8.1.jre11.jar"
set "CP=%JDBC%"

if not exist "%~dp0target\classes" mkdir "%~dp0target\classes"
if not exist "%~dp0target\test-classes" mkdir "%~dp0target\test-classes"

echo [1/3] Compiling main sources (week_03)...
(for /r "%~dp0src\main\java" %%f in (*.java) do @echo %%~f) > "%TEMP%\lab3_main_sources.txt"
javac -encoding UTF-8 --release 17 -cp "%CP%" -d "%~dp0target\classes" @"%TEMP%\lab3_main_sources.txt"
if errorlevel 1 (
    echo [ERROR] Main compilation failed!
    exit /b 1
)

echo [2/3] Compiling test suite (Lab3TestSuite)...
(for /r "%~dp0src\test\java" %%f in (*.java) do @echo %%~f) > "%TEMP%\lab3_test_sources.txt"
javac -encoding UTF-8 --release 17 -cp "%~dp0target\classes;%CP%" -d "%~dp0target\test-classes" @"%TEMP%\lab3_test_sources.txt"
if errorlevel 1 (
    echo [ERROR] Test compilation failed!
    exit /b 1
)

echo [3/3] Running Lab3TestSuite...
java -Dfile.encoding=UTF-8 -cp "%~dp0target\classes;%~dp0target\test-classes;%CP%" week_03.Lab3TestSuite %*
if errorlevel 1 exit /b 1
endlocal
