# Sistema de Control de Acceso Residencial - Frontend

Frontend moderno y funcional para el sistema de control de acceso residencial, construido con HTML5, CSS3 y JavaScript vanilla.

## 🚀 Características

- **Autenticación JWT**: Login seguro con tokens JWT
- **Dashboard Interactivo**: Estadísticas en tiempo real
- **Gestión Completa**: CRUD de visitas, entradas, salidas y usuarios
- **Diseño Responsive**: Interfaz adaptable a todos los dispositivos
- **Notificaciones**: Sistema de notificaciones en tiempo real
- **4 Historias de Usuario Implementadas**:
  - HU1: Registrar visitas con información completa
  - HU2: Control de acceso con entradas/salidas
  - HU3: Notificaciones automáticas de visitas
  - HU4: Gestión de usuarios por roles

## 📁 Estructura del Proyecto

```
frontend/
├── index.html      # Página principal con toda la interfaz
├── styles.css      # Estilos modernos y responsive
├── script.js       # Lógica completa de la aplicación
└── README.md       # Este archivo
```

## 🛠️ Tecnologías Utilizadas

- **HTML5**: Estructura semántica y moderna
- **CSS3**: Flexbox, Grid, animaciones y variables CSS
- **JavaScript ES6+**: Async/await, fetch API, módulos
- **Font Awesome**: Iconos profesionales
- **CSS Custom Properties**: Temas y personalización

## 🔧 Configuración

1. **Asegúrate de que el backend esté ejecutándose**:
   ```bash
   # En la carpeta principal del proyecto
   mvn spring-boot:run
   ```

2. **Abre el archivo `index.html` en tu navegador** o

3. **Sirve los archivos con un servidor web local**:
   ```bash
   # Opción 1: Python
   python -m http.server 3000
   
   # Opción 2: Node.js (instala http-server globalmente)
   npx http-server -p 3000
   
   # Opción 3: VS Code con Live Server extension
   # Click derecho en index.html -> "Open with Live Server"
   ```

4. **Accede a la aplicación**:
   - URL: http://localhost:3000
   - Credenciales por defecto: admin@residencial.com / admin123

## 🎯 Funcionalidades Implementadas

### 🔐 Autenticación
- Login con email y contraseña
- JWT token almacenado en localStorage
- Validación de sesiones
- Cierre de sesión seguro

### 📊 Dashboard
- Estadísticas en tiempo real
- Total de visitas, entradas, salidas y notificaciones
- Actividad reciente con timeline
- Responsive y moderno

### 👥 Gestión de Visitas (HU1)
- ✅ Registrar nuevas visitas con información completa
- ✅ Buscar y filtrar visitas
- ✅ Editar información de visitas
- ✅ Eliminar visitas
- ✅ Campos: nombre, documento, teléfono, tipo de visita

### 🚪 Control de Acceso (HU2)
- ✅ Registrar entradas de visitas
- ✅ Registrar salidas de visitas
- ✅ Control por torre y apartamento
- ✅ Tipos de entrada: vehicular y peatonal
- ✅ Historial completo de movimientos

### 🔔 Notificaciones (HU3)
- ✅ Notificaciones automáticas al registrar entradas
- ✅ Sistema de notificaciones en tiempo real
- ✅ Marcar notificaciones como leídas
- ✅ Indicador de notificaciones no leídas

### 👤 Gestión de Usuarios (HU4)
- ✅ CRUD completo de usuarios (solo Admin)
- ✅ Roles: ADMIN, VIGILANTE, RESIDENTE
- ✅ Estados: ACTIVO, INACTIVO
- ✅ Control de acceso por roles

## 🎨 Diseño

### Características Visuales
- **Diseño Moderno**: Interfaz limpia y profesional
- **Colores Corporativos**: Esquema de colores azul profesional
- **Animaciones Suaves**: Transiciones y efectos hover
- **Iconografía**: Font Awesome para consistencia
- **Tipografía**: Inter y sistemas de fuente nativos

### Responsive Design
- **Desktop**: Layout completo con sidebar
- **Tablet**: Adaptación automática
- **Mobile**: Menú hamburguesa y diseño vertical
- **Breakpoints**: 768px y 480px

## 🔌 Integración con Backend

### Endpoints API Consumidos
```
# Autenticación
POST /api/auth/login

# Visitas
GET    /api/visitas
POST   /api/visitas
PUT    /api/visitas/{id}
DELETE /api/visitas/{id}

# Entradas
GET    /api/entradas
GET    /api/entradas/hoy
GET    /api/entradas/ultimas
GET    /api/entradas/activas
POST   /api/entradas
DELETE /api/entradas/{id}

# Salidas
GET    /api/salidas
GET    /api/salidas/hoy
GET    /api/salidas/ultimas
POST   /api/salidas
DELETE /api/salidas/{id}

# Usuarios (Admin)
GET    /api/usuarios
POST   /api/usuarios
PUT    /api/usuarios/{id}
DELETE /api/usuarios/{id}

# Notificaciones
GET    /api/notificaciones
GET    /api/notificaciones/no-leidas
PUT    /api/notificaciones/marcar-leidas
```

## 🚀 Uso de la Aplicación

### 1. Iniciar Sesión
- Abre la aplicación en tu navegador
- Usa las credenciales: admin@residencial.com / admin123
- El dashboard mostrará estadísticas generales

### 2. Registrar una Visita (HU1)
- Ve a "Visitas" en el menú
- Click en "Nueva Visita"
- Completa el formulario con nombre, documento, teléfono y tipo
- Guarda la visita

### 3. Registrar Entrada (HU2)
- Ve a "Entradas" en el menú
- Click en "Registrar Entrada"
- Selecciona la visita, tipo, torre y apartamento
- Guarda la entrada (se genera notificación automáticamente)

### 4. Registrar Salida (HU2)
- Ve a "Salidas" en el menú
- Click en "Registrar Salida"
- Selecciona la visita que sale
- Confirma el registro

### 5. Ver Notificaciones (HU3)
- Ve a "Notificaciones" en el menú
- Verás todas las notificaciones generadas
- Puedes marcarlas como leídas

### 6. Gestión de Usuarios (HU4) - Solo Admin
- Ve a "Usuarios" en el menú (solo visible para Admin)
- Crea, edita o elimina usuarios
- Asigna roles y estados

## 🛡️ Seguridad

- **JWT Authentication**: Tokens seguros para autenticación
- **Role-Based Access**: Control de acceso por roles
- **HTTPS Ready**: Preparado para conexiones seguras
- **Input Validation**: Validación de formularios
- **XSS Protection**: Protección básica contra XSS

## 🌟 Características Adicionales

- **Búsqueda en tiempo real**: Filtrado instantáneo de datos
- **Ordenamiento**: Tablas con ordenamiento implícito
- **Validación de formularios**: Feedback visual inmediato
- **Loading states**: Indicadores de carga profesionales
- **Toast notifications**: Notificaciones elegantes
- **Error handling**: Manejo robusto de errores
- **Responsive images**: Adaptación a dispositivos

## 📝 Notas de Desarrollo

### Estructura del Código JavaScript
```javascript
// Configuración API
const API_BASE_URL = 'http://localhost:8080/api';

// Estado Global
let currentUser = null;
let authToken = null;

// Funciones por categoría
- Authentication Functions
- Screen Management
- API Functions
- Dashboard Functions
- Visitas Functions
- Entradas Functions
- Salidas Functions
- Usuarios Functions
- Notificaciones Functions
- Modal Functions
- Utility Functions
```

### Variables CSS Personalizadas
```css
:root {
    --primary-color: #2563eb;
    --secondary-color: #64748b;
    --success-color: #10b981;
    --warning-color: #f59e0b;
    --danger-color: #ef4444;
    // ... más variables
}
```

## 🐛 Solución de Problemas

### CORS Errors
- Verifica que el backend esté ejecutándose
- Confirma que CORS esté configurado en Spring Boot
- Revisa el puerto del backend (por defecto 8080)

### Login No Funciona
- Verifica credenciales: admin@residencial.com / admin123
- Confirma que el backend esté ejecutándose
- Revisa la consola del navegador para errores

### Datos No Se Cargan
- Verifica conexión con el backend
- Revisa que MongoDB esté ejecutándose
- Confirma que haya datos en la base de datos

## 📞 Soporte

Si encuentras problemas:
1. Verifica que el backend esté ejecutándose
2. Revisa la consola del navegador (F12)
3. Confirma que MongoDB esté activo
4. Verifica la configuración de CORS

## 🎉 ¡Listo!

Tu frontend está completo y funcional. Abre `index.html` en tu navegador y comienza a usar el sistema de control de acceso residencial.