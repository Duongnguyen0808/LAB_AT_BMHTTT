@echo off
chcp 65001 >nul
setlocal

set "JDBC=%USERPROFILE%\.m2\repository\com\microsoft\sqlserver\mssql-jdbc\12.8.1.jre11\mssql-jdbc-12.8.1.jre11.jar"
set "BC=%USERPROFILE%\.m2\repository\org\bouncycastle\bcprov-jdk18on\1.78.1\bcprov-jdk18on-1.78.1.jar"
set "CP=%JDBC%;%BC%"

if not exist "%~dp0target\classes" mkdir "%~dp0target\classes"
if not exist "%~dp0target\test-classes" mkdir "%~dp0target\test-classes"

echo [1/4] Dang bien dich ma nguon chinh (src\main\java)...
(for /r "%~dp0src\main\java" %%f in (*.java) do @echo %%~f) > "%TEMP%\lab2_main_sources.txt"
javac -encoding UTF-8 --release 17 -cp "%CP%" -d "%~dp0target\classes" @"%TEMP%\lab2_main_sources.txt"
if errorlevel 1 exit /b 1

echo [2/4] Dang bien dich bo kiem thu (src\test\java)...
(for /r "%~dp0src\test\java" %%f in (*.java) do @echo %%~f) > "%TEMP%\lab2_test_sources.txt"
javac -encoding UTF-8 --release 17 -cp "%~dp0target\classes;%CP%" -d "%~dp0target\test-classes" @"%TEMP%\lab2_test_sources.txt"
if errorlevel 1 exit /b 1

echo [3/4] Dang chay Java Test Suite (CipherUtil, DES, 3DES, AES-GCM, RC4, Twofish, AES-CTR, Swing, SQL)...
if "%~1"=="" (
    java -cp "%~dp0target\test-classes;%~dp0target\classes;%CP%" week_02.Lab2TestSuite
) else (
    java -cp "%~dp0target\test-classes;%~dp0target\classes;%CP%" week_02.Lab2TestSuite "--db-pass=%~1"
)
if errorlevel 1 exit /b 1

echo.
echo [4/4] Dang chay Python AES-CBC Self-Test...
py "%~dp0..\LAB2_Python\aes_cbc_gui.py" --self-test
if errorlevel 1 exit /b 1

echo.
echo ==================================================================
echo   HOAN TAT TOAN BO KIEM THU (JAVA + SQL SERVER + PYTHON): PASS!
echo ==================================================================
