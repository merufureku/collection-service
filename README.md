# Collection Service

Lets users save fragrances to their personal collection. Think of it like a "my fragrances" list. This data feeds into the recommendation engine for collaborative filtering.

## Tech Stack

- Spring Boot 3.x
- Spring Security
- PostgreSQL
- Gradle
- Docker

## Endpoints

### User Endpoints (requires auth)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/collections` | Get your collection |
| POST | `/collections/{fragranceId}` | Add fragrance to collection |
| DELETE | `/collections/{fragranceId}` | Remove from collection |

### Internal (service-to-service)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/internal/collections/{userId}` | Get user's collection by ID |
| POST | `/internal/collections/batch` | Find collections containing specific fragrances |

## How It Works

Pretty simple - users can add or remove fragrances from their collection. The service prevents duplicates automatically.

Response example:
```json
{
  "status": 200,
  "message": "User collection fetched successfully",
  "data": {
    "userId": 1,
    "fragrances": [
      {
        "id": 49,
        "name": "Baccarat Rouge 540",
        "brand": "Maison Francis Kurkdjian",
        "imageUrl": "https://..."
      }
    ]
  }
}
```

## Why This Matters for Recommendations

The collection data is used as **implicit feedback** for the recommendation engine:
- Content-based filtering analyzes the notes from collected fragrances
- Collaborative filtering finds users with similar collections

The internal batch endpoint lets the Recommendation Service find all users who have collected specific fragrances - this is how it finds "similar users" for CF.

## Environment Variables

```
DB_URL=jdbc:postgresql://localhost:5432/your_db
DB_USERNAME=your_username
DB_PASSWORD=your_password
ACCESS_SECRET=base64_encoded_key
INTERNAL_COLLECTION_SECRET=internal_service_key
```

## Running Locally

```bash
./gradlew bootRun
```

Swagger UI: `http://localhost:8083/api/collection-service/swagger-ui`

## Docker

```bash
docker build -t collection-service .
docker run -p 8083:8083 --env-file .env collection-service
```

## Database Schema

```sql
CREATE TABLE collections (
    id BIGSERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    fragrance_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, fragrance_id)
);
```

## Project Structure

```
src/main/java/.../collection_service/
├── config/          # Security, JWT filter
├── controller/      # User and Internal endpoints
├── dao/             # Collections entity
├── dto/             # Request/response objects
├── helper/          # Validation, token helpers
├── services/        # Business logic
└── utilities/       # Token utilities
```

## Related Services

- [Auth Service](../auth-service) - Authentication
- [Fragrance Service](../fragrance-service) - Perfume catalog
- [Review Service](../review-service) - Reviews and ratings
- [Recommendation Service](../recommendation-service) - Uses collections for recommendations
