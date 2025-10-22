# Contributing to Native Spring Boot + Angular User Management System

Thank you for your interest in contributing to this project! This document provides guidelines and information for contributors.

## 🤝 How to Contribute

### 1. Fork and Clone
```bash
# Fork the repository on GitHub, then clone your fork
git clone https://github.com/YOUR_USERNAME/native.git
cd native
```

### 2. Set Up Development Environment

#### Prerequisites
- Java 21 or higher
- Node.js 18+ and npm
- Docker and Docker Compose
- Maven 3.6+

#### Backend Setup
```bash
# Start the database
docker-compose up mariadb -d

# Run tests
./mvnw test

# Start the application
./mvnw spring-boot:run
```

#### Frontend Setup
```bash
cd front
npm install
npm start
```

### 3. Create a Branch
```bash
git checkout -b feature/your-feature-name
# or
git checkout -b bugfix/your-bugfix-name
# or
git checkout -b docs/your-documentation-update
```

### 4. Make Your Changes
- Write clean, readable code
- Follow existing code style and patterns
- Add tests for new functionality
- Update documentation as needed

### 5. Test Your Changes
```bash
# Backend tests
./mvnw test

# Frontend tests
cd front
npm test

# Integration tests with Docker
docker-compose up --build
```

### 6. Commit Your Changes
```bash
git add .
git commit -m "feat: add new user validation feature"
```

Use conventional commit messages:
- `feat:` for new features
- `fix:` for bug fixes
- `docs:` for documentation changes
- `style:` for formatting changes
- `refactor:` for code refactoring
- `test:` for adding tests
- `chore:` for maintenance tasks

### 7. Push and Create Pull Request
```bash
git push origin feature/your-feature-name
```

Then create a Pull Request on GitHub.

## 📋 Pull Request Guidelines

### Before Submitting
- [ ] Code follows the project's style guidelines
- [ ] All tests pass
- [ ] New features include tests
- [ ] Documentation is updated
- [ ] No merge conflicts
- [ ] Commit messages follow conventional format

### Pull Request Template
When creating a PR, please include:

1. **Description**: What does this PR do?
2. **Type of Change**: Bug fix, feature, documentation, etc.
3. **Testing**: How was this tested?
4. **Screenshots**: If applicable
5. **Checklist**: Confirm all requirements are met

## 🎯 Areas for Contribution

### Backend (Spring Boot)
- [ ] Add new API endpoints
- [ ] Improve error handling
- [ ] Add validation
- [ ] Enhance security features
- [ ] Optimize database queries
- [ ] Add caching mechanisms
- [ ] Improve native image compilation

### Frontend (Angular)
- [ ] Add new components
- [ ] Improve UI/UX
- [ ] Add form validation
- [ ] Implement error handling
- [ ] Add loading states
- [ ] Improve accessibility
- [ ] Add animations/transitions

### DevOps & Infrastructure
- [ ] Improve Docker configuration
- [ ] Add CI/CD pipelines
- [ ] Add monitoring and logging
- [ ] Optimize build processes
- [ ] Add deployment scripts

### Documentation
- [ ] Improve README
- [ ] Add API documentation
- [ ] Create tutorials
- [ ] Add code comments
- [ ] Create architecture diagrams

## 🐛 Bug Reports

When reporting bugs, please include:

1. **Environment**: OS, Java version, Node version
2. **Steps to Reproduce**: Clear, numbered steps
3. **Expected Behavior**: What should happen
4. **Actual Behavior**: What actually happens
5. **Screenshots**: If applicable
6. **Logs**: Relevant error messages or logs

## 💡 Feature Requests

When suggesting features:

1. **Use Case**: Why is this feature needed?
2. **Proposed Solution**: How should it work?
3. **Alternatives**: Other ways to solve the problem
4. **Additional Context**: Any other relevant information

## 🏗️ Code Style Guidelines

### Java (Backend)
- Follow Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc for public methods
- Keep methods small and focused
- Use proper exception handling

### TypeScript (Frontend)
- Follow Angular style guide
- Use meaningful component and service names
- Add JSDoc comments for public methods
- Use TypeScript strict mode
- Follow Angular best practices

### General
- Write self-documenting code
- Add comments for complex logic
- Keep functions/methods small
- Use consistent formatting
- Follow SOLID principles

## 🧪 Testing Guidelines

### Backend Tests
- Unit tests for services and repositories
- Integration tests for API endpoints
- Test edge cases and error conditions
- Aim for good test coverage

### Frontend Tests
- Unit tests for components and services
- Integration tests for user workflows
- Test accessibility features
- Test responsive design

## 📚 Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Angular Documentation](https://angular.io/docs)
- [GraalVM Native Image](https://www.graalvm.org/latest/reference-manual/native-image/)
- [Docker Documentation](https://docs.docker.com/)
- [Conventional Commits](https://www.conventionalcommits.org/)

## 🆘 Getting Help

- Check existing issues and discussions
- Ask questions in GitHub Discussions
- Review the documentation
- Check the API documentation at `/swagger-ui.html`

## 📝 License

By contributing to this project, you agree that your contributions will be licensed under the MIT License.

---

Thank you for contributing! 🎉
