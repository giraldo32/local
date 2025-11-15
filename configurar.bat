@echo off
REM ================================================
REM Script de Configuración Rápida
REM CRUD Local - Sabor Paisa
REM ================================================

echo.
echo ============================================
echo   CONFIGURACION CRUD LOCAL - SABOR PAISA
echo ============================================
echo.

REM Verificar Java
echo [1/4] Verificando Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java no esta instalado o no esta en el PATH
    echo Por favor instale Java JDK 11 o superior
    pause
    exit /b 1
)
echo [OK] Java esta instalado
echo.

REM Verificar Maven
echo [2/4] Verificando Maven...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ADVERTENCIA] Maven no esta instalado o no esta en el PATH
    echo El proyecto puede ejecutarse desde un IDE
) else (
    echo [OK] Maven esta instalado
)
echo.

REM Verificar PostgreSQL
echo [3/4] Verificando PostgreSQL...
psql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ADVERTENCIA] PostgreSQL no esta en el PATH
    echo Asegurese de que PostgreSQL este ejecutandose
) else (
    echo [OK] PostgreSQL esta instalado
)
echo.

REM Compilar proyecto
echo [4/4] Compilando proyecto...
if exist pom.xml (
    mvn clean compile
    if %errorlevel% equ 0 (
        echo [OK] Proyecto compilado exitosamente
    ) else (
        echo [ERROR] Error al compilar el proyecto
        pause
        exit /b 1
    )
) else (
    echo [ADVERTENCIA] No se encontro pom.xml
    echo Asegurese de estar en el directorio correcto
)
echo.

echo ============================================
echo   CONFIGURACION COMPLETADA
echo ============================================
echo.
echo PROXIMOS PASOS:
echo.
echo 1. Crear la base de datos en PostgreSQL:
echo    psql -U postgres -c "CREATE DATABASE saborpaisa;"
echo.
echo 2. Restaurar el backup:
echo    psql -U postgres -d saborpaisa -f backup_saborpaisa.sql
echo.
echo 3. Configurar credenciales en:
echo    src\main\java\com\saborpaisa\conexion\Conexion.java
echo.
echo 4. Ejecutar la aplicacion:
echo    mvn exec:java -Dexec.mainClass="com.saborpaisa.main.Aplicacion"
echo.
echo ============================================
echo.
pause
