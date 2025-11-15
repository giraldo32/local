-- ================================================
-- SCRIPT DE RESTAURACIÓN RÁPIDA
-- Base de Datos: saborpaisa
-- ================================================

-- INSTRUCCIONES:
-- 1. Ejecutar como usuario postgres:
--    psql -U postgres -f restaurar_db.sql
-- 2. O desde psql:
--    \i restaurar_db.sql

-- Conectar como superusuario
\c postgres

-- Eliminar base de datos si existe (CUIDADO: Borra todos los datos)
DROP DATABASE IF EXISTS saborpaisa;

-- Crear base de datos
CREATE DATABASE saborpaisa
    WITH 
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Colombia.1252'
    LC_CTYPE = 'Spanish_Colombia.1252'
    TEMPLATE = template0;

-- Conectar a la base de datos
\c saborpaisa

-- Ejecutar el backup completo
\i backup_saborpaisa.sql

-- Mensaje de confirmación
\echo '================================================'
\echo 'Base de datos saborpaisa restaurada exitosamente'
\echo '================================================'
\echo 'Locales registrados:'
SELECT COUNT(*) || ' locales' AS total FROM LOCAL;

\echo ''
\echo 'La aplicacion esta lista para ejecutarse'
\echo '================================================'
