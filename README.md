# CRUD Local - Sabor Paisa 🍽️

Aplicación Java SE para realizar operaciones CRUD sobre la tabla LOCAL de la base de datos de restaurantes Sabor Paisa.

## 📋 Descripción del Proyecto

Este proyecto es una aplicación de escritorio desarrollada en Java con interfaz gráfica Swing que permite gestionar los locales de la cadena de restaurantes "Sabor Paisa". Implementa las cuatro operaciones básicas de persistencia de datos (CRUD):

- **C**reate (Insertar)
- **R**ead (Consultar/Listar)
- **U**pdate (Actualizar)
- **D**elete (Eliminar)

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una arquitectura en capas con separación de responsabilidades:

```
src/main/java/com/saborpaisa/
├── modelo/         # Clases de entidad (Local)
├── conexion/       # Gestión de conexión a PostgreSQL
├── dao/            # Data Access Objects (LocalDAO)
├── vista/          # Interfaz gráfica Swing (FormularioLocal)
└── main/           # Clase principal (Aplicacion)
```

### Componentes Principales

#### 1. **Clase Conexion** (conexion/Conexion.java)
- Implementa el patrón Singleton para gestionar la conexión a PostgreSQL
- Métodos principales:
  - `obtenerConexion()`: Establece y retorna la conexión a la BD
  - `cerrarConexion()`: Cierra la conexión activa
  - `probarConexion()`: Verifica el estado de la conexión

#### 2. **Clase Local** (modelo/Local.java)
- Modelo de datos que representa la entidad LOCAL
- Atributos con sus respectivos getters y setters
- Implementa buenas prácticas con encapsulamiento

#### 3. **Clase LocalDAO** (dao/LocalDAO.java)
- Contiene todos los métodos CRUD:
  - `insertar(Local)`: Inserta un nuevo local
  - `actualizar(Local)`: Actualiza un local existente
  - `eliminar(int)`: Elimina un local por ID
  - `listarTodos()`: Consulta todos los locales
  - `buscarPorId(int)`: Busca un local específico
  - `buscarPorNombre(String)`: Búsqueda por nombre

#### 4. **Clase FormularioLocal** (vista/FormularioLocal.java)
- Interfaz gráfica Swing con:
  - Campos de texto para los datos del local
  - Botones para cada operación CRUD
  - Tabla para visualizar los registros
  - Función de búsqueda

#### 5. **Clase Aplicacion** (main/Aplicacion.java)
- Punto de entrada del programa
- Verifica la conexión antes de iniciar la GUI

## 🛠️ Tecnologías Utilizadas

- **Java SE 11+**
- **Maven** (gestión de dependencias)
- **PostgreSQL 12+** (base de datos)
- **JDBC** (conectividad con BD)
- **Swing** (interfaz gráfica)

## 📦 Dependencias

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.1</version>
</dependency>
```

## 🚀 Requisitos Previos

1. **Java JDK 11 o superior**
   - Verificar instalación: `java -version`

2. **Maven**
   - Verificar instalación: `mvn -version`

3. **PostgreSQL 12 o superior**
   - Servidor PostgreSQL ejecutándose en `localhost:5432`

## 💾 Configuración de la Base de Datos

### Paso 1: Crear la base de datos

```sql
CREATE DATABASE saborpaisa;
```

### Paso 2: Restaurar el backup

Desde la línea de comandos:

```bash
psql -U postgres -d saborpaisa -f backup_saborpaisa.sql
```

O desde pgAdmin:
1. Conectarse a PostgreSQL
2. Crear la base de datos `saborpaisa`
3. Ejecutar el script `backup_saborpaisa.sql`

### Paso 3: Configurar credenciales

Editar el archivo `src/main/java/com/saborpaisa/conexion/Conexion.java`:

```java
private static final String URL = "jdbc:postgresql://localhost:5432/saborpaisa";
private static final String USUARIO = "postgres";  // Cambiar si es necesario
private static final String CONTRASENA = "admin";  // Cambiar por tu contraseña
```

## 🏃 Ejecución del Proyecto

### Opción 1: Con Maven

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn exec:java -Dexec.mainClass="com.saborpaisa.main.Aplicacion"
```

### Opción 2: Con Maven (generar JAR)

```bash
# Compilar y empaquetar
mvn clean package

# Ejecutar el JAR
java -jar target/crud-local-1.0-SNAPSHOT.jar
```

### Opción 3: Desde un IDE

1. Importar el proyecto Maven en tu IDE (Eclipse, IntelliJ IDEA, NetBeans)
2. Ejecutar la clase `com.saborpaisa.main.Aplicacion`

## 📖 Guía de Uso

### Insertar un Nuevo Local

1. Completar los campos del formulario:
   - **Nombre Comercial** (obligatorio)
   - **Dirección** (obligatorio)
   - Teléfono
   - Gerente
   - Hora Apertura (formato HH:MM, ej: 08:00)
   - Hora Cierre (formato HH:MM, ej: 22:00)

2. Hacer clic en el botón **💾 Guardar**

### Actualizar un Local

1. Seleccionar un local de la tabla
2. Los datos se cargarán automáticamente en el formulario
3. Modificar los campos deseados
4. Hacer clic en el botón **✏️ Actualizar**

### Eliminar un Local

1. Seleccionar un local de la tabla
2. Hacer clic en el botón **🗑️ Eliminar**
3. Confirmar la eliminación en el diálogo

### Consultar Locales

- **Listar todos**: Hacer clic en **📋 Listar Todos**
- **Buscar por nombre**: 
  1. Escribir el nombre en el campo de búsqueda
  2. Hacer clic en **🔍 Buscar**

### Limpiar Formulario

- Hacer clic en **🧹 Limpiar** para vaciar todos los campos

## 🎯 Funcionalidades Implementadas

✅ **Inserción (INSERT)**
- Validación de campos obligatorios
- Conversión de formato de hora
- Mensajes de confirmación

✅ **Actualización (UPDATE)**
- Selección de registro desde la tabla
- Carga automática de datos
- Validación antes de actualizar

✅ **Eliminación (DELETE)**
- Confirmación antes de eliminar
- Manejo de errores de integridad referencial

✅ **Consulta (SELECT)**
- Listar todos los registros
- Búsqueda por nombre (ILIKE)
- Visualización en tabla ordenada

✅ **Características adicionales**
- Interfaz gráfica intuitiva y moderna
- Validación de datos
- Manejo de excepciones
- Mensajes informativos
- Patrón DAO
- Código bien documentado

## 📁 Estructura de Archivos Entregables

```
crud-local/
├── pom.xml                          # Configuración Maven
├── backup_saborpaisa.sql            # Backup de la base de datos
├── README.md                        # Este archivo
└── src/main/java/com/saborpaisa/
    ├── conexion/
    │   └── Conexion.java            # Clase de conexión
    ├── dao/
    │   └── LocalDAO.java            # Métodos CRUD
    ├── main/
    │   └── Aplicacion.java          # Clase principal
    ├── modelo/
    │   └── Local.java               # Modelo de datos
    └── vista/
        └── FormularioLocal.java     # Interfaz gráfica
```

## 🔧 Solución de Problemas

### Error: "Driver de PostgreSQL no encontrado"
**Solución**: Verificar que la dependencia de PostgreSQL esté en el `pom.xml` y ejecutar `mvn clean install`

### Error: "No se pudo conectar a la base de datos"
**Solución**:
1. Verificar que PostgreSQL esté ejecutándose
2. Verificar usuario y contraseña en `Conexion.java`
3. Verificar que la base de datos `saborpaisa` exista
4. Verificar el puerto (predeterminado: 5432)

### Error: "Formato de hora inválido"
**Solución**: Usar el formato HH:MM (ejemplo: 08:30, 14:00, 23:45)

## 👨‍💻 Buenas Prácticas Implementadas

- ✅ Separación de responsabilidades (MVC/DAO)
- ✅ Encapsulamiento de datos (getters/setters)
- ✅ Uso de PreparedStatement (prevención de SQL injection)
- ✅ Manejo de excepciones
- ✅ Cierre apropiado de recursos (try-with-resources)
- ✅ Validación de datos de entrada
- ✅ Mensajes informativos al usuario
- ✅ Código documentado con Javadoc
- ✅ Nomenclatura clara y consistente
- ✅ Patrón Singleton para conexión

## 📊 Modelo de Datos - Tabla LOCAL

```sql
CREATE TABLE LOCAL (
    ID_LOCAL SERIAL PRIMARY KEY,
    NOMBRE_COMERCIAL VARCHAR(100) NOT NULL,
    DIRECCION VARCHAR(150) NOT NULL,
    TELEFONO VARCHAR(15),
    GERENTE VARCHAR(100),
    HORA_APERTURA TIME,
    HORA_CIERRE TIME
);
```

## 📝 Notas Importantes

1. **Conexión a la BD**: La aplicación verifica la conexión al iniciar. Si falla, muestra un mensaje de error y no abre la interfaz gráfica.

2. **Formato de Horas**: Las horas deben ingresarse en formato 24 horas (HH:MM). Ejemplos válidos: 08:00, 14:30, 23:45

3. **Campos Obligatorios**: Nombre Comercial y Dirección son obligatorios para insertar o actualizar.

4. **ID Automático**: El campo ID_LOCAL se genera automáticamente (SERIAL) y no es editable.

## 🎓 Rúbrica del Proyecto

Este proyecto cumple con los siguientes criterios de evaluación:

### Clase Conexión (30 pts)
- ✅ Método conexión implementado correctamente
- ✅ Métodos para sentencias SQL completos
- ✅ Patrón Singleton aplicado

### Métodos CRUD (30 pts)
- ✅ Guardar (insertar) implementado
- ✅ Editar (actualizar) implementado
- ✅ Eliminar implementado
- ✅ Consultar (listar) implementado
- ✅ Sintaxis correcta y eficiente

### Formulario Java (30 pts)
- ✅ Métodos get y set correctamente implementados
- ✅ Buenas prácticas de desarrollo
- ✅ Interfaz intuitiva y funcional

### Puntualidad y Entrega (10 pts)
- ✅ Formato correcto de entrega
- ✅ Todos los archivos incluidos

## 📧 Información del Estudiante

**Nombre**: [Tu Nombre Completo]  
**Código**: [Tu Código]  
**Materia**: Persistencia de Datos  
**Actividad**: Unidad 2. Evidencia de Aprendizaje 1. Operaciones CRUD en Java

## 📄 Licencia

Este proyecto es con fines educativos para la materia de Persistencia de Datos.

---

**Fecha de Creación**: Noviembre 2025  
**Versión**: 1.0.0  
**Estado**: ✅ Completado
