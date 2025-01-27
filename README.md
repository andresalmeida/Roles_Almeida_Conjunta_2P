# Evaluación Conjunta del Segundo Parcial 😔

Este proyecto forma parte de la **Evaluación Conjunta del Segundo Parcial** y consiste en la implementación de un sistema basado en microservicios para la **gestión de roles y usuarios**. Incluye funcionalidades clave como la asignación, revocación y listado de roles asociados a usuarios, además de persistencia en una base de datos MySQL alojada en Docker.

---

## 🔧 **Tecnologías Utilizadas**
- **Java** con **Spring Boot**: Framework para la construcción de microservicios.
- **Hibernate (JPA)**: Manejo de la capa de persistencia.
- **MySQL**: Base de datos relacional.
- **Docker**: Contenedor para la base de datos MySQL.
- **RestTemplate** y **Feign Client**: Comunicación entre microservicios.
- **Lombok**: Para simplificar el código con anotaciones.
- **Maven**: Gestión de dependencias.

---

## ⚙️ **Funcionalidades del Sistema**

### 🟢 Microservicio `micro-roles`
- **CRUD de roles**: Crear, listar, actualizar y eliminar roles.
- **Gestión de relaciones muchos a muchos**:
  - Asignar roles a un usuario.
  - Revocar roles de un usuario.
  - Listar los roles asociados a un usuario.
  - Listar usuarios que tienen un rol específico.
- **Persistencia en MySQL**: Los datos se almacenan de forma estructurada y pueden ser consultados posteriormente.

### 🔵 Microservicio `micro-usuarios`
- **Gestión de usuarios**: CRUD básico para usuarios.
- **Comunicación entre microservicios**: Los usuarios se integran con el microservicio `micro-roles`.

---

## 📁 **Estructura del Proyecto**

### `micro-roles`
- `controller`: Controladores REST para manejar las operaciones de roles y asignaciones.
- `service`: Lógica de negocio para la gestión de roles.
- `model.entity`: Entidades de la base de datos (`Rol` y `Usuario`).
- `repository`: Interfaces JPA para interactuar con MySQL.
- `clients`: Cliente Feign para la comunicación con `micro-usuarios`.

### `micro-usuarios`
- Estructura similar a `micro-roles`, con funcionalidades específicas para la gestión de usuarios.

---

## 🚀 **Instrucciones de Ejecución**

### 1. **Configurar MySQL con Docker**
Se ejecuta el siguiente comando para crear un contenedor MySQL:

```bash
docker run -d \
--name bd_almeida \
-e MYSQL_ROOT_PASSWORD=abcd \
-e MYSQL_DATABASE=sisdb_examen \
-e MYSQL_USER=root123 \
-e MYSQL_PASSWORD=abcd \
-p 3308:3306 \
mysql:8.0
```

La base de datos la puedes abrir en MySQL Workbench con los parámetros de conexión de arriba, y se verá así:
<div align="center">
<img width="600" alt="bd" src="https://github.com/user-attachments/assets/2742e274-4ac6-4f9a-91f8-33f98f85b3e3" />
</div>

⚠️ Importante: Fíjate en el puerto, porque el código de ambos microservicios está apuntando al puerto 3008 para conectarse a MySQL.
---

**micro-usuarios define su *application.properties* de esta forma:**
<div align="center">
<img width="600" alt="micro-usuarios" src="https://github.com/user-attachments/assets/265ffca3-67a5-4223-97b7-c131773d4736" />
</div>
<br>

**micro-roles define su *application.properties* de esta forma:**
<div align="center">
<img width="600" alt="micro-roles" src="https://github.com/user-attachments/assets/f41224bd-dc21-41d1-8f49-f91cc9968d9c" />
</div>

### 2. **Levantar los Microservicios**

1. Clona el repositorio y navega al directorio raíz.
2. En cada microservicio (`micro-roles` y `micro-usuarios`), compila y ejecuta:
  ```bash
  mvn spring-boot:run
  ```
3. Los microservicios estarán disponibles en:
  - `micro-roles`: http://localhost:8003/api/roles
  - `micro-usuarios`: http://localhost:8002/api/usuarios

### 3. **Pruebas de Funcionalidad**

### USUARIOS

- Listar los usuarios disponibles:
  - **GET:** http://localhost:8002/api/usuarios

<div align="center">
<img width="600" alt="ver_usuarios" src="https://github.com/user-attachments/assets/0321bb2e-6c91-4b21-84fc-004694ca52a3" />
</div>
<br>

- Crear un usuario:
  - **POST:** http://localhost:8002/api/usuarios
    ```json
    {
      "nombre": "Pepe",
      "apellido": "Bottles",
      "email": "pepe@chimi.com",
      "fechaNacimiento": "2002-04-16",
      "telefono": "0982343722"
    }
    ```
<div align="center">
<img width="600" alt="crear_user" src="https://github.com/user-attachments/assets/0f1089f8-7091-40d7-a454-3a97d2669765" />
</div>
<br>
  
- Eliminar un usuario:
  - **DELETE:** http://localhost:8002/api/usuarios/{usuarioId}

<div align="center">
<img width="600" alt="elim_user" src="https://github.com/user-attachments/assets/ade122b8-46ea-4e5e-96b8-5f6955ea37a5" />
</div>
<br>

### ROLES

- Listar los roles disponibles, y los usuarios que están asignados:
  - **GET:** http://localhost:8003/api/roles

<div align="center">
<img width="600" alt="ver_rol" src="https://github.com/user-attachments/assets/9a58fb87-9f45-440f-be45-6b8303b92cc0" />
</div>
<br>

- Crear un rol:
  - **POST:** http://localhost:8003/api/roles
    ```json
    {
     "nombre": "DB Administrator 6",
     "descripcion": "Admin de las BD hehe"
    }
    ```

<div align="center">
<img width="600" alt="crear_rol" src="https://github.com/user-attachments/assets/1bee6415-cd1a-4798-ad45-6bfbdc0008cd" />
</div>
<br>

- Eliminar un rol:
  - **DELETE:** hhttp://localhost:8003/api/roles/{rolId}
    
<div align="center">
<img width="600" alt="elim_rol" src="https://github.com/user-attachments/assets/e4f0deca-7bb7-4208-beb0-131f38058a71" />
</div>
<br>

- Asignar un rol a un usuario:
  - **POST:** http://localhost:8003/api/roles/asignar-rol/{rolId}
    ```json
    {
     "id": 3
    }
    ```

<div align="center">
<img width="600" alt="asign_rol" src="https://github.com/user-attachments/assets/5b79b04b-7e82-4653-9038-412f73080349" />
</div>
<br>

- Revocar rol a un usuario:
  - **DELETE:** http://localhost:8003/api/roles/desasignar-rol/{rolId}/usuario/{usuarioId}

<div align="center">
<img width="600" alt="revocar_rol" src="https://github.com/user-attachments/assets/573618a0-3972-4c42-913b-3249b7735b5e" />
</div>
<br>

---
## 🛠️ Pruebas con Postman
Se ha incluido una colección de Postman (`Roles_Postman.json`) para probar las funcionalidades de los microservicios de forma rápida. Importa los archivo en Postman y prueba las rutas disponibles.

## 💻 Tecnologías Utilizadas
- Spring Boot: Para la creación de los microservicios.
- Spring Security: Para la gestión de autenticación y autorización.
- Spring Data JPA: Para la gestión de acceso a la base de datos.
- MySQL: Base de datos relacional.

## Contribuciones
Si deseas contribuir, por favor sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama para tus cambios (git checkout -b feature/mi-nueva-caracteristica).
3. Realiza tus cambios y haz un commit (git commit -am 'Añadir nueva característica').
4. Haz un push a tu rama (git push origin feature/mi-nueva-caracteristica).
5. Abre un Pull Request.

## Licencia
Este proyecto está bajo la licencia MIT. 

---






