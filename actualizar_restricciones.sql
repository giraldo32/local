-- ========================================
-- SCRIPT PARA ACTUALIZAR RESTRICCIONES
-- Agrega ON DELETE CASCADE a la tabla PEDIDO
-- ========================================

-- Conectarse a la base de datos
\c sabor_paisa;

-- 1. Eliminar la restricción de clave foránea existente
ALTER TABLE PEDIDO 
DROP CONSTRAINT IF EXISTS pedido_id_local_fkey;

-- 2. Agregar la nueva restricción con ON DELETE CASCADE
ALTER TABLE PEDIDO 
ADD CONSTRAINT pedido_id_local_fkey 
FOREIGN KEY (ID_LOCAL) REFERENCES LOCAL (ID_LOCAL) ON DELETE CASCADE;

-- Mostrar mensaje de confirmación
SELECT 'Restricciones actualizadas exitosamente. Ahora se pueden eliminar locales con pedidos asociados.' AS mensaje;
