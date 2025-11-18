// API Configuration
const API_BASE_URL = 'http://localhost:8080/api';
const USE_SIMPLE_AUTH = true; // Usar autenticación simple para pruebas

// Global State
let currentUser = null;
let authToken = null;
let activeRequests = new Set();

// Utility Functions
function showLoading() {
    document.getElementById('loading-overlay').classList.add('active');
}

function hideLoading() {
    document.getElementById('loading-overlay').classList.remove('active');
}

function showToast(message, type = 'info', title = '') {
    const toastContainer = document.getElementById('toast-container');
    const toastId = `toast-${Date.now()}`;
    
    const toastHTML = `
        <div id="${toastId}" class="toast ${type}">
            <div class="toast-icon">
                <i class="fas fa-${getToastIcon(type)}"></i>
            </div>
            <div class="toast-content">
                ${title ? `<h4>${title}</h4>` : ''}
                <p>${message}</p>
            </div>
            <button class="toast-close" onclick="removeToast('${toastId}')">
                <i class="fas fa-times"></i>
            </button>
        </div>
    `;
    
    toastContainer.insertAdjacentHTML('beforeend', toastHTML);
    
    setTimeout(() => {
        removeToast(toastId);
    }, 5000);
}

function getToastIcon(type) {
    const icons = {
        success: 'check-circle',
        error: 'exclamation-circle',
        warning: 'exclamation-triangle',
        info: 'info-circle'
    };
    return icons[type] || 'info-circle';
}

function removeToast(toastId) {
    const toast = document.getElementById(toastId);
    if (toast) {
        toast.style.animation = 'slideOutRight 0.3s ease-in-out';
        setTimeout(() => {
            toast.remove();
        }, 300);
    }
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleString('es-CO', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    });
}

function formatDateOnly(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('es-CO');
}

// Authentication Functions
async function login(email, password) {
    try {
        showLoading();
        
        let response;
        let data;
        
        if (USE_SIMPLE_AUTH) {
            // Usar endpoint alternativo para pruebas
            response = await fetch(`${API_BASE_URL}/auth2/login-simple`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password })
            });
            
            if (!response.ok) {
                throw new Error('Credenciales inválidas');
            }
            
            const result = await response.json();
            authToken = result.token;
            currentUser = {
                id: result.usuario.id,
                nombre: result.usuario.nombre,
                email: result.usuario.email,
                rol: String(result.usuario.rol)
            };
        } else {
            // Usar endpoint original
            response = await fetch(`${API_BASE_URL}/auth/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password })
            });

            if (!response.ok) {
                throw new Error('Credenciales inválidas');
            }

            data = await response.json();
            authToken = data.token;
            currentUser = data.usuario;
        }
        
        localStorage.setItem('authToken', authToken);
        localStorage.setItem('currentUser', JSON.stringify(currentUser));
        
        showToast('Inicio de sesión exitoso', 'success');
        showDashboard();
        
    } catch (error) {
        showToast(error.message, 'error', 'Error de autenticación');
        document.getElementById('login-error').textContent = error.message;
    } finally {
        hideLoading();
    }
}

function logout() {
    cancelActiveRequests();
    authToken = null;
    currentUser = null;
    localStorage.removeItem('authToken');
    localStorage.removeItem('currentUser');
    showLoginScreen();
    showToast('Sesión cerrada exitosamente', 'info');
}

function checkAuth() {
    const token = localStorage.getItem('authToken');
    const user = localStorage.getItem('currentUser');
    
    if (token && user) {
        authToken = token;
        currentUser = JSON.parse(user);
        currentUser.rol = String(currentUser.rol || '');
        showDashboard();
    } else {
        showLoginScreen();
    }
}

// Screen Management
function showLoginScreen() {
    document.querySelectorAll('.screen').forEach(screen => {
        screen.classList.remove('active');
    });
    document.getElementById('login-screen').classList.add('active');
    const loginScreen = document.getElementById('login-screen');
    const dashboardScreen = document.getElementById('dashboard-screen');
    if (loginScreen && dashboardScreen) {
        loginScreen.style.display = 'flex';
        dashboardScreen.style.display = 'none';
    }
    document.getElementById('login-form').reset();
    document.getElementById('login-error').textContent = '';
    document.body.classList.remove('no-scroll');
}

function showDashboard() {
    document.querySelectorAll('.screen').forEach(screen => {
        screen.classList.remove('active');
    });
    const loginScreen = document.getElementById('login-screen');
    const dashboardScreen = document.getElementById('dashboard-screen');
    if (loginScreen && dashboardScreen) {
        loginScreen.classList.remove('active');
        dashboardScreen.classList.add('active');
        loginScreen.style.display = 'none';
        dashboardScreen.style.display = 'block';
    } else {
        document.getElementById('dashboard-screen').classList.add('active');
    }
    document.body.classList.add('no-scroll');
    window.scrollTo(0, 0);
    
    // Update user info
    document.getElementById('user-name').textContent = currentUser.nombre;
    document.getElementById('user-role').textContent = currentUser.rol;
    document.getElementById('user-role').setAttribute('data-role', currentUser.rol);
    
    // Show/hide admin menu items
    if (currentUser.rol === 'ADMIN') {
        document.getElementById('usuarios-menu').style.display = 'flex';
    } else {
        document.getElementById('usuarios-menu').style.display = 'none';
    }
    const btnEntrada = document.getElementById('btn-nueva-entrada');
    const btnSalida = document.getElementById('btn-nueva-salida');
    if (btnEntrada) btnEntrada.style.display = currentUser.rol === 'VIGILANTE' ? 'inline-flex' : 'none';
    if (btnSalida) btnSalida.style.display = currentUser.rol === 'VIGILANTE' ? 'inline-flex' : 'none';
    
    loadDashboardData();
}

// Navigation
function showSection(sectionId) {
    // Hide all sections
    document.querySelectorAll('.content-section').forEach(section => {
        section.classList.remove('active');
    });
    
    // Remove active class from nav items
    document.querySelectorAll('.nav-item').forEach(item => {
        item.classList.remove('active');
    });
    
    // Show selected section
    document.getElementById(`${sectionId}-section`).classList.add('active');
    
    // Add active class to nav item
    document.querySelector(`[data-section="${sectionId}"]`).classList.add('active');
    
    // Load section data
    switch(sectionId) {
        case 'dashboard':
            loadDashboardData();
            break;
        case 'visitas':
            loadVisitas();
            break;
        case 'entradas':
            loadEntradas();
            break;
        case 'salidas':
            loadSalidas();
            break;
        case 'usuarios':
            if (currentUser.rol === 'ADMIN') {
                loadUsuarios();
            } else {
                showToast('No tienes permisos para acceder a esta sección', 'error');
                showSection('dashboard');
            }
            break;
        case 'notificaciones':
            loadNotificaciones();
            break;
    }
}

// API Functions
async function apiRequest(endpoint, method = 'GET', body = null) {
    if (!authToken && !endpoint.startsWith('/auth') && !endpoint.startsWith('/auth2')) {
        if (method === 'GET') return [];
        throw new Error('No autenticado');
    }
    const controller = new AbortController();
    activeRequests.add(controller);

    const headers = {
        method,
        headers: {
            'Content-Type': 'application/json'
        },
        signal: controller.signal
    };
    if (authToken) {
        headers.headers['Authorization'] = `Bearer ${authToken}`;
    }
    
    if (body) {
        headers.body = JSON.stringify(body);
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, headers);

        if (response.status === 401) {
            logout();
            throw new Error('Sesión expirada');
        }

        if (!response.ok) {
            let error;
            try { error = await response.json(); } catch (_) { error = {}; }
            const msg = error.message || error.error || `Error ${response.status}`;
            throw new Error(msg);
        }

        return response.json();
    } finally {
        activeRequests.delete(controller);
    }
}

function cancelActiveRequests() {
    activeRequests.forEach(c => {
        try { c.abort(); } catch (_) {}
    });
    activeRequests.clear();
}

// Dashboard Functions
async function loadDashboardData() {
    try {
        const [visitas, entradas, salidas, notificaciones] = await Promise.all([
            apiRequest('/visitantes'),
            apiRequest('/entradas'),
            apiRequest('/salidas'),
            apiRequest('/notificaciones/no-leidas')
        ]);
        
        document.getElementById('total-visitas').textContent = visitas.length;
        document.getElementById('total-entradas').textContent = entradas.length;
        document.getElementById('total-salidas').textContent = salidas.length;
        document.getElementById('total-notificaciones').textContent = notificaciones.length;
        
        loadRecentActivity();
        
    } catch (error) {
        showToast('Error al cargar datos del dashboard', 'error');
    }
}

async function loadRecentActivity() {
    try {
        const [entradas, salidas] = await Promise.all([
            apiRequest('/entradas'),
            apiRequest('/salidas')
        ]);
        
        const activities = [...entradas, ...salidas]
            .sort((a, b) => new Date((b.horaEntrada || b.horaSalida)) - new Date((a.horaEntrada || a.horaSalida)))
            .slice(0, 5);
        
        const activityList = document.getElementById('recent-activity-list');
        activityList.innerHTML = activities.map(activity => {
            const isEntrada = activity.hasOwnProperty('horaEntrada');
            const icon = isEntrada ? 'sign-in-alt' : 'sign-out-alt';
            const color = isEntrada ? 'success' : 'warning';
            const action = isEntrada ? 'entró' : 'salió';
            const visitorName = '-';
            
            return `
                <div class="activity-item">
                    <div class="activity-icon" style="background-color: var(--${color}-color);">
                        <i class="fas fa-${icon}"></i>
                    </div>
                    <div class="activity-content">
                        <h4>${visitorName} ${action}</h4>
                        <p>${isEntrada ? `Torre ${activity.torre}, Apto ${activity.apartamento}` : 'Registro de salida'}</p>
                    </div>
                    <div class="activity-time">${formatDate(activity.horaEntrada || activity.horaSalida)}</div>
                </div>
            `;
        }).join('');
        
    } catch (error) {
        showToast('Error al cargar actividad reciente', 'error');
    }
}

// Visitas Functions
async function loadVisitas() {
    try {
        const visitas = await apiRequest('/visitantes');
        const tbody = document.getElementById('visitas-tbody');
        
        tbody.innerHTML = visitas.map(visita => `
            <tr>
                <td>${visita.id}</td>
                <td>${visita.nombre}</td>
                <td>${visita.documento}</td>
                <td>${visita.telefono || '-'}</td>
                <td>${visita.motivoVisita || '-'}</td>
                <td><span class="status-badge status-active">Activo</span></td>
                <td>${formatDateOnly(visita.fechaRegistro)}</td>
                <td class="actions">
                    <button class="btn btn-sm btn-primary" onclick="editVisita('${visita.id}')">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deleteVisita('${visita.id}')">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `).join('');
        
    } catch (error) {
        showToast('Error al cargar visitas', 'error');
    }
}

function showVisitaModal(visita = null) {
    const modal = document.getElementById('modal-visita');
    const form = document.getElementById('visita-form');
    const title = document.getElementById('visita-modal-title');
    
    if (visita) {
        title.textContent = 'Editar Visita';
        document.getElementById('visita-id').value = visita.id;
        document.getElementById('visita-nombre').value = visita.nombre;
        document.getElementById('visita-documento').value = visita.documento;
        document.getElementById('visita-telefono').value = visita.telefono || '';
        document.getElementById('visita-tipo').value = visita.tipoVisita;
    } else {
        title.textContent = 'Nueva Visita';
        form.reset();
        document.getElementById('visita-id').value = '';
    }
    
    modal.classList.add('active');
}

async function saveVisita(formData) {
    try {
        const visitaData = {
            nombre: formData.get('nombre'),
            documento: formData.get('documento'),
            motivoVisita: formData.get('tipoVisita') || ''
        };
        
        const visitaId = document.getElementById('visita-id').value;
        
        if (visitaId) {
            await apiRequest(`/visitantes/${visitaId}`, 'PUT', visitaData);
            showToast('Visita actualizada exitosamente', 'success');
        } else {
            await apiRequest('/visitantes', 'POST', visitaData);
            showToast('Visita creada exitosamente', 'success');
        }
        
        closeModal('modal-visita');
        loadVisitas();
        
    } catch (error) {
        showToast(error.message, 'error');
    }
}

async function editVisita(id) {
    try {
        const visita = await apiRequest(`/visitantes/${id}`);
        showVisitaModal(visita);
    } catch (error) {
        showToast('Error al cargar visita', 'error');
    }
}

async function deleteVisita(id) {
    if (confirm('¿Está seguro de eliminar esta visita?')) {
        try {
            await apiRequest(`/visitantes/${id}`, 'DELETE');
            showToast('Visita eliminada exitosamente', 'success');
            loadVisitas();
        } catch (error) {
            showToast('Error al eliminar visita', 'error');
        }
    }
}

// Entradas Functions
async function loadEntradas() {
    try {
        const [entradas, visitas, usuarios] = await Promise.all([
            apiRequest('/entradas'),
            apiRequest('/visitantes'),
            apiRequest('/usuarios')
        ]);
        const vMap = Object.fromEntries((visitas || []).map(v => [v.id, v]));
        const uMap = Object.fromEntries((usuarios || []).map(u => [u.id, u]));
        const tbody = document.getElementById('entradas-tbody');
        const data = (currentUser && currentUser.rol === 'RESIDENTE')
            ? (entradas || []).filter(e => e.usuarioId === currentUser.id)
            : (entradas || []);
        
        tbody.innerHTML = data.map(entrada => {
            const v = entrada.visitanteId ? vMap[entrada.visitanteId] : null;
            const u = (!v && entrada.usuarioId) ? uMap[entrada.usuarioId] : null;
            const idDisplay = v?.documento || u?.apartamento || entrada.id;
            const visitaDisplay = v?.nombre || u?.nombre || '-';
            const canDelete = currentUser && currentUser.rol === 'ADMIN';
            return `
            <tr>
                <td>${idDisplay}</td>
                <td>${visitaDisplay}</td>
                <td>${entrada.tipo || '-'}</td>
                <td>-</td>
                <td>-</td>
                <td>${formatDate(entrada.horaEntrada)}</td>
                <td>${entrada.registradoPor || '-'}</td>
                <td class="actions">
                    ${canDelete ? `<button class="btn btn-sm btn-danger" onclick="deleteEntrada('${entrada.id}')"><i class="fas fa-trash"></i></button>` : ''}
                </td>
            </tr>
        `}).join('');
        
    } catch (error) {
        showToast('Error al cargar entradas', 'error');
    }
}

async function loadVisitasForSelect() {
    try {
        const visitas = await apiRequest('/visitantes');
        const select = document.getElementById('entrada-visita');
        
        select.innerHTML = '<option value="">Seleccionar visita...</option>' +
            visitas.map(visita => `
                <option value="${visita.documento}">${visita.nombre} - ${visita.documento}</option>
            `).join('');
        
    } catch (error) {
        showToast('Error al cargar visitas', 'error');
    }
}

async function saveEntrada(formData) {
    try {
        if (!currentUser || currentUser.rol !== 'VIGILANTE') {
            showToast('Solo el vigilante puede registrar entradas', 'error');
            return;
        }
        const documentoVisitante = formData.get('visitaId');

        // Intentar obtener residente desde la visita seleccionada
        let visitas = [];
        try {
            visitas = await apiRequest('/visitantes');
        } catch (_) {}
        const visitaSel = (visitas || []).find(v => String(v.documento) === String(documentoVisitante));

        // Buscar un residente válido (usar el de la visita si existe, sino tomar el primero)
        let residentes = [];
        try {
            residentes = await apiRequest('/usuarios');
        } catch (e) {
            const debugUsers = await fetch(`${API_BASE_URL}/debug/usuarios`).then(r => r.json());
            residentes = debugUsers.filter(u => String(u.rol) === 'RESIDENTE').map(u => ({ id: u.id, rol: 'RESIDENTE' }));
        }
        let residenteId = visitaSel?.residenteVisitado || (residentes.find(u => String(u.rol) === 'RESIDENTE') || residentes[0])?.id;
        if (!residenteId) throw new Error('No hay residente disponible para registrar la entrada');

        const payload = {
            documentoVisitante,
            residenteId,
            vigilanteId: currentUser.id
        };

        await apiRequest('/entradas/visitante', 'POST', payload);
        showToast('Entrada registrada exitosamente', 'success');
        
        closeModal('modal-entrada');
        loadEntradas();
        loadDashboardData(); // Update dashboard stats
        
    } catch (error) {
        const msg = /403/.test(String(error.message)) ? 'Sin permisos: inicie sesión como vigilante' : error.message;
        showToast(msg, 'error');
    }
}

async function deleteEntrada(id) {
    if (confirm('¿Está seguro de eliminar esta entrada?')) {
        try {
            await apiRequest(`/entradas/${id}`, 'DELETE');
            showToast('Entrada eliminada exitosamente', 'success');
            loadEntradas();
        } catch (error) {
            showToast('Error al eliminar entrada', 'error');
        }
    }
}

// Salidas Functions
async function loadSalidas() {
    try {
        const [salidas, entradas, visitas, usuarios] = await Promise.all([
            apiRequest('/salidas'),
            apiRequest('/entradas'),
            apiRequest('/visitantes'),
            apiRequest('/usuarios')
        ]);
        const eMap = Object.fromEntries((entradas || []).map(e => [e.id, e]));
        const vMap = Object.fromEntries((visitas || []).map(v => [v.id, v]));
        const uMap = Object.fromEntries((usuarios || []).map(u => [u.id, u]));
        const tbody = document.getElementById('salidas-tbody');
        const dataSalidas = (currentUser && currentUser.rol === 'RESIDENTE')
            ? (salidas || []).filter(s => {
                const ent = eMap[s.entradaId];
                return ent && ent.usuarioId === currentUser.id;
            })
            : (salidas || []);
        
        tbody.innerHTML = dataSalidas.map(salida => {
            const entrada = eMap[salida.entradaId];
            const v = entrada && entrada.visitanteId ? vMap[entrada.visitanteId] : null;
            const u = (!v && entrada && entrada.usuarioId) ? uMap[entrada.usuarioId] : null;
            const idDisplay = v?.documento || u?.apartamento || salida.id;
            const visitaDisplay = v?.nombre || u?.nombre || '-';
            const canDelete = currentUser && currentUser.rol === 'ADMIN';
            return `
            <tr>
                <td>${idDisplay}</td>
                <td>${visitaDisplay}</td>
                <td>${formatDate(salida.horaSalida)}</td>
                <td>${salida.registradoPor || '-'}</td>
                <td class="actions">
                    ${canDelete ? `<button class="btn btn-sm btn-danger" onclick="deleteSalida('${salida.id}')"><i class="fas fa-trash"></i></button>` : ''}
                </td>
            </tr>
        `}).join('');
        
    } catch (error) {
        showToast('Error al cargar salidas', 'error');
    }
}

async function loadVisitasForSalidaSelect() {
    try {
        const [entradas, visitas, usuarios, salidas] = await Promise.all([
            apiRequest('/entradas'),
            apiRequest('/visitantes'),
            apiRequest('/usuarios'),
            apiRequest('/salidas')
        ]);
        const vMap = Object.fromEntries((visitas || []).map(v => [v.id, v]));
        const uMap = Object.fromEntries((usuarios || []).map(u => [u.id, u]));
        const salidaSet = new Set((salidas || []).map(s => s.entradaId));
        const select = document.getElementById('salida-visita');
        
        const opcionesElegibles = (entradas || []).filter(e => !salidaSet.has(e.id));

        select.innerHTML = '<option value="">Seleccionar entrada...</option>' +
            opcionesElegibles.map(entrada => {
                if (entrada.visitanteId) {
                    const v = vMap[entrada.visitanteId];
                    const label = v ? `${v.nombre} - ${v.documento}` : `Entrada ${entrada.id} (VISITANTE)`;
                    return `<option value="${entrada.id}">${label}</option>`;
                } else {
                    const u = uMap[entrada.usuarioId];
                    const label = u ? `${u.nombre} - ${u.apartamento}` : `Entrada ${entrada.id} (RESIDENTE)`;
                    return `<option value="${entrada.id}">${label}</option>`;
                }
            }).join('');

        if (opcionesElegibles.length === 0) {
            select.innerHTML = '<option value="" disabled>No hay entradas activas sin salida</option>';
        }
        
    } catch (error) {
        showToast('Error al cargar visitas activas', 'error');
    }
}

async function saveSalida(formData) {
    try {
        if (!currentUser || currentUser.rol !== 'VIGILANTE') {
            showToast('Solo el vigilante puede registrar salidas', 'error');
            return;
        }
        const payload = {
            entradaId: formData.get('visitaId'),
            vigilanteId: currentUser.id
        };

        await apiRequest('/salidas', 'POST', payload);
        showToast('Salida registrada exitosamente', 'success');
        
        closeModal('modal-salida');
        loadSalidas();
        loadDashboardData(); // Update dashboard stats
        
    } catch (error) {
        showToast(error.message, 'error');
    }
}

async function deleteSalida(id) {
    if (currentUser && currentUser.rol !== 'ADMIN') {
        showToast('Solo el administrador puede eliminar salidas', 'error');
        return;
    }
    if (confirm('¿Está seguro de eliminar esta salida?')) {
        try {
            await apiRequest(`/salidas/${id}`, 'DELETE');
            showToast('Salida eliminada exitosamente', 'success');
            loadSalidas();
        } catch (error) {
            showToast('Error al eliminar salida', 'error');
        }
    }
}

// Usuarios Functions (Admin only)
async function loadUsuarios() {
    if (currentUser.rol !== 'ADMIN') {
        showToast('No tienes permisos para ver usuarios', 'error');
        return;
    }
    
    try {
        const usuarios = await apiRequest('/usuarios');
        const tbody = document.getElementById('usuarios-tbody');
        
        tbody.innerHTML = usuarios.map(usuario => `
            <tr>
                <td>${usuario.id}</td>
                <td>${usuario.nombre}</td>
                <td>${usuario.email}</td>
                <td><span class="role-badge" data-role="${usuario.rol}">${usuario.rol}</span></td>
                <td>${usuario.telefono || '-'}</td>
                <td><span class="status-badge ${usuario.activo ? 'status-active' : 'status-inactive'}">${usuario.activo ? 'ACTIVO' : 'INACTIVO'}</span></td>
                <td>${formatDateOnly(usuario.fechaRegistro)}</td>
                <td class="actions">
                    <button class="btn btn-sm btn-primary" onclick="editUsuario('${usuario.id}')">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="deleteUsuario('${usuario.id}')">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `).join('');
        
    } catch (error) {
        showToast('Error al cargar usuarios', 'error');
    }
}

function showUsuarioModal(usuario = null) {
    const modal = document.getElementById('modal-usuario');
    const form = document.getElementById('usuario-form');
    const title = document.getElementById('usuario-modal-title');
    
    if (usuario) {
        title.textContent = 'Editar Usuario';
        document.getElementById('usuario-id').value = usuario.id;
        document.getElementById('usuario-nombre').value = usuario.nombre;
        document.getElementById('usuario-email').value = usuario.email;
        document.getElementById('usuario-telefono').value = usuario.telefono || '';
        document.getElementById('usuario-rol').value = usuario.rol;
        document.getElementById('usuario-estado').value = usuario.estado;
        document.getElementById('usuario-password').required = false;
    } else {
        title.textContent = 'Nuevo Usuario';
        form.reset();
        document.getElementById('usuario-id').value = '';
        document.getElementById('usuario-password').required = true;
    }
    
    modal.classList.add('active');
}

async function saveUsuario(formData) {
    try {
        const usuarioData = {
            nombre: formData.get('nombre'),
            apellido: formData.get('apellido'),
            rol: formData.get('rol'),
            email: formData.get('email'),
            contrasena: formData.get('password'),
            apartamento: formData.get('apartamento') || '',
            activo: formData.get('estado') ? formData.get('estado') === 'ACTIVO' : true
        };
        
        // Remove password if empty (edit mode)
        if (!usuarioData.password) {
            delete usuarioData.password;
        }
        
        const usuarioId = document.getElementById('usuario-id').value;
        
        if (usuarioId) {
            await apiRequest(`/usuarios/${usuarioId}`, 'PUT', usuarioData);
            showToast('Usuario actualizado exitosamente', 'success');
        } else {
            await apiRequest('/usuarios', 'POST', usuarioData);
            showToast('Usuario creado exitosamente', 'success');
        }
        
        closeModal('modal-usuario');
        loadUsuarios();
        
    } catch (error) {
        showToast(error.message, 'error');
    }
}

async function editUsuario(id) {
    try {
        const usuario = await apiRequest(`/usuarios/${id}`);
        showUsuarioModal(usuario);
    } catch (error) {
        showToast('Error al cargar usuario', 'error');
    }
}

async function deleteUsuario(id) {
    if (confirm('¿Está seguro de eliminar este usuario?')) {
        try {
            await apiRequest(`/usuarios/${id}`, 'DELETE');
            showToast('Usuario eliminado exitosamente', 'success');
            loadUsuarios();
        } catch (error) {
            showToast('Error al eliminar usuario', 'error');
        }
    }
}

// Notificaciones Functions
async function loadNotificaciones() {
    try {
        const notificaciones = await apiRequest('/notificaciones/no-leidas');
        const container = document.getElementById('notificaciones-list');
        
        function getNotificationType(mensaje) {
            const m = (mensaje || '').toLowerCase();
            if (m.includes('ingresado') || m.includes('entrada')) return 'success';
            if (m.includes('salido') || m.includes('salida')) return 'warning';
            return 'info';
        }

        function getNotificationTitle(mensaje) {
            const m = (mensaje || '').toLowerCase();
            if (m.includes('ingresado') || m.includes('entrada')) return 'Entrada de visitante';
            if (m.includes('salido') || m.includes('salida')) return 'Salida de visitante';
            return 'Notificación';
        }

        container.innerHTML = (notificaciones || []).map(n => {
            const type = getNotificationType(n.mensaje);
            const title = getNotificationTitle(n.mensaje);
            const unreadClass = n.leida ? '' : 'unread';
            return `
            <div class="notification-item ${unreadClass} ${type}">
                <div class="notification-icon">
                    <i class="fas fa-bell"></i>
                </div>
                <div class="notification-content">
                    <h4>${title}</h4>
                    <p>${n.mensaje || ''}</p>
                </div>
                <div class="notification-time">${formatDate(n.fecha)}</div>
            </div>`;
        }).join('');
        
    } catch (error) {
        showToast('Error al cargar notificaciones', 'error');
    }
}

async function markNotificationsAsRead() {
    try {
        const pendientes = await apiRequest('/notificaciones/no-leidas');
        await Promise.all((pendientes || []).map(n => apiRequest(`/notificaciones/${n.id}/leer`, 'PATCH')));
        showToast('Notificaciones marcadas como leídas', 'success');
        loadNotificaciones();
        loadDashboardData(); // Update notification count
    } catch (error) {
        showToast('Error al marcar notificaciones', 'error');
    }
}

// Modal Functions
function closeModal(modalId) {
    document.getElementById(modalId).classList.remove('active');
}

function setupModalListeners() {
    // Close modals when clicking close button
    document.querySelectorAll('.modal-close, .modal-cancel').forEach(button => {
        button.addEventListener('click', function() {
            const modal = this.closest('.modal');
            modal.classList.remove('active');
        });
    });
    
    // Close modals when clicking outside
    document.querySelectorAll('.modal').forEach(modal => {
        modal.addEventListener('click', function(e) {
            if (e.target === this) {
                this.classList.remove('active');
            }
        });
    });
}

// Event Listeners
document.addEventListener('DOMContentLoaded', function() {
    // Check authentication
    checkAuth();
    
    // Setup modal listeners
    setupModalListeners();
    
    // Login form
    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const formData = new FormData(this);
            login(formData.get('email'), formData.get('password'));
        });
    }
    
    // Logout button
    document.getElementById('logout-btn').addEventListener('click', logout);
    
    // Navigation
    document.querySelectorAll('.nav-item').forEach(item => {
        item.addEventListener('click', function() {
            const section = this.getAttribute('data-section');
            if (section) {
                showSection(section);
            }
        });
    });
    
    // Modal buttons
    document.getElementById('btn-nueva-visita').addEventListener('click', () => showVisitaModal());
    document.getElementById('btn-nueva-entrada').addEventListener('click', () => {
        loadVisitasForSelect();
        document.getElementById('modal-entrada').classList.add('active');
    });
    document.getElementById('btn-nueva-salida').addEventListener('click', () => {
        loadVisitasForSalidaSelect();
        document.getElementById('modal-salida').classList.add('active');
    });
    document.getElementById('btn-nuevo-usuario').addEventListener('click', () => showUsuarioModal());
    document.getElementById('btn-marcar-leidas').addEventListener('click', markNotificationsAsRead);
    
    // Form submissions
    document.getElementById('visita-form').addEventListener('submit', function(e) {
        e.preventDefault();
        saveVisita(new FormData(this));
    });
    
    document.getElementById('entrada-form').addEventListener('submit', function(e) {
        e.preventDefault();
        saveEntrada(new FormData(this));
    });
    
    document.getElementById('salida-form').addEventListener('submit', function(e) {
        e.preventDefault();
        saveSalida(new FormData(this));
    });
    
    document.getElementById('usuario-form').addEventListener('submit', function(e) {
        e.preventDefault();
        saveUsuario(new FormData(this));
    });
    
    // Search functionality
    document.getElementById('search-visitas').addEventListener('input', function() {
        const searchTerm = this.value.toLowerCase();
        const rows = document.querySelectorAll('#visitas-tbody tr');
        
        rows.forEach(row => {
            const text = row.textContent.toLowerCase();
            row.style.display = text.includes(searchTerm) ? '' : 'none';
        });
    });
});

// Make functions available globally for onclick handlers
window.editVisita = editVisita;
window.deleteVisita = deleteVisita;
window.deleteEntrada = deleteEntrada;
window.deleteSalida = deleteSalida;
window.editUsuario = editUsuario;
window.deleteUsuario = deleteUsuario;
window.removeToast = removeToast;