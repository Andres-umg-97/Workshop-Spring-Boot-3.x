🚀 Workshop Spring Boot 3.x + JDK 17
📌 Descripción

Este proyecto es una API REST desarrollada con Spring Boot 3.x como parte de un laboratorio práctico.
Incluye validaciones, manejo de errores, documentación con Swagger/OpenAPI y un endpoint de negocio.

🛠️ Tecnologías utilizadas
Java 17
Spring Boot 3.x
Maven
Jakarta Validation
Swagger / OpenAPI

⚙️ Ejecución del proyecto
1. Clonar repositorio
git clone <TU_REPOSITORIO>
cd springboot-api-demo
2. Ejecutar aplicación
mvn spring-boot:run

🌐 Endpoints disponibles
✔ Health Check
GET /api/v1

Respuesta:

{
  "estado": "ok",
  "mensaje": "Workshop Spring Boot activo"
}
✔ Saludos (GET)
GET /api/v1/saludos?nombre=Ana
✔ Saludos (POST)
POST /api/v1/saludos

Body:

{
  "nombre": "Ana"
}

Validación:

Si el nombre está vacío → error 400

💰 Endpoint desafiante: Simulador de préstamo
✔ Endpoint
POST /api/v1/simulaciones/prestamo
✔ Request
{
  "monto": 10000,
  "tasaAnual": 12,
  "meses": 12
}
✔ Response
{
  "cuotaMensual": 888.49,
  "interesTotal": 661.88,
  "totalPagar": 10661.88
}

📊 Fórmula utilizada
cuota = P * (r * (1 + r)^n) / ((1 + r)^n - 1)

Donde:

P = monto
r = tasa mensual
n = número de meses
⚠️ Manejo de errores

Se implementa un manejador global:

VALIDATION_ERROR → errores de validación
BUSINESS_RULE_ERROR → reglas de negocio

📄 Documentación (Swagger)

Disponible en:

http://localhost:8080/swagger-ui/index.html

También:

http://localhost:8080/v3/api-docs
