# Microservices-FoodDelivery

## Docs
- See `docs/Microservices-Learning-Roadmap.md` for the 16-week roadmap.

## Local Database (MySQL)
- Requirements: Docker
- Start MySQL (three DBs: `user_db`, `product_db`, `order_db`):
  - `docker compose up -d`
- Stop: `docker compose down`

## Services (Step 1)
- Each service uses its own database (configured in `application.properties`).
- Ports:
  - `user-service`: 8081
  - `product-service`: 8082
  - `order-service`: 8083

### Build & Run (from repo root)
- Aggregate build: `./user-service/user-service/mvnw -q -DskipTests package && ./product-service/product-service/mvnw -q -DskipTests package && ./order-service/order-service/mvnw -q -DskipTests package`
- Run each service:
  - `./user-service/user-service/mvnw spring-boot:run`
  - `./product-service/product-service/mvnw spring-boot:run`
  - `./order-service/order-service/mvnw spring-boot:run`

## Notion Integration
- Files: `tools/notion/`
- Setup:
  - Copy `tools/notion/.env.example` to `.env` and set `NOTION_TOKEN`
  - Install deps: `pip3 install --user -r tools/notion/requirements.txt`
- Export page:
  - JSON: `python3 tools/notion/notion_export.py export --page-id <url-or-id> --format json --verbose`
  - Markdown: `python3 tools/notion/notion_export.py export --page-id <url-or-id> --format md --verbose`