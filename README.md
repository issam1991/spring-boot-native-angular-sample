# Native Spring Boot + Angular User Management System

A full-stack web application demonstrating modern Java development with Spring Boot Native Image compilation and Angular frontend. This project showcases a complete user management system with REST API, database integration, and a responsive web interface.

## 🚀 Features

### Backend (Spring Boot Native)
- **Native Image Compilation**: Ultra-fast startup times with GraalVM native image
- **RESTful API**: Complete CRUD operations for user management
- **Database Integration**: MariaDB with JPA/Hibernate
- **API Documentation**: Swagger/OpenAPI 3.0 integration
- **Docker Support**: Containerized deployment with Docker Compose
- **CORS Configuration**: Cross-origin resource sharing enabled

### Frontend (Angular)
- **Modern Angular**: Built with Angular 20+ and standalone components
- **Server-Side Rendering (SSR)**: Enhanced performance and SEO
- **Responsive Design**: Mobile-friendly user interface
- **TypeScript**: Type-safe development
- **HTTP Client**: RESTful API integration

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Angular 20+   │    │  Spring Boot 3  │    │    MariaDB      │
│   Frontend      │◄──►│   Native Image  │◄──►│    Database     │
│   (Port 4200)   │    │   (Port 8080)   │    │   (Port 3306)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 🛠️ Technology Stack

### Backend
- **Java 21** - Latest LTS version
- **Spring Boot 3.4.0** - Modern Spring framework
- **Spring Data JPA** - Database abstraction layer
- **MariaDB** - Relational database
- **GraalVM Native Image** - Native compilation
- **SpringDoc OpenAPI** - API documentation
- **Maven** - Build tool

### Frontend
- **Angular 20.3.0** - Modern web framework
- **TypeScript 5.9.2** - Type-safe JavaScript
- **Angular SSR** - Server-side rendering
- **RxJS** - Reactive programming
- **Express** - SSR server

### DevOps
- **Docker & Docker Compose** - Containerization
- **Maven** - Java build tool
- **npm** - Node.js package manager

## 📋 Prerequisites

- **Java 21** or higher
- **Node.js 18+** and npm
- **Docker** and Docker Compose
- **Maven 3.6+**

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/issam1991/spring-boot-native-angular-sample
cd native
```

### 2. Start the Database
```bash
docker-compose up mariadb -d
```

### 3. Build and Run Backend
```bash
# Build the application
./mvnw clean package

# Run in JVM mode
./mvnw spring-boot:run

# Or build native image (requires GraalVM)
./mvnw -Pnative native:compile
./target/native-native
```

### 4. Build and Run Frontend
```bash
cd front
npm install
npm start
```

### 5. Access the Application
- **Frontend**: http://localhost:4200
- **Backend API**: http://localhost:8080
- **API Documentation**: http://localhost:8080/swagger-ui.html

## 🐳 Docker Deployment

### Full Stack with Docker Compose
```bash
# Build the native image first
./mvnw -Pnative native:compile

# Start all services
docker-compose up -d
```

This will start:
- MariaDB database on port 3306
- Spring Boot application on port 8080
- Frontend can be served separately or built into the backend

## 📚 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Welcome message |
| GET | `/api/status` | Application status |
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/count` | Get user count |
| POST | `/api/users` | Create new user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

### Example API Usage

```bash
# Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "John Doe", "email": "john@example.com"}'

# Get all users
curl http://localhost:8080/api/users

# Update a user
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "Jane Doe", "email": "jane@example.com"}'
```

## 🗄️ Database Schema

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);
```

## 🔧 Configuration

### Application Properties
- Database connection: `src/main/resources/application.properties`
- Native image configuration: `src/main/resources/application-native.properties`
- Docker environment variables in `docker-compose.yml`

### Environment Variables
- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password

## 🧪 Testing

### Backend Tests
```bash
./mvnw test
```

### Frontend Tests
```bash
cd front
npm test
```

## 📦 Build Commands

### Backend
```bash
# Clean and compile
./mvnw clean compile

# Run tests
./mvnw test

# Package JAR
./mvnw package

# Build native image
./mvnw -Pnative native:compile

# Run with Docker
./mvnw spring-boot:build-image
```

### Frontend
```bash
cd front

# Install dependencies
npm install

# Development server
npm start

# Build for production
npm run build

# Run tests
npm test

# Build with SSR
npm run build
npm run serve:ssr:user-management
```

## 🚀 Performance Benefits

### Native Image Advantages
- **Ultra-fast startup**: ~50ms vs ~3-5 seconds for JVM
- **Lower memory footprint**: ~50MB vs ~200MB+ for JVM
- **Better resource utilization**: Optimized for cloud deployments
- **Instant scaling**: Perfect for serverless and microservices

## 🔍 Monitoring and Observability

- **Health Checks**: Built-in Spring Boot Actuator endpoints
- **API Documentation**: Interactive Swagger UI
- **Database Monitoring**: Connection pool metrics
- **Logging**: Structured logging with configurable levels

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Angular team for the modern frontend framework
- GraalVM team for native image compilation
- MariaDB team for the reliable database

## 📞 Support

If you have any questions or need help, please:
- Open an issue on GitHub
- Check the documentation
- Review the API documentation at `/swagger-ui.html`

---

**Happy Coding! 🎉**
