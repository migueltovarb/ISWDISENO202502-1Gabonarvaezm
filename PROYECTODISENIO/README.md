# Sistema de Control de Acceso Residencial

Un sistema completo de gestión de acceso para conjuntos residenciales, desarrollado con Spring Boot en el backend y HTML/CSS/JavaScript en el frontend.

## 📁 Estructura del Proyecto

Este proyecto sigue los principios de **Clean Code** y está organizado en dos carpetas principales:

```
PROYECTODISENIO/
├── backend/                 # Backend Spring Boot
│   ├── src/main/java/     # Código Java
│   ├── src/main/resources/ # Configuraciones
│   ├── target/            # Archivos compilados
│   └── pom.xml            # Dependencias Maven
├── frontend/              # Frontend HTML/CSS/JS
│   ├── index.html         # Página principal
│   ├── styles.css         # Estilos
│   ├── script.js          # Lógica de la aplicación
│   └── README.md          # Documentación del frontend
└── README.md             # Este archivo
```

## 🚀 Tecnologías Utilizadas

### Backend
- **Java 17** con **Spring Boot 3.3.4**
- **MongoDB** como base de datos
- **Spring Security** con JWT para autenticación
- **Maven** como gestor de dependencias
- **Swagger** para documentación de APIs

### Frontend
- **HTML5** con estructura semántica
- **CSS3** con diseño responsive y moderno
- **JavaScript ES6+** con programación funcional
- **Fetch API** para comunicación con el backend

## 🎯 Funcionalidades

### Historias de Usuario Implementadas

- **HU1**: Registro y gestión de residentes y vigilantes
- **HU2**: Registro de visitantes y generación de credenciales temporales
- **HU3**: Registro de entradas y salidas con QR/código
- **HU4**: Envío de notificaciones a residentes

### Características Principales

✅ **Autenticación segura** con JWT  
✅ **Gestión de usuarios** (residentes, vigilantes, administradores)  
✅ **Control de visitantes** con credenciales temporales  
✅ **Registro de entradas/salidas** en tiempo real  
✅ **Sistema de notificaciones** para residentes  
✅ **Interfaz responsive** para dispositivos móviles  
✅ **Documentación API** con Swagger UI  

## 🛠️ Instalación y Ejecución

### Requisitos Previos
- Java 17 o superior
- MongoDB instalado y ejecutándose
- Maven 3.6+

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone [URL_DEL_REPOSITORIO]
cd PROYECTODISENIO
```

2. **Configurar MongoDB**
Asegúrate de que MongoDB esté ejecutándose en `mongodb://localhost:27017`

3. **Iniciar el Backend**
```bash
cd backend
mvn spring-boot:run
```
El backend estará disponible en: `http://localhost:8080`

4. **Abrir el Frontend**
Abre el archivo `frontend/index.html` en tu navegador o usa un servidor local:
```bash
cd frontend
# Opción 1: Abrir directamente index.html
# Opción 2: Usar servidor local (recomendado)
python -m http.server 3000
# O con Node.js
npx http-server -p 3000
```

## 📋 Credenciales de Prueba

El sistema crea automáticamente usuarios de prueba al iniciar:

| Email | Contraseña | Rol |
|-------|------------|-----|
| admin@residencial.com | admin123 | ADMIN |
| vigilante1@residencial.com | vigilante123 | VIGILANTE |
| residente1@residencial.com | residente123 | RESIDENTE |

## 🔗 Endpoints Principales

### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/register` - Registrar usuario

### Usuarios
- `GET /api/usuarios` - Listar usuarios
- `POST /api/usuarios` - Crear usuario
- `PUT /api/usuarios/{id}` - Actualizar usuario
- `DELETE /api/usuarios/{id}` - Eliminar usuario

### Visitantes
- `GET /api/visitantes` - Listar visitantes
- `POST /api/visitantes` - Crear visitante
- `PUT /api/visitantes/{id}` - Actualizar visitante

### Entradas/Salidas
- `POST /api/entradas/registrar` - Registrar entrada
- `POST /api/salidas/registrar` - Registrar salida
- `GET /api/entradas` - Listar entradas
- `GET /api/salidas` - Listar salidas

### Notificaciones
- `GET /api/notificaciones` - Listar notificaciones
- `POST /api/notificaciones/enviar` - Enviar notificación

## 📚 Documentación API

Visita `http://localhost:8080/swagger-ui.html` para ver la documentación completa de la API con Swagger UI.

## 🧪 Pruebas

### Backend
```bash
cd backend
mvn test
```

### Frontend
Abre la consola del navegador (F12) para ver logs y debugging.

## 📱 Diseño Responsive

El frontend está diseñado para funcionar en:
- ✅ Desktop (1200px+)
- ✅ Tablet (768px - 1199px)
- ✅ Móvil (< 768px)

## 🔒 Seguridad

- Autenticación JWT con expiración
- Encriptación de contraseñas con BCrypt
- Validación de entrada en backend
- CORS configurado apropiadamente
- Roles y permisos por usuario

## 📝 Notas de Desarrollo

### Clean Code Aplicado
- ✅ Separación de responsabilidades (backend/frontend)
- ✅ Nomenclatura clara y consistente
- ✅ Estructura de carpetas lógica
- ✅ Código comentado y documentado
- ✅ Principios SOLID en el backend
- ✅ Funciones pequeñas y reutilizables

### Mejores Prácticas
- ✅ API RESTful con respuestas consistentes
- ✅ Manejo de errores centralizado
- ✅ Validación en frontend y backend
- ✅ Logs detallados para debugging
- ✅ Código modular y mantenible

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

## 👥 Autores

- **Tu Nombre** - *Trabajo inicial* - [TuUsuario](https://github.com/TuUsuario)

## 🙏 Agradecimientos

- Spring Boot por el excelente framework
- MongoDB por la base de datos NoSQL
- Tailwind CSS por inspiración en diseño
- Comunidad open source por las herramientas utilizadas

---

