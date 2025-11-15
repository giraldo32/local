@echo off
REM ================================================
REM Script para ejecutar la aplicación
REM CRUD Local - Sabor Paisa
REM ================================================

echo.
echo ============================================
echo   INICIANDO APLICACION SABOR PAISA
echo ============================================
echo.

if not exist pom.xml (
    echo [ERROR] No se encontro pom.xml
    echo Asegurese de estar en el directorio correcto
    pause
    exit /b 1
)

echo Ejecutando aplicacion...
echo.

mvn exec:java -Dexec.mainClass="com.saborpaisa.main.Aplicacion"

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Error al ejecutar la aplicacion
    echo.
    echo Posibles causas:
    echo - PostgreSQL no esta ejecutandose
    echo - La base de datos no existe
    echo - Credenciales incorrectas
    echo - Maven no esta instalado
    echo.
    pause
)
