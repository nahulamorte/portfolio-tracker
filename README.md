# Portfolio Tracker

Aplicación full stack para el seguimiento de un portfolio de inversiones (acciones y criptomonedas), con cálculo de ganancia/pérdida en base a precios de mercado en tiempo real.

Proyecto desarrollado con fines de aprendizaje y portfolio técnico, con foco en buenas prácticas de arquitectura, seguridad y testing sobre un dominio fintech.

---

## Stack técnico

| Capa | Tecnología                                 |
|---|--------------------------------------------|
| Backend | Spring Boot 4.1.1, Java 21                 |
| Persistencia | Spring Data JPA + Hibernate, PostgreSQL 16 |
| Migraciones | Flyway                                     |
| Seguridad | Spring Security + JWT (jjwt)               |
| Frontend | React + TypeScript *(en desarrollo)*       |
| Contenedores | Docker Compose                             |
| Testing de API | Postman + SwaggerW                         |

---

## Esquema de la base de datos

![Esquema de la base de datos](docs/db-schema.png)

*(Diagrama generado con Redgate Data Modeler. Ver `docs/db-schema.png` — reemplazar con la versión más actualizada del modelo si cambia.)*


---

## Endpoints disponibles

### Autenticación (`/v1/api/auth`)

| Método | Endpoint | Descripción | Acceso |
|---|---|---|---|
| `POST` | `/v1/api/auth/register` | Registra un nuevo usuario (crea `UserApp`, `UserAuth` y un `Portfolio` inicial en balance 0) | Público |
| `POST` | `/v1/api/auth/login` | Autentica un usuario y devuelve un JWT | Público |
| `GET` | `/v1/api/auth/me` | Devuelve el usuario autenticado actual | Requiere token |


```

Para el resto de las rutas protegidas, incluir el token en el header:
```
Authorization: Bearer <token>
```

### Portfolio (`/v1/api/portfolio`)

| Método | Endpoint | Descripción | Acceso |
|---|---|---|---|
| `GET` | `/v1/api/portfolio` | Devuelve el portfolio del usuario autenticado (balance, datos del usuario, cantidad de activos con posición) | Requiere token |

El usuario se resuelve siempre a partir del token (`SecurityContext`), nunca de un parámetro en la URL — no existe forma de consultar el portfolio de otro usuario.

**Respuesta:**
```json
{
  "balance": 0.0000,
  "user": {
    "userId": 5,
    "name": "Juan Pérez",
    "dateBirth": "1995-03-14",
    "createdAt": "2026-09-04T23:53:48.097439",
    "updatedAt": "2026-09-04T23:53:48.097439"
  },
  "createdAt": "2026-09-04T23:53:48.388616",
  "countAssets": 1
}
```

> `balance` no refleja el valor de mercado del portfolio — ese cálculo se implementa en la Épica 4, a partir de las transacciones y el precio actual de cada activo. Es un campo reservado para uso futuro.

### Transacciones (`/v1/api/portfolio/transactions`)

| Método | Endpoint | Descripción | Acceso |
|---|---|---|---|
| `POST` | `/v1/api/portfolio/transactions` | Registra una compra (`BUY`) o venta (`SELL`) sobre el portfolio del usuario autenticado | Requiere token |

Si el `ticker` no existe todavía, se crea automáticamente (alta implícita) — en ese caso, `assetType` (`STOCK` o `CRYPTO`) es obligatorio en el request. Si el ticker ya existe, `assetType` puede omitirse.

**Ejemplo — Compra:**
```http
POST /v1/api/portfolio/transactions
Authorization: Bearer <token>
Content-Type: application/json

{
  "quantity": 10,
  "price": 150.50,
  "transactionType": "BUY",
  "ticker": "AAPL",
  "assetType": "STOCK"
}
```

**Respuesta:**
```json
{
  "transactionId": 1,
  "ticker": "AAPL",
  "quantity": 10.00000000,
  "price": 150.50000000,
  "totalPrice": 1505.0000000000000000,
  "transactionType": "BUY",
  "transactionDate": "2026-09-05T14:21:08.057298"
}
```

**Reglas de negocio:**
- Una venta (`SELL`) no puede superar la cantidad neta disponible del activo (compras acumuladas menos ventas previas) — se valida en cada operación y rechaza con `422` si no alcanza.
- Vender exactamente la cantidad disponible es un caso válido.
- No se puede vender un activo que nunca se compró en el portfolio.

> Documentación interactiva completa disponible vía Swagger en `/swagger-ui.html` *(pendiente de configurar)*.

---


## Estado del proyecto

Desarrollo organizado en sprints semanales (3hs/día, L-V).

- [x] **Épica 1 — Autenticación y usuarios**
  Registro, login, JWT, hasheo de passwords, manejo de excepciones (401/403/409/400), filtro de autenticación con validación de tokens (expirados, malformados, firma inválida).
- [x] **Épica 2 — Gestión de posiciones**
  Lectura de `Portfolio` (con conteo de activos), alta implícita de `Asset` con validación de tipo, registro de `Transaction` (compra/venta) con validación de cantidad neta disponible. Persistencia de `TransactionType` vía `AttributeConverter` custom (enum ↔ `CHAR(1)`).
- [ ] **Épica 3 — Integración de precios externos**
  Consumo de API de precios (acciones/cripto), actualización periódica.
- [ ] **Épica 4 — Cálculo y visualización**
  Ganancia/pérdida, dashboard, gráficos.
- [ ] **Épica 5 — Calidad y despliegue**
  Tests automatizados, triggers de auditoría en PL/pgSQL, Swagger, deploy.

---

## Arquitectura del backend

Organización híbrida: capas técnicas generales (`controller/`, `service/`, `repository/`, `dto/`, `mapper/`, `entity/`) más un módulo por feature para autenticación (`auth/`), dado su alcance transversal y autocontenido.

```
src/main/java/org/portfoliotracker/portfolio/
├── auth/           # UserAuth, JWT, filtro, service y controller de autenticación
├── config/         # SecurityConfig, ApplicationConfig (beans de infraestructura)
├── controller/
├── service/
├── repository/
├── dto/
│   ├── request/
│   └── response/
├── mapper/
├── entity/
│   ├── UserApp.java
│   ├── Portfolio.java
│   ├── Asset.java
│   ├── AssetType.java
│   ├── Transaction.java
│   ├── TransactionType.java
│   └── TransactionTypeConverter.java   # persiste el enum como CHAR(1)
└── exception/      # GlobalExceptionHandler y excepciones genéricas
```

---

## Seguridad

- Passwords hasheadas con BCrypt, nunca almacenadas en texto plano.
- Autenticación stateless vía JWT (sin sesiones ni cookies).
- Variables sensibles gestionadas por entorno (`.env`, nunca commiteado — ver `.gitignore`).
- Mensajes de error de login genéricos, para no filtrar si un username existe o no.