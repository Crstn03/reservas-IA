# Agenda TdeA — Laboratorio Spring Boot y React

Aplicación full stack para crear, consultar y cancelar reservas. El backend expone una API REST con Spring Boot, persiste en PostgreSQL y valida que no haya dos reservas activas en el mismo horario. El frontend está construido con React y Vite.

## Tecnologías

- Java 25 y Spring Boot 4.0.7
- Spring Data JPA, Bean Validation, Flyway y PostgreSQL
- OpenAPI / Swagger UI
- React 19, Vite y Axios
- Docker Compose para ejecutar PostgreSQL localmente

## Requisitos

- JDK 25
- Maven 3.9 o superior
- Node.js 24 o superior y npm
- Docker Desktop (o una instancia de PostgreSQL 17 accesible)

## Ejecución local

1. Inicia la base de datos desde la raíz del proyecto:

   ```bash
   docker compose up -d database
   ```

2. Inicia la API:

   ```bash
   cd backend
   mvn spring-boot:run
   ```

   La API queda en `http://localhost:8080`; Flyway crea la tabla al arrancar. La configuración local predeterminada está en `backend/src/main/resources/application.properties`. Para otra instancia, define `DB_URL`, `DB_USERNAME` y `DB_PASSWORD`.

3. En otra terminal, inicia React:

   ```bash
   cd frontend
   npm install
   npm run dev
   ```

   Abre `http://localhost:5173`. Si la API está en otra dirección, copia `frontend/.env.example` como `frontend/.env` y ajusta `VITE_API_URL`. Para un origen diferente, configura `FRONTEND_ORIGIN` en el backend.

Para detener la base de datos usa `docker compose down`. Los datos permanecen en el volumen `reservation-data`; para borrarlos localmente, `docker compose down -v` elimina ese volumen.

## API

La documentación interactiva está en `http://localhost:8080/swagger-ui.html`.

| Método | Ruta | Resultado |
|---|---|---|
| `GET` | `/reservas` | Lista las reservas, ordenadas por fecha y hora |
| `POST` | `/reservas` | Crea una reserva; responde `201` |
| `DELETE` | `/reservas/{id}` | Cancela una reserva existente |

Ejemplo de creación:

```json
{
  "customerName": "Diego",
  "reservationDate": "2026-12-10",
  "reservationTime": "13:00:00",
  "service": "Capacitación"
}
```

El endpoint `POST` exige los cuatro campos, no acepta fechas pasadas y responde `409 Conflict` cuando ya existe una reserva activa en el horario. Las respuestas de error tienen `timestamp`, `status`, `error`, `message`, `path` y, para validaciones, un objeto `fields`.

## Estructura

```text
backend/   API, reglas de negocio, manejo de errores y migraciones SQL
frontend/  formulario reactivo, agenda, cancelación y avisos tipo toast
compose.yaml
```

El ejercicio propuesto de transferencias bancarias que aparece al final de la guía es una actividad adicional y separada del sistema de reservas descrito en el laboratorio.
