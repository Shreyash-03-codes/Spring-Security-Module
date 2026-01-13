# Spring Security Module

A hands-on playground for learning and experimenting with Spring Security.  
This repository is designed to help beginners and intermediate developers understand Spring Security in depth through practical examples.

---

## Topics Covered

This repository demonstrates practical implementations of:

- Spring Security Basics – Core concepts and configuration
- Filter Chain & Filters – How requests are processed using security filters
- Username & Password Authentication – Form login and custom authentication
- CSRF (Cross-Site Request Forgery) – Enabling and disabling CSRF protection
- CORS (Cross-Origin Resource Sharing) – Configuring CORS in Spring Security
- JWT (JSON Web Tokens) – Secure token-based authentication
- Refresh Tokens – Token renewal and session continuation
- Session Management – Controlling user sessions, concurrency, and expiration
- OAuth2 / Social Login – Integration with Google, Facebook, or other providers
- Exception Handling – Custom handling of authentication and authorization exceptions
- Role-Based Authorization – Access control using roles and authorities
- Advanced Security Configurations – Combining multiple security features in real-world scenarios

---

## Getting Started

### Prerequisites

- Java 17+
- Spring Boot 3+
- Maven or Gradle
- IDE like IntelliJ IDEA or Eclipse

### Clone the Repository

```bash
git clone https://github.com/Shreyash-03-codes/Spring-Security-Module.git
cd spring-security-module
```

### Build and Run

```bash
mvn clean install
mvn spring-boot:run
```


The project runs on `http://localhost:8080` by default.

---

## Project Structure

```bash

src
├── main
│   ├── java
│   │   └── com.example.security
│   │       ├── config        # Security configuration, filter chain, CORS/CSRF setup
│   │       ├── controller    # REST controllers
│   │       ├── exception     # Custom exception handling
│   │       ├── model         # User, Role, JWT models
│   │       ├── repository    # Spring Data JPA repositories
│   │       └── service       # Authentication, JWT, OAuth2 services
└── resources
    ├── application.properties # DB, JWT, and OAuth2 configurations

---

```

## Learning Outcomes

After working with this repository, you will be able to:

- Configure Spring Security for different authentication mechanisms
- Implement JWT authentication with refresh tokens
- Secure REST APIs with role-based access control
- Handle CSRF and CORS in a Spring Boot application
- Integrate OAuth2 social login
- Customize the filter chain and handle security exceptions

---

## Contributing

Contributions are welcome! Feel free to:

- Add new examples or security features
- Improve existing configurations
- Report issues or request features

---

## License

This project is licensed under the MIT License. See the LICENSE file for details.

---

## Author

Shreyash Gurav – Developer & Learner of Spring Security  
GitHub: https://github.com/Shreyash-03-codes

