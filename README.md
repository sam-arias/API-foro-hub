<h1 align="center">API REST para un Foro con Java y Spring Boot</h1>

<p align="center">
    <img src="https://img.shields.io/badge/Spring%20Boot-v3.3.0-brightgreen" alt="Spring Boot Starter Data JPA">
    <img src="https://img.shields.io/badge/MySQL-runtime-blue" alt="MySQL">
    <img src="https://img.shields.io/badge/STATUS-CONCLUIDO-green" alt="Badge concluido">
    <img src="https://img.shields.io/badge/RELEASE%20DATE-JANUARY-yellow" alt="Badge date">
    <img src="https://img.shields.io/badge/CHALLENGE-ALURA%20ONE-darkblue" alt="Badge challenge">
</p>

---

## 📄 Descripción

**Foro Hub** es una API RESTful diseñada para gestionar un foro en línea, permitiendo a los usuarios interactuar mediante la creación y visualización de *tópicos*. Este proyecto implementa un CRUD completo y está construido utilizando Java y Spring Boot, con persistencia de datos en una base de datos MySQL.

La aplicación incorpora medidas de seguridad sólidas mediante el uso de **JWT tokens** con una validez de 2 horas para proteger los datos y evitar accesos no autorizados. Además, cuenta con un manejo de errores robusto para garantizar que los usuarios reciban mensajes claros y detallados en caso de realizar acciones inválidas.

---

## ✨ Funcionalidades principales

1. **Autenticación y registro de usuarios**
   - Los usuarios pueden registrarse e iniciar sesión de manera segura utilizando credenciales encriptadas.
   - Generación de tokens JWT para autenticación.
  
2. **Gestión de usuarios**
   - Visualización de usuarios existentes.
   - Actualización y eliminación de usuarios.
   - Restricción de duplicados según los criterios definidos (email único por cada usuario).

3. **Gestión de tópicos**
   - Creación de nuevos tópicos.
   - Visualización de tópicos existentes.
   - Actualización y eliminación de tópicos.
   - Restricción de duplicados según los criterios definidos (título y mensaje únicos por usuario).

4. **Manejo de errores**
   - Validación de datos enviados por los usuarios.
   - Respuesta clara con códigos HTTP estándar (400, 404, 409, etc.) y mensajes explicativos.

5. **Seguridad**
   - Protección de rutas con autenticación mediante JWT.
   - Restricción de acceso a usuarios no autenticados (HTTP 403 Forbidden).

---

## 🛠️ Tecnologías utilizadas

- **Java**: Lenguaje de programación principal para la lógica del backend.
- **Spring Boot (v3.3.0)**: Framework para simplificar el desarrollo de aplicaciones Java.
  - *Spring Security*: Para la autenticación y autorización mediante tokens JWT.
  - *Spring Data JPA*: Para la integración con MySQL.
  - *Spring Web*: Para manejar peticiones HTTP RESTful.
  - *Spring Boot DevTools*: Para facilitar el desarrollo con reinicio automático.
- **MySQL**: Base de datos relacional para la persistencia de datos.
- **JWT (JSON Web Tokens)**: Para la autenticación basada en tokens.
- **Maven**: Para la gestión de dependencias y construcción del proyecto.
- **Validation**: Para la validación de datos en los DTOs.
- **Flyway Migration**: Para gestionar la migración de la base de datos.
- **Auth0**: Solución para la autenticación y autorización simplificada con opciones para manejar usuarios y roles.

---

## 📖 Endpoints principales

### Autenticación de usuarios
| Método | Endpoint         | Descripción                        | Autenticación |
|--------|------------------|------------------------------------|---------------|
| POST   | `/auth/register` | Registro de un nuevo usuario       | ❌            |
| POST   | `/auth/login`    | Inicio de sesión y generación de JWT     | ❌            |

### Gestión de usuarios
| Método | Endpoint         | Descripción                        | Autenticación |
|--------|------------------|------------------------------------|---------------|
| GET    | `/usuarios`        | Obtener todos los usuarios                | ✅            |
| PUT    | `/usuarios/{id}`   | Actualizar un usuario existente           | ✅            |
| DELETE | `/usuarios/{id}`   | Eliminar un usuario lógicamente           | ✅            |


### Tópicos
| Método | Endpoint          | Descripción                              | Autenticación |
|--------|-------------------|------------------------------------------|---------------|
| GET    | `/topicos`        | Obtener todos los tópicos                | ✅            |
| POST   | `/topicos`        | Crear un nuevo tópico                    | ✅            |
| PUT    | `/topicos/{id}`   | Actualizar un tópico existente           | ✅            |
| DELETE | `/topicos/{id}`   | Eliminar un tópico lógicamente           | ✅            |
| DELETE | `/topicos/eliminar/{id}`   | Eliminar un tópico              | ✅            |


---

## 🛡️ Seguridad

La seguridad se basa en **Spring Security** con autenticación mediante JWT. Cada usuario registrado debe enviar el token en los encabezados de la solicitud para acceder a los endpoints protegidos.

## 🧪 Testing
Los endpoints de la API han sido probados utilizando Postman. Se incluye una colección de ejemplos en el repositorio.

## :hammer: Configuración del Proyecto ⚙️
- spring.datasource.url=jdbc:mysql://localhost:tu_puerto/tu_base_de_datos
- spring.datasource.username=tu_usuario
- spring.datasource.password=tu_contraseña
- api.security.secret=clave_secreta

## © Copyright

- Samuel Stevan Arias T. 
- Challenge Alura Latam, equipo ONE
