# 📏 Reglas de Calidad y Buenas Prácticas - CrediYa

Este documento es la **ley** del proyecto. Todo código debe cumplir con estas reglas.

## 🏗️ 1. Arquitectura (Clean Architecture)
- **Aislamiento del Dominio:** El paquete domain NUNCA debe importar clases de infrastructure ni de Spring Boot.
- **Inyección de Dependencias:** Usar constructores, nunca @Autowired en los campos.
- **DTOs:** No exponer entidades de dominio en los controladores. Usar DTOs para peticiones y respuestas.

## 🔒 2. Seguridad (DevSecOps)
- **Contraseñas:** NUNCA guardar contraseñas en texto plano. Usar BCryptPasswordEncoder.
- **Secretos:** NO subir archivos .env o pplication.properties con contraseñas reales. Usar variables de entorno.
- **JWT:** Todos los endpoints (excepto Login y Registro) deben requerir un token válido.
- **CORS:** Configurar explícitamente los orígenes permitidos.

## 🎨 3. Estilo y UI (Frontend)
- **Nebular/Tailwind:** Usar los componentes de Nebular para mantener la consistencia visual.
- **Colores:** Azul principal #3366ff, Rojo peligro #dc3545, Verde éxito #28a745.
- **Responsive:** Todas las vistas deben funcionar en móvil y escritorio.

## ✅ 4. Buenas Prácticas de Código
- **Nombres:** Variables en camelCase (ej. montoSolicitud), constantes en UPPER_SNAKE_CASE.
- **Métodos:** No deben superar las 20 líneas. Si es más largo, refactorizar.
- **Tests:** Todo nuevo caso de uso debe tener su test unitario (JUnit + Mockito).
- **Logs:** Usar SLF4J, nunca System.out.println() (excepto en prototipos).
- **Git:** Commits semánticos (ej. eat:, ix:, 	est:).

## 🚫 5. Prohibiciones
- No usar ar en Java (usar tipos explícitos).
- No usar ny en TypeScript (usar interfaces o tipos).
- No dejar código comentado ni TODO sin resolver.
- No hacer git push directo a main (usar Pull Requests).