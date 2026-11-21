# Informe del laboratorio — Agenda de reservas

## Objetivo

Construir una aplicación web para administrar reservas. La solución conecta un frontend React con una API Spring Boot y persiste los registros en PostgreSQL.

## Funcionalidades implementadas

- Crear una reserva con nombre del cliente, fecha, hora y servicio.
- Consultar todas las reservas en orden cronológico.
- Cancelar una reserva sin borrar su historial.
- Evitar que se asignen dos reservas activas a la misma fecha y hora.
- Validar los campos y rechazar fechas anteriores al día actual.
- Informar errores con códigos HTTP y mensajes legibles para el usuario.
- Explorar y ejecutar los endpoints desde Swagger UI.

## Diseño

El backend organiza el código en entidad, repositorio, servicio, controlador, DTO y manejo centralizado de excepciones. Flyway crea y versiona el esquema de base de datos. Además de comprobar la disponibilidad desde el servicio, un índice único parcial en PostgreSQL protege la misma regla ante solicitudes simultáneas.

El frontend separa las llamadas HTTP del componente de página. El formulario valida los datos requeridos, la tabla muestra el estado de cada reserva y los avisos tipo toast comunican el resultado de las operaciones.

## Recorrido de demostración

1. Iniciar PostgreSQL y los dos servidores según los pasos de `README.md`.
2. Abrir `http://localhost:5173` y crear una reserva para una fecha futura.
3. Comprobar que aparece en la tabla y que el número de reservas activas aumenta.
4. Intentar reservar nuevamente la misma fecha y hora; la API debe responder `409 Conflict` y la interfaz mostrar el mensaje de conflicto.
5. Cancelar la reserva desde la tabla y comprobar que su estado cambia a cancelada.
6. Abrir `http://localhost:8080/swagger-ui.html` para explorar la API.

## Entrega

El código fuente y las instrucciones de ejecución están en este proyecto. El repositorio GitHub no se ha publicado; puedes crear un repositorio propio y subir esta carpeta desde tu cuenta. La guía también incluye al final un ejercicio propuesto independiente sobre transferencias bancarias; el alcance de esta entrega implementa el laboratorio de reservas.
